package com.mycompany.consultoriomedico6;

import java.security.*;
import java.util.Base64;
import java.util.UUID;

public class Utils {
    public static String randomSalt(){
        return UUID.randomUUID().toString().replace("-", "");
    }
    public static String hashPassword(String password, String salt){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((salt + password).getBytes("UTF-8"));
            byte[] digest = md.digest();
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
