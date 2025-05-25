package simpledatabase;
import java.sql.*;

public class Simpledatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/demodb";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    private static Connection conn;

    public static void main(String[] args) {
        connect();
        
        //addUser("Sam", "samcode@gmail.com");
        showAllUsers();
        //updateUser(1, "The Cooler Sam", "coolsam@gmail.com");
        deleteUser(4);
        //closeConnection();
    }

//hell yeah
    
    
    
    //METHOD 1 CONNECT TO DB
    public static void connect() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database yippie.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    
    //METHOD 2 ADD STUFF TO DB
    public static void addUser(String name, String email) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("User added.");
        } catch (SQLException e) {
            System.out.println("Add failed: " + e.getMessage());
        }
    }
    
    
    //METHOD 3 OUTPUT STUFF FROM DB
    public static void showAllUsers() {
    String sql = "SELECT * FROM users";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        System.out.println("User List:");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");

            System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
        }

    } catch (SQLException e) {
        System.out.println("Failed to fetch users: " + e.getMessage());
    }
}
    
    
    //METHOD 4 UPDATE STUFF FROM DB
    public static void updateUser(int id, String name, String email) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("User updated.");
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
    

    //METHOD 5 DELETE STUFF FROM DB
    public static void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("User deleted.");
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    
    //CLOSE DB
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
