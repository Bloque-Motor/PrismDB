import Interfaces.People;
import Interfaces.Prism;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;


public class ClientApp {


    //private static final String SOURCE = "PrismDB Client";
    private static final Logger logger = LogManager.getLogger(ClientApp.class);

    public static void main(String[] args) throws RemoteException {

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

        logger.info("Starting Client");
        ConsoleMenus.mainMenu();

    }

    static People search(String name, String surname, String dni, String phone, String email) {
        Map<String, String> searchParam = new HashMap<>();

        if (!name.equals("")) searchParam.put("name", name);
        if (!surname.equals("")) searchParam.put("surname", surname);
        if (!dni.equals("")) searchParam.put("dni", dni);
        if (!phone.equals("")) searchParam.put("phone", phone);
        if (!email.equals("")) searchParam.put("email", email);

        logger.info("Searching person with the following parameters: " + name + " " + surname + " " + dni + " " + phone + " " + email);

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

    static boolean updateUser(String oldDni, People res) throws RemoteException {
        logger.info("Attempting to update the person " + oldDni + " with the following new data: " + res.getName() + " " + res.getSurname() + " " + res.getDni() + " " + res.getTelephone() + " " + res.getEmail());


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

    static boolean deleteUser(People res) throws RemoteException {
        logger.info("Attempting to delete person " + res.getDni());

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
            return stub.addUser(name, surname, dni, phone, email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
