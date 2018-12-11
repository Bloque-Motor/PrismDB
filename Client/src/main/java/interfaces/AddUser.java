package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddUser extends Remote {

    public void setName (String name) throws RemoteException;
    public void setSurname (String surname) throws RemoteException;
    public void setDNI (String dni) throws RemoteException;
    public void setTelephone (String telephone) throws RemoteException;
    public void setEmail (String email) throws RemoteException;
}
