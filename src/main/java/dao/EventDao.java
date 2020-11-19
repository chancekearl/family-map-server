package dao;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 *
 * Data Access Object (DAO) Class for the Event Class.
 * Allows access to the Event Table in the SQL database
 *
 */
public class EventDao {

    public EventDao(Connection c) {
        conn = c;
    }

    private Connection conn = null;

    public boolean doesEventIDExist(String eventID) {
        boolean exists = true;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM Event WHERE EventID = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, eventID);
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


    public boolean doesAssociatedUsernameExist(String associatedUsername) {
        boolean exists = true;

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM Event WHERE AssociatedUsername = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, associatedUsername);
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
     * Adds an event to the database
     * @param e the event to add
     */
    public void addEvent(Event e) throws SQLException {
        PreparedStatement stmt = null;
        String insert = "INSERT INTO Event VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        stmt = conn.prepareStatement(insert);
        stmt.setString(1, e.getEventID());
        stmt.setString(2, e.getAssociatedUsername());
        stmt.setString(3, e.getPersonID());
        stmt.setDouble(4, e.getLatitude());
        stmt.setDouble(5, e.getLongitude());
        stmt.setString(6, e.getCountry());
        stmt.setString(7, e.getCity());
        stmt.setString(8, e.getEventType());
        stmt.setInt(9, e.getYear());
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Returns a single event based on the event ID
     * @param eventID event ID of the event which should be found and returned from the database
     * @return single event
     */
    public Event returnEvent(String eventID) {
        Event ev = new Event();

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM Event WHERE EventID = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, eventID);
            results = stmt.executeQuery();
            while (results.next()) {
                results.getString(1);
                String associatedUsername = results.getString(2);
                String personID = results.getString(3);
                Double latitude = results.getDouble(4);
                Double longitude = results.getDouble(5);
                String country = results.getString(6);
                String city = results.getString(7);
                String eventType = results.getString(8);
                int year = results.getInt(9);
                ev = new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
            }
            stmt.close();
            results.close();
            return ev;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * Returns all Events for all family members of the current user
     * @param associatedUsername username for which to find all event data
     * @return arrayList of events
     */
    public ArrayList<Event> returnAllEvents(String associatedUsername) {
        ArrayList<Event> events = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet results = null;
        String query = "SELECT * FROM Event WHERE AssociatedUsername = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, associatedUsername);
            results = stmt.executeQuery();
            while (results.next()) {
                String eventID = results.getString(1);
                results.getString(2);
                String personID = results.getString(3);
                Double latitude = results.getDouble(4);
                Double longitude = results.getDouble(5);
                String country = results.getString(6);
                String city = results.getString(7);
                String eventType = results.getString(8);
                int year = results.getInt(9);
                events.add(new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year));
            }
            stmt.close();
            results.close();
            return events;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteUsernameData(String username) {
        PreparedStatement stmt = null;
        String delete = "DELETE FROM Event WHERE AssociatedUsername = ?";
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
