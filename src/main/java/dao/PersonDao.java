package dao;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 *
 * Data Access Object (DAO) Class for the Person Class.
 * Allows access to the Person Table in the SQL database
 * 
 */
public class PersonDao {

    public PersonDao(Connection c) {
        conn = c;
    }

    private Connection conn = null;

    public boolean doesPersonIDExist(String personID) {
        boolean exists = true;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM Person WHERE PersonID = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, personID);
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
     * Adds a person to the person table
     * @param p person to add
     */
    public void addPerson(Person p) throws SQLException {
        PreparedStatement stmt = null;
        String insert = "INSERT INTO Person VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        stmt = conn.prepareStatement(insert);
        stmt.setString(1, p.getPersonID());
        stmt.setString(2, p.getAssociatedUsername());
        stmt.setString(3, p.getFirstName());
        stmt.setString(4, p.getLastName());
        stmt.setString(5, p.getGender());
        stmt.setString(6, p.getFatherID());
        stmt.setString(7, p.getMotherID());
        stmt.setString(8, p.getSpouseID());
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Returns single person based on a specific person ID
     * @param personID person ID for which to pull the person object from the database
     * @return returns a Person object
     */
    public Person returnPerson(String personID) {
        Person pers = new Person();

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM Person WHERE PersonID = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, personID);
            results = stmt.executeQuery();
            while (results.next()) {
                results.getString(1);
                String associatedUsername = results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                String gender = results.getString(5);
                String fatherID = results.getString(6);
                String motherID = results.getString(7);
                String spouseID = results.getString(8);
                pers = new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
            }
            stmt.close();
            results.close();
            return pers;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * Returns all ancestors for a specific username
     * @param associatedUsername username for which to find all ancestors
     * @return returns a Person array
     */
    public ArrayList<Person> returnAllPeople(String associatedUsername) {
        ArrayList<Person> people = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM Person WHERE AssociatedUsername = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, associatedUsername);
            results = stmt.executeQuery();
            while (results.next()) {
                String personID = results.getString(1);
                results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                String gender = results.getString(5);
                String fatherID = results.getString(6);
                String motherID = results.getString(7);
                String spouseID = results.getString(8);
                people.add(new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID));
            }
            stmt.close();
            results.close();
            return people;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteUsernameData(String username) {
        PreparedStatement stmt = null;
        String delete = "DELETE FROM Person WHERE AssociatedUsername = ?";
        try {
            stmt = conn.prepareStatement(delete);
            stmt.setString(1, username);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
