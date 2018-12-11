package Interfaces;
import Entities.Person;

public interface Prism extends java.rmi.Remote {

    void addUser(String name, String surname, String dni, String email, String phone) throws java.rmi.RemoteException;

    Person searchUser(String query) throws java.rmi.RemoteException;

    void deleteUser(String dni) throws java.rmi.RemoteException;

    void updateUser(String oldDni, String name, String surname, String dni, String email, String phone) throws java.rmi.RemoteException;
}
