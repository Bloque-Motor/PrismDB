import Entities.Person;
import Handlers.Database;

import java.util.HashMap;

public class ServerApp {

    public static void main(String[] args) {
        System.out.println("[*] Database Tests");
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
        else System.out.println("[+] DB Tests Passed!");
    }
}

/*
 *  Database Data
 * ('000111222X', 'user1', 'surname1', '+34666111000', 'user1@gmail.com'),
 * ('333444555-A', 'user2', 'surname2', '+34666222000', 'user2@yahoo.com'),
 * ('666777888-B', 'user3', 'surname3', '+34666333000', 'user3@outlook.com'),
 * ('999000111 C', 'user4', 'surname4', '+34666444000', 'user4@mail.com')
 */