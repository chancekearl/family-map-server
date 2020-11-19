package service;

import dao.AuthTokenDao;
import dao.DatabaseManager;
import dao.PersonDao;
import junit.framework.TestCase;
import model.AuthToken;
import model.Person;
import result.PersonResult;

import java.sql.SQLException;

public class PersonServiceTest extends TestCase {

    public void testGetPerson() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        AuthTokenDao aDao = d.getADao();
        PersonDao pDao = d.getPDao();

        String [] parsedArray = new String [1];

        Person p1 = new Person("myPerson", "username", "first", "last", "gender", "f", "m", "s");
        Person p2 = new Person("2MyPerson", "username", "hello", "notMe", "female", "father", "mother", "spouse");
        Person p3 = new Person("person", "username", "fir", "tree", "male", "dad", "mom", "spouse");

        try {
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            pDao.addPerson(p3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        aDao.addToAuthTable(new AuthToken("username", "auth"));
        d.disconnect(true);
        // negative test
        PersonResult pResult = new PersonService().getPerson("auth", parsedArray);

        assert(pResult.getMessage().equals("error: Invalid input. Try again"));

        // positive test to call getAllEvents
        parsedArray = new String [2];

        pResult = new PersonService().getPerson("auth", parsedArray);
        assert(pResult.getData().size() == 3);

        // positive test to call getSingleEvent

        parsedArray = new String [3];
        parsedArray[0] = "hello";
        parsedArray[1] = "event";
        parsedArray[2] = "myPerson";
        pResult = new PersonService().getPerson("auth", parsedArray);
        assert(pResult.getFirstName().equals("first"));
    }

    public void testGetSinglePerson() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        AuthTokenDao aDao = d.getADao();
        PersonDao pDao = d.getPDao();

        String [] parsedArray = new String [3];

        Person p1 = new Person("myPerson", "username", "first", "last", "gender", "f", "m", "s");
        Person p2 = new Person("2MyPerson", "username", "hello", "notMe", "female", "father", "mother", "spouse");
        Person p3 = new Person("person", "username", "fir", "tree", "male", "dad", "mom", "spouse");

        try {
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            pDao.addPerson(p3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        aDao.addToAuthTable(new AuthToken("username", "auth"));
        aDao.addToAuthTable(new AuthToken("notUser", "notAuth"));
        d.disconnect(true);

        // positive test
        parsedArray[2] = "myPerson";
        PersonResult pResult = new PersonService().getSinglePerson("auth", parsedArray);
        assert(pResult.getFirstName().equals("first"));

        // negative test
        pResult = new PersonService().getSinglePerson("notAuth", parsedArray);
        assert(pResult.getMessage().equals("error: Requested Person does not belong to this user"));
    }

    public void testGetAllPeople() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        AuthTokenDao aDao = d.getADao();
        PersonDao pDao= d.getPDao();

        String [] parsedArray = new String [2];

        Person p1 = new Person("myPerson", "username", "first", "last", "gender", "f", "m", "s");
        Person p2 = new Person("2MyPerson", "username", "hello", "notMe", "female", "father", "mother", "spouse");
        Person p3 = new Person("person", "username", "fir", "tree", "male", "dad", "mom", "spouse");

        try {
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            pDao.addPerson(p3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        aDao.addToAuthTable(new AuthToken("username", "auth"));

        // positive test
        d.disconnect(true);

        PersonResult pResult = new PersonService().getAllPeople("auth");
        assert(pResult.getData().size() == 3);


        // negative test
        pResult = new PersonService().getAllPeople("doesNotExist");
        assert(pResult.getMessage().equals("error: Auth Token does not exist"));
    }
}