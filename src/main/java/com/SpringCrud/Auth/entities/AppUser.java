package com.SpringCrud.Auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data @AllArgsConstructor@NoArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    //cad si on a change un objet user a partir de DB il  va
    // charger auto les roles de utilisateur AppRole
    // NB il est preferable de initialise avec new array list
    private Collection<AppRole> appRoles = new ArrayList<>();
}
