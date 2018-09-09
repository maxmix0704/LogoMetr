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
    }

    @Override
    public boolean insert(Logo logo) {
        logo.setId(logoList.size()+1);
        logoList.add(logo);
        Utils.saveDb(logoList);
        return true;
    }

    @Override
    public boolean delete(Logo logo) {
        logoList.remove(logo);
        Utils.saveDb(logoList);
        return true;
    }

    @Override
    public Logo find(Logo logo) {
        return logoList.get(logoList.indexOf(logo));
    }

    @Override
    public boolean update(Logo logo) {
        Logo buf = new Logo();

        buf.setId(logo.getId());
        buf.setProductName(logo.getProductName());
        buf.setIdBase(logo.getIdBase());
        buf.setSize(logo.getSize());
        buf.setEventTypeLogo(logo.getEventTypeLogo());
        buf.setDate(logo.getDate());
        buf.setImage(logo.getImage());

        logoList.remove(logoList.indexOf(logo));
        logoList.add(buf);

        Utils.saveDb(logoList);
        return false;
    }

    @Override
    public ObservableList<Logo> getAll() {
        return logoList;
    }
}
