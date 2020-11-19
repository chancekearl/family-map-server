package dao;

import junit.framework.TestCase;
import model.User;

import java.sql.SQLException;

public class UserDaoTest extends TestCase {

    public void testDoesUsernameExist() {
        DatabaseManager d = new DatabaseManager();
        d.deleteUserTable();
        d.createUserTable();

        //negative test
        UserDao dao = d.getUDao();
        assert(!dao.doesUsernameExist("username"));

        //positive test
        User user = new User("username", "password", "email", "first", "last", "gender", "pID");
        try {
            dao.addUser(user);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert(dao.doesUsernameExist("username"));

        d.disconnect(true);
    }

    public void testAddUser() {
        DatabaseManager d = new DatabaseManager();
        d.deleteUserTable();
        d.createUserTable();

        //negative test
        UserDao dao = d.getUDao();
        assert(!dao.doesUsernameExist("username"));

        //positive test
        User user = new User("user", "password", "email", "first", "last", "gender", "pID");
        try {
            dao.addUser(user);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert(dao.doesUsernameExist("user"));

        d.disconnect(true);
    }

    public void testGetPassword() {
        DatabaseManager d = new DatabaseManager();
        d.deleteUserTable();
        d.createUserTable();

        UserDao dao = d.getUDao();

        // positive test
        User user = new User("username", "123456", "email", "first", "last", "gender", "pID");
        User user2 = new User("u2", "pass", "e", "f", "l", "g", "personID");
        try {
            dao.addUser(user);
            dao.addUser(user2);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String pWord = dao.getPassword("username");
        assert(pWord.equals("123456"));

        // negative test
        pWord = dao.getPassword("u2");
        assert(!(pWord.equals("123456")));

        d.disconnect(true);
    }

    public void testGetPersonID() {
        DatabaseManager d = new DatabaseManager();
        d.deleteUserTable();
        d.createUserTable();

        UserDao dao = d.getUDao();

        // positive test
        User user = new User("username", "123456", "email", "first", "last", "gender", "pID");
        User user2 = new User("u2", "pass", "e", "f", "l", "g", "personID");
        try {
            dao.addUser(user);
            dao.addUser(user2);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String id = dao.getPersonID("username");
        assert(id.equals("pID"));

        // negative test
        id = dao.getPersonID("u2");
        assert(!(id.equals("pID")));

        d.disconnect(true);
    }

    public void testReturnUser() {
        DatabaseManager d = new DatabaseManager();
        d.deleteUserTable();
        d.createUserTable();

        UserDao dao = d.getUDao();

        // positive test
        User user = new User("username", "123456", "email", "first", "last", "gender", "pID");
        User user2 = new User("u2", "pass", "e", "f", "l", "g", "personID");
        try {
            dao.addUser(user);
            dao.addUser(user2);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        User returnedUser = dao.returnUser("username");
        assert(returnedUser.getEmail().equals("email"));

        // negative test
        assert(returnedUser.getEmail() != "e");

        d.disconnect(true);
    }
}