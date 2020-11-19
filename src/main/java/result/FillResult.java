package result;

/**
 * The result of a request to fill the database for a user
 */
public class FillResult {
    private String message;

    private Boolean success;

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
