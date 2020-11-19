package model;

/*
 *
 * The AuthToken Class is a model class.
 * 
 * An AuthToken will allow the server to determine which user is making the
 * login request. It also allows the same user to be logged in from multiple
 * clients at the same time.
 *
 */
public class AuthToken {

    public AuthToken(String u, String a) {
        username = u;
        authToken = a;
    }
    /**
     * unique 8-digit hexadecimal AuthToken
     */
    private String authToken;
    /**
     * The username of the person for which the AuthToken was generated
     */
    private String username;

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }
}
