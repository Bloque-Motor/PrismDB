--
-- RMI Users dir
-- Database procedures
--
-- Wed 12 Dec 2018
-- v0.3
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
  IN userdni VARCHAR(11))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userdni IS NULL OR CHAR_LENGTH(userdni) = 0 THEN
    LEAVE main;
  END IF;

  -- Delete user
  DELETE FROM prismdb.users WHERE dni = userdni LIMIT 1;

  SET exitstatus = 0;
END//
delimiter ;


----------------------------
-- Update User Procedures --
----------------------------

DROP PROCEDURE IF EXISTS prismdb.updatename;
delimiter //
CREATE PROCEDURE prismdb.updateuser(OUT exitstatus INTEGER,
  IN userdni VARCHAR(11),
  IN newdni VARCHAR(11),
  IN newname VARCHAR(50),
  IN newusername VARCHAR(50),
  IN newtelephone VARCHAR(15),
  IN newemail VARCHAR(100))
MODIFIES SQL DATA
main:BEGIN
  SET exitstatus = -1;
  IF userdni IS NULL OR CHAR_LENGTH(userdni) = 0 THEN
   LEAVE main;
  END IF;
  IF newdni IS NULL OR CHAR_LENGTH(newdni) = 0 THEN
   LEAVE main;
  END IF;

  -- Update user
  UPDATE prismdb.users SET name = newname, surname = newusername, telephone = newtelephone, email = newemail WHERE dni = userdni;
  
  IF newdni != userdni THEN
   UPDATE prismdb.users SET dni = newdni WHERE dni = userdni;
  END IF;

  SET exitstatus = 0;
END//
delimiter ;

