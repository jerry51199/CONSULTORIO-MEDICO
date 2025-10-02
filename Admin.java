package com.mycompany.consultoriomedico6;

public class Admin {
    private final String id;
    private final String username;
    private final String salt;
    private final String passHash;

    public Admin(String id, String username, String salt, String passHash){
        this.id=id; this.username=username; this.salt=salt; this.passHash=passHash;
    }
    public String getId(){return id;}
    public String getUsername(){return username;}
    public String getSalt(){return salt;}
    public String getPassHash(){return passHash;}
    public String toCsv(){ return String.join(",", id, username, salt, passHash); }
    public static Admin fromCsv(String line){
        String[] p = line.split("(?<!\\\\),",4);
        return new Admin(p[0], p[1], p[2], p[3]);
    }
    public static Admin create(String username, String plainPassword){
        String salt = Utils.randomSalt();
        String hash = Utils.hashPassword(plainPassword, salt);
        return new Admin(java.util.UUID.randomUUID().toString(), username, salt, hash);
    }
}
