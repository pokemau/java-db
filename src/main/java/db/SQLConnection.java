package db;

import java.sql.*;

public class SQLConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/taneca_java";
    public static final String USERNAME = "pokemau";
    public static final String PASSWORD = "123456";


    public static Connection getConnection() {
        Connection c = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("CONNECTED TO THE DATABASE");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return c;
    }

    public static String updateUserPassword(String username, String password, String newPassword) {


        return "";
    }

    public static boolean getUser(String username, String password) {

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM tblUser WHERE username=? AND password=?")) {

            statement.setString(1, username);
            statement.setString(2, password);


            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return true;
            }

            return createUser(username, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createUser(String username, String password) {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO tblUser(username, password) VALUES(?, ?)")) {

            statement.setString(1, username);
            statement.setString(2, password);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
