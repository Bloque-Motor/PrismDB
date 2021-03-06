package Handlers;

import Entities.Person;
import Interfaces.People;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/*
 * This class serves as an intermediate layer between
 * the server and the database. Clients request
 * information from the database to the server and it
 * obtains it through this manager.
 *
 *    /-----------------------------------\
 * client <-> server <-> dbhandler <-> database
 *
 * In this way, the application is modularized and tasks
 * are separated.
 *
 * Every method to get or post information from/to the
 * database must go here.
 *
 */
public class Database {

    private static String host = "localhost";
    private static int port = 3306;
    private static String dbname = "prismdb";
    private static String user = "dir_admin";
    private static String password = "dir_admin";
    private static String properties = "?noAccessToProcedureBodies=true";

    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static CallableStatement cs;

    // Opening and closing the connection are expensive operations,
    // it is recommended not to close it at the end of an operation
    // if many queries are made frequently.
    private static boolean autoDisconnect = false;

    private static void connect() {
        try {
            // Doesnt open the connection twice
            if (conn != null && !conn.isClosed()) return;
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname + properties, user, password);
            conn.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void disconnect() {
        try {
            if (conn != null) conn.close();
            if (ps != null) ps.close();
            if (rs != null) rs.close();
            if (cs != null) cs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean addUser(People user) {
        boolean error;

        try {
            connect();
            cs = conn.prepareCall("{call adduser(?,?,?,?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, user.getDni());
            cs.setString(3, user.getName());
            cs.setString(4, user.getSurname());
            cs.setString(5, user.getTelephone());
            cs.setString(6, user.getEmail());
            cs.execute();
            conn.commit();

            error = (cs.getInt(1) != 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            error = true;
        }

        if (error) {
            rollbackQuery();
        }

        if (autoDisconnect) {
            disconnect();
        }

        return !error;
    }

    public static boolean deleteUser(People user) {
        boolean error;

        try {
            String dni = user.getDni();

            connect();
            cs = conn.prepareCall("{call removeuser(?, ?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, dni);
            cs.execute();
            conn.commit();

            error = (cs.getInt(1) != 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            error = true;
        }

        if (error) {
            rollbackQuery();
        }

        if (autoDisconnect) {
            disconnect();
        }

        return !error;
    }

    public static boolean updateUser(String dniOld, People user) {
        boolean error;

        try {
            connect();
            cs = conn.prepareCall("{call updateuser(?,?,?,?,?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, dniOld);
            cs.setString(3, user.getDni());
            cs.setString(4, user.getName());
            cs.setString(5, user.getSurname());
            cs.setString(6, user.getTelephone());
            cs.setString(7, user.getEmail());
            cs.execute();
            conn.commit();

            error = (cs.getInt(1) != 0);
            System.out.println("output is "+cs.getInt(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            error = true;
        }

        if (error) {
            rollbackQuery();
        }

        if (autoDisconnect) {
            disconnect();
        }

        return !error;
    }

    public static ArrayList<People> search(Map<People.keyType, String> parameters) {
        if (parameters.size() == 0) return null;

        People user = null;
        Iterator<Map.Entry<People.keyType, String>> it = parameters.entrySet().iterator();
        Map.Entry<People.keyType, String> entry;
        People.keyType ekey;
        String evalue, q = " SELECT * FROM " + dbname + ".users WHERE ";
        ArrayList<String> qattrs = new ArrayList<>();
        ArrayList<String> qvalues = new ArrayList<>();

        while (it.hasNext()) {
            entry = it.next();
            ekey = entry.getKey();
            evalue = entry.getValue();

            switch (ekey) {
                case NAME:
                    qattrs.add("name = ?");
                    break;

                case SURNAME:
                    qattrs.add("surname = ?");
                    break;

                case DNI:
                    qattrs.add("dni = ?");
                    break;

                case PHONE:
                    qattrs.add("telephone = ?");
                    break;

                case EMAIL:
                    qattrs.add("email = ?");
                    break;

                default:
                    return null;
            }

            qvalues.add(evalue);
        }

        for (int i = 0, size = qattrs.size(); i < size; i++) {
            q += " " + qattrs.get(i) + " ";
            if (i < size - 1) {
                q += " AND ";
            }
        }

        ArrayList<People> results = new ArrayList<>();

        try {
            connect();
            ps = conn.prepareStatement(q);

            for (int i = 0, size = qvalues.size(); i < size; i++) {
                ps.setString(i + 1, qvalues.get(i));
            }

            rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                String dni = rs.getString("dni");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                user = new Person(dni, name, surname, telephone, email);
                results.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return results;
    }

    public static void setAutoDisconnect(boolean autoDisconnect) {
        Database.autoDisconnect = autoDisconnect;
    }

    private static void rollbackQuery() {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
