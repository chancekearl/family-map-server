package dao;
import java.sql.*;

import static java.util.UUID.randomUUID;


public class DatabaseManager {

    public DatabaseManager() {
        if (conn == null) {
            connect();
        }
        createAllTables();
        aDao = new AuthTokenDao(conn);
        eDao = new EventDao(conn);
        pDao = new PersonDao(conn);
        uDao = new UserDao(conn);
    }

    private AuthTokenDao aDao;

    public AuthTokenDao getADao() {
        return aDao;
    }

    private EventDao eDao;

    public EventDao getEDao() {
        return eDao;
    }

    private PersonDao pDao;

    public PersonDao getPDao() {
        return pDao;
    }

    private UserDao uDao;

    public UserDao getUDao() {
        return uDao;
    }



    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection conn = null;

    public Connection getConn() {
        return conn;
    }

    public String generateToken() {
        String token = randomUUID().toString();
        token = token.substring(0, 8); // first 8 characters of the UUID
        return token;
    }

    private String authTable = "CREATE TABLE IF NOT EXISTS AuthToken (\n" +
            "\tUsername\tTEXT NOT NULL,\n" +
            "\tAuthToken\tTEXT NOT NULL UNIQUE,\n" +
            "\tPRIMARY KEY(AuthToken)\n" +
            ");";

    private String dropAuthTable = "DROP TABLE IF EXISTS AuthToken";

    private String eventTable = "CREATE TABLE IF NOT EXISTS Event (\n" +
            "\tEventID\tTEXT NOT NULL UNIQUE,\n" +
            "\tAssociatedUsername\tTEXT NOT NULL,\n" +
            "\tPerson\tTEXT NOT NULL,\n" +
            "\tLatitude\tREAL NOT NULL,\n" +
            "\tLongitude\tREAL NOT NULL,\n" +
            "\tCountry\tTEXT NOT NULL,\n" +
            "\tCity\tTEXT NOT NULL,\n" +
            "\tEventType\tTEXT NOT NULL,\n" +
            "\tYear\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(EventID)\n" +
            ");";

    private String dropEventTable = "DROP TABLE IF EXISTS Event";

    private String personTable = "CREATE TABLE IF NOT EXISTS Person (\n" +
            "\tPersonID\tTEXT NOT NULL UNIQUE,\n" +
            "\tAssociatedUsername\tTEXT NOT NULL,\n" +
            "\tFirstName\tTEXT NOT NULL,\n" +
            "\tLastName\tTEXT NOT NULL,\n" +
            "\tGender\tTEXT NOT NULL,\n" +
            "\tFatherID\tTEXT,\n" +
            "\tMotherID\tTEXT,\n" +
            "\tSpouseID\tTEXT,\n" +
            "\tPRIMARY KEY(PersonID)\n" +
            ");";

    private String dropPersonTable = "DROP TABLE IF EXISTS Person";

    private String userTable = "CREATE TABLE IF NOT EXISTS User (\n" +
            "\tUsername\tTEXT NOT NULL UNIQUE,\n" +
            "\tPassword\tTEXT NOT NULL,\n" +
            "\tEmail\tTEXT NOT NULL,\n" +
            "\tFirstName\tTEXT NOT NULL,\n" +
            "\tLastName\tTEXT NOT NULL,\n" +
            "\tGender\tTEXT NOT NULL,\n" +
            "\tPersonID\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(Username)\n" +
            ");";

    private String dropUserTable = "DROP TABLE IF EXISTS User";

    public void createAuthTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(authTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAuthTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(dropAuthTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createEventTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(eventTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteEventTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(dropEventTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createPersonTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(personTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePersonTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(dropPersonTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createUserTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(userTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUserTable() {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(dropUserTable);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createAllTables() {
        createAuthTable();
        createEventTable();
        createPersonTable();
        createUserTable();
    }

    public void dropAllTables() {
        deleteAuthTable();
        deleteEventTable();
        deletePersonTable();
        deleteUserTable();
    }

    public void connect() {
        String dbName = "fms.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;

        try {
            conn = DriverManager.getConnection(connectionURL);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect(boolean commit) {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace(); //Java is dumb
            }
        }
    }
}
