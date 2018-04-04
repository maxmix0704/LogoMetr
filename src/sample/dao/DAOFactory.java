package sample.dao;

import sample.dao.factory.CollectionDAOFactory;
import sample.dao.factory.MySQLDAOFactory;
import sample.dao.factory.PostgreSQLDAOFactory;
import sample.dao.interfaces.LogoDAO;

import java.sql.Connection;

public abstract class DAOFactory {
    public static final int COLLECTION = 1;
    public static final int POSTGRESQL = 2;
    public static final int MYSQL = 3;

    public abstract LogoDAO getLogoDAO();
    public abstract Connection createConnection();

    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case COLLECTION :
                return new CollectionDAOFactory();
            case POSTGRESQL :
                return new PostgreSQLDAOFactory();
            case MYSQL :
                return new MySQLDAOFactory();
            default :
                return null;
        }
    }
}
