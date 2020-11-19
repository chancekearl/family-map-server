package service;

import com.google.gson.Gson;
import dao.DatabaseManager;
import dao.UserDao;
import jsonObject.*;
//import junit.framework.TestCase;
import junit.framework.TestCase;
import model.Person;
import model.User;
import result.FillResult;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.sql.SQLException;

public class FillTest extends TestCase {
    private ArrayList<Location> locations;

    private Random rand = new Random();

    public void testFill() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        UserDao uDao = d.getUDao();

        Gson gson = new Gson();
        try {
            locations = gson.fromJson(new FileReader("json/locations.json"), LocationFile.class).getData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        User user = new User("username", "password", "email", "first", "last", "gender", "pID");
        try {
            uDao.addUser(user);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        d.disconnect(true);
        // negative test
        String [] parsedArray = new String [4];
        parsedArray[2] = "username";
        parsedArray[3] = "notAnInt";
        FillResult fResult = new Fill().fill(parsedArray);
        assert(fResult.getMessage().equals("error: Invalid integer parameter. Try again"));

        // positive test
        parsedArray = new String [3];
        parsedArray[2] = "username";
        Fill filler = new Fill();
        filler.fill(parsedArray);
        int personsAdded = filler.getPersonsAdded();
        assert(personsAdded == 31);
    }

    public void testFillHelper() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        UserDao uDao = d.getUDao();

        User user = new User("username", "password", "email", "first", "last", "gender", "pID");
        try {
            uDao.addUser(user);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        d.disconnect(true);

        // positive test
        Fill filler = new Fill();
        filler.fillHelper("username", 4);
        assert(filler.getPersonsAdded() == 31);

        // positive test 2 - should delete old data

        filler.fillHelper("username", 5);
        assert(filler.getPersonsAdded() == 63);
    }

    public void testMakeParents() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();


        // positive test
        Person p = new Person("myPerson", "username", "first", "last", "gender", "f", "m", "s");

        Fill filler = new Fill();
        filler.loadJsonFiles();
        filler.setPersonsAdded(0);
        try {
            Location m = locations.get(rand.nextInt(locations.size() - 1));
            filler.makeParents(d, p, m, 4);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        assert(filler.getPersonsAdded() == 31);

        //positive test 2
        d.dropAllTables();
        d.createAllTables();
        Fill filler2 = new Fill();
        filler2.loadJsonFiles();
        filler2.setPersonsAdded(0);
        try {
            Location m = locations.get(rand.nextInt(locations.size() - 1));
            filler2.makeParents(d, p, m, 6);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        assert(filler2.getPersonsAdded() == 127);

        d.disconnect(true);
    }

    public void testCreateMother() {
        DatabaseManager d = new DatabaseManager();
        String associatedUsername = "associatedUsername";

        Fill filler = new Fill();
        filler.loadJsonFiles();

        // positive test
        Person mother = filler.createMother(d, associatedUsername);
        assert(mother.getGender().equals("f"));
        assert(mother.getPersonID().length() == 8);

        // positive test 2
        Person mother2 = filler.createMother(d, associatedUsername);
        assert(mother2.getAssociatedUsername().equals(associatedUsername));
        assert(mother2.getFatherID() == null);

        d.disconnect(true);
    }

    public void testCreateFather() {
        DatabaseManager d = new DatabaseManager();
        String associatedUsername = "associatedUsername";

        Fill filler = new Fill();
        filler.loadJsonFiles();

        // positive test
        Person father = filler.createFather(d, associatedUsername);
        assert(father.getGender().equals("m"));
        assert(father.getPersonID().length() == 8);

        // positive test 2
        Person father2 = filler.createFather(d, associatedUsername);
        assert(father2.getAssociatedUsername().equals(associatedUsername));
        assert(father2.getMotherID() == null);

        d.disconnect(true);
    }

    public void testCreateEvents() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();


        // positive test
        Person p = new Person("myPerson", "username", "first", "last", "gender", "f", "m", "s");

        Fill filler = new Fill();
        filler.loadJsonFiles();
        filler.setEventsAdded(0);
        try {
            Location m = locations.get(rand.nextInt(locations.size() - 1));
            filler.createEvents(d, p, "username", 1990, m);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        assert(filler.getEventsAdded() == 3);


        // positive test 2
        Person p2 = new Person("myPerson2", "username", "first", "last", "gender", "f", "m", "s");
        filler.setEventsAdded(0);

        try {
            Location m = locations.get(rand.nextInt(locations.size() - 1));
            filler.createEvents(d, p2, "username", 1700, m);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        assert(filler.getEventsAdded() == 4);

        d.disconnect(true);
    }

    public void testIsInteger() {
        Fill filler = new Fill();

        // positive test
        assert(filler.isInteger("2"));

        // positive test
        assert(filler.isInteger("-1"));

        // negative test
        assert(!filler.isInteger("y"));

        // negative test
        assert(!filler.isInteger("one"));
    }
}