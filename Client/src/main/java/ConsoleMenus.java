import Interfaces.People;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
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
                break;
            }
            case 2: {
                searchUserMenu();
                break;
            }
            case 3: {
                System.exit(0);
            }
            default: {
                System.out.println("Error: Unknown option '"+option+"'");
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

        boolean res = ClientApp.addUser(dni, name, surname, phone, email);

        if (res) System.out.println("Operacion completada con exito");
        if (!res) System.out.println("Internal Server Error");

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

        ArrayList<Map<People.keyType, String>> res = ClientApp.search(dni, name, surname, phone, email);
        //String oldDni = null;

        Map<People.keyType, String> aux = null;
        if (res == null || res.isEmpty()) {
            System.out.println("Sin resultados.");
            mainMenu();
        } else {
            int i = 1;
            for (Map<People.keyType, String> var : res) {
                System.out.println("Resultado " + i);
                i++;
                System.out.println("Nombre(s): " + var.get(People.keyType.NAME));
                System.out.println("Apellido(s): " + var.get(People.keyType.SURNAME));
                System.out.println("DNI: " + var.get(People.keyType.DNI));
                //oldDni = res.getDni();
                System.out.println("Teléfono: " + var.get(People.keyType.PHONE));
                System.out.println("E-mail: " + var.get(People.keyType.EMAIL));
            }

            System.out.println("Seleccione un resultado: ");
            int index = scanner.nextInt();
            scanner.nextLine();
            aux = res.get(index - 1);
        }


        searchUserSubMenu(aux);
    }

    private static void searchUserSubMenu(Map<People.keyType, String> res) throws RemoteException {
        if (res != null) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Editar Usuario \n2. Eliminar Usuario\n3.Menú Principal");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1: {
                    editUserMenu(res);
                    break;
                }
                case 2: {
                    deleteUserMenu(res);
                    break;
                }
                case 3: {
                    mainMenu();
                    break;
                }
                default: {
                    System.out.println("Error: Unknown option '"+option+"'");
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
                    break;
                }
                case 2: {
                    mainMenu();
                    break;
                }
                default: {
                    System.out.println("Error: Unknown option '"+option+"'");
                    searchUserSubMenu(null);
                }
            }
        }
    }

    private static void editUserMenu(Map<People.keyType, String> res) throws RemoteException {
        String[] data = editUserSubMenu();
        String oldDni = res.get(People.keyType.DNI);

        System.out.println("Estos son los nuevos datos: ");
        System.out.println("Nombre: " + data[0]);
        System.out.println("Apellidos: " + data[1]);
        System.out.println("DNI: " + data[2]);
        System.out.println("Teléfono: " + data[3]);
        System.out.println("E-mail: " + data[4]);
        System.out.println("¿Es correcto? [y]/n");
        String option = scanner.nextLine().toLowerCase();
        if (!data[0].equals("")) res.put(People.keyType.NAME, data[0]);

        if (!data[1].equals("")) res.put(People.keyType.SURNAME, data[1]);
        if (!data[2].equals("")) res.put(People.keyType.DNI, data[2]);
        if (!data[3].equals("")) res.put(People.keyType.PHONE, data[3]);
        if (!data[4].equals("")) res.put(People.keyType.EMAIL, data[4]);

        if (option.equals("n") || option.equals("no")) editUserMenu(res);

        boolean successful = ClientApp.updateUser(oldDni, res);
        if (successful) System.out.println("Operacion completada con exito");
        else System.out.println("Internal Server Error");
        mainMenu();
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

    private static void deleteUserMenu(Map<People.keyType, String> res) throws RemoteException {
        System.out.println("¿Está seguro de que desea eliminar los datos? [y]/n");
        String option = scanner.nextLine();
        if (option.equals("n") || option.equals("no")) searchUserSubMenu(res);

        boolean successful = ClientApp.deleteUser(res);
        if (successful) System.out.println("Operacion completada con exito");
        else System.out.println("Internal Server Error");
        mainMenu();
    }

    private static String dniInput(String edit) {
        String dni;
        do {
            if (edit != null) {
                System.out.println("Introduzca el" + edit + " dni: ");
                dni = scanner.nextLine();
                return dni;
            }
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
        return !(dni.length() >= 8 && dni.length() <= 11 && dni.matches(pattern));
    }

    private static boolean isNotTelephone(String telephone) {
        String pattern = "(\\+(0[1-9][0-9]?|[1-9][0-9]{0,2}))?\\s*[1-9]{3}\\s*[0-9]{3}\\s*[0-9]{3}";
        return !(telephone.length() >= 9 && telephone.length() <= 15 && telephone.matches(pattern));
    }

    private static boolean isNotEmail(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
        return !(email.length() >= 8 && email.length() <= 50 && email.matches(pattern));
    }
}
