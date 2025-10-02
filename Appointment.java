package com.mycompany.consultoriomedico6;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private final String id;
    private final String doctorId;
    private final String patientId;
    private final LocalDateTime dateTime;
    private final String motive;

    public Appointment(String id, String doctorId, String patientId, LocalDateTime dateTime, String motive){
        this.id = id; this.doctorId = doctorId; this.patientId = patientId; this.dateTime = dateTime; this.motive = motive;
    }
    public String getId(){return id;}
    public String getDoctorId(){return doctorId;}
    public String getPatientId(){return patientId;}
    public LocalDateTime getDateTime(){return dateTime;}
    public String getMotive(){return motive;}
    public String toCsv(){
        return String.join(",", escape(id), escape(doctorId), escape(patientId), dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), escape(motive));
    }
    public static Appointment fromCsv(String line){
        String[] p = line.split("(?<!\\\\),",5);
        for (int i=0;i<p.length;i++) p[i]=p[i].replace("\\,", ",");
        LocalDateTime dt = LocalDateTime.parse(p[3]);
        return new Appointment(p[0], p[1], p[2], dt, p[4]);
    }
    private static String escape(String s){ return s.replace(",", "\\,"); }
}
