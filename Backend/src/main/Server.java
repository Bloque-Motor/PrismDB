import Handlers.PrismImp;
import Interfaces.Prism;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends PrismImp {

    public Server() {}
    public static void main(String args[]) {
        try {
            // Instantiating the implementation class
            PrismImp obj = new PrismImp();

            // Exporting the object of implementation class (here we are exporting the remote object to the stub)
            Prism stub = (Prism) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();

            registry.bind("Prism", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    //TESTS SHOULD BE DONE WITH JUNIT

        /*System.out.println("[*] Database Tests");
        boolean done;
        Person target1, target2;

        // It is necessary to reset the DB everytime
        done = Database.addUser(new Person("123456789-Z", "NewUser", "NewSurname", "+1 666000666", "fake_email@fake.com"));
        done = Database.deleteUser(new Person("000111222X", "user1", "surname1", "+34666111000", "user1@gmail.com"));
        done = Database.updateUser("999000111 C", new Person("999000111 C", "user4_modified", "surname4_modified", "+34666444000", "user4_modified@mail.com"));

        target1 = (Person) Database.search(new HashMap<Person.KeyType, String>() {{
            put(Person.KeyType.NAME, "user3");
            put(Person.KeyType.TELEPHONE, "+34666333000");
        }});

        target2 = (Person) Database.search(new HashMap<Person.KeyType, String>() {{
            put(Person.KeyType.DNI, "000000000X");
            put(Person.KeyType.EMAIL, "not_found@gmail.com");
        }});

        System.out.println("target1: " + target1);
        System.out.println("target2: " + target2);

        if (!done) System.out.println("[-] Error in addUser, deleteUser, updateUser");
        else if (target1 == null) System.out.println("[-] Error finding target1");
        else if (target2 != null) System.out.println("[-] Error finding target2");
        else System.out.println("[+] DB Tests Passed!");*/

}

/*
 *  Database Data
 * ('000111222X', 'user1', 'surname1', '+34666111000', 'user1@gmail.com'),
 * ('333444555-A', 'user2', 'surname2', '+34666222000', 'user2@yahoo.com'),
 * ('666777888-B', 'user3', 'surname3', '+34666333000', 'user3@outlook.com'),
 * ('999000111 C', 'user4', 'surname4', '+34666444000', 'user4@mail.com')
 */