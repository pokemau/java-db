package db;

import java.sql.*;

public class SQLConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/taneca_java";
    public static final String USERNAME = "pokemau";
    public static final String PASSWORD = "123456";

    public static int CURRENT_USER_ID = -1;


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

    public static boolean checkIfUsernameIsUnique(String username) {
        String query = "SELECT * FROM tbluser WHERE username=?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);

            ResultSet res = statement.executeQuery();

            return !res.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteUserAccount() {
        String query = "DELETE FROM tbluser WHERE userID=?";

        try (Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, CURRENT_USER_ID);

            int resultRows = statement.executeUpdate();
            return resultRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateUsername(String newUsername) {
        String query = "UPDATE tbluser SET username= ? WHERE userID = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, newUsername);
            statement.setInt(2, CURRENT_USER_ID);

            int resultRows = statement.executeUpdate();
            return resultRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateUserPassword(String newPassword) {
        String query = "UPDATE tbluser SET password = ? WHERE userID = ?";

        try (Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, newPassword);
            statement.setInt(2, CURRENT_USER_ID);

            int resultRows = statement.executeUpdate();
            return resultRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getUser(String username, String password) {
        String query = "SELECT * FROM tblUser WHERE username=? AND password=?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);


            ResultSet res = statement.executeQuery();

            if (res.next()) {
                CURRENT_USER_ID = res.getInt("userID");

                System.out.println("CURR ID: " + CURRENT_USER_ID);
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createUser(String username, String password) {
        String query = "INSERT INTO tblUser(username, password) VALUES(?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, username);
            statement.setString(2, password);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        CURRENT_USER_ID = generatedKey.getInt(1);


                        System.out.println("CURR ID: " + CURRENT_USER_ID);
                        return true;
                    }
                    return false;
                }
            }

            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
