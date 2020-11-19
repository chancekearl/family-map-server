package result;

import model.Event;

import java.util.ArrayList;

public class EventResult {

    public EventResult() {
        success = false;
    }

    public EventResult(Event e) {
        eventID = e.getEventID();
        associatedUsername = e.getAssociatedUsername();
        personID = e.getPersonID();
        latitude = e.getLatitude();
        longitude = e.getLongitude();
        country = e.getCountry();
        city = e.getCity();
        eventType = e.getEventType();
        year = e.getYear();
        success = false;
    }

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

    private ArrayList<Event> data;

    private String message;

    private Boolean success;

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

    public Integer getYear() {
        return year;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        message = m;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }
    
    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> events) {
        data = events;
    }
}
