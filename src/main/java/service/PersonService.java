package service;

import dao.AuthTokenDao;
import dao.DatabaseManager;
import dao.PersonDao;
import model.Person;
import result.PersonResult;

import java.util.ArrayList;

/**
 * Returns the single Person object with the specified ID, or an arraylist containing all family
 * members of the current user
 */
public class PersonService {
    /**
     * Returns the single Person object with the specified ID, or Returns
     * ALL family members of the current user. The current user is
     * determined from the provided auth token.
     * @param authToken authToken for the user for which all family members should be returned
     * @return returns the PersonResult related for the request
     */
    public PersonResult getPerson(String authToken, String parsedArray []) {
        if (parsedArray.length == 3) {  // user put an additional parameter
            return getSinglePerson(authToken, parsedArray);
        }
        else if (parsedArray.length == 2) {  // user did not put an additional parameter
            return getAllPeople(authToken);
        }
        else {
            PersonResult pResult = new PersonResult();
            pResult.setMessage("error: Invalid input. Try again");
            return pResult;
        }
    }

    public PersonResult getSinglePerson(String authToken, String parsedArray []) {
        PersonResult pResult = new PersonResult();
        String personID = parsedArray[2];
        DatabaseManager d = new DatabaseManager();
        PersonDao pDao = d.getPDao();
        AuthTokenDao aDao = d.getADao();

        if (!aDao.doesAuthTokenExist(authToken)) {
            pResult.setMessage("error: Auth Token does not exist");
            d.disconnect(false);
            return pResult;
        }

        if (!pDao.doesPersonIDExist(personID)) {
            pResult.setMessage("error: Person ID does not exist");
            d.disconnect(false);
            return pResult;
        }

        Person person = pDao.returnPerson(personID);
        String username = person.getAssociatedUsername();
        if (!username.equals(aDao.getUsername(authToken))) {
            pResult.setMessage("error: Requested Person does not belong to this user");
            d.disconnect(false);
            return pResult;
        }

        pResult = new PersonResult(person);
        pResult.setSuccess(true);
        d.disconnect(true);
        return pResult;
    }

    public PersonResult getAllPeople(String authToken) {
        PersonResult pResult = new PersonResult();
        DatabaseManager d = new DatabaseManager();
        PersonDao pDao = d.getPDao();
        AuthTokenDao aDao = d.getADao();

        if (!aDao.doesAuthTokenExist(authToken)) {
            pResult.setMessage("error: Auth Token does not exist");
            d.disconnect(false);
            return pResult;
        }

        ArrayList<Person> people = pDao.returnAllPeople(aDao.getUsername(authToken));
        pResult.setData(people);
        pResult.setSuccess(true);
        d.disconnect(true);
        return pResult;
    }
}
