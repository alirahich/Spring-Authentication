package com.SpringCrud.Auth.service;

import com.SpringCrud.Auth.entities.AppRole;
import com.SpringCrud.Auth.entities.AppUser;
import com.SpringCrud.Auth.repo.AppRoleRepository;
import com.SpringCrud.Auth.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(AppUser appUser) {
        //catte methode permet d'ajoute des users ces pour ce la je dois encodee mdp
        String pw=appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {

        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.getByRole(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
//pour charger un utilisateur avec user name
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
//pour retourne la liste de tous les utilisateur
        return appUserRepository.findAll();
    }
}
