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
  appointment_time TIMESTAMP NOT NULL,
  duration NUMBER,
  diagnosis VARCHAR2(3000),
  treatment VARCHAR2(4000),
  id_examination NUMBER REFERENCES Examination(id_examination)
);

CREATE TABLE Examination (
  id_examination NUMBER PRIMARY KEY,
  id_cabinet NUMBER REFERENCES Cabinets(id),
  examination_name VARCHAR2(200),
  average_duration NUMBER
);
