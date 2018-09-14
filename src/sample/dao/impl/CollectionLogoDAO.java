package sample.dao.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import sample.entity.Logo;
import sample.dao.interfaces.LogoDAO;
import sample.utils.Utils;

import javax.imageio.ImageIO;
import javax.sql.RowSet;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;

public class CollectionLogoDAO implements LogoDAO{

    private ObservableList<Logo> logoList;

    public CollectionLogoDAO(){
        logoList = FXCollections.observableArrayList();
        //load collection from file (collectionDB)
        logoList=Utils.loadDb();
    }

    @Override
    public boolean insert(Logo logo) {
        if (logo.getId()==0)
        logo.setId(logoList.size()+1);
        if (logoList.add(logo)) {
            Utils.saveDb(logoList);
            return true;
        } else
        return false;
    }

    @Override
    public boolean delete(Logo logo) {
        if (logoList.remove(logo)){
            Utils.saveDb(logoList);
            return true;
        }
        return false;
    }

    @Override
    public Logo find(Logo logo) {
        return logoList.get(logoList.indexOf(logo));
    }

    @Override
    public boolean update(Logo logo) {
        Logo buf = new Logo(logo);
        logoList.remove(logoList.indexOf(logo));
        if (logoList.add(buf)) {
            Utils.saveDb(logoList);
            return  true;
        } else
        return false;
    }

    @Override
    public ObservableList<Logo> getAll() {
        return logoList;
    }
}
