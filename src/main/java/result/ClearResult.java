package result;

/**
 * The result of a clear attempt
 */
public class ClearResult {
    /**
     * The message containing the result of the clear
     */
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
