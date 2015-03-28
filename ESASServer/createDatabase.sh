#!/bin/bash

DATABASENAME="esasrecords.db"

sqlite3 -init initdatabase.sql $DATABASENAME ""
