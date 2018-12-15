package Handlers;

import Entities.Person;
import Interfaces.People;
import Interfaces.Prism;

import java.rmi.RemoteException;
import java.util.Map;

public class PrismImp implements Prism {

    public PrismImp(){}

    public boolean addUser(String dni, String name, String surname, String email, String phone) throws RemoteException {
        return Database.addUser(new Person(dni, name, surname, phone, email));
    }

    public People searchUser(Map params) throws RemoteException {
        return Database.search(params);
    }

    public boolean deleteUser(People user) throws RemoteException {
        return Database.deleteUser(user);
    }

    public boolean updateUser(String oldDni, People user) throws RemoteException {
        return Database.updateUser(oldDni, user);
    }
}
