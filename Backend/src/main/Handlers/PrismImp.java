package Handlers;

import Entities.Person;
import Interfaces.People;
import Interfaces.Prism;

import java.rmi.RemoteException;
import java.util.*;

public class PrismImp implements Prism {

    public PrismImp(){}

    public boolean addUser(String dni, String name, String surname, String email, String phone) throws RemoteException {
        return Database.addUser(new Person(dni, name, surname, phone, email));
    }

    public ArrayList<Map<People.keyType, String>> searchUser(Map params) throws RemoteException {
        ArrayList<People> results = Database.search(params);
        ArrayList<Map<People.keyType, String>> toClient = new ArrayList<>();
        for (People person : results){
            Map<People.keyType, String> result = new HashMap<>();
            result.put(People.keyType.NAME, person.getName());
            result.put(People.keyType.SURNAME, person.getSurname());
            result.put(People.keyType.DNI, person.getDni());
            result.put(People.keyType.EMAIL, person.getEmail());
            result.put(People.keyType.PHONE, person.getTelephone());
            toClient.add(result);
        }
        return toClient;
    }

    public boolean deleteUser(Map<People.keyType, String> user) throws RemoteException {
        return Database.deleteUser(mapToPeople(user));
    }

    public boolean updateUser(String oldDni, Map<People.keyType, String> user) throws RemoteException {
        return Database.updateUser(oldDni, mapToPeople(user));
    }

    private People mapToPeople(Map<People.keyType,String> map){
        String name = map.get(People.keyType.NAME);
        String surname = map.get(People.keyType.SURNAME);
        String dni = map.get(People.keyType.DNI);
        String email = map.get(People.keyType.EMAIL);
        String phone = map.get(People.keyType.PHONE);
        People person = new Person(dni, name, surname, phone, email);
        return person;
    }

}
