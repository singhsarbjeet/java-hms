/*
Minimal Hospital Management System Demonstration
Shows classes, inheritance, JDBC with PostgreSQL, and a menu-driven Swing GUI
*/

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* ----------- CONSTANT CLASS ----------- */
class HospitalConstants {
    public static final String HOSPITAL_NAME = "City Hospital";
}

/* ----------- BASE CLASS ----------- */
class Person {

    protected String name;
    protected int age;

    // Creates a base person object with shared details.
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Displays the common details for a person.
    public void displayInfo() {
        System.out.println("Name: " + name + " Age: " + age);
    }

    // Returns the person's name.
    public String getName() {
        return name;
    }

    // Returns the person's age.
    public int getAge() {
        return age;
    }
}

/* ----------- INHERITANCE ----------- */
class Patient extends Person {

    private Integer id;
    private String disease;

    // Creates a patient before a database id is assigned.
    public Patient(String name, int age, String disease) {
        super(name, age);
        this.disease = disease;
    }

    // Creates a patient with an existing database id.
    public Patient(int id, String name, int age, String disease) {
        this(name, age, disease);
        this.id = id;
    }

    // Returns the patient id.
    public Integer getId() {
        return id;
    }

    // Stores the generated patient id after insert.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the disease recorded for the patient.
    public String getDisease() {
        return disease;
    }

    // Displays the patient details including disease.
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Disease: " + disease);
    }
}

/* ----------- ANOTHER CHILD CLASS ----------- */
class Doctor extends Person {

    private Integer id;
    private String specialization;

    // Creates a doctor before a database id is assigned.
    public Doctor(String name, int age, String specialization) {
        super(name, age);
        this.specialization = specialization;
    }

    // Creates a doctor with an existing database id.
    public Doctor(int id, String name, int age, String specialization) {
        this(name, age, specialization);
        this.id = id;
    }

    // Returns the doctor id.
    public Integer getId() {
        return id;
    }

    // Stores the generated doctor id after insert.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the doctor's specialization.
    public String getSpecialization() {
        return specialization;
    }

    // Displays the doctor details including specialization.
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Specialization: " + specialization);
    }
}

/* ----------- APPOINTMENT CLASS ----------- */
class Appointment {

    private Integer id;
    private int patientId;
    private int doctorId;
    private String patientName;
    private String doctorName;
    private Date appointmentDate;
    private Time appointmentTime;
    private String status;

    // Creates an appointment before a database id is assigned.
    public Appointment(
        int patientId,
        int doctorId,
        Date appointmentDate,
        Time appointmentTime,
        String status
    ) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Creates an appointment with an existing database id.
    public Appointment(
        int id,
        int patientId,
        int doctorId,
        Date appointmentDate,
        Time appointmentTime,
        String status
    ) {
        this(patientId, doctorId, appointmentDate, appointmentTime, status);
        this.id = id;
    }

    // Creates an appointment loaded together with patient and doctor names.
    public Appointment(
        int id,
        int patientId,
        int doctorId,
        String patientName,
        String doctorName,
        Date appointmentDate,
        Time appointmentTime,
        String status
    ) {
        this(id, patientId, doctorId, appointmentDate, appointmentTime, status);
        this.patientName = patientName;
        this.doctorName = doctorName;
    }

    // Returns the appointment id.
    public Integer getId() {
        return id;
    }

    // Stores the generated appointment id after insert.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the patient id linked to this appointment.
    public int getPatientId() {
        return patientId;
    }

    // Returns the doctor id linked to this appointment.
    public int getDoctorId() {
        return doctorId;
    }

    // Returns the patient name loaded for display.
    public String getPatientName() {
        return patientName;
    }

    // Returns the doctor name loaded for display.
    public String getDoctorName() {
        return doctorName;
    }

    // Returns the appointment date.
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    // Returns the appointment time.
    public Time getAppointmentTime() {
        return appointmentTime;
    }

    // Returns the current appointment status.
    public String getStatus() {
        return status;
    }
}

/* ----------- VISIT RECORD CLASS ----------- */
class VisitRecord {

    private Integer id;
    private int appointmentId;
    private String patientName;
    private String doctorName;
    private Date appointmentDate;
    private Time appointmentTime;
    private Date visitDate;
    private String diagnosis;
    private String prescription;
    private String notes;

    // Creates a visit record before a database id is assigned.
    public VisitRecord(
        int appointmentId,
        Date visitDate,
        String diagnosis,
        String prescription,
        String notes
    ) {
        this.appointmentId = appointmentId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
    }

    // Creates a visit record with an existing database id.
    public VisitRecord(
        int id,
        int appointmentId,
        Date visitDate,
        String diagnosis,
        String prescription,
        String notes
    ) {
        this(appointmentId, visitDate, diagnosis, prescription, notes);
        this.id = id;
    }

    // Creates a visit record loaded with appointment, patient, and doctor details.
    public VisitRecord(
        int id,
        int appointmentId,
        String patientName,
        String doctorName,
        Date appointmentDate,
        Time appointmentTime,
        Date visitDate,
        String diagnosis,
        String prescription,
        String notes
    ) {
        this(id, appointmentId, visitDate, diagnosis, prescription, notes);
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    // Returns the visit record id.
    public Integer getId() {
        return id;
    }

    // Stores the generated visit record id after insert.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the appointment id linked to this visit.
    public int getAppointmentId() {
        return appointmentId;
    }

    // Returns the patient name loaded for display.
    public String getPatientName() {
        return patientName;
    }

    // Returns the doctor name loaded for display.
    public String getDoctorName() {
        return doctorName;
    }

    // Returns the appointment date loaded for display.
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    // Returns the appointment time loaded for display.
    public Time getAppointmentTime() {
        return appointmentTime;
    }

    // Returns the date on which the visit was recorded.
    public Date getVisitDate() {
        return visitDate;
    }

    // Returns the diagnosis recorded for the visit.
    public String getDiagnosis() {
        return diagnosis;
    }

    // Returns the prescription recorded for the visit.
    public String getPrescription() {
        return prescription;
    }

    // Returns any additional notes for the visit.
    public String getNotes() {
        return notes;
    }
}

/* ----------- JDBC CONNECTION ----------- */
class DBConnection {

    private static final String DRIVER =
            "org.postgresql.Driver";

    private static final String DEFAULT_URL =
            "jdbc:postgresql://localhost:5432/demo";

    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "postgres";

    // Returns an environment variable value or a fallback default.
    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }

    // Opens a PostgreSQL database connection.
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e) {
            throw new SQLException(
                "PostgreSQL JDBC driver not found. Add the PostgreSQL JDBC JAR to the classpath.",
                e
            );
        }

        String url = getEnvOrDefault("HMS_DB_URL", DEFAULT_URL);
        String user = getEnvOrDefault("HMS_DB_USER", DEFAULT_USER);
        String password = getEnvOrDefault("HMS_DB_PASSWORD", DEFAULT_PASSWORD);

        return DriverManager.getConnection(url, user, password);
    }
}

/* ----------- DATA ACCESS OBJECT ----------- */
class PatientDAO {

    private static final int MAX_TEXT_LENGTH = 100;

    // Validates patient input before database operations.
    private void validatePatient(Patient p) {
        if (p == null) {
            throw new IllegalArgumentException("Patient details are required");
        }

        String name = p.getName() == null ? "" : p.getName().trim();
        String disease = p.getDisease() == null ? "" : p.getDisease().trim();

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (disease.isEmpty()) {
            throw new IllegalArgumentException("Disease cannot be empty");
        }

        if (name.length() > MAX_TEXT_LENGTH) {
            throw new IllegalArgumentException(
                "Name must be at most " + MAX_TEXT_LENGTH + " characters"
            );
        }

        if (disease.length() > MAX_TEXT_LENGTH) {
            throw new IllegalArgumentException(
                "Disease must be at most " + MAX_TEXT_LENGTH + " characters"
            );
        }

        if (p.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be greater than 0");
        }
    }

    // Validates the patient id used for update and delete operations.
    private void validatePatientId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Patient ID must be greater than 0");
        }
    }

    // Inserts a new patient record and captures the generated id.
    public boolean addPatient(Patient p) throws SQLException {
        validatePatient(p);

        String sql =
                "INSERT INTO patient(name,age,disease) VALUES(?,?,?)";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            stmt.setString(1, p.getName().trim());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getDisease().trim());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setId(generatedKeys.getInt(1));
                }
            }

            return true;
        }
    }

    // Updates an existing patient record by id.
    public boolean updatePatient(Patient p) throws SQLException {
        validatePatient(p);
        validatePatientId(p.getId());

        String sql =
                "UPDATE patient SET name = ?, age = ?, disease = ? WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, p.getName().trim());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getDisease().trim());
            stmt.setInt(4, p.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Deletes a patient record by id.
    public boolean deletePatient(int id) throws SQLException {
        validatePatientId(id);

        String sql =
                "DELETE FROM patient WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Returns all patient records ordered by id.
    public List<Patient> listPatients() throws SQLException {
        String sql =
                "SELECT id, name, age, disease FROM patient ORDER BY id";

        List<Patient> patients = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                patients.add(
                    new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("disease")
                    )
                );
            }
        }

        return patients;
    }
}

/* ----------- DOCTOR DATA ACCESS OBJECT ----------- */
class DoctorDAO {

    private static final int MAX_TEXT_LENGTH = 100;

    // Validates doctor input before database operations.
    private void validateDoctor(Doctor d) {
        if (d == null) {
            throw new IllegalArgumentException("Doctor details are required");
        }

        String name = d.getName() == null ? "" : d.getName().trim();
        String specialization =
                d.getSpecialization() == null ? "" : d.getSpecialization().trim();

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (specialization.isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }

        if (name.length() > MAX_TEXT_LENGTH) {
            throw new IllegalArgumentException(
                "Name must be at most " + MAX_TEXT_LENGTH + " characters"
            );
        }

        if (specialization.length() > MAX_TEXT_LENGTH) {
            throw new IllegalArgumentException(
                "Specialization must be at most " + MAX_TEXT_LENGTH + " characters"
            );
        }

        if (d.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be greater than 0");
        }
    }

    // Validates the doctor id used for update and delete operations.
    private void validateDoctorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Doctor ID must be greater than 0");
        }
    }

    // Inserts a new doctor record and captures the generated id.
    public boolean addDoctor(Doctor d) throws SQLException {
        validateDoctor(d);

        String sql =
                "INSERT INTO doctor(name,age,specialization) VALUES(?,?,?)";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            stmt.setString(1, d.getName().trim());
            stmt.setInt(2, d.getAge());
            stmt.setString(3, d.getSpecialization().trim());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    d.setId(generatedKeys.getInt(1));
                }
            }

            return true;
        }
    }

    // Updates an existing doctor record by id.
    public boolean updateDoctor(Doctor d) throws SQLException {
        validateDoctor(d);
        validateDoctorId(d.getId());

        String sql =
                "UPDATE doctor SET name = ?, age = ?, specialization = ? WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, d.getName().trim());
            stmt.setInt(2, d.getAge());
            stmt.setString(3, d.getSpecialization().trim());
            stmt.setInt(4, d.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Deletes a doctor record by id.
    public boolean deleteDoctor(int id) throws SQLException {
        validateDoctorId(id);

        String sql =
                "DELETE FROM doctor WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Returns all doctor records ordered by id.
    public List<Doctor> listDoctors() throws SQLException {
        String sql =
                "SELECT id, name, age, specialization FROM doctor ORDER BY id";

        List<Doctor> doctors = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                doctors.add(
                    new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("specialization")
                    )
                );
            }
        }

        return doctors;
    }
}

/* ----------- APPOINTMENT DATA ACCESS OBJECT ----------- */
class AppointmentDAO {

    private static final int MAX_STATUS_LENGTH = 30;

    // Validates appointment input before database operations.
    private void validateAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment details are required");
        }

        if (appointment.getPatientId() <= 0) {
            throw new IllegalArgumentException("Patient ID must be greater than 0");
        }

        if (appointment.getDoctorId() <= 0) {
            throw new IllegalArgumentException("Doctor ID must be greater than 0");
        }

        if (appointment.getAppointmentDate() == null) {
            throw new IllegalArgumentException("Appointment date is required");
        }

        if (appointment.getAppointmentTime() == null) {
            throw new IllegalArgumentException("Appointment time is required");
        }

        String status =
                appointment.getStatus() == null ? "" : appointment.getStatus().trim();

        if (status.isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }

        if (status.length() > MAX_STATUS_LENGTH) {
            throw new IllegalArgumentException(
                "Status must be at most " + MAX_STATUS_LENGTH + " characters"
            );
        }
    }

    // Validates the appointment id used for update and cancel operations.
    private void validateAppointmentId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "Appointment ID must be greater than 0"
            );
        }
    }

    // Inserts a new appointment record and captures the generated id.
    public boolean addAppointment(Appointment appointment) throws SQLException {
        validateAppointment(appointment);

        String sql =
                "INSERT INTO appointment(patient_id,doctor_id,appointment_date,appointment_time,status) "
              + "VALUES(?,?,?,?,?)";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setDate(3, appointment.getAppointmentDate());
            stmt.setTime(4, appointment.getAppointmentTime());
            stmt.setString(5, appointment.getStatus().trim());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setId(generatedKeys.getInt(1));
                }
            }

            return true;
        }
    }

    // Updates an existing appointment record by id.
    public boolean updateAppointment(Appointment appointment) throws SQLException {
        validateAppointment(appointment);
        validateAppointmentId(appointment.getId());

        String sql =
                "UPDATE appointment SET patient_id = ?, doctor_id = ?, appointment_date = ?, "
              + "appointment_time = ?, status = ? WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setDate(3, appointment.getAppointmentDate());
            stmt.setTime(4, appointment.getAppointmentTime());
            stmt.setString(5, appointment.getStatus().trim());
            stmt.setInt(6, appointment.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Marks an appointment as cancelled without deleting the record.
    public boolean cancelAppointment(int id) throws SQLException {
        validateAppointmentId(id);

        String sql =
                "UPDATE appointment SET status = 'CANCELLED' WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Returns all appointments together with patient and doctor names.
    public List<Appointment> listAppointments() throws SQLException {
        String sql =
                "SELECT a.id, a.patient_id, a.doctor_id, p.name AS patient_name, "
              + "d.name AS doctor_name, a.appointment_date, a.appointment_time, a.status "
              + "FROM appointment a "
              + "JOIN patient p ON a.patient_id = p.id "
              + "JOIN doctor d ON a.doctor_id = d.id "
              + "ORDER BY a.appointment_date, a.appointment_time, a.id";

        List<Appointment> appointments = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                appointments.add(
                    new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getDate("appointment_date"),
                        rs.getTime("appointment_time"),
                        rs.getString("status")
                    )
                );
            }
        }

        return appointments;
    }
}

/* ----------- VISIT RECORD DATA ACCESS OBJECT ----------- */
class VisitRecordDAO {

    private static final int MAX_DIAGNOSIS_LENGTH = 255;

    // Validates visit record input before database operations.
    private void validateVisitRecord(VisitRecord visitRecord) {
        if (visitRecord == null) {
            throw new IllegalArgumentException("Visit record details are required");
        }

        if (visitRecord.getAppointmentId() <= 0) {
            throw new IllegalArgumentException(
                "Appointment ID must be greater than 0"
            );
        }

        if (visitRecord.getVisitDate() == null) {
            throw new IllegalArgumentException("Visit date is required");
        }

        String diagnosis =
                visitRecord.getDiagnosis() == null
                    ? ""
                    : visitRecord.getDiagnosis().trim();
        String prescription =
                visitRecord.getPrescription() == null
                    ? ""
                    : visitRecord.getPrescription().trim();

        if (diagnosis.isEmpty()) {
            throw new IllegalArgumentException("Diagnosis is required");
        }

        if (diagnosis.length() > MAX_DIAGNOSIS_LENGTH) {
            throw new IllegalArgumentException(
                "Diagnosis must be at most " + MAX_DIAGNOSIS_LENGTH + " characters"
            );
        }

        if (prescription.isEmpty()) {
            throw new IllegalArgumentException("Prescription is required");
        }
    }

    // Validates the visit record id used for update operations.
    private void validateVisitRecordId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "Visit Record ID must be greater than 0"
            );
        }
    }

    // Inserts a new visit record and captures the generated id.
    public boolean addVisitRecord(VisitRecord visitRecord) throws SQLException {
        validateVisitRecord(visitRecord);

        String sql =
                "INSERT INTO visit_record(appointment_id,visit_date,diagnosis,prescription,notes) "
              + "VALUES(?,?,?,?,?)";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            stmt.setInt(1, visitRecord.getAppointmentId());
            stmt.setDate(2, visitRecord.getVisitDate());
            stmt.setString(3, visitRecord.getDiagnosis().trim());
            stmt.setString(4, visitRecord.getPrescription().trim());
            stmt.setString(5, normalizeNullableText(visitRecord.getNotes()));

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    visitRecord.setId(generatedKeys.getInt(1));
                }
            }

            return true;
        }
    }

    // Updates an existing visit record by id.
    public boolean updateVisitRecord(VisitRecord visitRecord) throws SQLException {
        validateVisitRecord(visitRecord);
        validateVisitRecordId(visitRecord.getId());

        String sql =
                "UPDATE visit_record SET appointment_id = ?, visit_date = ?, diagnosis = ?, "
              + "prescription = ?, notes = ? WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, visitRecord.getAppointmentId());
            stmt.setDate(2, visitRecord.getVisitDate());
            stmt.setString(3, visitRecord.getDiagnosis().trim());
            stmt.setString(4, visitRecord.getPrescription().trim());
            stmt.setString(5, normalizeNullableText(visitRecord.getNotes()));
            stmt.setInt(6, visitRecord.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Returns all visit records with linked appointment, patient, and doctor details.
    public List<VisitRecord> listVisitRecords() throws SQLException {
        String sql =
                "SELECT vr.id, vr.appointment_id, p.name AS patient_name, d.name AS doctor_name, "
              + "a.appointment_date, a.appointment_time, vr.visit_date, vr.diagnosis, "
              + "vr.prescription, vr.notes "
              + "FROM visit_record vr "
              + "JOIN appointment a ON vr.appointment_id = a.id "
              + "JOIN patient p ON a.patient_id = p.id "
              + "JOIN doctor d ON a.doctor_id = d.id "
              + "ORDER BY vr.visit_date DESC, vr.id DESC";

        List<VisitRecord> visitRecords = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                visitRecords.add(
                    new VisitRecord(
                        rs.getInt("id"),
                        rs.getInt("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getDate("appointment_date"),
                        rs.getTime("appointment_time"),
                        rs.getDate("visit_date"),
                        rs.getString("diagnosis"),
                        rs.getString("prescription"),
                        rs.getString("notes")
                    )
                );
            }
        }

        return visitRecords;
    }

    // Converts blank notes to null so the database stores optional text cleanly.
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}

/* ----------- SEARCH AND REPORT DATA ACCESS OBJECT ----------- */
class SearchReportDAO {

    // Finds patients whose names match the given search text.
    public List<Patient> searchPatientsByName(String keyword) throws SQLException {
        String sql =
                "SELECT id, name, age, disease FROM patient "
              + "WHERE LOWER(name) LIKE LOWER(?) ORDER BY name, id";

        List<Patient> patients = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, "%" + keyword.trim() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    patients.add(
                        new Patient(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("disease")
                        )
                    );
                }
            }
        }

        return patients;
    }

    // Finds doctors by matching name or specialization.
    public List<Doctor> searchDoctors(String keyword) throws SQLException {
        String sql =
                "SELECT id, name, age, specialization FROM doctor "
              + "WHERE LOWER(name) LIKE LOWER(?) OR LOWER(specialization) LIKE LOWER(?) "
              + "ORDER BY name, id";

        List<Doctor> doctors = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            String searchText = "%" + keyword.trim() + "%";
            stmt.setString(1, searchText);
            stmt.setString(2, searchText);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doctors.add(
                        new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("specialization")
                        )
                    );
                }
            }
        }

        return doctors;
    }

    // Returns appointments scheduled for a specific date.
    public List<Appointment> searchAppointmentsByDate(Date appointmentDate)
        throws SQLException {
        String sql =
                "SELECT a.id, a.patient_id, a.doctor_id, p.name AS patient_name, "
              + "d.name AS doctor_name, a.appointment_date, a.appointment_time, a.status "
              + "FROM appointment a "
              + "JOIN patient p ON a.patient_id = p.id "
              + "JOIN doctor d ON a.doctor_id = d.id "
              + "WHERE a.appointment_date = ? "
              + "ORDER BY a.appointment_time, a.id";

        List<Appointment> appointments = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setDate(1, appointmentDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(
                        new Appointment(
                            rs.getInt("id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("patient_name"),
                            rs.getString("doctor_name"),
                            rs.getDate("appointment_date"),
                            rs.getTime("appointment_time"),
                            rs.getString("status")
                        )
                    );
                }
            }
        }

        return appointments;
    }

    // Returns visit records for a specific patient id.
    public List<VisitRecord> getPatientVisitReport(int patientId) throws SQLException {
        String sql =
                "SELECT vr.id, vr.appointment_id, p.name AS patient_name, d.name AS doctor_name, "
              + "a.appointment_date, a.appointment_time, vr.visit_date, vr.diagnosis, "
              + "vr.prescription, vr.notes "
              + "FROM visit_record vr "
              + "JOIN appointment a ON vr.appointment_id = a.id "
              + "JOIN patient p ON a.patient_id = p.id "
              + "JOIN doctor d ON a.doctor_id = d.id "
              + "WHERE p.id = ? "
              + "ORDER BY vr.visit_date DESC, vr.id DESC";

        List<VisitRecord> visitRecords = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    visitRecords.add(
                        new VisitRecord(
                            rs.getInt("id"),
                            rs.getInt("appointment_id"),
                            rs.getString("patient_name"),
                            rs.getString("doctor_name"),
                            rs.getDate("appointment_date"),
                            rs.getTime("appointment_time"),
                            rs.getDate("visit_date"),
                            rs.getString("diagnosis"),
                            rs.getString("prescription"),
                            rs.getString("notes")
                        )
                    );
                }
            }
        }

        return visitRecords;
    }

    // Returns appointments scheduled for a specific doctor.
    public List<Appointment> getDoctorScheduleReport(int doctorId) throws SQLException {
        String sql =
                "SELECT a.id, a.patient_id, a.doctor_id, p.name AS patient_name, "
              + "d.name AS doctor_name, a.appointment_date, a.appointment_time, a.status "
              + "FROM appointment a "
              + "JOIN patient p ON a.patient_id = p.id "
              + "JOIN doctor d ON a.doctor_id = d.id "
              + "WHERE d.id = ? "
              + "ORDER BY a.appointment_date, a.appointment_time, a.id";

        List<Appointment> appointments = new ArrayList<>();

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, doctorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(
                        new Appointment(
                            rs.getInt("id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("patient_name"),
                            rs.getString("doctor_name"),
                            rs.getDate("appointment_date"),
                            rs.getTime("appointment_time"),
                            rs.getString("status")
                        )
                    );
                }
            }
        }

        return appointments;
    }
}

/* ----------- SWING GUI ----------- */
class HospitalGUI {

    private final JFrame frame;
    private final JTextArea displayArea;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final AppointmentDAO appointmentDAO;
    private final VisitRecordDAO visitRecordDAO;
    private final SearchReportDAO searchReportDAO;

    // Builds the main window and loads the initial view.
    public HospitalGUI() {
        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();
        appointmentDAO = new AppointmentDAO();
        visitRecordDAO = new VisitRecordDAO();
        searchReportDAO = new SearchReportDAO();
        frame = new JFrame(HospitalConstants.HOSPITAL_NAME);
        displayArea = new JTextArea();

        initializeFrame();
        refreshPatientList();
    }

    // Configures the frame layout, menus, and close handling.
    private void initializeFrame() {
        frame.setJMenuBar(createMenuBar());
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(
            "Hospital Record Management",
            SwingConstants.CENTER
        );

        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        displayArea.setText(
            "Use the Patient, Doctor, Appointment, Visit Record, or Search / Reports menu to manage records."
        );

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        frame.setVisible(true);
    }

    // Creates the menu bar for patient, doctor, appointment, visit, search, and app actions.
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu patientMenu = new JMenu("Patient");
        JMenuItem addPatientItem = new JMenuItem("Add Patient Record");
        JMenuItem updatePatientItem = new JMenuItem("Update Patient Record");
        JMenuItem deletePatientItem = new JMenuItem("Delete Patient Record");
        JMenuItem listPatientItem = new JMenuItem("List Patient Record");

        addPatientItem.addActionListener(e -> showAddPatientDialog());
        updatePatientItem.addActionListener(e -> showUpdatePatientDialog());
        deletePatientItem.addActionListener(e -> showDeletePatientDialog());
        listPatientItem.addActionListener(e -> refreshPatientList());

        patientMenu.add(addPatientItem);
        patientMenu.add(updatePatientItem);
        patientMenu.add(deletePatientItem);
        patientMenu.add(listPatientItem);

        JMenu doctorMenu = new JMenu("Doctor");
        JMenuItem addDoctorItem = new JMenuItem("Add Doctor");
        JMenuItem updateDoctorItem = new JMenuItem("Update Doctor Details");
        JMenuItem deleteDoctorItem = new JMenuItem("Delete Doctor");
        JMenuItem listDoctorItem = new JMenuItem("List Doctors");

        addDoctorItem.addActionListener(e -> showAddDoctorDialog());
        updateDoctorItem.addActionListener(e -> showUpdateDoctorDialog());
        deleteDoctorItem.addActionListener(e -> showDeleteDoctorDialog());
        listDoctorItem.addActionListener(e -> refreshDoctorList());

        doctorMenu.add(addDoctorItem);
        doctorMenu.add(updateDoctorItem);
        doctorMenu.add(deleteDoctorItem);
        doctorMenu.add(listDoctorItem);

        JMenu appointmentMenu = new JMenu("Appointment");
        JMenuItem addAppointmentItem = new JMenuItem("Book Appointment");
        JMenuItem updateAppointmentItem = new JMenuItem("Update Appointment");
        JMenuItem cancelAppointmentItem = new JMenuItem("Cancel Appointment");
        JMenuItem listAppointmentItem = new JMenuItem("List Appointments");

        addAppointmentItem.addActionListener(e -> showAddAppointmentDialog());
        updateAppointmentItem.addActionListener(e -> showUpdateAppointmentDialog());
        cancelAppointmentItem.addActionListener(e -> showCancelAppointmentDialog());
        listAppointmentItem.addActionListener(e -> refreshAppointmentList());

        appointmentMenu.add(addAppointmentItem);
        appointmentMenu.add(updateAppointmentItem);
        appointmentMenu.add(cancelAppointmentItem);
        appointmentMenu.add(listAppointmentItem);

        JMenu visitRecordMenu = new JMenu("Visit Record");
        JMenuItem addVisitRecordItem = new JMenuItem("Add Visit Record");
        JMenuItem updateVisitRecordItem = new JMenuItem("Update Visit Record");
        JMenuItem listVisitRecordItem = new JMenuItem("List Visit Records");

        addVisitRecordItem.addActionListener(e -> showAddVisitRecordDialog());
        updateVisitRecordItem.addActionListener(e -> showUpdateVisitRecordDialog());
        listVisitRecordItem.addActionListener(e -> refreshVisitRecordList());

        visitRecordMenu.add(addVisitRecordItem);
        visitRecordMenu.add(updateVisitRecordItem);
        visitRecordMenu.add(listVisitRecordItem);

        JMenu searchReportsMenu = new JMenu("Search / Reports");
        JMenuItem searchPatientItem = new JMenuItem("Search Patients");
        JMenuItem searchDoctorItem = new JMenuItem("Search Doctors");
        JMenuItem searchAppointmentItem = new JMenuItem("Search Appointments By Date");
        JMenuItem patientVisitReportItem = new JMenuItem("Patient Visit Report");
        JMenuItem doctorScheduleReportItem = new JMenuItem("Doctor Schedule Report");

        searchPatientItem.addActionListener(e -> showSearchPatientsDialog());
        searchDoctorItem.addActionListener(e -> showSearchDoctorsDialog());
        searchAppointmentItem.addActionListener(e -> showSearchAppointmentsByDateDialog());
        patientVisitReportItem.addActionListener(e -> showPatientVisitReportDialog());
        doctorScheduleReportItem.addActionListener(e -> showDoctorScheduleReportDialog());

        searchReportsMenu.add(searchPatientItem);
        searchReportsMenu.add(searchDoctorItem);
        searchReportsMenu.add(searchAppointmentItem);
        searchReportsMenu.add(patientVisitReportItem);
        searchReportsMenu.add(doctorScheduleReportItem);

        JMenu appMenu = new JMenu("Application");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> exitApplication());
        appMenu.add(exitItem);

        menuBar.add(patientMenu);
        menuBar.add(doctorMenu);
        menuBar.add(appointmentMenu);
        menuBar.add(visitRecordMenu);
        menuBar.add(searchReportsMenu);
        menuBar.add(appMenu);

        return menuBar;
    }

    // Builds a reusable patient input panel for add and update dialogs.
    private JPanel createPatientFormPanel(
        JTextField idField,
        JTextField nameField,
        JTextField ageField,
        JTextField diseaseField,
        boolean includeId
    ) {
        int rows = includeId ? 4 : 3;
        JPanel panel = new JPanel(new GridLayout(rows, 2, 5, 5));

        if (includeId) {
            panel.add(new JLabel("Patient ID"));
            panel.add(idField);
        }

        panel.add(new JLabel("Name"));
        panel.add(nameField);
        panel.add(new JLabel("Age"));
        panel.add(ageField);
        panel.add(new JLabel("Disease"));
        panel.add(diseaseField);

        return panel;
    }

    // Builds a reusable doctor input panel for add and update dialogs.
    private JPanel createDoctorFormPanel(
        JTextField idField,
        JTextField nameField,
        JTextField ageField,
        JTextField specializationField,
        boolean includeId
    ) {
        int rows = includeId ? 4 : 3;
        JPanel panel = new JPanel(new GridLayout(rows, 2, 5, 5));

        if (includeId) {
            panel.add(new JLabel("Doctor ID"));
            panel.add(idField);
        }

        panel.add(new JLabel("Name"));
        panel.add(nameField);
        panel.add(new JLabel("Age"));
        panel.add(ageField);
        panel.add(new JLabel("Specialization"));
        panel.add(specializationField);

        return panel;
    }

    // Builds a reusable appointment input panel for add and update dialogs.
    private JPanel createAppointmentFormPanel(
        JTextField idField,
        JTextField patientIdField,
        JTextField doctorIdField,
        JTextField dateField,
        JTextField timeField,
        JComboBox<String> statusBox,
        boolean includeId
    ) {
        int rows = includeId ? 6 : 5;
        JPanel panel = new JPanel(new GridLayout(rows, 2, 5, 5));

        if (includeId) {
            panel.add(new JLabel("Appointment ID"));
            panel.add(idField);
        }

        panel.add(new JLabel("Patient ID"));
        panel.add(patientIdField);
        panel.add(new JLabel("Doctor ID"));
        panel.add(doctorIdField);
        panel.add(new JLabel("Date (YYYY-MM-DD)"));
        panel.add(dateField);
        panel.add(new JLabel("Time (HH:MM or HH:MM:SS)"));
        panel.add(timeField);
        panel.add(new JLabel("Status"));
        panel.add(statusBox);

        return panel;
    }

    // Builds a reusable visit record input panel for add and update dialogs.
    private JPanel createVisitRecordFormPanel(
        JTextField idField,
        JTextField appointmentIdField,
        JTextField visitDateField,
        JTextField diagnosisField,
        JTextArea prescriptionArea,
        JTextArea notesArea,
        boolean includeId
    ) {
        int rows = includeId ? 6 : 5;
        JPanel panel = new JPanel(new GridLayout(rows, 2, 5, 5));

        if (includeId) {
            panel.add(new JLabel("Visit Record ID"));
            panel.add(idField);
        }

        panel.add(new JLabel("Appointment ID"));
        panel.add(appointmentIdField);
        panel.add(new JLabel("Visit Date (YYYY-MM-DD)"));
        panel.add(visitDateField);
        panel.add(new JLabel("Diagnosis"));
        panel.add(diagnosisField);
        panel.add(new JLabel("Prescription"));
        panel.add(new JScrollPane(prescriptionArea));
        panel.add(new JLabel("Notes"));
        panel.add(new JScrollPane(notesArea));

        return panel;
    }

    // Converts patient dialog values into a Patient object.
    private Patient buildPatientFromInput(
        Integer id,
        JTextField nameField,
        JTextField ageField,
        JTextField diseaseField
    ) {
        String name = nameField.getText().trim();
        String disease = diseaseField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());

        if (id == null) {
            return new Patient(name, age, disease);
        }

        return new Patient(id, name, age, disease);
    }

    // Converts doctor dialog values into a Doctor object.
    private Doctor buildDoctorFromInput(
        Integer id,
        JTextField nameField,
        JTextField ageField,
        JTextField specializationField
    ) {
        String name = nameField.getText().trim();
        String specialization = specializationField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());

        if (id == null) {
            return new Doctor(name, age, specialization);
        }

        return new Doctor(id, name, age, specialization);
    }

    // Converts appointment dialog values into an Appointment object.
    private Appointment buildAppointmentFromInput(
        Integer id,
        JTextField patientIdField,
        JTextField doctorIdField,
        JTextField dateField,
        JTextField timeField,
        JComboBox<String> statusBox
    ) {
        int patientId = Integer.parseInt(patientIdField.getText().trim());
        int doctorId = Integer.parseInt(doctorIdField.getText().trim());
        Date appointmentDate = Date.valueOf(dateField.getText().trim());

        String timeText = timeField.getText().trim();
        if (timeText.length() == 5) {
            timeText = timeText + ":00";
        }

        Time appointmentTime = Time.valueOf(timeText);
        String status = statusBox.getSelectedItem().toString();

        if (id == null) {
            return new Appointment(
                patientId,
                doctorId,
                appointmentDate,
                appointmentTime,
                status
            );
        }

        return new Appointment(
            id,
            patientId,
            doctorId,
            appointmentDate,
            appointmentTime,
            status
        );
    }

    // Converts visit record dialog values into a VisitRecord object.
    private VisitRecord buildVisitRecordFromInput(
        Integer id,
        JTextField appointmentIdField,
        JTextField visitDateField,
        JTextField diagnosisField,
        JTextArea prescriptionArea,
        JTextArea notesArea
    ) {
        int appointmentId = Integer.parseInt(appointmentIdField.getText().trim());
        Date visitDate = Date.valueOf(visitDateField.getText().trim());
        String diagnosis = diagnosisField.getText().trim();
        String prescription = prescriptionArea.getText().trim();
        String notes = notesArea.getText().trim();

        if (id == null) {
            return new VisitRecord(
                appointmentId,
                visitDate,
                diagnosis,
                prescription,
                notes
            );
        }

        return new VisitRecord(
            id,
            appointmentId,
            visitDate,
            diagnosis,
            prescription,
            notes
        );
    }

    // Opens the dialog used to add a new patient record.
    private void showAddPatientDialog() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField diseaseField = new JTextField();

        JPanel panel = createPatientFormPanel(
            null,
            nameField,
            ageField,
            diseaseField,
            false
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Add Patient Record",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Patient patient = buildPatientFromInput(
                null,
                nameField,
                ageField,
                diseaseField
            );

            if (patientDAO.addPatient(patient)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Patient added successfully. ID: " + patient.getId()
                );
                refreshPatientList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Patient could not be added"
                );
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Age must be a valid number"
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage()
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to update an existing patient record.
    private void showUpdatePatientDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField diseaseField = new JTextField();

        JPanel panel = createPatientFormPanel(
            idField,
            nameField,
            ageField,
            diseaseField,
            true
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Update Patient Record",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());
            Patient patient = buildPatientFromInput(
                id,
                nameField,
                ageField,
                diseaseField
            );

            if (patientDAO.updatePatient(patient)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Patient record updated successfully"
                );
                refreshPatientList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "No patient record found for ID: " + id
                );
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Patient ID and Age must be valid numbers"
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage()
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to delete a patient record.
    private void showDeletePatientDialog() {
        String input = JOptionPane.showInputDialog(
            frame,
            "Enter Patient ID to delete",
            "Delete Patient Record",
            JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            int confirmation = JOptionPane.showConfirmDialog(
                frame,
                "Delete patient record with ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            if (patientDAO.deletePatient(id)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Patient record deleted successfully"
                );
                refreshPatientList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "No patient record found for ID: " + id
                );
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Patient ID must be a valid number"
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage()
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to add a new doctor record.
    private void showAddDoctorDialog() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField specializationField = new JTextField();

        JPanel panel = createDoctorFormPanel(
            null,
            nameField,
            ageField,
            specializationField,
            false
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Add Doctor",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Doctor doctor = buildDoctorFromInput(
                null,
                nameField,
                ageField,
                specializationField
            );

            if (doctorDAO.addDoctor(doctor)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Doctor added successfully. ID: " + doctor.getId()
                );
                refreshDoctorList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Doctor could not be added"
                );
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Age must be a valid number"
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage()
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to update an existing doctor record.
    private void showUpdateDoctorDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField specializationField = new JTextField();

        JPanel panel = createDoctorFormPanel(
            idField,
            nameField,
            ageField,
            specializationField,
            true
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Update Doctor Details",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());
            Doctor doctor = buildDoctorFromInput(
                id,
                nameField,
                ageField,
                specializationField
            );

            if (doctorDAO.updateDoctor(doctor)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Doctor record updated successfully"
                );
                refreshDoctorList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "No doctor record found for ID: " + id
                );
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Doctor ID and Age must be valid numbers"
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage()
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to delete a doctor record.
    private void showDeleteDoctorDialog() {
        String input = JOptionPane.showInputDialog(
            frame,
            "Enter Doctor ID to delete",
            "Delete Doctor",
            JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            int confirmation = JOptionPane.showConfirmDialog(
                frame,
                "Delete doctor record with ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            if (doctorDAO.deleteDoctor(id)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Doctor record deleted successfully"
                );
                refreshDoctorList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "No doctor record found for ID: " + id
                );
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Doctor ID must be a valid number"
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage()
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to add a new appointment record.
    private void showAddAppointmentDialog() {
        JTextField patientIdField = new JTextField();
        JTextField doctorIdField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(
            new String[] {"SCHEDULED", "COMPLETED", "CANCELLED"}
        );

        JPanel panel = createAppointmentFormPanel(
            null,
            patientIdField,
            doctorIdField,
            dateField,
            timeField,
            statusBox,
            false
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Book Appointment",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Appointment appointment = buildAppointmentFromInput(
                null,
                patientIdField,
                doctorIdField,
                dateField,
                timeField,
                statusBox
            );

            if (appointmentDAO.addAppointment(appointment)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Appointment booked successfully. ID: "
                        + appointment.getId()
                );
                refreshAppointmentList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Appointment could not be booked"
                );
            }
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        catch (Exception ex) {
            handleInputOrDatabaseError(ex);
        }
    }

    // Opens the dialog used to update an existing appointment.
    private void showUpdateAppointmentDialog() {
        JTextField idField = new JTextField();
        JTextField patientIdField = new JTextField();
        JTextField doctorIdField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(
            new String[] {"SCHEDULED", "COMPLETED", "CANCELLED"}
        );

        JPanel panel = createAppointmentFormPanel(
            idField,
            patientIdField,
            doctorIdField,
            dateField,
            timeField,
            statusBox,
            true
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Update Appointment",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());
            Appointment appointment = buildAppointmentFromInput(
                id,
                patientIdField,
                doctorIdField,
                dateField,
                timeField,
                statusBox
            );

            if (appointmentDAO.updateAppointment(appointment)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Appointment updated successfully"
                );
                refreshAppointmentList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "No appointment record found for ID: " + id
                );
            }
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        catch (Exception ex) {
            handleInputOrDatabaseError(ex);
        }
    }

    // Opens the dialog used to cancel an appointment.
    private void showCancelAppointmentDialog() {
        String input = JOptionPane.showInputDialog(
            frame,
            "Enter Appointment ID to cancel",
            "Cancel Appointment",
            JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return;
        }

        try {
            int id = Integer.parseInt(input.trim());

            int confirmation = JOptionPane.showConfirmDialog(
                frame,
                "Cancel appointment with ID " + id + "?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            if (appointmentDAO.cancelAppointment(id)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Appointment cancelled successfully"
                );
                refreshAppointmentList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "No appointment record found for ID: " + id
                );
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Appointment ID must be a valid number"
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to add a new visit record.
    private void showAddVisitRecordDialog() {
        JTextField appointmentIdField = new JTextField();
        JTextField visitDateField = new JTextField();
        JTextField diagnosisField = new JTextField();
        JTextArea prescriptionArea = new JTextArea(3, 20);
        JTextArea notesArea = new JTextArea(3, 20);

        JPanel panel = createVisitRecordFormPanel(
            null,
            appointmentIdField,
            visitDateField,
            diagnosisField,
            prescriptionArea,
            notesArea,
            false
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Add Visit Record",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            VisitRecord visitRecord = buildVisitRecordFromInput(
                null,
                appointmentIdField,
                visitDateField,
                diagnosisField,
                prescriptionArea,
                notesArea
            );

            if (visitRecordDAO.addVisitRecord(visitRecord)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Visit record added successfully. ID: " + visitRecord.getId()
                );
                refreshVisitRecordList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Visit record could not be added"
                );
            }
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        catch (Exception ex) {
            handleVisitRecordError(ex);
        }
    }

    // Opens the dialog used to update an existing visit record.
    private void showUpdateVisitRecordDialog() {
        JTextField idField = new JTextField();
        JTextField appointmentIdField = new JTextField();
        JTextField visitDateField = new JTextField();
        JTextField diagnosisField = new JTextField();
        JTextArea prescriptionArea = new JTextArea(3, 20);
        JTextArea notesArea = new JTextArea(3, 20);

        JPanel panel = createVisitRecordFormPanel(
            idField,
            appointmentIdField,
            visitDateField,
            diagnosisField,
            prescriptionArea,
            notesArea,
            true
        );

        int choice = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Update Visit Record",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());
            VisitRecord visitRecord = buildVisitRecordFromInput(
                id,
                appointmentIdField,
                visitDateField,
                diagnosisField,
                prescriptionArea,
                notesArea
            );

            if (visitRecordDAO.updateVisitRecord(visitRecord)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Visit record updated successfully"
                );
                refreshVisitRecordList();
            }
            else {
                JOptionPane.showMessageDialog(
                    frame,
                    "No visit record found for ID: " + id
                );
            }
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        catch (Exception ex) {
            handleVisitRecordError(ex);
        }
    }

    // Opens the dialog used to search patients by name.
    private void showSearchPatientsDialog() {
        String keyword = JOptionPane.showInputDialog(
            frame,
            "Enter patient name to search",
            "Search Patients",
            JOptionPane.PLAIN_MESSAGE
        );

        if (keyword == null) {
            return;
        }

        try {
            List<Patient> patients = searchReportDAO.searchPatientsByName(keyword);
            displayArea.setText(
                formatPatientList("Patient Search Results", patients)
            );
            displayArea.setCaretPosition(0);
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to search doctors by name or specialization.
    private void showSearchDoctorsDialog() {
        String keyword = JOptionPane.showInputDialog(
            frame,
            "Enter doctor name or specialization to search",
            "Search Doctors",
            JOptionPane.PLAIN_MESSAGE
        );

        if (keyword == null) {
            return;
        }

        try {
            List<Doctor> doctors = searchReportDAO.searchDoctors(keyword);
            displayArea.setText(
                formatDoctorList("Doctor Search Results", doctors)
            );
            displayArea.setCaretPosition(0);
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to search appointments by date.
    private void showSearchAppointmentsByDateDialog() {
        String input = JOptionPane.showInputDialog(
            frame,
            "Enter appointment date (YYYY-MM-DD)",
            "Search Appointments By Date",
            JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return;
        }

        try {
            Date appointmentDate = Date.valueOf(input.trim());
            List<Appointment> appointments =
                searchReportDAO.searchAppointmentsByDate(appointmentDate);
            displayArea.setText(
                formatAppointmentList(
                    "Appointments on " + appointmentDate,
                    appointments
                )
            );
            displayArea.setCaretPosition(0);
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Use YYYY-MM-DD for the appointment date."
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to generate a patient visit report.
    private void showPatientVisitReportDialog() {
        String input = JOptionPane.showInputDialog(
            frame,
            "Enter patient ID for visit report",
            "Patient Visit Report",
            JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return;
        }

        try {
            int patientId = Integer.parseInt(input.trim());
            List<VisitRecord> visitRecords =
                searchReportDAO.getPatientVisitReport(patientId);
            displayArea.setText(
                formatVisitRecordList(
                    "Patient Visit Report (Patient ID: " + patientId + ")",
                    visitRecords
                )
            );
            displayArea.setCaretPosition(0);
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Patient ID must be a valid number"
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Opens the dialog used to generate a doctor schedule report.
    private void showDoctorScheduleReportDialog() {
        String input = JOptionPane.showInputDialog(
            frame,
            "Enter doctor ID for schedule report",
            "Doctor Schedule Report",
            JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) {
            return;
        }

        try {
            int doctorId = Integer.parseInt(input.trim());
            List<Appointment> appointments =
                searchReportDAO.getDoctorScheduleReport(doctorId);
            displayArea.setText(
                formatAppointmentList(
                    "Doctor Schedule Report (Doctor ID: " + doctorId + ")",
                    appointments
                )
            );
            displayArea.setCaretPosition(0);
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Doctor ID must be a valid number"
            );
        }
        catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // Loads and displays the current patient list in the main text area.
    private void refreshPatientList() {
        try {
            List<Patient> patients = patientDAO.listPatients();
            displayArea.setText(formatPatientList(patients));
            displayArea.setCaretPosition(0);
        }
        catch (SQLException ex) {
            displayArea.setText("Unable to load patient records.");
            showDatabaseError(ex);
        }
    }

    // Loads and displays the current visit record list in the main text area.
    private void refreshVisitRecordList() {
        try {
            List<VisitRecord> visitRecords = visitRecordDAO.listVisitRecords();
            displayArea.setText(
                formatVisitRecordList("Visit Records", visitRecords)
            );
            displayArea.setCaretPosition(0);
        }
        catch (SQLException ex) {
            displayArea.setText("Unable to load visit records.");
            showDatabaseError(ex);
        }
    }

    // Loads and displays the current appointment list in the main text area.
    private void refreshAppointmentList() {
        try {
            List<Appointment> appointments = appointmentDAO.listAppointments();
            displayArea.setText(formatAppointmentList(appointments));
            displayArea.setCaretPosition(0);
        }
        catch (SQLException ex) {
            displayArea.setText("Unable to load appointment records.");
            showDatabaseError(ex);
        }
    }

    // Loads and displays the current doctor list in the main text area.
    private void refreshDoctorList() {
        try {
            List<Doctor> doctors = doctorDAO.listDoctors();
            displayArea.setText(formatDoctorList(doctors));
            displayArea.setCaretPosition(0);
        }
        catch (SQLException ex) {
            displayArea.setText("Unable to load doctor records.");
            showDatabaseError(ex);
        }
    }

    // Formats patient records for display in the main text area.
    private String formatPatientList(List<Patient> patients) {
        return formatPatientList("Patient Records", patients);
    }

    // Formats patient records with a custom title.
    private String formatPatientList(String title, List<Patient> patients) {
        if (patients.isEmpty()) {
            return "No patient records found.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(title).append('\n');
        builder.append("---------------\n");

        for (Patient patient : patients) {
            builder.append("ID: ").append(patient.getId()).append('\n');
            builder.append("Name: ").append(patient.getName()).append('\n');
            builder.append("Age: ").append(patient.getAge()).append('\n');
            builder.append("Disease: ").append(patient.getDisease()).append('\n');
            builder.append('\n');
        }

        return builder.toString();
    }

    // Formats doctor records for display in the main text area.
    private String formatDoctorList(List<Doctor> doctors) {
        return formatDoctorList("Doctor Records", doctors);
    }

    // Formats doctor records with a custom title.
    private String formatDoctorList(String title, List<Doctor> doctors) {
        if (doctors.isEmpty()) {
            return "No doctor records found.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(title).append('\n');
        builder.append("--------------\n");

        for (Doctor doctor : doctors) {
            builder.append("ID: ").append(doctor.getId()).append('\n');
            builder.append("Name: ").append(doctor.getName()).append('\n');
            builder.append("Age: ").append(doctor.getAge()).append('\n');
            builder.append("Specialization: ")
                   .append(doctor.getSpecialization())
                   .append('\n');
            builder.append('\n');
        }

        return builder.toString();
    }

    // Formats appointment records for display in the main text area.
    private String formatAppointmentList(List<Appointment> appointments) {
        return formatAppointmentList("Appointment Records", appointments);
    }

    // Formats appointment records with a custom title.
    private String formatAppointmentList(
        String title,
        List<Appointment> appointments
    ) {
        if (appointments.isEmpty()) {
            return "No appointment records found.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(title).append('\n');
        builder.append("-------------------\n");

        for (Appointment appointment : appointments) {
            builder.append("ID: ").append(appointment.getId()).append('\n');
            builder.append("Patient: ")
                   .append(appointment.getPatientName())
                   .append(" (ID: ")
                   .append(appointment.getPatientId())
                   .append(")\n");
            builder.append("Doctor: ")
                   .append(appointment.getDoctorName())
                   .append(" (ID: ")
                   .append(appointment.getDoctorId())
                   .append(")\n");
            builder.append("Date: ")
                   .append(appointment.getAppointmentDate())
                   .append('\n');
            builder.append("Time: ")
                   .append(appointment.getAppointmentTime())
                   .append('\n');
            builder.append("Status: ")
                   .append(appointment.getStatus())
                   .append('\n');
            builder.append('\n');
        }

        return builder.toString();
    }

    // Formats visit records for display in the main text area.
    private String formatVisitRecordList(
        String title,
        List<VisitRecord> visitRecords
    ) {
        if (visitRecords.isEmpty()) {
            return "No visit records found.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(title).append('\n');
        builder.append("-----------------\n");

        for (VisitRecord visitRecord : visitRecords) {
            builder.append("ID: ").append(visitRecord.getId()).append('\n');
            builder.append("Appointment ID: ")
                   .append(visitRecord.getAppointmentId())
                   .append('\n');
            builder.append("Patient: ")
                   .append(visitRecord.getPatientName())
                   .append('\n');
            builder.append("Doctor: ")
                   .append(visitRecord.getDoctorName())
                   .append('\n');
            builder.append("Appointment Slot: ")
                   .append(visitRecord.getAppointmentDate())
                   .append(' ')
                   .append(visitRecord.getAppointmentTime())
                   .append('\n');
            builder.append("Visit Date: ")
                   .append(visitRecord.getVisitDate())
                   .append('\n');
            builder.append("Diagnosis: ")
                   .append(visitRecord.getDiagnosis())
                   .append('\n');
            builder.append("Prescription: ")
                   .append(visitRecord.getPrescription())
                   .append('\n');
            builder.append("Notes: ")
                   .append(
                        visitRecord.getNotes() == null
                            ? "-"
                            : visitRecord.getNotes()
                    )
                   .append('\n');
            builder.append('\n');
        }

        return builder.toString();
    }

    // Shows friendly messages for parsing and database issues.
    private void handleInputOrDatabaseError(Exception ex) {
        if (ex instanceof NumberFormatException) {
            JOptionPane.showMessageDialog(
                frame,
                "Patient ID and Doctor ID must be valid numbers"
            );
            return;
        }

        if (ex instanceof IllegalArgumentException) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
            return;
        }

        if (ex instanceof SQLException) {
            showDatabaseError((SQLException) ex);
            return;
        }

        JOptionPane.showMessageDialog(
            frame,
            "Invalid appointment input. Use YYYY-MM-DD for date and HH:MM for time."
        );
    }

    // Shows friendly messages for visit record parsing and database issues.
    private void handleVisitRecordError(Exception ex) {
        if (ex instanceof NumberFormatException) {
            JOptionPane.showMessageDialog(
                frame,
                "Appointment ID and Visit Record ID must be valid numbers"
            );
            return;
        }

        if (ex instanceof SQLException) {
            showDatabaseError((SQLException) ex);
            return;
        }

        JOptionPane.showMessageDialog(
            frame,
            "Invalid visit record input. Use YYYY-MM-DD for the visit date."
        );
    }

    // Shows a database error message in a dialog.
    private void showDatabaseError(SQLException ex) {
        String sqlState = ex.getSQLState();
        String message = ex.getMessage();

        if ("23505".equals(sqlState)) {
            if (message != null && message.contains("uq_doctor_slot")) {
                message =
                    "This doctor already has an appointment at that date and time.";
            }
            else if (
                message != null
                    && message.contains("visit_record_appointment_id_key")
            ) {
                message =
                    "A visit record already exists for that appointment.";
            }
        }
        else if ("23503".equals(sqlState)) {
            if (message != null && message.contains("appointment")) {
                message = "Appointment ID does not exist.";
            }
            else {
                message = "Patient ID or Doctor ID does not exist.";
            }
        }

        JOptionPane.showMessageDialog(
            frame,
            "Database Error: " + message
        );
    }

    // Confirms and closes the application.
    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(
            frame,
            "Exit the application?",
            "Exit",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
            System.exit(0);
        }
    }
}

/* ----------- MAIN CLASS ----------- */
public class HospitalDemo {

    // Starts the Swing application on the event dispatch thread.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HospitalGUI::new);
    }
}
