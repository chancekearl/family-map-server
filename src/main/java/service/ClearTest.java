package service;

import dao.*;
import junit.framework.TestCase;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClearTest extends TestCase {

    public void testClear() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        AuthTokenDao aDao = d.getADao();
        EventDao eDao = d.getEDao();
        PersonDao pDao = d.getPDao();
        UserDao uDao = d.getUDao();


        // negative test
        AuthToken a = new AuthToken("user","token");
        Event e = new Event("myEvent", "associatedUsername","b",24.0,26.9,"e","f","g",1);
        Person p = new Person("personID", "associatedUsername", "first", "last", "gender", "f", "m", "s");
        User user = new User("username", "password", "email", "first", "last", "gender", "pID");

        try {
            aDao.addToAuthTable(a);
            eDao.addEvent(e);
            pDao.addPerson(p);
            uDao.addUser(user);
        }
        catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }

        String query = "select count(*) from all_objects where object_type = 'TABLE';";

        PreparedStatement stmt = null;
        ResultSet results = null;
        Connection conn = d.getConn();

        try {
            stmt = conn.prepareStatement(query);
            results = stmt.executeQuery();
            int numTables = results.getInt(1);
            assert(numTables != 0);
        }
        catch (SQLException exc) {
        }

        d.disconnect(true);

        // positive test
        new Clear().clear();

        try {
            stmt = conn.prepareStatement(query);
            results = stmt.executeQuery();
            int numTables = results.getInt(1);
            assert(numTables == 0);
        }
        catch (SQLException exc) {
        }
    }
}