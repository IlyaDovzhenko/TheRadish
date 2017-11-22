package accounts;

import databaseService.DBException;
import databaseService.DBServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private DBServiceImpl dbServiceImpl;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        dbServiceImpl = new DBServiceImpl();
    }

    public void addNewUser(UserProfile userProfile) {
        //loginToProfile.put(userProfile.getLogin(), userProfile);
        try {
            dbServiceImpl.addUser(userProfile);
            System.out.println(userProfile.toString());
        } catch(DBException e) {
            e.printStackTrace();
        }

    }

    public UserProfile getUserByLogin(String login) {
        //return loginToProfile.get(login);
        try {
            return dbServiceImpl.getUser(login);
        } catch (DBException e) {
            System.out.println("User " + login + " does not exist.");
        }
        return null;
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public UserProfile getUserBySesionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
