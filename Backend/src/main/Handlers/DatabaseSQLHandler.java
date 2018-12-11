package Handlers;

import Entities.DirUser;
import org.jetbrains.annotations.Contract;

import java.sql.*;
import java.util.ArrayList;

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
 * -- HOW TO USE --
 * DatabaseSQLHandler
 *
 * DatabaseSQLHandler.addUser(new DirUser());
 * DatabaseSQLHandler.removeUser(DirUser.KeyType.FULLNAME, "Pepe, Martinez")
 * DatabaseSQLHandler.removeUser(DirUser.KeyType.DNI, "000111222F")
 * DatabaseSQLHandler.removeUser(DirUser.KeyType.EMAIL, "example@gmail.com")
 * DatabaseSQLHandler.updateUser(DirUser.KeyType.DNI, "000111222-A", "44455566-B")
 * DatabaseSQLHandler.updateUser(DirUser.KeyType.TELEPHONE, "123456789", "+34666555444")
 * DatabaseSQLHandler.getUsers();
 * DatabaseSQLHandler.setAutoDisconnect(true);
 *
 * -- WIP --
 * DatabaseSQLHandler.grepUser()|getUser();
 */
public class DatabaseSQLHandler {

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

    public static void disconnect() {
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

    // PUBLIC METHODS

    public static boolean addUser(DirUser user) {
        int status;

        try {
            connect();
            cs = conn.prepareCall("{call adduser(?,?,?,?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, user.getName());
            cs.setString(3, user.getSurname());
            cs.setString(4, user.getDni());
            cs.setString(5, user.getTelephone());
            cs.setString(6, user.getEmail());
            cs.execute();
            conn.commit();

            status = cs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            status = -1;
        }

        if (status != 0) {
            rollbackQuery();
            return false;
        }

        if (autoDisconnect) {
            disconnect();
        }

        return true;
    }

    public static boolean removeUser(DirUser.KeyType key, String target) {
        int status, id;

        try {
            connect();

            id = getId(key, target);
            if (id < 1) return false;

            cs = conn.prepareCall("{call removeuser(?, ?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, id);
            cs.execute();
            conn.commit();

            status = cs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            status = -1;
        }

        if (status != 0) {
            rollbackQuery();
            return false;
        }

        if (autoDisconnect) {
            disconnect();
        }

        return true;
    }

    public static boolean updateUser(DirUser.KeyType key, String target, String newvalue) {
        int id = 0;

        if (isValidKeyValue(key, newvalue)) {
            id = getId(key, target);
            if (id < 1) return false;
            callAppropriateDatabaseUpdateMethod(id, key, newvalue);
            return true;
        }

        if (autoDisconnect) {
            disconnect();
        }

        return false;
    }

    public static DirUser[] getUsers() {
        ArrayList<DirUser> userslist = new ArrayList<DirUser>(0);
        String q = "SELECT * FROM " + dbname + ".users";

        try {
            connect();
            ps = conn.prepareStatement(q);
            rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String dni = rs.getString("dni");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                DirUser user = new DirUser(name, surname, dni, telephone, email);
                userslist.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            rollbackQuery();
            return null;
        }

        if (autoDisconnect) {
            disconnect();
        }

        return userslist.toArray(new DirUser[userslist.size()]);
    }

    // TODO change or remove
    private static DirUser getUserById(int id) {
        DirUser user = null;
        String q = "SELECT * FROM " + dbname + ".users WHERE id = " + id;

        try {
            connect();
            ps = conn.prepareStatement(q);
            rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String dni = rs.getString("dni");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                user = new DirUser(name, surname, dni, telephone, email);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            rollbackQuery();
            return null;
        }

        return user;
    }

    // Greps a user with user specified params
    public static DirUser grepUser() {
        return null;
    }

    public static void setAutoDisconnect(boolean autoDisconnect) {
        DatabaseSQLHandler.autoDisconnect = autoDisconnect;
    }

    // PRIVATE METHODS -- OPAQUE FOR THE USER.

    private static int getId(DirUser.KeyType key, String target) {
        int id = 0;
        String q = "SELECT id FROM " + dbname + ".users WHERE ";

        switch (key) {
            case FULLNAME:
                String name = target.split(",")[0];
                String surname = target.split(",")[1];
                q += " name = " + name + " AND surname = " + surname;
                break;

            case DNI:
                q += " dni = " + target;
                break;

            case TELEPHONE:
                q += " telephone = " + target;
                break;

            case EMAIL:
                q += " email = " + target;
                break;

            default:
                throw new IllegalArgumentException();
        }

        try {
            connect();
            ps = conn.prepareStatement(q);
            rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            rollbackQuery();
            return -1;
        }

        if (autoDisconnect) {
            disconnect();
        }

        return id;
    }

    private static boolean isValidKeyValue(DirUser.KeyType key, String value) {
        switch (key) {
            case FULLNAME:
                String name = value.split(",")[0];
                String surname = value.split(",")[1];
                if (name.equals("") || surname.equals("")) return false;
                break;

            case DNI:
                if (!DirUser.isDni(value)) return false;
                break;

            case TELEPHONE:
                if (!DirUser.isTelephone(value)) return false;
                break;

            case EMAIL:
                if (!DirUser.isEmail(value)) return false;
                break;

            default:
                throw new IllegalArgumentException("Invalid parameter key: '" + key + "'");
        }
        return true;
    }

    // Call update in db format with user specified params
    private static void callAppropriateDatabaseUpdateMethod(int id, DirUser.KeyType key, String newvalue) {
        switch (key) {
            case FULLNAME:
                String name = newvalue.split(",")[0];
                String surname = newvalue.split(",")[1];
                updateUserName(id, name);
                updateUserSurname(id, surname);
                break;

            case DNI:
                updateUserDni(id, newvalue);
                break;

            case TELEPHONE:
                updateUserTelephone(id, newvalue);
                break;

            case EMAIL:
                updateUserEmail(id, newvalue);
                break;

            default:
                throw new IllegalArgumentException("Invalid parameter key: '" + key + "'");
        }
    }

    private static boolean updateUserName(int id, String newname) {
        int status;
        connect();

        try {
            cs = conn.prepareCall("{call updatename(?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, id);
            cs.setString(3, newname);
            cs.executeUpdate();
            conn.commit();

            status = cs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            status = -1;
        }

        if (status != 0) {
            rollbackQuery();
            return false;
        }

        return true;
    }

    private static boolean updateUserSurname(int id, String newsurname) {
        int status;
        connect();

        try {
            cs = conn.prepareCall("{call updatesurname(?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, id);
            cs.setString(3, newsurname);
            cs.executeUpdate();
            conn.commit();

            status = cs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            status = -1;
        }

        if (status != 0) {
            rollbackQuery();
            return false;
        }

        return true;
    }

    private static boolean updateUserDni(int id, String newdni) {
        int status;
        connect();

        try {
            cs = conn.prepareCall("{call updatedni(?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, id);
            cs.setString(3, newdni);
            cs.executeUpdate();
            conn.commit();

            status = cs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            status = -1;
        }

        if (status != 0) {
            rollbackQuery();
            return false;
        }

        return true;
    }

    private static boolean updateUserTelephone(int id, String newtelephone) {
        int status;
        connect();

        try {
            cs = conn.prepareCall("{call updatetelephone(?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, id);
            cs.setString(3, newtelephone);
            cs.executeUpdate();
            conn.commit();

            status = cs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            status = -1;
        }

        if (status != 0) {
            rollbackQuery();
            return false;
        }

        return true;
    }

    private static boolean updateUserEmail(int id, String newemail) {
        int status;
        connect();

        try {
            cs = conn.prepareCall("{call updateemail(?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, id);
            cs.setString(3, newemail);
            cs.executeUpdate();
            conn.commit();

            status = cs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            status = -1;
        }

        if (status != 0) {
            rollbackQuery();
            return false;
        }

        return true;
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
