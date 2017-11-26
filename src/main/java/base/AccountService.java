package base;

import accounts.UserProfile;

import java.util.List;

public interface AccountService {
    void addNewUser(UserProfile userProfile);
    UserProfile getUserByLogin(String login);
    List<String> getAllUsersList();
    void addSession(String session, UserProfile userProfile);
    UserProfile getUserBySesionId(String sessionId);
    void deleteSession(String sessionId);
}
