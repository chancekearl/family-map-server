package result;

/*
 *
 * Result class for a register attempt
 * 
 */
public class RegisterResult {

    public RegisterResult() {
        success = false;
    }

    public RegisterResult(LoginResult l) {
        authToken = l.getAuthToken();
        userName = l.getUserName();
        personID = l.getPersonID();
        success = l.getSuccess();
    }

    /**
     * AuthToken for the successful register attempt
     */
    private String authToken;
    /**
     * username for the successful register attempt
     */
    private String userName;
    /**
     * person ID for the successful register attempt
     */
    private String personID;
    /**
     * The error message for an unsuccessful register attempt
     */
    private String message;

    private Boolean success;

    public void setMessage(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }

    public Boolean getSuccess() {
        return success;
    }
}
