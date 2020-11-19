package service;

import dao.AuthTokenDao;
import dao.DatabaseManager;
import dao.UserDao;
import model.AuthToken;
import request.LoginRequest;
import result.LoginResult;

import javax.xml.crypto.Data;

/**
 * Logs the user in and returns an auth token
 */
public class Login {
    /**
     * Requests a login of a user into the system
     * @param r contains the request information
     * @return returns the result of the login attempt
     */
    public LoginResult login(LoginRequest r) {
        LoginResult lResult = new LoginResult();
        DatabaseManager d = new DatabaseManager();
        UserDao uDao = d.getUDao();
        AuthTokenDao aDao = d.getADao();

        if (!uDao.doesUsernameExist(r.getUserName())) {
            lResult.setMessage("error: Username does not exist");
            d.disconnect(false);
            return lResult;
        }

        if (!r.getPassword().equals(uDao.getPassword(r.getUserName()))) {
            lResult.setMessage("error: Password is incorrect");
            d.disconnect(false);
            return lResult;
        }

        String authToken = d.generateToken();
        AuthToken auth = new AuthToken(r.getUserName(), authToken);
        aDao.addToAuthTable(auth);

        lResult = new LoginResult(authToken, r.getUserName(), uDao.getPersonID(r.getUserName()));
        lResult.setSuccess(true);
        d.disconnect(true);
        return lResult;
    }
}
