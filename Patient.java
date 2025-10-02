package com.mycompany.consultoriomedico6;

public class Patient {
    private final String id;
    private final String fullName;
    public Patient(String id, String fullName){ this.id = id; this.fullName = fullName; }
    public String getId(){return id;}
    public String getFullName(){return fullName;}
    public String toCsv(){ return String.join(",", escape(id), escape(fullName)); }
    public static Patient fromCsv(String line){
        String[] p = line.split("(?<!\\\\),", 2);
        p[1] = p[1].replace("\\,", ",");
        return new Patient(p[0], p[1]);
    }
    private static String escape(String s){ return s.replace(",", "\\,"); }
}
