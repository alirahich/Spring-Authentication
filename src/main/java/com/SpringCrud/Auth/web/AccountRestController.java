package com.SpringCrud.Auth.web;

import com.SpringCrud.Auth.JWTUtil;
import com.SpringCrud.Auth.entities.AppRole;
import com.SpringCrud.Auth.entities.AppUser;
import com.SpringCrud.Auth.repo.AppRoleRepository;
import com.SpringCrud.Auth.service.AccountService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {
    //methode qui retourne liste des utilisateurs
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppRoleRepository appRoleRepository;

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers() {
        return accountService.listUsers();

    }

    @PostMapping(path = "/register")
    public AppUser saveUser(@RequestBody AppUser appUser) {
        AppUser user= accountService.addNewUser(appUser);
        accountService.addRoleToUser(user.getUsername(),"User");
        return user;
    }

    @PostMapping(path = "/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole saveRole(@RequestBody AppRole appRole) {
        return accountService.addNewRole(appRole);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRolename());

    }

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if (authToken != null && authToken.startsWith(JWTUtil.PREFIX)) {
            try {
                String jwt = authToken.substring(7);//cad je ignore les 7 premier cara "bearer "
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);//il faut utiliser meme secret dans JWTautenti
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = accountService.loadUserByUsername(username);
                String jwtAccessToken = JWT.create().withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", appUser.getAppRoles()
                                .stream().map(r -> r.getRole()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);


            } catch (Exception e) {
                throw e;
            }

        } else {
            throw new RuntimeException("Refresh Token required !!");
        }
    }

    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal) {
        return accountService.loadUserByUsername(principal.getName());
    }

    @GetMapping(path = "/role/{role}")
        public AppRole getRole(@PathVariable String role) {
            AppRole appRole =  appRoleRepository.getByRole(role);
            System.out.println(appRole);
            return appRole;
        }
}
@Data
class RoleUserForm{
    private String username;
    private String rolename;
}
