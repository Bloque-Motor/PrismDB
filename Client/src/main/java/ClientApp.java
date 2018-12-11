import GUI.ClientGUI;
import interfaces.AddUser;

import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClientGUI gui = new ClientGUI();
                gui.setTitle("RMI Client");
                gui.setSize(800,600);
                gui.setContentPane(new ClientGUI().panelMain);
                gui.setLocationRelativeTo(null);
                gui.setDefaultCloseOperation(ClientGUI.EXIT_ON_CLOSE);
                gui.setVisible(true);
            }
        });

    }

    public void addUser (String name, String surname, String dni, String phone, String email){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();
            AddUser stub = (AddUser) registry.lookup("AddUser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
