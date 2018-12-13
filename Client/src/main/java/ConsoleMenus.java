import interfaces.People;

import java.rmi.RemoteException;
import java.util.Scanner;

class ConsoleMenus {

    private static Scanner scanner = new Scanner(System.in);


    static void mainMenu() {

        System.out.println("////////// Welcome to PrismDB \\\\\\\\\\");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Añadir Persona \n2. Buscar Persona \n3. Exit");
        int option = scanner.nextInt();
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

    private static void addUserMenu() {
        System.out.println("Introduzca el nombre: ");
        String name = scanner.nextLine();
        System.out.println("Introduzca el apellido: ");
        String surname = scanner.nextLine();
        String dni = dniInput(null);
        String phone = phoneInput(null);
        String email = emailInput(null);

        ClientApp.addUser(name, surname, dni, phone, email);
    }

    private static void searchUserMenu() {
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
        String oldDni = null;

        if (res == null) System.out.println("Sin resultados.");
        else {
            try {
                System.out.println("Nombre: " + res.getName());
                System.out.println("Apellidos: " + res.getSurname());
                System.out.println("DNI: " + res.getDni());
                oldDni = res.getDni();
                System.out.println("Teléfono: " + res.getTelephone());
                System.out.println("E-mail: " + res.getEmail());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        searchUserSubMenu(oldDni);
    }

    private static void searchUserSubMenu(String oldDni) {
        if (oldDni != null) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Editar Usuario \n2. Eliminar Usuario\n3.Menú Principal");
            int option = scanner.nextInt();
            switch (option) {
                case 1: {
                    editUserMenu(oldDni);
                }
                case 2: {
                    deleteUserMenu(oldDni);
                }
                case 3: {
                    mainMenu();
                }
                default: {
                    System.out.println("Error");
                    searchUserSubMenu(oldDni);
                }
            }
        } else {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Reintroducir Datos \n2. Menú Principal");
            int option = scanner.nextInt();
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

    private static void editUserMenu(String oldDni) {
        String[] data = editUserSubMenu();

        System.out.println("Estos son los nuevos datos: ");
        System.out.println("Nombre: " + data[0]);
        System.out.println("Apellidos: " + data[1]);
        System.out.println("DNI: " + data[2]);
        System.out.println("Teléfono: " + data[3]);
        System.out.println("E-mail: " + data[4]);
        System.out.println("¿Es correcto? [y]/n");
        String option = scanner.nextLine();
        switch (option) {
            case "y": {
                ClientApp.updateUser(oldDni, data[0], data[1], data[2], data[3], data[4]);
                mainMenu();
            }
            case "n": {
                editUserMenu(oldDni);
            }
            default: {
                ClientApp.updateUser(oldDni, data[0], data[1], data[2], data[3], data[4]);
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

    private static void deleteUserMenu(String dni) {
        System.out.println("¿Está seguro de que desea eliminar los datos? [y]/n");
        String option = scanner.nextLine();
        switch (option) {
            case "y": {
                ClientApp.deleteUser(dni);
                mainMenu();
            }
            case "n": {
                searchUserSubMenu(dni);
            }
            default: {
                ClientApp.deleteUser(dni);
                mainMenu();
            }
        }
    }

    private static String dniInput(String edit) {
        String dni;
        do {
            System.out.println("Introduzca el" + edit + " dni: ");
            dni = scanner.nextLine();
            if (!isDni(dni)) System.out.println("Formato de DNI incorrecto.");
        } while (!isDni(dni));

        return dni;
    }

    private static String phoneInput(String edit) {
        String phone;
        do {
            System.out.println("Introduzca el" + edit + " teléfono: ");
            phone = scanner.nextLine();
            if (!isTelephone(phone)) System.out.println("Formato de teléfono incorrecto.");
        } while (!isTelephone(phone));

        return phone;
    }

    private static String emailInput(String edit) {
        String email;
        do {
            System.out.println("Introduzca el" + edit + " e-mail: ");
            email = scanner.nextLine();
            if (!isEmail(email)) System.out.println("Formato de e-mail incorrecto.");
        } while (!isEmail(email));

        return email;
    }

    private static boolean isDni(String dni) {
        String pattern = "[0-9]{7,8}(\\s*-?\\s*)?[A-Za-z]";
        return dni.matches(pattern);
    }

    private static boolean isEmail(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(pattern);
    }

    private static boolean isTelephone(String telephone) {
        String pattern = "(\\+(0[1-9][0-9]?|[1-9][0-9]{0,2}))?\\s*[1-9]{3}\\s*[0-9]{3}\\s*[0-9]{3}";
        return telephone.matches(pattern);
    }
}
