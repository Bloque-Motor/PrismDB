--
-- RMI Users dir
-- Database procedures
--
-- Mon 03 Dec 2018
-- v0.1
--

------------------------
-- Add User Procedure --
------------------------
DROP PROCEDURE IF EXISTS prismdb.adduser;
delimiter //
CREATE PROCEDURE prismdb.adduser(OUT exitstatus INTEGER,
  IN name VARCHAR(50),
  IN surname VARCHAR(50),
  IN dni VARCHAR(10),
  IN telephone VARCHAR(11),
  IN email VARCHAR(100))
MODIFIES SQL DATA
BEGIN
  SET exitstatus = -1;
  INSERT INTO prismdb.users (`id`, `name`, `surname`, `dni`, `telephone`, `email`)
  VALUES (name, surname, dni, telephone, email);
  SET exitstatus = 0;
END//
delimiter ;


---------------------------
-- Remove User Procedure --
---------------------------
DROP PROCEDURE IF EXISTS prismdb.removeuser;
delimiter //
CREATE PROCEDURE prismdb.removeuser(OUT exitstatus INTEGER,
  IN userid INTEGER)
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userid IS NULL OR userid <= -1 THEN
    LEAVE main;
  END IF;

  -- Delete user
  DELETE FROM prismdb.users WHERE id = userid;

  SET exitstatus = 0;
END//
delimiter ;


----------------------------
-- Update User Procedures --
----------------------------

DROP PROCEDURE IF EXISTS prismdb.updatename;
delimiter //
CREATE PROCEDURE prismdb.updatename(OUT exitstatus INTEGER,
  IN userid INTEGER,
  IN newname VARCHAR(50))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userid IS NULL OR userid <= -1 THEN
    LEAVE main;
  END IF;

  -- Update user name
  UPDATE prismdb.users SET name = newname WHERE id = userid;

  SET exitstatus = 0;
END//
delimiter ;



DROP PROCEDURE IF EXISTS prismdb.updatesurname;
delimiter //
CREATE PROCEDURE prismdb.udpatesurname(OUT exitstatus INTEGER,
  IN userid INTEGER,
  IN newsurname VARCHAR(50))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userid IS NULL OR userid <= -1 THEN
    LEAVE main;
  END IF;

  -- Update user surname
  UPDATE prismdb.users SET surname = newsurname WHERE id = userid;

  SET exitstatus = 0;
END//
delimiter ;


DROP PROCEDURE IF EXISTS prismdb.udpatedni;
delimiter //
CREATE PROCEDURE prismdb.updatedni(OUT exitstatus INTEGER,
  IN userid INTEGER,
  IN newdni VARCHAR(10))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userid IS NULL OR userid <= -1 THEN
    LEAVE main;
  END IF;

  -- Update user dni
  UPDATE prismdb.users SET dni = newdni WHERE id = userid;

  SET exitstatus = 0;
END//
delimiter ;


DROP PROCEDURE IF EXISTS prismdb.updatetelephone;
delimiter //
CREATE PROCEDURE prismdb.updatetelephone(OUT exitstatus INTEGER,
  IN userid INTEGER,
  IN newtelephone VARCHAR(11))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userid IS NULL OR userid <= -1 THEN
    LEAVE main;
  END IF;

  -- Update user telephone
  UPDATE prismdb.users SET telephone = newtelephone WHERE id = userid;

  SET exitstatus = 0;
END//
delimiter ;


DROP PROCEDURE IF EXISTS prismdb.updateemail;
delimiter //
CREATE PROCEDURE prismdb.updateemail(OUT exitstatus INTEGER,
  IN userid INTEGER,
  IN newemail VARCHAR(100))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userid IS NULL OR userid <= -1 THEN
    LEAVE main;
  END IF;

  -- Update user email
  UPDATE prismdb.users SET email = newemail WHERE id = userid;

  SET exitstatus = 0;
END//
delimiter ;

