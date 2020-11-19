package dao;

import model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *
 * Data Access Object (DAO) Class for the AuthToken Class.
 * Allows access to the AuthToken Table in the SQL database
 *
 */
public class AuthTokenDao {

    public AuthTokenDao(Connection c) {
        conn = c;
    }

    private Connection conn = null;

    /**
     * Checks to see if an AuthToken already exists in our database
     * @param a AuthToken String to be checked
     * @return true or false
     */

    public boolean doesAuthTokenExist(String a) {
        boolean exists = true;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM AuthToken WHERE AuthToken = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, a);
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
     * Adds an AuthToken to the current AuthToken database
     * @param a
     */
    public void addToAuthTable(AuthToken a) {
        PreparedStatement stmt = null;
        String insert = "INSERT INTO AuthToken VALUES (?, ?);";
        try {
            stmt = conn.prepareStatement(insert);
            stmt.setString(1, a.getUsername());
            stmt.setString(2, a.getAuthToken());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the username associated with a specific string
     * @param a authtoken string
     */
    public String getUsername(String a) {
        String username;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM AuthToken WHERE AuthToken = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, a);
            results = stmt.executeQuery();
            username = results.getString(1);
            stmt.close();
            results.close();
            return username;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "An error has occurred";
        }
    }
}
