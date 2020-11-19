package request;

/*
 *
 * Contains the information needed for a login attempt
 *
 */
public class LoginRequest {

    public LoginRequest() {}

    public LoginRequest(String u, String p) {
        userName = u;
        password = p;
    }

    /**
     * username for the login attempt, non-empty string
     */
    private String userName;
    /**
     * password for the login attempt, non-empty string
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
