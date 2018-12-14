package Interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Prism extends Remote {

    boolean addUser(String name, String surname, String dni, String email, String phone) throws RemoteException;

    People searchUser(Map params) throws RemoteException;

    boolean deleteUser(People user) throws RemoteException;

    boolean updateUser(String oldDni, People user) throws RemoteException;
}
