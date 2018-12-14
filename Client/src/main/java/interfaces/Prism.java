package interfaces;

import java.util.Map;

public interface Prism extends java.rmi.Remote {

    void addUser(String name, String surname, String dni, String email, String phone) throws java.rmi.RemoteException;

    People searchUser(Map params) throws java.rmi.RemoteException;

    void deleteUser(String dni) throws java.rmi.RemoteException;

    void updateUser(String oldDni, String name, String surname, String dni, String email, String phone) throws java.rmi.RemoteException;
}
