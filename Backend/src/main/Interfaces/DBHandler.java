package Interfaces;

import java.sql.SQLException;
import java.util.Map;

public interface DBHandler {

    void addUser(People user) throws SQLException;

    void deleteUser(People user) throws SQLException;

    void updateUser(String dniOld, People user) throws SQLException;

    People search(Map parameters) throws SQLException;

}
