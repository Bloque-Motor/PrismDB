--
-- RMI Users dir
-- Database procedures
--
-- Wed 12 Dec 2018
-- v0.2
--

------------------------
-- Add User Procedure --
------------------------
DROP PROCEDURE IF EXISTS prismdb.adduser;
delimiter //
CREATE PROCEDURE prismdb.adduser(OUT exitstatus INTEGER,
  IN dni VARCHAR(11),
  IN name VARCHAR(50),
  IN surname VARCHAR(50),
  IN telephone VARCHAR(15),
  IN email VARCHAR(100))
MODIFIES SQL DATA
BEGIN
  SET exitstatus = -1;
  INSERT INTO prismdb.users (`dni`, `name`, `surname`, `telephone`, `email`)
  VALUES (dni, name, surname, telephone, email);
  SET exitstatus = 0;
END//
delimiter ;


---------------------------
-- Remove User Procedure --
---------------------------
DROP PROCEDURE IF EXISTS prismdb.removeuser;
delimiter //
CREATE PROCEDURE prismdb.removeuser(OUT exitstatus INTEGER,
  IN dni VARCHAR(11))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF dni IS NULL OR CHAR_LENGTH(dni) = 0 THEN
    LEAVE main;
  END IF;

  -- Delete user
  DELETE FROM prismdb.users WHERE dni = dni LIMIT 1;

  SET exitstatus = 0;
END//
delimiter ;


----------------------------
-- Update User Procedures --
----------------------------

DROP PROCEDURE IF EXISTS prismdb.updatename;
delimiter //
CREATE PROCEDURE prismdb.updatename(OUT exitstatus INTEGER,
  IN dni VARCHAR(11),
  IN newname VARCHAR(50))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF dni IS NULL OR CHAR_LENGTH(dni) = 0 THEN
    LEAVE main;
  END IF;

  -- Update user name
  UPDATE prismdb.users SET name = newname WHERE dni = dni;

  SET exitstatus = 0;
END//
delimiter ;


DROP PROCEDURE IF EXISTS prismdb.updatesurname;
delimiter //
CREATE PROCEDURE prismdb.udpatesurname(OUT exitstatus INTEGER,
  IN dni VARCHAR(11),
  IN newsurname VARCHAR(50))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF dni IS NULL OR CHAR_LENGTH(dni) = 0 THEN
    LEAVE main;
  END IF;

  -- Update user surname
  UPDATE prismdb.users SET surname = newsurname WHERE dni = dni;

  SET exitstatus = 0;
END//
delimiter ;


DROP PROCEDURE IF EXISTS prismdb.udpatedni;
delimiter //
CREATE PROCEDURE prismdb.updatedni(OUT exitstatus INTEGER,
  IN dni VARCHAR(11),
  IN newdni VARCHAR(11))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF dni IS NULL OR CHAR_LENGTH(dni) = 0 THEN
    LEAVE main;
  END IF;

  -- Update user dni
  UPDATE prismdb.users SET dni = newdni WHERE dni = dni;

  SET exitstatus = 0;
END//
delimiter ;


DROP PROCEDURE IF EXISTS prismdb.updatetelephone;
delimiter //
CREATE PROCEDURE prismdb.updatetelephone(OUT exitstatus INTEGER,
  IN dni VARCHAR(11),
  IN newtelephone VARCHAR(15))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF dni IS NULL OR CHAR_LENGTH(dni) = 0 THEN
    LEAVE main;
  END IF;

  -- Update user telephone
  UPDATE prismdb.users SET telephone = newtelephone WHERE dni = dni;

  SET exitstatus = 0;
END//
delimiter ;


DROP PROCEDURE IF EXISTS prismdb.updateemail;
delimiter //
CREATE PROCEDURE prismdb.updateemail(OUT exitstatus INTEGER,
  IN dni VARCHAR(11),
  IN newemail VARCHAR(100))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF dni IS NULL OR CHAR_LENGTH(dni) = 0 THEN
    LEAVE main;
  END IF;

  -- Update user email
  UPDATE prismdb.users SET email = newemail WHERE dni = dni;

  SET exitstatus = 0;
END//
delimiter ;

