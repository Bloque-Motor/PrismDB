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
            Prism obj = new PrismImp();

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
}