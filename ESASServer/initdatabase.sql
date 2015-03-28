CREATE TABLE patients
(
patientid INTEGER PRIMARY KEY,
firstname TEXT NOT NULL,
lastname TEXT NOT NULL,
email TEXT UNIQUE,
password TEXT NOT NULL,
phone INTEGER,
providerid INTEGER NOT NULL,
FOREIGN KEY(providerid) REFERENCES providers(providerid)
);

CREATE TABLE providers
(
providerid INTEGER PRIMARY KEY ASC,
firstname TEXT NOT NULL,
lastname TEXT NOT NULL,
email TEXT UNIQUE,
password TEXT NOT NULL,
phone INTEGER
);

CREATE TABLE surveys
(
patientid INTEGER NOT NULL,
painindex INTEGER NOT NULL,
timestamp INTEGER NOT NULL,
FOREIGN KEY(patientid) REFERENCES patients(patientid)
);
