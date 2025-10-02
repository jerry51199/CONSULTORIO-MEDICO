package com.mycompany.consultoriomedico6;

import java.nio.file.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Path DATA_DIR = Paths.get("data");

    public static void main(String[] args) {
        try {
            Files.createDirectories(DATA_DIR);
        } catch (Exception e) {
            System.err.println("No se pudo crear carpeta data: " + e.getMessage());
        }

        Repos repos = new Repos(DATA_DIR);
        AuthService auth = new AuthService(repos);

        System.out.println("=== Sistema de Citas - Consultorio ===");

        // Si no hay administradores, crear uno por defecto
        try {
            if (repos.listAdmins().isEmpty()) {
                System.out.println("No existen administradores. Crear uno ahora.");
                System.out.print("Usuario: ");
                String u = scanner.nextLine().trim();
                System.out.print("Contraseña: ");
                String p = scanner.nextLine().trim();
                repos.addAdmin(Admin.create(u, p));
                System.out.println("Administrador creado.");
            }
        } catch (Exception e) {
            System.err.println("Error inicial al revisar admins: " + e.getMessage());
        }

        while (true) {
            try {
                System.out.println("\n1) Iniciar sesion (administrador)");
                System.out.println("2) Salir");
                System.out.print("> ");
                String opt = scanner.nextLine().trim();
                if (opt.equals("1")) {
                    if (loginAndRun(auth, repos)) {
                        // al cerrar sesión volver al menú
                    }
                } else if (opt.equals("2") || opt.equalsIgnoreCase("salir")) {
                    System.out.println("Saliendo...");
                    break;
                } else {
                    System.out.println("Opcion inválida.");
                }
            } catch (Exception ex) {
                System.err.println("Error inesperado: " + ex.getMessage());
            }
        }
    }

    private static boolean loginAndRun(AuthService auth, Repos repos) {
        System.out.print("Usuario: ");
        String user = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pw = scanner.nextLine().trim();

        try {
            if (!auth.authenticate(user, pw)) {
                System.out.println("Credenciales invalidas.");
                return false;
            }
            System.out.println("Login correcto. Bienvenido " + user + "!");
            adminMenu(repos);
            return true;
        } catch (Exception e) {
            System.err.println("Error en autenticacion: " + e.getMessage());
            return false;
        }
    }

    private static void adminMenu(Repos repos) {
        while (true) {
            try {
                System.out.println("\n--- Menú Admin ---");
                System.out.println("1) Alta Doctor");
                System.out.println("2) Alta Paciente");
                System.out.println("3) Crear Cita");
                System.out.println("4) Listar Doctores");
                System.out.println("5) Listar Pacientes");
                System.out.println("6) Listar Citas");
                System.out.println("7) Cerrar sesion");
                System.out.print("> ");
                String opt = scanner.nextLine().trim();
                switch (opt) {
                    case "1": altaDoctor(repos); break;
                    case "2": altaPaciente(repos); break;
                    case "3": crearCita(repos); break;
                    case "4": listarDoctores(repos); break;
                    case "5": listarPacientes(repos); break;
                    case "6": listarCitas(repos); break;
                    case "7": System.out.println("Cerrando sesion..."); return;
                    default: System.out.println("Opcion invalida."); break;
                }
            } catch (Exception e) {
                System.err.println("Error en operacion: " + e.getMessage());
            }
        }
    }

    private static void altaDoctor(Repos repos) {
        System.out.print("Nombre completo: ");
        String name = scanner.nextLine().trim();
        System.out.print("Especialidad: ");
        String spec = scanner.nextLine().trim();
        Doctor d = new Doctor(UUID.randomUUID().toString(), name, spec);
        repos.addDoctor(d);
        System.out.println("Doctor creado: " + d.getId());
    }

    private static void altaPaciente(Repos repos) {
        System.out.print("Nombre completo: ");
        String name = scanner.nextLine().trim();
        Patient p = new Patient(UUID.randomUUID().toString(), name);
        repos.addPatient(p);
        System.out.println("Paciente creado: " + p.getId());
    }

    private static void crearCita(Repos repos) {
        List<Doctor> doctors = repos.listDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No hay doctores registrados.");
            return;
        }
        List<Patient> patients = repos.listPatients();
        if (patients.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        System.out.println("Seleccione doctor (id):");
        doctors.forEach(d -> System.out.printf("%s : %s (%s)%n", d.getId(), d.getFullName(), d.getSpecialty()));
        System.out.print("> ");
        String did = scanner.nextLine().trim();
        System.out.println("Seleccione paciente (id):");
        patients.forEach(p -> System.out.printf("%s : %s%n", p.getId(), p.getFullName()));
        System.out.print("> ");
        String pid = scanner.nextLine().trim();

        System.out.print("Fecha y hora (YYYY-MM-DDTHH:MM) e.g. 2025-10-01T14:30 : ");
        String dt = scanner.nextLine().trim();
        LocalDateTime ldt;
        try {
            ldt = LocalDateTime.parse(dt);
        } catch (Exception e) {
            System.out.println("Formato invalido: " + e.getMessage());
            return;
        }
        System.out.print("Motivo: ");
        String motive = scanner.nextLine().trim();
        Appointment a = new Appointment(UUID.randomUUID().toString(), did, pid, ldt, motive);
        repos.addAppointment(a);
        System.out.println("Cita creada: " + a.getId());
    }

    private static void listarDoctores(Repos repos) {
        repos.listDoctors().forEach(d -> System.out.printf("%s | %s | %s%n", d.getId(), d.getFullName(), d.getSpecialty()));
    }

    private static void listarPacientes(Repos repos) {
        repos.listPatients().forEach(p -> System.out.printf("%s | %s%n", p.getId(), p.getFullName()));
    }

    private static void listarCitas(Repos repos) {
        DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        repos.listAppointments().forEach(a -> System.out.printf("%s | Doctor:%s Paciente:%s | %s | %s%n",
                a.getId(), a.getDoctorId(), a.getPatientId(), a.getDateTime().format(f), a.getMotive()));
    }
}
