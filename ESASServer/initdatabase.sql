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
pain INTEGER NOT NULL,
drowsiness INTEGER NOT NULL,
nausea INTEGER NOT NULL,
appetite INTEGER NOT NULL,
shortnessofbreath INTEGER NOT NULL,
depression INTEGER NOT NULL,
anxiety INTEGER NOT NULL,
wellbeing INTEGER NOT NULL,
comments TEXT,
timestamp INTEGER NOT NULL,
FOREIGN KEY(patientid) REFERENCES patients(patientid)
);
