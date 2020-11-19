package request;

import model.User;

/*
 *
 * This class gathers user information necessary to attempt to create and use
 * their account as a logged in user.
 *
 */
public class RegisterRequest {

    public RegisterRequest() {}

    public RegisterRequest(User u) {
        userName = u.getUsername();
        password = u.getPassword();
        email = u.getEmail();
        firstName = u.getFirstName();
        lastName = u.getLastName();
        gender = u.getGender();
    }

    /**
     * username for the request, non-empty string
     */
    private String userName;
    /**
     * password for the request, non-empty string
     */
    private String password;
    /**
     * email for the request, non-empty string
     */
    private String email;
    /**
     * first name for the request, non-empty string
     */
    private String firstName;
    /**
     * last name for the request, non-empty string
     */
    private String lastName;
    /**
     * gender for the request, "m" or "f"
     */
    private String gender;

    public String getUserName() {
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
}
