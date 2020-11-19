package service;

import com.google.gson.Gson;
import dao.DatabaseManager;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import jsonObject.*;
import model.Event;
import model.Person;
import model.User;
import result.FillResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/*
 * Populates the server's database with generated data for the specified user name.
 * The required username parameter must be a user already registered with the server. If there is
 * any data in the database already associated with the given user name, it is deleted. The
 * optional generations parameter lets the caller specify the number of generations of ancestors
 * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
 * persons each with associated events)
 *
 */
public class Fill {
    private int DEFAULT_GENERATIONS = 4;

    private ArrayList<String> femaleNames;

    private ArrayList<String> maleNames;

    private ArrayList<String> lastNames;

    private ArrayList<Location> locations;

    private int CURRENT_YEAR = 2020;

    private int personsAdded = 0;

    private int eventsAdded = 0;

    private int totalGenerations = 0;

    private Random rand = new Random();

    public int getPersonsAdded() {
        return personsAdded;
    }

    public void setPersonsAdded(int num) {
        personsAdded = num;
    }

    public int getEventsAdded() {
        return eventsAdded;
    }

    public void setEventsAdded(int num) {
        eventsAdded = num;
    }
    /**
     * Same as class description, populates the database for the given user
     * @param parsedArray String array passed from the handler
     * @return result of the attempt
     */
    public FillResult fill(String parsedArray []) {
        FillResult fResult = new FillResult();
        int generations = DEFAULT_GENERATIONS;

        if (parsedArray.length == 4) {
            if (!isInteger(parsedArray[3])) {
                fResult.setMessage("error: Invalid integer parameter. Try again");
                return fResult;
            }
            else if (Integer.parseInt(parsedArray[3]) < 0) {
                fResult.setMessage("error: Number of generations cannot be less than zero");
                return fResult;
            }
            else {
                generations = Integer.parseInt(parsedArray[3]);
            }
        }
        else if (parsedArray.length != 3) {
            fResult.setMessage("error: Invalid parameters passed. Please try again");
            return fResult;
        }

        DatabaseManager d = new DatabaseManager();
        UserDao uDao = d.getUDao();

        String username = parsedArray[2];
        if (!uDao.doesUsernameExist(username)) {
            fResult.setMessage("error: User does not exist");
            d.disconnect(false);
            return fResult;
        }

        d.disconnect(true);

        return fillHelper(username, generations);
    }

    public FillResult fillHelper(String username, int generations) {
        personsAdded = 0;
        eventsAdded = 0;
        totalGenerations = generations;

        FillResult fResult = new FillResult();
        loadJsonFiles();

        DatabaseManager d = new DatabaseManager();
        PersonDao pDao = d.getPDao();
        EventDao eDao = d.getEDao();
        UserDao uDao = d.getUDao();

        User currentUser = uDao.returnUser(username);
        Person currentPerson = new Person(currentUser.getPersonID(), username, currentUser.getFirstName(), currentUser.getLastName(), currentUser.getGender(), null, null, null);

        pDao.deleteUsernameData(username);
        eDao.deleteUsernameData(username);

        try {
            Location marriageLocation = locations.get(rand.nextInt(locations.size() - 1));
            makeParents(d, currentPerson, marriageLocation, generations);
        }
        catch (SQLException e) {
            d.disconnect(false);
            fResult.setMessage("error: A problem occurred filing the user");
        }

        fResult.setMessage("Successfully added " + personsAdded + " persons and " + eventsAdded + " events to the database.");
        fResult.setSuccess(true);
        d.disconnect(true);
        return fResult;
    }

    public void makeParents(DatabaseManager d, Person currentPerson, Location marriageLocation, int generationsLeft) throws SQLException {
        int birthYear = CURRENT_YEAR - (29 * (totalGenerations + 1 - generationsLeft)); // realistic death of user's mother required to be born at later (29 vs. 25)

        createEvents(d, currentPerson, currentPerson.getAssociatedUsername(), birthYear, marriageLocation);
        PersonDao pDao = d.getPDao();

        if (generationsLeft > 0) {
            Person mother = createMother(d, currentPerson.getAssociatedUsername());
            Person father = createFather(d, currentPerson.getAssociatedUsername());
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            marriageLocation = locations.get(rand.nextInt(locations.size() - 1));
            makeParents(d, mother, marriageLocation, generationsLeft - 1);
            makeParents(d, father, marriageLocation, generationsLeft - 1);

            currentPerson.setMotherID(mother.getPersonID());
            currentPerson.setFatherID(father.getPersonID());
        }

        pDao.addPerson(currentPerson);
        ++personsAdded;

        return;
    }

    public Person createMother(DatabaseManager d, String associatedUsername) {
        String personID = d.generateToken();
        String firstName = femaleNames.get(rand.nextInt(femaleNames.size() - 1));
        String lastName = lastNames.get(rand.nextInt(lastNames.size() - 1));
        String gender = "f";

        return new Person(personID, associatedUsername, firstName, lastName, gender, null, null, null);
    }

    public Person createFather(DatabaseManager d, String associatedUsername) {
        String personID = d.generateToken();
        String firstName = maleNames.get(rand.nextInt(maleNames.size() - 1));
        String lastName = lastNames.get(rand.nextInt(lastNames.size() - 1));
        String gender = "m";

        return new Person(personID, associatedUsername, firstName, lastName, gender, null, null, null);
    }

    public void createEvents(DatabaseManager d, Person currentPerson, String associatedUsername, int birthYear, Location marriageLocation) throws SQLException {
        String personID = currentPerson.getPersonID();
        EventDao eDao = d.getEDao();

        String birthID = d.generateToken();
        Location birthLocation = locations.get(rand.nextInt(locations.size() - 1));
        Event birth = new Event(birthID, associatedUsername, personID, birthLocation.getLatitude(), birthLocation.getLongitude(),
                birthLocation.getCountry(), birthLocation.getCity(), "Birth", birthYear);
        eDao.addEvent(birth);
        ++eventsAdded;


        String baptismID = d.generateToken();
        Location baptismLocation = locations.get(rand.nextInt(locations.size() - 1));
        Event baptism = new Event(baptismID, associatedUsername, personID, baptismLocation.getLatitude(), baptismLocation.getLongitude(),
                baptismLocation.getCountry(), baptismLocation.getCity(), "Baptism", birthYear + 8);
        eDao.addEvent(baptism);
        ++eventsAdded;

        String marriageID = d.generateToken();
        Event marriage = new Event(marriageID, associatedUsername, personID, marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                marriageLocation.getCountry(), marriageLocation.getCity(), "Marriage", birthYear + 24);
        eDao.addEvent(marriage);
        ++eventsAdded;

        if ((birthYear + 50) < CURRENT_YEAR) { // realistic death of user's mother required in tests so have deaeth sometime over 50 instead of 72...
            String deathID = d.generateToken();
            Location deathLocation = locations.get(rand.nextInt(locations.size() - 1));
            Event death = new Event(deathID, associatedUsername, personID, deathLocation.getLatitude(), deathLocation.getLongitude(),
                    deathLocation.getCountry(), deathLocation.getCity(), "Death", birthYear + 50);
            eDao.addEvent(death);
            ++eventsAdded;
        }
    }

    public boolean isInteger(String toCheck) {
        try {
            int x = Integer.parseInt(toCheck);
        }
        catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public void loadJsonFiles() {
        Gson gson = new Gson();
        try {
            locations = gson.fromJson(new FileReader("json/locations.json"), LocationFile.class).getData();

            maleNames = gson.fromJson(new FileReader("json/mnames.json"), MaleNamesFile.class).getData();

            femaleNames = gson.fromJson(new FileReader("json/fnames.json"), FemaleNamesFile.class).getData();

            lastNames = gson.fromJson(new FileReader("json/snames.json"), LastNamesFile.class).getData();

            /*JsonObject jsonObj = gson.fromJson(new FileReader("resources/locations.json"), JsonObject.class);
            locations = gson.fromJson(jsonObj.get("data"), LocationFile.class).getData();

            jsonObj = gson.fromJson(new FileReader("resources/mnames.json"), JsonObject.class);
            maleNames = gson.fromJson(jsonObj.get("data"), MaleNamesFile.class).getData();

            jsonObj = gson.fromJson(new FileReader("resources/fnames.json"), JsonObject.class);
            femaleNames = gson.fromJson(jsonObj.get("data"), FemaleNamesFile.class).getData();

            jsonObj = gson.fromJson(new FileReader("resources/snames.json"), JsonObject.class);
            lastNames = gson.fromJson(jsonObj.get("data"), LastNamesFile.class).getData();
            */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
