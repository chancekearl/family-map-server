package service;

import dao.AuthTokenDao;
import dao.DatabaseManager;
import dao.EventDao;
import model.Event;
import result.EventResult;

import java.util.ArrayList;

/**
 * Returns the single Event object with the specified ID, or all events belonging to the
 * current user
 */
public class EventService {

    public EventResult getEvent(String authToken, String parsedArray []) {
        if (parsedArray.length == 3) {
            return getSingleEvent(authToken, parsedArray);
        }
        else if (parsedArray.length == 2) {
            return getAllEvents(authToken);
        }
        else {
            EventResult eResult = new EventResult();
            eResult.setMessage("Invalid input. Try again");
            return eResult;
        }
    }

    /**
     * Returns the single Event object with the specified ID
     * @param authToken the authToken for the user who is the owner of the event
     * @param parsedArray the array from the http request
     * @return returns the Event object
     */
    public EventResult getSingleEvent(String authToken, String parsedArray []) {
        EventResult eResult = new EventResult();
        String eventID = parsedArray[2];
        DatabaseManager d = new DatabaseManager();
        EventDao eDao = d.getEDao();
        AuthTokenDao aDao = d.getADao();

        if (!aDao.doesAuthTokenExist(authToken)) {
            eResult.setMessage("error: Auth Token does not exist");
            d.disconnect(false);
            return eResult;
        }

        if (!eDao.doesEventIDExist(eventID)) {
            eResult.setMessage("error: Event ID does not exist");
            d.disconnect(false);
            return eResult;
        }

        Event event = eDao.returnEvent(eventID);
        String username = event.getAssociatedUsername();
        if (!username.equals(aDao.getUsername(authToken))) {
            eResult.setMessage("error: Requested Event does not belong to this user");
            d.disconnect(false);
            return eResult;
        }

        eResult = new EventResult(event);
        eResult.setSuccess(true);
        d.disconnect(true);
        return eResult;
    }

    /**
     * Returns ALL events for ALL family members of the current user. The current
     * user is determined from the provided auth token.
     * @return returns the array of Events
     */
    public EventResult getAllEvents(String authToken) {
        EventResult eResult = new EventResult();
        DatabaseManager d = new DatabaseManager();
        EventDao eDao = d.getEDao();
        AuthTokenDao aDao = d.getADao();

        if (!aDao.doesAuthTokenExist(authToken)) {
            eResult.setMessage("error: Auth Token does not exist");
            d.disconnect(false);
            return eResult;
        }

        ArrayList<Event> events = eDao.returnAllEvents(aDao.getUsername(authToken));
        eResult.setData(events);
        eResult.setSuccess(true);
        d.disconnect(true);
        return eResult;
    }
}
