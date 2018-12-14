import Interfaces.People;
import Interfaces.Prism;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;


public class ClientApp {


    private static final String SOURCE = "PrismDB Client";
    private static final Logger logger = LogManager.getLogger(ClientApp.class);

    public static void main(String[] args) {

//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                ClientGUI gui = new ClientGUI();
//                gui.setTitle("RMI Client");
//                gui.setContentPane(new ClientGUI().panelMain);
//                gui.setLocationRelativeTo(null);
//                gui.setDefaultCloseOperation(ClientGUI.EXIT_ON_CLOSE);
//                gui.setVisible(true);
//            }
//        });

        logger.info(SOURCE, "Starting Client");
        ConsoleMenus.mainMenu();

    }

    static People search(String name, String surname, String dni, String phone, String email) {
        Map<String, String> searchParam = new HashMap<>();

        if (!name.equals("")) searchParam.put("name", name);
        if (!surname.equals("")) searchParam.put("surname", surname);
        if (!dni.equals("")) searchParam.put("dni", dni);
        if (!phone.equals("")) searchParam.put("phone", phone);
        if (!email.equals("")) searchParam.put("email", email);

        logger.info(SOURCE, "Searching person with the following parameters: " + name + " " + surname + " " + dni + " " + phone + " " + email);

        Registry registry;
        People res = null;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            res = stub.searchUser(searchParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    static void updateUser(String oldDni, String name, String surname, String dni, String phone, String email) {
        logger.info(SOURCE, "Attempting to update the person " + oldDni + " with the following new data: " + name + " " + surname + " " + dni + " " + phone + " " + email);

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            stub.updateUser(oldDni, name, surname, dni, phone, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteUser(String dni) {
        logger.info(SOURCE, "Attempting to delete person " + dni);

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            stub.deleteUser(dni);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addUser(String name, String surname, String dni, String phone, String email) {
        logger.info(SOURCE, "Attempting to add new person with the following data: " + name + " " + surname + " " + dni + " " + phone + " " + email);

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            stub.addUser(name, surname, dni, phone, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
