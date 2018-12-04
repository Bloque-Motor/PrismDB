package Handlers;

import Entities.DirUser;

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

    public static void connect() {
        try {
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

    // Entities methods

    public static boolean addUser(DirUser user) {
        int status;
        connect();

        try {
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

        return true;
    }

    public static boolean removeUserById(int uid) {
        int status;
        connect();

        try {
            cs = conn.prepareCall("{call removeuser(?, ?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, uid);
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

        return true;
    }

    public static boolean removeUserByFullName(String name, String surname) {
        int status;
        connect();
        int uid = getUserByFullName(name, surname).getId();

        try {
            cs = conn.prepareCall("{call removeuser(?, ?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, uid);
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

        return true;
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
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String dni = rs.getString("dni");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                DirUser user = new DirUser(id, name, surname, dni, telephone, email);
                userslist.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            rollbackQuery();
            return null;
        }

        return userslist.toArray(new DirUser[userslist.size()]);
    }

    public static DirUser getUserById(int id) {
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
                user = new DirUser(id, name, surname, dni, telephone, email);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            rollbackQuery();
            return null;
        }

        return user;
    }

    public static DirUser getUserByFullName(String name, String surname) {
        DirUser user = null;
        String q = "SELECT * FROM " + dbname + ".users WHERE name = " + name + " AND surname = " + surname;

        try {
            connect();
            ps = conn.prepareStatement(q);
            rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                int id = rs.getInt("id");
                String dni = rs.getString("dni");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                user = new DirUser(id, name, surname, dni, telephone, email);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            rollbackQuery();
            return null;
        }

        return user;
    }

    public static boolean updateUserName(int id, String newname) {
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

    public static boolean updateUserSurname(int id, String newsurname) {
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

    public static boolean updateUserDni(int id, String newdni) {
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

    public static boolean updateUserTelephone(int id, String newtelephone) {
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

    public static boolean updateUserEmail(int id, String newemail) {
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
