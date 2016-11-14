#!/bin/bash

java -cp /home/aleksander/Downloads/hsqldb-2.3.4/hsqldb/lib/hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb
