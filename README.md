# Hospital Management System

A Java Swing based Hospital Management System with PostgreSQL integration.

## Features

- Patient management
  - Add patient record
  - Update patient record
  - Delete patient record
  - List patient records
- Doctor management
  - Add doctor
  - Update doctor details
  - Delete doctor
  - List doctors
- Appointment management
  - Book appointment
  - Update appointment
  - Cancel appointment
  - List appointments
- Visit records and prescriptions
  - Add visit record
  - Update visit record
  - List visit records
- Search and reports
  - Search patients by name
  - Search doctors by name or specialization
  - Search appointments by date
  - Patient visit report
  - Doctor schedule report

## Tech Stack

- Java
- Swing
- PostgreSQL
- JDBC
- Docker Compose for database setup

## Project Structure

- `HospitalDemo.java`: main source file containing models, DAOs, and GUI
- `.vscode/`: VS Code run configuration
- `Run-HospitalDemo.ps1`: PowerShell launcher for local runs
- `docker-compose.yml`: Dockerized PostgreSQL setup
- `sql/init.sql`: database schema initialization script

## Database Setup

Use a PostgreSQL database named `demo`.

### Option 1: Start PostgreSQL with Docker

Run this from the project root:

```powershell
docker compose up -d
```

This starts PostgreSQL on `localhost:5432` and automatically creates:

- `patient`
- `doctor`
- `appointment`
- `visit_record`

To stop it later:

```powershell
docker compose down
```

To stop it and remove saved database data:

```powershell
docker compose down -v
```

### Option 2: Create the schema manually

```sql
CREATE TABLE patient (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  age INT,
  disease VARCHAR(100)
);

CREATE TABLE doctor (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  age INT,
  specialization VARCHAR(100)
);

CREATE TABLE appointment (
  id SERIAL PRIMARY KEY,
  patient_id INT NOT NULL REFERENCES patient(id) ON DELETE CASCADE,
  doctor_id INT NOT NULL REFERENCES doctor(id) ON DELETE CASCADE,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  status VARCHAR(30) NOT NULL,
  CONSTRAINT uq_doctor_slot UNIQUE (doctor_id, appointment_date, appointment_time)
);

CREATE TABLE visit_record (
  id SERIAL PRIMARY KEY,
  appointment_id INT NOT NULL UNIQUE REFERENCES appointment(id) ON DELETE CASCADE,
  visit_date DATE NOT NULL,
  diagnosis VARCHAR(255) NOT NULL,
  prescription TEXT NOT NULL,
  notes TEXT
);
```

## JDBC Driver Setup

Download the PostgreSQL JDBC driver and place it in the project root as:

`postgresql-42.7.10.jar`

This file is ignored by git and should not be committed.

## Environment Variables

The application now supports environment-based database settings.

Supported variables:

- `HMS_DB_URL`
- `HMS_DB_USER`
- `HMS_DB_PASSWORD`

Defaults:

- `jdbc:postgresql://localhost:5432/demo`
- `postgres`
- `postgres`

Example PowerShell session:

```powershell
$env:HMS_DB_URL="jdbc:postgresql://localhost:5432/demo"
$env:HMS_DB_USER="postgres"
$env:HMS_DB_PASSWORD="postgres"
```

You can copy `.env.example` as a reference, though Java reads the values from your environment, not directly from the file.

## Run From PowerShell

```powershell
javac -cp ".;postgresql-42.7.10.jar" HospitalDemo.java
java -cp ".;postgresql-42.7.10.jar" HospitalDemo
```

Or run:

```powershell
.\Run-HospitalDemo.ps1
```

## Run From VS Code

Open the full folder in VS Code and use the Java extension launch configuration.

## Notes

- Database connection defaults exist in `HospitalDemo.java`, but they can now be overridden with environment variables.
- The application is menu-driven and all major actions are handled through the GUI.
