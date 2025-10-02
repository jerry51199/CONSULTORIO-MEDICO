package com.mycompany.consultoriomedico6;

import java.util.*;

public class AuthService {
    private final Repos repos;
    public AuthService(Repos repos){ this.repos = repos; }

    public boolean authenticate(String username, String plainPassword){
        List<Admin> admins = repos.listAdmins();
        for (Admin a : admins){
            if (a.getUsername().equals(username)){
                String hashed = Utils.hashPassword(plainPassword, a.getSalt());
                return hashed.equals(a.getPassHash());
            }
        }
        return false;
    }
}
