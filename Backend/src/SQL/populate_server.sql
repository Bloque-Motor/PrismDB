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

INSERT INTO users (`name`, `surname`, `dni`, `telephone`, `email`) VALUES
  -- 1
  ('user1', 'surname1', '000111222X', '+34666111000', 'user1@gmail.com'),
  -- 2
  ('user2', 'surname2', '333444555A', '+34666222000', 'user2@yahoo.com'),
  -- 3
  ('user3', 'surname3', '666777888B', '+34666333000', 'user3@outlook.com'),
  -- 4
  ('user4', 'surname4', '999000111C', '+34666444000', 'user4@mail.com')

