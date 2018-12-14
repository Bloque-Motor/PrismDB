import Interfaces.People;

import java.rmi.RemoteException;
import java.util.Scanner;

class ConsoleMenus {

    private static Scanner scanner = new Scanner(System.in);


    static void mainMenu() throws RemoteException {

        System.out.println("////////// Welcome to PrismDB \\\\\\\\\\\\\\\\\\\\");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Añadir Persona \n2. Buscar Persona \n3. Exit");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1: {
                addUserMenu();
            }
            case 2: {
                searchUserMenu();
            }
            case 3: {
                System.exit(0);
            }
            default: {
                System.out.println("Error");
                mainMenu();
            }
        }
    }

    private static void addUserMenu() throws RemoteException {
        System.out.println("Introduzca el nombre: ");
        String name = scanner.nextLine();
        System.out.println("Introduzca el apellido: ");
        String surname = scanner.nextLine();
        String dni = dniInput(null);
        String phone = phoneInput(null);
        String email = emailInput(null);

        ClientApp.addUser(name, surname, dni, phone, email);

        mainMenu();
    }

    private static void searchUserMenu() throws RemoteException {
        System.out.println("Introduzca un nombre: ");
        String name = scanner.nextLine();
        System.out.println("Introduzca un apellido: ");
        String surname = scanner.nextLine();
        System.out.println("Introduzca un dni: ");
        String dni = scanner.nextLine();
        System.out.println("Introduzca un telefono: ");
        String phone = scanner.nextLine();
        System.out.println("Introduzca un e-mail: ");
        String email = scanner.nextLine();

        People res = ClientApp.search(name, surname, dni, phone, email);
        //String oldDni = null;

        if (res == null) System.out.println("Sin resultados.");
        else {
            try {
                System.out.println("Nombre: " + res.getName());
                System.out.println("Apellidos: " + res.getSurname());
                System.out.println("DNI: " + res.getDni());
                //oldDni = res.getDni();
                System.out.println("Teléfono: " + res.getTelephone());
                System.out.println("E-mail: " + res.getEmail());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        searchUserSubMenu(res);
    }

    private static void searchUserSubMenu(People res) throws RemoteException {
        if (res != null) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Editar Usuario \n2. Eliminar Usuario\n3.Menú Principal");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1: {
                    editUserMenu(res);
                }
                case 2: {
                    deleteUserMenu(res);
                }
                case 3: {
                    mainMenu();
                }
                default: {
                    System.out.println("Error");
                    searchUserSubMenu(res);
                }
            }
        } else {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Reintroducir Datos \n2. Menú Principal");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1: {
                    searchUserMenu();
                }
                case 2: {
                    mainMenu();
                }
                default: {
                    System.out.println("Error");
                    searchUserSubMenu(null);
                }
            }
        }
    }

    private static void editUserMenu(People res) throws RemoteException {
        String[] data = editUserSubMenu();
        String oldDni = res.getDni();

        System.out.println("Estos son los nuevos datos: ");
        System.out.println("Nombre: " + data[0]);
        System.out.println("Apellidos: " + data[1]);
        System.out.println("DNI: " + data[2]);
        System.out.println("Teléfono: " + data[3]);
        System.out.println("E-mail: " + data[4]);
        System.out.println("¿Es correcto? [y]/n");
        String option = scanner.nextLine();
        res.setName(data[0]);
        res.setSurname(data[1]);
        res.setDni(data[2]);
        res.setTelephone(data[3]);
        res.setEmail(data[4]);

        switch (option) {
            case "y": {
                ClientApp.updateUser(oldDni, res);
                mainMenu();
            }
            case "n": {
                editUserMenu(res);
            }
            default: {
                ClientApp.updateUser(oldDni, res);
                mainMenu();
            }
        }
    }

    private static String[] editUserSubMenu() {
        String[] res = new String[5];
        System.out.println("Introduzca el nuevo nombre: ");
        res[0] = scanner.nextLine();
        System.out.println("Introduzca el nuevo apellido: ");
        res[1] = scanner.nextLine();
        res[2] = dniInput(" nuevo");
        res[3] = phoneInput(" nuevo");
        res[4] = emailInput(" nuevo");
        return res;
    }

    private static void deleteUserMenu(People res) throws RemoteException {
        System.out.println("¿Está seguro de que desea eliminar los datos? [y]/n");
        String option = scanner.nextLine();
        switch (option) {
            case "y": {
                ClientApp.deleteUser(res);
                mainMenu();
            }
            case "n": {
                searchUserSubMenu(res);
            }
            default: {
                ClientApp.deleteUser(res);
                mainMenu();
            }
        }
    }

    private static String dniInput(String edit) {
        String dni;
        do {
            if (edit != null) System.out.println("Introduzca el" + edit + " dni: ");
            else System.out.println("Introduzca el dni: ");
            dni = scanner.nextLine();
            if (isNotDni(dni)) System.out.println("Formato de DNI incorrecto.");
        } while (isNotDni(dni));

        return dni;
    }

    private static String phoneInput(String edit) {
        String phone;
        do {
            if (edit != null) System.out.println("Introduzca el" + edit + " teléfono: ");
            else System.out.println("Introduzca el teléfono: ");
            phone = scanner.nextLine();
            if (isNotTelephone(phone)) System.out.println("Formato de teléfono incorrecto.");
        } while (isNotTelephone(phone));

        return phone;
    }

    private static String emailInput(String edit) {
        String email;
        do {
            if (edit != null) System.out.println("Introduzca el" + edit + " e-mail: ");
            else System.out.println("Introduzca el e-mail: ");
            email = scanner.nextLine();
            if (isNotEmail(email)) System.out.println("Formato de e-mail incorrecto.");
        } while (isNotEmail(email));

        return email;
    }

    private static boolean isNotDni(String dni) {
        String pattern = "[0-9]{7,8}(\\s*-?\\s*)?[A-Za-z]";
        return !dni.matches(pattern);
    }

    private static boolean isNotEmail(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
        return !email.matches(pattern);
    }

    private static boolean isNotTelephone(String telephone) {
        String pattern = "(\\+(0[1-9][0-9]?|[1-9][0-9]{0,2}))?\\s*[1-9]{3}\\s*[0-9]{3}\\s*[0-9]{3}";
        return !telephone.matches(pattern);
    }
}
