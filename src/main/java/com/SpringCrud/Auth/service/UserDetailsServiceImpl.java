package com.SpringCrud.Auth.service;

import com.SpringCrud.Auth.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= accountService.loadUserByUsername(username);

        Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.getAppRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        });
        return new User(appUser.getUsername(),appUser.getPassword(),authorities);
    }
}
