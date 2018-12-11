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
    }

    public static People search() {
        //String name, String surname, String dni, String phone, String email
        System.out.println("Introduzca un nombre: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Introduzca un apellido: ");
        String surname = scanner.nextLine();
        System.out.println("Introduzca un dni: ");
        String dni = scanner.nextLine();
        System.out.println("Introduzca un telefono: ");
        String phone = scanner.nextLine();
        System.out.println("Introduzca un email: ");
        String email = scanner.nextLine();

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
            res = stub.searchUser(name);
        } catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }

    public void addUser (String name, String surname, String dni, String phone, String email){
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
