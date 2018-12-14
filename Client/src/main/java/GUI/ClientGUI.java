package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {


    public JTextField searchTextField;
    public JTextField nombreTextField;
    public JTextField apellidosTextField;
    public JTextField DNITextField;
    public JTextField telefonoTextField;
    public JTextField eMailTextField;
    public JPanel panelMain;
    public JLabel nameLabel;
    public JLabel surnameLabel;
    public JLabel dniLabel;
    public JLabel phoneLabel;
    public JLabel emailLabel;
    public JPanel panelUser;
    private JToolBar menu;
    private JButton addUserButton;
    private JButton button1;
    private JButton button2;
    private JButton searchButton;

    public ClientGUI() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
