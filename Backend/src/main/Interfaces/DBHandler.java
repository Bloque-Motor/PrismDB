package Interfaces;

import java.util.Map;

public interface DBHandler {

    static boolean addUser(People user) {
        return true;
    }

    static boolean deleteUser(People user) {
        return true;
    }

    static boolean updateUser(String dniOld, People user) {
        return true;
    }

    static People search(Map parameters) {
        return null;
    }
}
