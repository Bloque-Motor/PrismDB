package Handlers;

import Entities.Person;
import Interfaces.Prism;
import Handlers.*;

import java.rmi.RemoteException;
import java.util.Map;

public class PrismImp implements Prism {
    @Override
    public void addUser(String name, String surname, String dni, String email, String phone) throws RemoteException {
        Database.addUser(new Person(name, surname, dni, phone, email));
    }

    @Override
    public Person searchUser(Map params) throws RemoteException {
        return null;
    }

    @Override
    public void deleteUser(String dni) throws RemoteException {

    }

    @Override
    public void updateUser(String oldDni, String name, String surname, String dni, String email, String phone) throws RemoteException {

    }
}
