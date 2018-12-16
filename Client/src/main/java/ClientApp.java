import Interfaces.People;
import Interfaces.Prism;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ClientApp {

    private static final Logger logger = LogManager.getLogger(ClientApp.class);

    public static void main(String[] args) throws RemoteException {

        logger.info("Starting Client");
        ConsoleMenus.mainMenu();
    }

    static ArrayList<Map<People.keyType, String>> search(String dni, String name, String surname, String phone, String email) {
        Map<People.keyType, String> searchParam = new HashMap<>();

        if (!name.equals("")) searchParam.put(People.keyType.NAME, name);
        if (!surname.equals("")) searchParam.put(People.keyType.SURNAME, surname);
        if (!dni.equals("")) searchParam.put(People.keyType.DNI, dni);
        if (!phone.equals("")) searchParam.put(People.keyType.PHONE, phone);
        if (!email.equals("")) searchParam.put(People.keyType.EMAIL, email);

        logger.info("Searching person with the following parameters: " + name + " " + surname + " " + dni + " " + phone + " " + email);

        Registry registry;
        ArrayList<Map<People.keyType, String>> res = null;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            res = stub.searchUser(searchParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    static boolean updateUser(String oldDni, Map<People.keyType, String> res) {
        logger.info("Attempting to update the person " + oldDni + " with the following new data: " + res.get(People.keyType.NAME) + " " + res.get(People.keyType.SURNAME) + " " + res.get(People.keyType.DNI) + " " + res.get(People.keyType.PHONE) + " " + res.get(People.keyType.EMAIL));

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            return stub.updateUser(oldDni, res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    static boolean deleteUser(Map<People.keyType, String> res) {
        logger.info("Attempting to delete person " + res.get(People.keyType.DNI));

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            return stub.deleteUser(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    static boolean addUser(String name, String surname, String dni, String phone, String email) {
        logger.info("Attempting to add new person with the following data: " + name + " " + surname + " " + dni + " " + phone + " " + email);

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            Map<People.keyType, String> var = new HashMap<>();
            var.put(People.keyType.NAME, name);
            var.put(People.keyType.SURNAME, surname);
            var.put(People.keyType.DNI, dni);
            var.put(People.keyType.PHONE, phone);
            var.put(People.keyType.EMAIL, email);
            return stub.addUser(var);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
