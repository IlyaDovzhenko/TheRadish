package databaseService;

import accounts.UserProfile;
import base.DBService;
import databaseService.dao.UsersDAO;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DBServiceImpl implements DBService {
    private final Connection connection;
    private static final String url = "jdbc:h2:./h2db";
    private static final String name = "root";
    private static final String password = "root";

    public DBServiceImpl() {
        this.connection = getH2Connection();
    }

    public UserProfile getUser(String login) throws DBException {
        try {
            return (new UsersDAO(connection).get(login));
        } catch(SQLException e) {
            throw new DBException(e);
        }
    }

    public List<String> getAll() throws DBException {
        try {
            return (new UsersDAO(connection).getAllUsers());
        } catch(SQLException e) {
            throw new DBException(e);
        }
    }

    public void addUser(UserProfile userProfile) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            //dao.dropTable();
            dao.createTable();
            dao.insertUser(userProfile);
            connection.commit();
            //return dao.get(login);
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getH2Connection() {
        try {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(password);

            return DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
