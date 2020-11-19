package model;

import java.util.TreeSet;

/*
 *
 * The Person class is a model class.
 *
 * This model contains information about a Person and their Family Histor data
 *
 */
public class Person {

    public Person() {}

    public Person(String p, String a, String first, String last, String g, String f, String m, String s) {
        personID = p;
        associatedUsername = a;
        firstName = first;
        lastName = last;
        gender = g;
        fatherID = f;
        motherID = m;
        spouseID = s;
    }

    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personID;
    /**
     * User (Username) to which this person belongs
     */
    private String associatedUsername;
    /**
     * Person’s first name (non-empty string)
     */
    private String firstName;
    /**
     * Person’s last name (non-empty string)
     */
    private String lastName;
    /**
     * Person’s gender (string f or m)
     */
    private String gender;
    /**
     * ID of person’s father (possibly null)
     */
    private String fatherID;
    /**
     * ID of person’s mother (possibly null)
     */
    private String motherID;
    /**
     * ID of person’s spouse (possibly null)
     */
    private String spouseID;
    /**
     * A set of randomly generated life events for this Person
     */
    private TreeSet<String> lifeEvents;

    public String getPersonID() {
        return personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String s) {
        spouseID = s;
    }

    public void setMotherID(String m) {
        motherID = m;
    }

    public void setFatherID(String f) {
        fatherID = f;
    }
}
