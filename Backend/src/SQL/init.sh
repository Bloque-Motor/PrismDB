#!/usr/bin/bash 

sudo service mysql start 
sudo mysql -v < create_database.sql
sudo mysql -v < populate_server.sql
sudo mysql -v < procedures.sql
