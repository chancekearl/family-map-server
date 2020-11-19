package model;

import request.RegisterRequest;

/*
 *
 * The User class is a model class.
 * 
 * It contains basic information about the user and their account.
 *
 */
public class User {

    public User() {}

    public User(String u, String pass, String em, String first, String last, String g, String p) {
        userName = u;
        password = pass;
        email = em;
        firstName = first;
        lastName = last;
        gender = g;
        personID = p;
    }

    public User(RegisterRequest r, String p) {
        userName = r.getUserName();
        password = r.getPassword();
        email = r.getEmail();
        firstName = r.getFirstName();
        lastName = r.getLastName();
        gender = r.getGender();
        personID = p;
    }

    /**
     * Unique username (non-empty string)
     */
    private String userName;
    /**
     * User's password (non-empty string)
     */
    private String password;
    /**
     * User’s email address (non-empty string)
     */
    private String email;
    /**
     * User’s first name (non-empty string)
     */
    private String firstName;
    /**
     * User’s last name (non-empty string)
     */
    private String lastName;
    /**
     * User’s gender (string f or m)
     */
    private String gender;
    /**
     * Unique Person ID assigned to this user’s generated Person object (non-empty string)
     */
    private String personID;

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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

    public String getPersonID() {
        return personID;
    }
}
