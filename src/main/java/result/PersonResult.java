package result;

import model.Person;

import java.util.ArrayList;

public class PersonResult {

    public PersonResult() {
        success = false;
    }

    public PersonResult(Person p) {
        personID = p.getPersonID();
        associatedUsername = p.getAssociatedUsername();
        firstName = p.getFirstName();
        lastName = p.getLastName();
        gender = p.getGender();
        fatherID = p.getFatherID();
        motherID = p.getMotherID();
        spouseID = p.getSpouseID();
        success = false;
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

    private ArrayList<Person> data;

    private String message;

    private Boolean success;

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

    public String getMessage() {
        return message;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setMessage(String m) {
        message = m;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setData(ArrayList<Person> people) {
        data = people;
    }
}
