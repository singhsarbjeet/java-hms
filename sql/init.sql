CREATE TABLE IF NOT EXISTS patient (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  age INT,
  disease VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS doctor (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  age INT,
  specialization VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS appointment (
  id SERIAL PRIMARY KEY,
  patient_id INT NOT NULL REFERENCES patient(id) ON DELETE CASCADE,
  doctor_id INT NOT NULL REFERENCES doctor(id) ON DELETE CASCADE,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  status VARCHAR(30) NOT NULL,
  CONSTRAINT uq_doctor_slot UNIQUE (doctor_id, appointment_date, appointment_time)
);

CREATE TABLE IF NOT EXISTS visit_record (
  id SERIAL PRIMARY KEY,
  appointment_id INT NOT NULL UNIQUE REFERENCES appointment(id) ON DELETE CASCADE,
  visit_date DATE NOT NULL,
  diagnosis VARCHAR(255) NOT NULL,
  prescription TEXT NOT NULL,
  notes TEXT
);
