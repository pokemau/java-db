package db;

import com.example.csit228f2_2.Note;

import java.sql.*;
import java.util.ArrayList;

public class SQLConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/taneca_java";
    public static final String USERNAME = "pokemau";
    public static final String PASSWORD = "123456";

    public static int CURRENT_USER_ID = -1;
    public static Note currentNote = new Note();

    public static Connection getConnection() {
        Connection c;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    public static void InitTables() {
        try (Connection c = getConnection();
             Statement statement = c.createStatement()) {

            c.setAutoCommit(false);

            String createUserTable =
                    "CREATE TABLE IF NOT EXISTS tbluser (" +
                    "userID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(100) NOT NULL)";
            statement.addBatch(createUserTable);

            String createNotesTable =
                    "CREATE TABLE IF NOT EXISTS tblnotes (" +
                    "noteID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(100) NOT NULL," +
                    "content VARCHAR(700) NOT NULL," +
                    "userID INT," +
                    "FOREIGN KEY (userID) REFERENCES tbluser(userID))";
            statement.addBatch(createNotesTable);

            statement.executeBatch();
            c.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int createNote(String title, String content) {
        String query = "INSERT INTO tblnotes(userID, title, content) VALUES(?, ?, ?)";

        try (Connection conn = getConnection();
            PreparedStatement s = conn.prepareStatement(query)) {

            s.setInt(1, CURRENT_USER_ID);
            s.setString(2, title);
            s.setString(3, content);

            return s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void deleteNote(int noteID) {
        String query = "DELETE FROM tblnotes WHERE noteID=?";

        try (Connection c = getConnection();
        PreparedStatement s = c.prepareStatement(query)) {

            s.setInt(1, noteID);

            s.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateNote(String title, String content,int noteID) {
        String query = "UPDATE tblnotes SET title = ?, content = ? WHERE noteID= ?";

        try (Connection conn = getConnection();
             PreparedStatement s = conn.prepareStatement(query)) {

            s.setString(1, title);
            s.setString(2, content);
            s.setInt(3, noteID);

            s.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Note> getNotesFromDB() {
        ArrayList<Note> notes = new ArrayList<>();

        String query = "SELECT * FROM tblnotes WHERE userID=?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, CURRENT_USER_ID);

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Note n = new Note(res.getInt("noteID"),
                        res.getInt("userID"),
                        res.getString("title"),
                        res.getString("content"));

                notes.add(n);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return notes;
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
