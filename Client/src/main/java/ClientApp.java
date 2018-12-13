import interfaces.People;
import interfaces.Prism;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientApp {

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
        ConsoleMenus.mainMenu();

    }

    public static People search(String name, String surname, String dni, String phone, String email) {
        Map<String, String> searchParam = new HashMap<>();

        if (!name.equals("")) searchParam.put("name", name);
        if (!surname.equals("")) searchParam.put("surname", surname);
        if (!dni.equals("")) searchParam.put("dni", dni);
        if (!phone.equals("")) searchParam.put("phone", phone);
        if (!email.equals("")) searchParam.put("email", email);

        Registry registry = null;
        People res = null;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            res = stub.searchUser(searchParam);
        } catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }

    public static void updateUser(String oldDni, String name, String surname, String dni, String phone, String email){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            stub.updateUser(oldDni, name, surname, dni, phone, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String dni){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            stub.deleteUser(dni);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String name, String surname, String dni, String phone, String email){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
            Prism stub = (Prism) registry.lookup("Prism");
            stub.addUser(name, surname, dni, phone, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract class Person implements People {
    }
}
