package sample.dao.factory;

import sample.dao.DAOFactory;
import sample.dao.impl.CollectionLogoDAO;
import sample.dao.impl.PostgresLogoDAO;
import sample.dao.interfaces.LogoDAO;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;

public class CollectionDAOFactory extends DAOFactory {
    @Override
    public LogoDAO getLogoDAO() {
        return new CollectionLogoDAO();
    }

    @Override
    public Connection createConnection() {
        return null;
    }

    public void loadDatabase(){

    }
}
