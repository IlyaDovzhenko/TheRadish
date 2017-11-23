package base;

import accounts.UserProfile;
import databaseService.DBException;

import java.util.List;

public interface DBService {
    UserProfile getUser(String login) throws DBException;
    List<String> getAll() throws DBException;
    void addUser(UserProfile userProfile) throws DBException;
    void cleanUp() throws DBException;
    void printConnectInfo();
}
