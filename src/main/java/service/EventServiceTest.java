package service;

import dao.AuthTokenDao;
import dao.DatabaseManager;
import dao.EventDao;
import junit.framework.TestCase;
import model.AuthToken;
import model.Event;
import result.EventResult;

import java.sql.SQLException;

public class EventServiceTest extends TestCase {

    public void testGetEvent() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        AuthTokenDao aDao = d.getADao();
        EventDao eDao = d.getEDao();

        String [] parsedArray = new String [1];

        Event e1 = new Event("myEvent1", "username","b",25.3,26.3,"e","f","g",1);
        Event e2 = new Event("myEvent2", "username","b",26.7,28.8,"e","f","g",200);
        Event e3 = new Event("myEvent3", "username","b",26.9,28.0,"e","f","g",300);

        try {
            eDao.addEvent(e1);
            eDao.addEvent(e2);
            eDao.addEvent(e3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        aDao.addToAuthTable(new AuthToken("username", "auth"));
        d.disconnect(true);
        // negative test
        EventResult eResult = new EventService().getEvent("auth", parsedArray);

        assert(eResult.getMessage().equals("error: Invalid input. Try again"));

        // positive test to call getAllEvents
        parsedArray = new String [2];

        eResult = new EventService().getEvent("auth", parsedArray);
        assert(eResult.getData().size() == 3);

        // positive test to call getSingleEvent

        parsedArray = new String [3];
        parsedArray[0] = "hello";
        parsedArray[1] = "event";
        parsedArray[2] = "myEvent1";
        eResult = new EventService().getEvent("auth", parsedArray);
        assert(eResult.getYear() == 1);
    }

    public void testGetSingleEvent() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        AuthTokenDao aDao = d.getADao();
        EventDao eDao = d.getEDao();

        Event e1 = new Event("myEvent1", "username","b",25.3,26.3,"e","f","g",1);
        Event e2 = new Event("myEvent2", "username","b",26.7,28.8,"e","f","g",200);
        Event e3 = new Event("myEvent3", "username","b",26.9,28.0,"e","f","g",300);

        try {
            eDao.addEvent(e1);
            eDao.addEvent(e2);
            eDao.addEvent(e3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        aDao.addToAuthTable(new AuthToken("username", "auth"));
        aDao.addToAuthTable(new AuthToken("u", "otherAuth"));


        String [] parsedArray = new String[3];
        parsedArray[2] = "myEvent1";

        // positive test
        d.disconnect(true);
        EventResult eResult = new EventService().getSingleEvent("auth", parsedArray);
        assert(eResult.getYear() == 1);

        // negative test
        eResult = new EventService().getSingleEvent("otherAuth", parsedArray);
        assert(eResult.getMessage().equals("error: Requested Event does not belong to this user"));
    }

    public void testGetAllEvents() {
        DatabaseManager d = new DatabaseManager();
        d.dropAllTables();
        d.createAllTables();
        AuthTokenDao aDao = d.getADao();
        EventDao eDao = d.getEDao();

        String [] parsedArray = new String [2];

        Event e1 = new Event("myEvent1", "username","b",25.3,26.3,"e","f","g",1);
        Event e2 = new Event("myEvent2", "username","b",26.7,28.8,"e","f","g",200);
        Event e3 = new Event("myEvent3", "username","b",26.9,28.0,"e","f","g",300);

        try {
            eDao.addEvent(e1);
            eDao.addEvent(e2);
            eDao.addEvent(e3);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        aDao.addToAuthTable(new AuthToken("username", "auth"));

        // positive test
        d.disconnect(true);

        EventResult eResult = new EventService().getAllEvents("auth");
        assert(eResult.getData().size() == 3);


        // negative test
        eResult = new EventService().getAllEvents("doesNotExist");
        assert(eResult.getMessage().equals("error: Auth Token does not exist"));
    }
}