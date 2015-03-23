CREATE TABLE patients
(
patientid INTEGER PRIMARY KEY,
providerid INTEGER NOT NULL,
name TEXT NOT NULL,
email TEXT,
phone INTEGER,
password TEXT NOT NULL,
FOREIGN KEY(providerid) REFERENCES providers(providerid)
);

CREATE TABLE providers
(
providerid INTEGER PRIMARY KEY,
name TEXT NOT NULL,
email TEXT,
phone INTEGER,
password TEXT NOT NULL
);

CREATE TABLE surveys
(
patientid INTEGER NOT NULL,
painindex INTEGER NOT NULL,
timestamp INTEGER NOT NULL,
FOREIGN KEY(patientid) REFERENCES patients(patientid)
);
