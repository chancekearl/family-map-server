package dao;

import junit.framework.TestCase;
import model.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDaoTest extends TestCase {

    public void testDoesPersonIDExist() {
        DatabaseManager d = new DatabaseManager();
        d.deletePersonTable();
        d.createPersonTable();

        //negative test
        PersonDao dao = d.getPDao();
        assert(!dao.doesPersonIDExist("noID"));

        //positive test
        Person p = new Person("personID", "associatedUsername", "first", "last", "gender", "f", "m", "s");
        try {
            dao.addPerson(p);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert(dao.doesPersonIDExist("personID"));

        d.disconnect(true);
    }

    public void testAddPerson() {
        DatabaseManager d = new DatabaseManager();
        d.deletePersonTable();
        d.createPersonTable();

        //negative test
        PersonDao dao = d.getPDao();
        assert(!dao.doesPersonIDExist("noPerson"));

        //positive test
        Person p = new Person("person", "associatedUsername", "first", "last", "gender", "f", "m", "s");
        try {
            dao.addPerson(p);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert(dao.doesPersonIDExist("person"));

        d.disconnect(true);

    }

    public void testReturnPerson() {
        DatabaseManager d = new DatabaseManager();
        d.deletePersonTable();
        d.createPersonTable();

        PersonDao pDao = d.getPDao();

        //positive test
        Person p1 = new Person("myPerson", "associatedUsername", "first", "last", "gender", "f", "m", "s");
        Person p2 = new Person("notMyPerson", "associatedUsername", "hello", "notMe", "female", "father", "mother", "spouse");
        try {
            pDao.addPerson(p1);
            pDao.addPerson(p2);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Person pers = pDao.returnPerson("myPerson");

        assert(pers.getFirstName().equals("first"));

        // negative test
        assert(!(pers.getFirstName().equals("hello")));

        d.disconnect(true);
    }

    public void testReturnAllPeople() {
        DatabaseManager d = new DatabaseManager();
        d.deletePersonTable();
        d.createPersonTable();

        PersonDao pDao = d.getPDao();

        //positive test
        Person p1 = new Person("myPerson", "associatedUsername", "first", "last", "gender", "f", "m", "s");
        Person p2 = new Person("notMyPerson", "notTheAssociatedUsername", "hello", "notMe", "female", "father", "mother", "spouse");
        Person p3 = new Person("person", "associatedUsername", "fir", "tree", "male", "dad", "mom", "spouse");

        try {
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            pDao.addPerson(p3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Person> persons = pDao.returnAllPeople("associatedUsername");

        assert(persons.size() == 2);

        // negative test
        assert(!(persons.size() != 2));

        d.disconnect(true);
    }

    public void testDeleteUsernameData() {
        DatabaseManager d = new DatabaseManager();
        d.deletePersonTable();
        d.createPersonTable();

        PersonDao pDao = d.getPDao();

        //negative test
        Person p1 = new Person("myPerson", "associatedUsername", "first", "last", "gender", "f", "m", "s");
        Person p2 = new Person("myPerson2", "associatedUsername", "hello", "notMe", "female", "father", "mother", "spouse");
        Person p3 = new Person("person", "associatedUsername", "fir", "tree", "male", "dad", "mom", "spouse");

        try {
            pDao.addPerson(p1);
            pDao.addPerson(p2);
            pDao.addPerson(p3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        pDao.deleteUsernameData("notTheAssociatedUsername");

        ArrayList<Person> persons = pDao.returnAllPeople("associatedUsername");

        assert(!(persons.size() == 0));

        // negative test
        pDao.deleteUsernameData("associatedUsername");
        persons = pDao.returnAllPeople("associatedUsername");
        assert(persons.size() == 0);

        d.disconnect(true);
    }
}