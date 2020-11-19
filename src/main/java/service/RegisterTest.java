package service;

import dao.DatabaseManager;
import dao.UserDao;
import junit.framework.TestCase;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.sql.SQLException;

public class RegisterTest extends TestCase {

    public void testRegister() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        UserDao uDao = d.getUDao();
        User user = new User("username", "123456", "email", "first", "last", "gender", "pID");
        User user2 = new User("u2", "pass", "e", "f", "l", "g", "personID");
        try {
            uDao.addUser(user2);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        d.disconnect(true);

        // negative test
        RegisterRequest rRequest = new RegisterRequest(user2);
        RegisterResult rResult = new Register().register(rRequest);
        assert(rResult.getMessage().equals("Username already exists. Try new username"));

        // positive test
        rRequest = new RegisterRequest(user);
        rResult = new Register().register(rRequest);
        assert(rResult.getAuthToken().length() == 8);
    }
}