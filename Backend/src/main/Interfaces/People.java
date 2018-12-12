package Interfaces;

public interface People extends java.rmi.Remote {

    String getName() throws java.rmi.RemoteException;

    String getSurname() throws java.rmi.RemoteException;

    String getDni() throws java.rmi.RemoteException;

    String getTelephone() throws java.rmi.RemoteException;

    String getEmail() throws java.rmi.RemoteException;

    void setName(String name) throws java.rmi.RemoteException;

    void setSurname(String surname) throws java.rmi.RemoteException;

    void setDni(String dni) throws java.rmi.RemoteException;

    void setTelephone(String telephone) throws java.rmi.RemoteException;

    void setEmail(String email) throws java.rmi.RemoteException;

    static boolean isDni(String dni) {
        String pattern = "[0-9]{7,8}(\\s*-?\\s*)?[A-Za-z]";
        return dni.matches(pattern);
    }

    static boolean isTelephone(String telephone) {
        String pattern = "(\\+(0[1-9][0-9]?|[1-9][0-9]{0,2}))?\\s*[1-9]{3}\\s*[0-9]{3}\\s*[0-9]{3}";
        return telephone.matches(pattern);
    }

    static boolean isEmail(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(pattern);
    }
}
