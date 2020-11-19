package request;

import model.Person;
import model.User;
import model.Event;

import java.util.ArrayList;

/*
 *
 * Information required to request a load
 *
 */
public class LoadRequest {
    public LoadRequest() {}

    public LoadRequest(ArrayList<User> u, ArrayList<Person> p, ArrayList<Event> e) {
        users = u;
        persons = p;
        events = e;
    }

    /**
     * Array of User objects
     */
    private ArrayList<User> users;
    /**
     * Array of Person objects
     */
    private ArrayList<Person> persons;
    /**
     * Array of Event objects
     */
    private ArrayList<Event> events;

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
