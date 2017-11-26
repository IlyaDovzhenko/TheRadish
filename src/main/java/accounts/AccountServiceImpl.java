package accounts;

import base.AccountService;
import base.DBService;
import databaseService.DBException;
import databaseService.DBServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountServiceImpl implements AccountService {
    private final Map<String, UserProfile> sessionIdToProfile;
    private DBService dbService;

    public AccountServiceImpl() {
        sessionIdToProfile = new HashMap<>();
        dbService = new DBServiceImpl();
        /*try {
            dbService.cleanUp();
            System.out.println("Clear");
        } catch (DBException e) {
            System.out.println("Not clear");
        }*/
    }

    public void addNewUser(UserProfile userProfile) {
        try {
            dbService.addUser(userProfile);
            System.out.println(userProfile.toString());
        } catch(DBException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
        try {
            return dbService.getUser(login);
        } catch (DBException e) {
            System.out.println("User " + login + " does not exist.");
        }
        return null;
    }

    public List<String> getAllUsersList() {
        List<String> list;
        try {
            list = dbService.getAll();
            for(String text : list) {
                System.out.print(text + " ");
            }
            return list;
        } catch(DBException ignore) {
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
