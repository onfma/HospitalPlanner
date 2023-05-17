CREATE TABLE User_authentication (
  Email VARCHAR2(100) PRIMARY KEY,
  Password VARCHAR2(100) NOT NULL,
  salt VARCHAR2(100)
);


CREATE TABLE Pacients (
  CNP NUMBER PRIMARY KEY,
  First_name VARCHAR2(50) NOT NULL,
  Last_name VARCHAR2(50) NOT NULL,
  Gender CHAR(1) NOT NULL,
  Phone_number VARCHAR2(20) NOT NULL,
  Email VARCHAR2(100) REFERENCES User_authentication(Email),
  Address VARCHAR2(200)
);


CREATE TABLE Doctors (
  CNP NUMBER PRIMARY KEY,
  First_name VARCHAR2(50) NOT NULL,
  Last_name VARCHAR2(50) NOT NULL,
  Gender CHAR(1) NOT NULL,
  Phone_number VARCHAR2(20) NOT NULL,
  Email VARCHAR2(100) REFERENCES User_authentication(Email),
  Address VARCHAR2(200)
);


CREATE TABLE Admins (
  Email VARCHAR2(100) REFERENCES User_authentication(Email)
);

CREATE TABLE Cabinets (
  id NUMBER PRIMARY KEY,
  Specialty VARCHAR2(100) NOT NULL
);


CREATE TABLE Cabinets_schedule (
  cabinet_id NUMBER REFERENCES Cabinets(id),
  day_of_week VARCHAR2(10) NOT NULL,
  start_time VARCHAR2(20) NOT NULL,
  end_time VARCHAR2(20) NOT NULL,
  PRIMARY KEY (cabinet_id, day_of_week, start_time)
);

CREATE TABLE Doctor_specialties (
  cabinet_id NUMBER REFERENCES Cabinets(id),
  doctor_CNP NUMBER REFERENCES Doctors(CNP),
  PRIMARY KEY (cabinet_id, doctor_CNP)
);

CREATE TABLE Doctors_schedule (
  doctor_CNP NUMBER REFERENCES Doctors(CNP),
  day_of_week VARCHAR2(10) NOT NULL,
  start_time VARCHAR2(20) NOT NULL,
  end_time VARCHAR2(20) NOT NULL,
  PRIMARY KEY (doctor_CNP, day_of_week, start_time)
);

CREATE TABLE Appointments (
  id NUMBER PRIMARY KEY,
  cabinet_id NUMBER REFERENCES Cabinets(id),
  doctor_CNP NUMBER REFERENCES Doctors(CNP),
  patient_CNP NUMBER REFERENCES Pacients(CNP),
  appointment_time TIMESTAMP NOT NULL
);


---- INSERARI

-- Insert pacients accounts
INSERT INTO User_authentication(Email, Password)
VALUES ('admin@yahoo.com', 'Admin1234');
INSERT INTO User_authentication (Email, Password)
VALUES ('patient@yahoo.com', 'Patient1234');
INSERT INTO User_authentication (Email, Password)
VALUES ('doctor@@yahoo.com', 'Doctor1234');

-- Insert Doctors accounts
INSERT INTO User_authentication (Email, Password)
VALUES ('william.jones@yahoo.com', 'secretpassword');
INSERT INTO User_authentication (Email, Password)
VALUES ('sarah.lee@yahoo.com', 'secretpassword');
INSERT INTO User_authentication (Email, Password)
VALUES ('david.kim@gmail.com', 'secretpassword');
INSERT INTO User_authentication (Email, Password)
VALUES ('emily.chen@gmail.com', 'secretpassword');

-- Insert Admins accounts
INSERT INTO User_authentication (Email, Password)
VALUES ('claudiu.chichirau@admin.com', 'admin');
INSERT INTO User_authentication (Email, Password)
VALUES ('maria.onofrei@admin.com', 'admin');

-- Insert patients
INSERT INTO Pacients (CNP, First_name, Last_name, Gender, Phone_number, Email, Address)
VALUES (1234567890123, 'John', 'Doe', 'M', '+447456123456', 'john.doe@example.com', '123 Main Street');
INSERT INTO Pacients (CNP, First_name, Last_name, Gender, Phone_number, Email, Address)
VALUES (2345678901234, 'Jane', 'Smith', 'F', '+44-7456-234567', 'jane.smith@yahoo.com', '456 Oak Avenue');
INSERT INTO Pacients (CNP, First_name, Last_name, Gender, Phone_number, Email, Address)
VALUES (3456789012345, 'Bob', 'Johnson', 'M', '+44-7456-345678', 'bob.johnson@yahoo.com', '789 Elm Street');

-- Insert doctors
INSERT INTO Doctors (CNP, First_name, Last_name, Gender, Phone_number, Email, Address)
VALUES (3455689012345, 'William', 'Jones', 'M', '+44-7963-345000', 'william.jones@yahoo.com', '26 Baker Street');
INSERT INTO Doctors (CNP, First_name, Last_name, Gender, Phone_number, Email, Address)
VALUES (2345638921592, 'Sarah', 'Lee', 'F', '+44-3510-345678', 'sarah.lee@yahoo.com', '14 Oak Avenue');
INSERT INTO Doctors (CNP, First_name, Last_name, Gender, Phone_number, Email, Address)
VALUES (7235567873123, 'David', 'Kim', 'M', '+44-7456-082467', 'david.kim@gmail.com', '12 Oxford Street');
INSERT INTO Doctors (CNP, First_name, Last_name, Gender, Phone_number, Email, Address)
VALUES (7239017873123, 'Emily', 'Chen', 'F', '+44-4012-085167', 'emily.chen@gmail.com', '49 Regent Street');

-- Insert cabinets
INSERT INTO Cabinets (id, Specialty)
VALUES (1, 'Cardiology');
INSERT INTO Cabinets (id, Specialty)
VALUES (2, 'Neurology');
INSERT INTO Cabinets (id, Specialty)
VALUES (3, 'Dermatology');

-- Insert doctor specialties
INSERT INTO Doctor_specialties (cabinet_id, doctor_CNP)
VALUES (1, 3455689012345);
INSERT INTO Doctor_specialties (cabinet_id, doctor_CNP)
VALUES (2, 2345638921592);
INSERT INTO Doctor_specialties (cabinet_id, doctor_CNP)
VALUES (2, 7235567873123);
INSERT INTO Doctor_specialties (cabinet_id, doctor_CNP)
VALUES (3, 7235567873123);
INSERT INTO Doctor_specialties (cabinet_id, doctor_CNP)
VALUES (3, 7239017873123);

-- Insert cabinet schedules
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (1, 'Monday', '08:00:00', '16:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (1, 'Tuesday', '08:00:00', '16:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (1, 'Wednesday', '10:00:00', '18:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (2, 'Monday', '09:00:00', '17:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (2, 'Tuesday', '09:00:00', '17:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (2, 'Friday', '12:00:00', '20:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (3, 'Wednesday', '08:00:00', '16:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (3, 'Thursday', '08:00:00', '16:00:00');
INSERT INTO Cabinets_schedule (cabinet_id, day_of_week, start_time, end_time)
VALUES (3, 'Friday', '08:00:00', '16:00:00');


-- Insert doctor schedules
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (3455689012345, 'Monday', '09:00:00', '17:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (3455689012345, 'Tuesday', '09:00:00', '17:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (3455689012345, 'Wednesday', '09:00:00', '17:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (2345638921592, 'Wednesday', '12:00:00', '20:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (2345638921592, 'Thursday', '12:00:00', '20:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (2345638921592, 'Friday', '12:00:00', '20:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (7235567873123, 'Monday', '10:00:00', '18:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (7235567873123, 'Tuesday', '10:00:00', '18:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (7235567873123, 'Wednesday', '10:00:00', '18:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (7239017873123, 'Thursday', '08:00:00', '16:00:00');
INSERT INTO Doctors_schedule (doctor_CNP, day_of_week, start_time, end_time)
VALUES (7239017873123, 'Friday', '08:00:00', '16:00:00');


-- Insert appointments
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (1, 1, 3455689012345, 1234567890123, TIMESTAMP '2023-07-07 10:00:00');
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (2, 2, 2345638921592, 2345678901234, TIMESTAMP '2023-08-17 13:00:00');
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (3, 2, 7235567873123, 3456789012345, TIMESTAMP '2023-07-27 12:00:00');
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (4, 3, 7239017873123, 1234567890123, TIMESTAMP '2023-06-30 11:00:00');
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (5, 1, 7239017873123, 2345678901234, TIMESTAMP '2023-07-14 15:00:00');
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (6, 2, 2345638921592, 3456789012345, TIMESTAMP '2023-07-01 14:40:00');
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (7, 2, 7235567873123, 1234567890123, TIMESTAMP '2023-07-02 11:20:00');
INSERT INTO Appointments (id, cabinet_id, doctor_cnp, patient_cnp, appointment_time)
VALUES (8, 3, 7239017873123, 2345678901234, TIMESTAMP '2023-08-01 12:00:00');
