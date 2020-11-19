package service;

import dao.DatabaseManager;
import dao.UserDao;
import junit.framework.TestCase;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.sql.SQLException;

public class LoginTest extends TestCase {

    public void testLogin() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        UserDao uDao = d.getUDao();
        User user = new User("username", "123456", "email", "first", "last", "gender", "pID");
        try {
            uDao.addUser(user);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        d.disconnect(true);

        // negative test
        LoginRequest lRequest = new LoginRequest("username", "notThePassword");
        LoginResult lResult = new Login().login(lRequest);
        assert(lResult.getMessage().equals("error: Password is incorrect"));

        // positive test
        lRequest = new LoginRequest("username", "123456");
        lResult = new Login().login(lRequest);
        assert(lResult.getAuthToken().length() == 8);
    }
}