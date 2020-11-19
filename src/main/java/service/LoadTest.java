package service;

import dao.DatabaseManager;
import junit.framework.TestCase;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.LoadResult;

import java.util.ArrayList;

public class LoadTest extends TestCase {

    public void testLoad() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        d.disconnect(true);

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();

        LoadRequest lRequest = new LoadRequest(users, persons, events);

        // negative test
        LoadResult lResult = new Load().load(lRequest);

        assert(lResult.getMessage().equals("error: No data provided. Please try again"));


        // positive test
        User user = new User("username", "123456", "email", "first", "last", "gender", "pID");
        User user2 = new User("u2", "pass", "e", "f", "l", "g", "personID");

        users.add(user);
        users.add(user2);

        Person p1 = new Person("myPerson", "associatedUsername", "first", "last", "gender", "f", "m", "s");
        Person p2 = new Person("notMyPerson", "associatedUsername", "hello", "notMe", "female", "father", "mother", "spouse");

        persons.add(p1);
        persons.add(p2);

        Event e1 = new Event("myEvent1", "associatedUsername", "b", 25.3, 26.3, "e", "f", "g", 1);
        Event e2 = new Event("myEvent2", "associatedUsername", "b", 26.7, 28.8, "e", "f", "g", 1);
        Event e3 = new Event("myEvent3", "associatedUsername", "b", 26.9, 28.0, "e", "f", "g", 1);

        events.add(e1);
        events.add(e2);
        events.add(e3);

        lRequest = new LoadRequest(users, persons, events);

        lResult = new Load().load(lRequest);

        String successMessage = "Successfully added 2 users, 2 persons, and 3 events to the database.";

        assert(lResult.getMessage().equals(successMessage));
    }
}