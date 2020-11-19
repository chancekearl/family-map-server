package result;

/*
 *
 * Result of a load call
 *
 */
public class LoadResult {

    public LoadResult() {
      success = false;
    }

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
