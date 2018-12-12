package Interfaces;

import java.sql.SQLException;
import java.util.Map;

public interface DBHandler {
    // TODO specify methods as public instead of friendly by default

    // TODO methods should not be void but boolean,
    //  there is no information about the status of the operation or security in carrying out db operation,
    //  something can go wrong and leave the database inconsistent

    // TODO methods should not raise SQLException, this management is handler work

    // TODO The entire People|Person object should not be passed as a parameter but only the necessary parameters, dni

    // TODO there should be an update method for each parameter, and not a generic one,
    //  it's more expensive and inefficient

    void addUser(People user) throws SQLException;

    void deleteUser(People user) throws SQLException;

    void updateUser(String dniOld, People user) throws SQLException;

    People search(Map parameters) throws SQLException;
}
