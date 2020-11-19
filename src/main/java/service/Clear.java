package service;

import dao.DatabaseManager;
import result.ClearResult;

/**
 * Deletes ALL data from the database, including user accounts, AuthTokens,
 * and generated person and event data
 */
public class Clear {
    /**
     * Deletes ALL data from the database, including user accounts, AuthTokens,
     * and generated person and event data
     * @return result of the clear attempt
     */
    public ClearResult clear() {
        DatabaseManager d = new DatabaseManager();
        ClearResult cResult = new ClearResult();

        try {
            d.dropAllTables();
            d.disconnect(true);
            cResult.setMessage("Clear succeeded.");
            cResult.setSuccess(true);
            return cResult;
        }
        catch (InternalError e) {
            cResult.setMessage("error: Internal server error");
            d.disconnect(false);
            return cResult;
        }
    }
}
