package Interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface Prism extends Remote {

    boolean addUser(String dni, String name, String surname, String email, String phone) throws RemoteException;

    ArrayList<Map<People.keyType, String>> searchUser(Map params) throws RemoteException;

    boolean deleteUser(Map<People.keyType, String> user) throws RemoteException;

    boolean updateUser(String oldDni, Map<People.keyType, String> user) throws RemoteException;
}
