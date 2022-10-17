package com.SpringCrud.Auth.service;



import com.SpringCrud.Auth.entities.AppRole;
import com.SpringCrud.Auth.entities.AppUser;

import java.util.List;
//cette interface permet d'eclare les methodes que j'ai besoin
public interface AccountService  {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    //void add role permer d'ajoute un role a un user
    void addRoleToUser(String username,String roleName);
    //permet de retourne un utilisateur
    AppUser loadUserByUsername(String username);
    //permet de retourner une liste de tout les utilisateur
    List<AppUser> listUsers();

}
