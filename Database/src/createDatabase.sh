#!/bin/bash

DATABASENAME="records.db"

sqlite3 -init initdatabase.sql $DATABASENAME ""
