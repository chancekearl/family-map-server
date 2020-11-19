package dao;

import junit.framework.TestCase;
import model.AuthToken;
import model.Event;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventDaoTest extends TestCase {

    public void testDoesEventIDExist() {
        DatabaseManager d = new DatabaseManager();
        d.deleteEventTable();
        d.createEventTable();

        //negative test
        EventDao dao = d.getEDao();
        assert(!dao.doesEventIDExist("noID"));

        //positive test
        Event e = new Event("eventID", "a","b",23.0,24.0,"e","f","g",1);
        try {
            dao.addEvent(e);
        }
        catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        assert(dao.doesEventIDExist("eventID"));

        d.disconnect(true);
    }

    public void testDoesAssociatedUsernameExist() {
        DatabaseManager d = new DatabaseManager();
        d.deleteEventTable();
        d.createEventTable();

        //negative test
        EventDao dao = d.getEDao();
        assert(!dao.doesAssociatedUsernameExist("noAssociatedUsername"));

        //positive test
        Event e = new Event("eventID", "AssociatedUsername","b",23.0,24.0,"e","f","g",1);
        try {
            dao.addEvent(e);
        }
        catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        assert(dao.doesAssociatedUsernameExist("AssociatedUsername"));

        d.disconnect(true);
    }

    public void testAddEvent() {
        DatabaseManager d = new DatabaseManager();
        d.deleteEventTable();
        d.createEventTable();

        //negative test
        EventDao dao = d.getEDao();
        assert(!dao.doesEventIDExist("myEvent"));

        //positive test
        Event e = new Event("myEvent", "AssociatedUsername","b",23.0,24.9,"e","f","g",1);
        try {
            dao.addEvent(e);
        }
        catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        assert(dao.doesEventIDExist("myEvent"));

        d.disconnect(true);
    }

    public void testReturnEvent() {
        DatabaseManager d = new DatabaseManager();
        d.deleteEventTable();
        d.createEventTable();

        //positive test
        EventDao dao = d.getEDao();
        Event e = new Event("myEvent", "AssociatedUsername","b",24.0,26.9,"e","f","g",1);
        try {
            dao.addEvent(e);
        }
        catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        Event q = dao.returnEvent("myEvent");
        assert(q.getAssociatedUsername().equals("AssociatedUsername"));

        // negative test
        assert(!q.getAssociatedUsername().equals("myEvent"));

        d.disconnect(true);
    }

    public void testReturnAllEvents() {
        DatabaseManager d = new DatabaseManager();
        d.deleteEventTable();
        d.createEventTable();

        //positive test
        EventDao dao = d.getEDao();
        Event e1 = new Event("myEvent1", "AssociatedUsername","b",25.3,26.3,"e","f","g",1);
        try {
            dao.addEvent(e1);
        }
        catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        Event e2 = new Event("myEvent2", "AssociatedUsername","b",26.7,28.8,"e","f","g",1);
        try {
            dao.addEvent(e2);
        }
        catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        ArrayList<Event> events = dao.returnAllEvents("AssociatedUsername");
        assert(events.size() == 2);

        // negative test
        assert(!(events.size() != 2));

        d.disconnect(true);
    }

    public void testDeleteUsernameData() {
        DatabaseManager d = new DatabaseManager();
        d.deleteEventTable();
        d.createEventTable();

        // negative test
        EventDao eDao = d.getEDao();
        Event e1 = new Event("myEvent1", "AssociatedUsername","b",25.3,26.3,"e","f","g",1);
        Event e2 = new Event("myEvent2", "AssociatedUsername","b",26.7,28.8,"e","f","g",1);
        Event e3 = new Event("myEvent3", "AssociatedUsername","b",26.9,28.0,"e","f","g",1);

        try {
            eDao.addEvent(e1);
            eDao.addEvent(e2);
            eDao.addEvent(e3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        eDao.deleteUsernameData("notTheAssociatedUsername");
        ArrayList<Event> events = eDao.returnAllEvents("AssociatedUsername");

        assert(!(events.size() == 0));

        // positive test
        eDao.deleteUsernameData("AssociatedUsername");
        events = eDao.returnAllEvents("AssociatedUsername");

        assert(events.size() == 0);

        d.disconnect(true);
    }
}