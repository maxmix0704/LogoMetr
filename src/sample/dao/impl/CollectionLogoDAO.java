package sample.dao.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.Logo;
import sample.dao.interfaces.LogoDAO;

import javax.sql.RowSet;

public class CollectionLogoDAO implements LogoDAO{

    private ObservableList<Logo> logoList = FXCollections.observableArrayList();

    @Override
    public boolean insert(Logo logo) {
        return logoList.add(logo);
    }

    @Override
    public boolean delete(Logo logo) {
        return logoList.remove(logo);
    }

    @Override
    public Logo find(Integer id) {
        return null;
    }

    @Override
    public boolean update(Logo logo) {
        return false;
    }

    @Override
    public ObservableList<Logo> getAll() {
        return logoList;
    }
}
