#!/bin/bash

DATABASENAME="esasrecords.sqlite"

sqlite3 -init initdatabase.sql $DATABASENAME ""
