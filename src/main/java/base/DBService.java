package base;

import accounts.UserProfile;
import databaseService.DBException;

public interface DBService {
    UserProfile getUser(String login) throws DBException;
    void addUser(UserProfile userProfile) throws DBException;
    void cleanUp() throws DBException;
    void printConnectInfo();
}
