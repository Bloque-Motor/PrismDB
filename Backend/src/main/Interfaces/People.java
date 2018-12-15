package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface People extends Remote {

    String getName() throws RemoteException;

    String getSurname() throws RemoteException;

    String getDni() throws RemoteException;

    String getTelephone() throws RemoteException;

    String getEmail() throws RemoteException;

    void setName(String name) throws RemoteException;

    void setSurname(String surname) throws RemoteException;

    void setDni(String dni) throws RemoteException;

    void setTelephone(String telephone) throws RemoteException;

    void setEmail(String email) throws RemoteException;

    // For Name and Surname
    // Admits compound names and surnames, check them together
    static boolean isName(String name){
        return name.length() >= 3 && name.length() <= 50;
    }

    static boolean isDni(String dni) {
        String pattern = "[0-9]{7,8}(\\s*-?\\s*)?[A-Za-z]";
        return dni.length() >= 8 && dni.length() <= 11 && dni.matches(pattern);
    }

    static boolean isTelephone(String telephone) {
        String pattern = "(\\+(0[1-9][0-9]?|[1-9][0-9]{0,2}))?\\s*[1-9]{3}\\s*[0-9]{3}\\s*[0-9]{3}";
        return telephone.length() >= 9 && telephone.length() <= 15 && telephone.matches(pattern);
    }

    static boolean isEmail(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.length() >= 8 && email.length() <= 50 && email.matches(pattern);
    }
}
