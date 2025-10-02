
# 📅 Sistema de Administración de Citas Médicas

Este proyecto es una aplicación de consola en **Java** que permite administrar doctores, pacientes y citas en un consultorio médico.  
Toda la información se almacena en **archivos CSV** y el sistema cuenta con **control de acceso por administradores**.  

---

## ✨ Funcionalidades

- 👨‍⚕️ **Alta de Doctores**: registrar nombre completo y especialidad.  
- 🧑‍🤝‍🧑 **Alta de Pacientes**: registrar nombre completo.  
- 📅 **Creación de Citas**: asignar fecha, hora, motivo, doctor y paciente.  
- 🔐 **Control de acceso**: solo administradores con usuario y contraseña pueden acceder.  
- 💾 **Persistencia en CSV**: los datos se guardan automáticamente en la carpeta `data/`.  
- ⚠️ **Manejo de errores**: el sistema continúa ejecutándose incluso si ocurre una excepción.  
- 🚀 **Portabilidad**: se puede ejecutar en cualquier sistema con **Java instalado**.  

---

## 📂 Estructura de Archivos

El sistema almacena información en la carpeta `data/`:

- `admins.csv` → Administradores (usuarios del sistema)  
- `doctors.csv` → Doctores registrados  
- `patients.csv` → Pacientes registrados  
- `appointments.csv` → Citas creadas  

Ejemplo de datos:

```csv
# doctors.csv
id,fullName,specialty
d1,Dr. Yadira Santiago,Cardiología
d2,Dr. Gerardo Sanchez,Cardiología
```

```csv
# patients.csv
id,fullName
p1,Esperanza Portilla
p2,Angel Sanchez
```

```csv
# appointments.csv
id,doctorId,patientId,datetime,motive
c1,d1,p1,2025-10-05T10:00,Dolor en el pecho
```

---

## 🛠️ Requisitos

- **Java 11+**  
- NetBeans, IntelliJ IDEA o cualquier IDE (opcional)  

---


## 📖 Ejemplo de Uso

```
=== Sistema de Citas - Consultorio ===
No existen administradores. Crear uno ahora.
Usuario: Gerardo
Contraseña: 1234
Administrador creado.

1) Iniciar sesión (administrador)
2) Salir
> 1
Usuario: Gerardo
Contraseña: 1234
Login correcto. Bienvenido Gerardo!

--- Menú Admin ---
1) Alta Doctor
2) Alta Paciente
3) Crear Cita
4) Listar Doctores
5) Listar Pacientes
6) Listar Citas
7) Cerrar sesión
> 
```

---

## 📦 Empaquetado FAT JAR

Para crear un `.jar` ejecutable (incluyendo dependencias):

```bash
mvn clean package
```

Esto generará en `target/` un archivo tipo:

```
consultorio-medico-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Que puedes ejecutar con:

```bash
java -jar consultorio-medico-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## 👨‍💻 Autor

Desarrollado como proyecto académico para la simulación de un **sistema de administración de citas médicas** en consola.  
