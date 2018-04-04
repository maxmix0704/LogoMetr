package sample.dao.factory;

import sample.dao.DAOFactory;
import sample.dao.interfaces.LogoDAO;

import java.sql.Connection;

public class CollectionDAOFactory extends DAOFactory {
    @Override
    public LogoDAO getLogoDAO() {
        return null;
    }

    @Override
    public Connection createConnection() {
        return null;
    }
}
