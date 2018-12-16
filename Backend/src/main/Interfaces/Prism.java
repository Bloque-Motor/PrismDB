package Interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface Prism extends Remote {

    boolean addUser(String dni, String name, String surname, String email, String phone) throws RemoteException;

    ArrayList<Map<String, String>> searchUser(Map params) throws RemoteException;

    boolean deleteUser(Map<String, String> user) throws RemoteException;

    boolean updateUser(String oldDni, Map<String, String> user) throws RemoteException;
}
