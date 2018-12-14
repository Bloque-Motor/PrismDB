--
-- RMI Users dir
-- SQL populate server script: Fills the Database with fake data for the tests.
-- Mon 03 Dec 2018
-- v0.1
--


--------------
-- Database --
--------------
USE prismdb


------------------------
-- Database Fake Data --
------------------------

INSERT INTO users (`dni`, `name`, `surname`, `telephone`, `email`) VALUES
  ('000111222X', 'user1', 'surname1', '+34666111000', 'user1@gmail.com'),
  ('333444555-A', 'user2', 'surname2', '+34666222000', 'user2@yahoo.com'),
  ('666777888-B', 'user3', 'surname3', '+34666333000', 'user3@outlook.com'),
  ('999000111 C', 'user4', 'surname4', '+34666444000', 'user4@mail.com')

