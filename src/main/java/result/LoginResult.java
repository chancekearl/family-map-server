package result;

import model.AuthToken;

/*
 *
 * The result of a login call
 * 
 */
public class LoginResult {

    public LoginResult() {
        success = false;
    }

    public LoginResult(String a, String u, String p) {
        authToken = a;
        userName = u;
        personID = p;
        success = false;
    }

    /**
     * AuthToken for the successful login attempt
     */
    private String authToken;
    /**
     * username of the successful login attempt
     */
    private String userName;
    /**
     * personID of the successful login attempt
     */
    private String personID;
    /**
     * The error message for an unsuccessful login attempt
     */
    private String message;

    private Boolean success;

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setMessage(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }

    public Boolean getSuccess() {
        return success;
    }
}
