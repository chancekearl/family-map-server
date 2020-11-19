package dao;

import junit.framework.TestCase;
import model.AuthToken;

public class AuthTokenDaoTest extends TestCase {

    public void testDoesAuthTokenExist() {
        DatabaseManager d = new DatabaseManager();
        d.deleteAuthTable();
        d.createAuthTable();

        //negative test
        AuthTokenDao dao = d.getADao();
        assert(!dao.doesAuthTokenExist("noToken"));

        //positive test
        AuthToken a = new AuthToken("user","token");
        dao.addToAuthTable(a);
        assert(dao.doesAuthTokenExist("token"));

        d.disconnect(true);
    }

    public void testAddToAuthTable() {
        DatabaseManager d = new DatabaseManager();
        d.deleteAuthTable();
        d.createAuthTable();

        //negative test
        AuthTokenDao dao = d.getADao();
        assert(!dao.doesAuthTokenExist("myToken"));

        //positive test
        AuthToken a = new AuthToken("user","myToken");
        dao.addToAuthTable(a);
        assert(dao.doesAuthTokenExist("myToken"));

        d.disconnect(true);
    }

    public void testGetUsername() {
        DatabaseManager d = new DatabaseManager();
        d.deleteAuthTable();
        d.createAuthTable();

        //positive test
        AuthTokenDao dao = d.getADao();
        AuthToken a = new AuthToken("u","a");
        dao.addToAuthTable(a);
        String username = dao.getUsername("a");
        assert(username.equals("u"));

        //negative test
        assert(!username.equals("a"));

        d.disconnect(true);
    }
}