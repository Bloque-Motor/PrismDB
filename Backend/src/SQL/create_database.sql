--
-- RMI Users Dir
-- SQL initialization script: Creates and initializes the sql environment, user, database and tables
--
-- Mon 03 Dec 2018
-- v0.1
--
-- Database: prismdb
-- Admin: dir_admin
--


--------------
-- Database --
--------------
DROP DATABASE IF EXISTS prismdb;
CREATE DATABASE prismdb;


----------------
-- Admin User --
----------------
DROP USER IF EXISTS 'dir_admin'@'localhost';
FLUSH PRIVILEGES;
CREATE USER 'dir_admin'@'localhost';

GRANT ALL ON prismdb.* TO 'dir_admin'@'localhost';
SET PASSWORD FOR 'dir_admin'@'localhost' = '*52716A243712980597FF5F4962C6A9BF5212DFBA';


---------------------
-- Database Tables --
---------------------
USE prismdb;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL DEFAULT '',
  `surname` VARCHAR(50) NOT NULL DEFAULT '',
  `dni` VARCHAR(10) NOT NULL DEFAULT '',
  `telephone` VARCHAR(11) NOT NULL DEFAULT '',
  `email` VARCHAR(100) NOT NULL DEFAULT '',  
  UNIQUE(`name`),
  UNIQUE(`dni`),
  UNIQUE(`telephone`),
  UNIQUE(`email`),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

