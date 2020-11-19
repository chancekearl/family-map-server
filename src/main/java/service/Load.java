package service;

import dao.DatabaseManager;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import model.Event;
import model.Person;
import model.User;
import service.Clear;
import result.ClearResult;
import request.LoadRequest;
import result.LoadResult;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clears all data from the database (just like the clear API), and then loads the
 * posted user, person, and event data into the database.
 */
public class Load {
    /**
     * Clears all data from the database (just like the clear API), and then loads the
     * posted user, person, and event data into the database.
     * @param r LoadRequest object
     * @return returns result of the load attempt
     */
    public LoadResult load(LoadRequest r) {
        ClearResult cResult = new Clear().clear();
        if (!cResult.getSuccess()) {
            System.out.println("clearning on a load went terribly wrong...");
        }

        LoadResult lResult = new LoadResult();
        DatabaseManager d = new DatabaseManager();
        UserDao uDao = d.getUDao();
        PersonDao pDao = d.getPDao();
        EventDao eDao = d.getEDao();


        ArrayList<User> users = r.getUsers();
        ArrayList<Person> persons = r.getPersons();
        ArrayList<Event> events = r.getEvents();

        for (User u : users) {
            try {
                uDao.addUser(u);
            }
            catch (SQLException e) {
                lResult.setMessage("error: Invalid users provided. Try again");
                d.disconnect(false);
                return lResult;
            }
        }

        for (Person p : persons) {
            try {
                pDao.addPerson(p);
            }
            catch (SQLException e) {
                System.out.println(e.toString());
                lResult.setMessage("error: Invalid persons provided. Try again");
                d.disconnect(false);
                return lResult;
            }
        }

        for (Event ev : events) {
            try {
                eDao.addEvent(ev);
            }
            catch (SQLException e) {
                lResult.setMessage("error: Invalid events provided. Try again");
                d.disconnect(false);
                return lResult;
            }
        }

        if (events.size() == 0 && persons.size() == 0 && users.size() == 0) {
            lResult.setMessage("error: No data provided. Please try again");
            d.disconnect(false);
            return lResult;
        }

        lResult.setMessage("Successfully added " + users.size() + " users, " + persons.size()
                + " persons, and " + events.size() + " events to the database.");
        lResult.setSuccess(true);
        d.disconnect(true);
        return lResult;
    }
}
