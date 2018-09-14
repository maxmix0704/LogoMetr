package sample.dao.factory;

import sample.dao.DAOFactory;
import sample.dao.impl.PostgresLogoDAO;
import sample.dao.interfaces.LogoDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class PostgreSQLDAOFactory extends DAOFactory {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "t710ph";

    public Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found");
            e.printStackTrace();
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
        }
        if (connection != null) {
            System.out.println("Connection is successful");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }


    @Override
    public LogoDAO getLogoDAO() {
        return  new PostgresLogoDAO();
    }
}
