package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *
 * Data Access Object (DAO) Class for the User Class.
 * Allows access to the User Table in the SQL database
 *
 */
public class UserDao {

    public UserDao(Connection c) {
        conn = c;
    }

    private Connection conn = null;

    /**
     * Checks to see if a certain username already exists in the database
     * @param username username to check in the table
     * @return true or false
     */
    public boolean doesUsernameExist(String username) {
        boolean exists = true;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM User WHERE Username = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            results = stmt.executeQuery();
            exists = results.next();
            stmt.close();
            results.close();
            return exists;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error was thrown, result is not accurate");
            return false;  // check to see if it actually worked
        }
    }

    /**
     * Adds a user to the database
     * @param u contains the user to add to the database
     */
    public void addUser(User u) throws SQLException {
        PreparedStatement stmt = null;
        String insert = "INSERT INTO User VALUES (?, ?, ?, ?, ?, ?, ?);";

        stmt = conn.prepareStatement(insert);
        stmt.setString(1, u.getUsername());
        stmt.setString(2, u.getPassword());
        stmt.setString(3, u.getEmail());
        stmt.setString(4, u.getFirstName());
        stmt.setString(5, u.getLastName());
        stmt.setString(6, u.getGender());
        stmt.setString(7, u.getPersonID());
        stmt.executeUpdate();
        stmt.close();
    }

    public String getPassword(String username) {
        String password;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM User WHERE Username = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            results = stmt.executeQuery();
            password = results.getString("Password");
            stmt.close();
            results.close();
            return password;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "An error has occurred";
        }
    }

    public String getPersonID(String username) {
        String personID;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM User WHERE Username = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            results = stmt.executeQuery();
            personID = results.getString("PersonID");
            stmt.close();
            results.close();
            return personID;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "An error has occurred";
        }
    }

    public User returnUser(String username) {
        User user = new User();

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM User WHERE Username = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            results = stmt.executeQuery();
            while (results.next()) {
                results.getString(1);
                String password = results.getString(2);
                String email = results.getString(3);
                String firstName = results.getString(4);
                String lastName = results.getString(5);
                String gender = results.getString(6);
                String personID = results.getString(7);
                user = new User(username, password, email, firstName, lastName, gender, personID);
            }
            stmt.close();
            results.close();
            return user;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
