package model;

import java.util.TreeSet;

/*
 *
 * The Event class is a model class.
 *
 * This model keeps track of the details of a generated Event
 *
 */
public class Event {

    public Event() {}

    public Event(String e, String a, String p, Double lat, Double lon, String cou, String cit, String et, int y) {
        eventID = e;
        associatedUsername = a;
        personID = p;
        latitude = lat;
        longitude = lon;
        country = cou;
        city = cit;
        eventType = et;
        year = y;
    }

    /**
     * Unique identifier for this event (non-empty string)
     */
    private String eventID;
    /**
     * User (Username) to which this person belongs
     */
    private String associatedUsername;
    /**
     * ID of person to which this event belongs
     */
    private String personID;
    /**
     * Latitude of event’s location
     */
    private Double latitude;
    /**
     * Longitude of event’s location
     */
    private Double longitude;
    /**
     * Country in which event occurred
     */
    private String country;
    /**
     * City in which event occurred
     */
    private String city;
    /**
     *  Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;
    /**
     * Year in which event occurred
     */
    private Integer year;

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
