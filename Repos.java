package com.mycompany.consultoriomedico6;

import java.nio.file.*;
import java.util.*;
import java.io.*;

public class Repos {
    private final Path dataDir;
    private final Path adminsFile, doctorsFile, patientsFile, appointmentsFile;

    public Repos(Path dataDir){
        this.dataDir = dataDir;
        adminsFile = dataDir.resolve("admins.csv");
        doctorsFile = dataDir.resolve("doctors.csv");
        patientsFile = dataDir.resolve("patients.csv");
        appointmentsFile = dataDir.resolve("appointments.csv");
        ensureFiles();
    }
    private void ensureFiles(){
        try {
            if (!Files.exists(adminsFile)) Files.write(adminsFile, Arrays.asList("id,username,salt,hash"), StandardOpenOption.CREATE);
            if (!Files.exists(doctorsFile)) Files.write(doctorsFile, Arrays.asList("id,fullName,specialty"), StandardOpenOption.CREATE);
            if (!Files.exists(patientsFile)) Files.write(patientsFile, Arrays.asList("id,fullName"), StandardOpenOption.CREATE);
            if (!Files.exists(appointmentsFile)) Files.write(appointmentsFile, Arrays.asList("id,doctorId,patientId,datetime,motive"), StandardOpenOption.CREATE);
        } catch (Exception e) {
            System.err.println("Error creando archivos: " + e.getMessage());
        }
    }

    // Admins
    public synchronized List<Admin> listAdmins(){
        List<Admin> out = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(adminsFile);
            for (int i=1;i<lines.size();i++){
                String l = lines.get(i).trim();
                if (!l.isEmpty()) out.add(Admin.fromCsv(l));
            }
        } catch (IOException e){ System.err.println("No se pudo leer admins: " + e.getMessage()); }
        return out;
    }
    public synchronized void addAdmin(Admin a){
        try {
            Files.write(adminsFile, Arrays.asList(a.toCsv()), StandardOpenOption.APPEND);
        } catch (IOException e){ System.err.println("No se pudo escribir admin: " + e.getMessage()); }
    }

    // Doctors
    public synchronized void addDoctor(Doctor d){
        try { Files.write(doctorsFile, Arrays.asList(d.toCsv()), StandardOpenOption.APPEND); }
        catch (IOException e){ System.err.println("No se pudo escribir doctor: " + e.getMessage()); }
    }
    public synchronized List<Doctor> listDoctors(){
        List<Doctor> out = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(doctorsFile);
            for (int i=1;i<lines.size();i++){
                String l = lines.get(i).trim();
                if (!l.isEmpty()) out.add(Doctor.fromCsv(l));
            }
        } catch (IOException e){ System.err.println("No se pudo leer doctors: " + e.getMessage()); }
        return out;
    }

    // Patients
    public synchronized void addPatient(Patient p){
        try { Files.write(patientsFile, Arrays.asList(p.toCsv()), StandardOpenOption.APPEND); }
        catch (IOException e){ System.err.println("No se pudo escribir paciente: " + e.getMessage()); }
    }
    public synchronized List<Patient> listPatients(){
        List<Patient> out = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(patientsFile);
            for (int i=1;i<lines.size();i++){
                String l = lines.get(i).trim();
                if (!l.isEmpty()) out.add(Patient.fromCsv(l));
            }
        } catch (IOException e){ System.err.println("No se pudo leer pacientes: " + e.getMessage()); }
        return out;
    }

    // Appointments
    public synchronized void addAppointment(Appointment a){
        try { Files.write(appointmentsFile, Arrays.asList(a.toCsv()), StandardOpenOption.APPEND); }
        catch (IOException e){ System.err.println("No se pudo escribir cita: " + e.getMessage()); }
    }
    public synchronized List<Appointment> listAppointments(){
        List<Appointment> out = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(appointmentsFile);
            for (int i=1;i<lines.size();i++){
                String l = lines.get(i).trim();
                if (!l.isEmpty()) out.add(Appointment.fromCsv(l));
            }
        } catch (IOException e){ System.err.println("No se pudo leer citas: " + e.getMessage()); }
        return out;
    }
}
