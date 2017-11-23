package databaseService.dao;


import accounts.UserProfile;
import databaseService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UserProfile get(String login) throws SQLException {
        return executor.execQuery("select * from users where login='" + login + "'", result-> {
            result.next();
            return new UserProfile(result.getString(1),
                                    result.getString(2));
        });
    }

    public List<String> getAllUsers() throws SQLException {
        return executor.execQuery("select login from users", result-> {
            List<String> usersList = new ArrayList<>();
            for(;;) {
                result.next();
                if(!result.isLast()) {
                    usersList.add(result.getString(1));
                } else {
                    usersList.add(result.getString(1));
                    break;
                }
            }
            return usersList;
        });
    }

    public void insertUser(UserProfile userProfile) throws SQLException {
        executor.execUpdate("insert into users (login, password) values ('" + userProfile.getLogin() + "','" + userProfile.getPassword() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key(id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }


}
