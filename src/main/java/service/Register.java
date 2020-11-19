package service;

import dao.DatabaseManager;
import dao.UserDao;
import model.User;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;

import java.sql.SQLException;

/*
 * This class is a register service class that creates a new user account,
 * generates four generations of ancestor data for the new user, logs
 * the user in, and returns an AuthToken
 *
 */
public class Register {
    /**
     * Registers a user into the user database
     * @param r RegisterRequest object with potential user information
     * @return result of the attempt to register the user
     */
    public RegisterResult register(RegisterRequest r) {
        RegisterResult rResult = new RegisterResult();
        DatabaseManager d = new DatabaseManager();
        UserDao uDao = d.getUDao();

        if (uDao.doesUsernameExist(r.getUserName())) {
            rResult.setMessage("error: Username already exists. Try new username");
            d.disconnect(false);
            return rResult;
        }

        String personID = d.generateToken();

        User user = new User(r, personID);

        try {
            uDao.addUser(user);
        }
        catch (SQLException e) {
            rResult.setMessage("error: Invalid request data. Try again");
            d.disconnect(false);
            return rResult;
        }
        d.disconnect(true);

        new Fill().fillHelper(r.getUserName(), 4);

        LoginRequest lRequest = new LoginRequest(r.getUserName(), r.getPassword());
        LoginResult lResult = new Login().login(lRequest);

        rResult = new RegisterResult(lResult);
        rResult.setSuccess(true);
        return rResult;
    }
}
