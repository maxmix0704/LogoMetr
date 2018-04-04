package sample.dao.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import org.postgresql.core.Utils;
import sample.dao.factory.PostgreSQLDAOFactory;
import sample.dao.interfaces.LogoDAO;
import sample.entity.Logo;
import sample.utils.EventTypeLogo;
import sample.utils.ImageConvert;

import javax.imageio.ImageIO;
import javax.sql.RowSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.Executors;

public class PostgresLogoDAO implements LogoDAO {
    private static String INSERT_SQL = "INSERT INTO public.logo (nameproduct,idbase,sizelogo,type_event,date)VALUES (?,?,?,?,?);";
    private static String DELETE_SQL = "DELETE FROM public.logo WHERE id=?;";
    private static String SELECT_SQL = "SELECT nameproduct, idbase, sizelogo, type_event, date, id FROM public.logo WHERE id = ?;";
    private static String SELECT_ALL_SQL = "SELECT nameproduct, idbase, sizelogo, type_event, date, id FROM public.logo;";
    private static String UPDATE_SQL = "UPDATE public.logo SET nameproduct=?, idbase=? WHERE id=?;";

    Connection conn =  new PostgreSQLDAOFactory().createConnection();

    @Override
    public boolean insert(Logo logo) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1,logo.getProductName());
            preparedStatement.setInt(2,logo.getIdBase());
            preparedStatement.setDouble(3,logo.getSize());
            preparedStatement.setString(4,logo.getEventTypeLogo().toString());
            preparedStatement.setString(5,logo.getDate());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Logo logo) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1,logo.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Logo find(Logo logo) {
        PreparedStatement preparedStatement = null;
        Logo resultLogo = new Logo();
        try {
            preparedStatement = conn.prepareStatement(SELECT_SQL);
            preparedStatement.setInt(1,logo.getId());
            ResultSet rs = preparedStatement.executeQuery();
            resultLogo.setId(rs.getInt("id"));
            resultLogo.setSize(rs.getFloat("sizelogo"));
            resultLogo.setProductName(rs.getString("nameproduct"));
            resultLogo.setDate(rs.getString("date"));
            resultLogo.setIdBase(rs.getInt("idbase"));
            resultLogo.setEventTypeLogo(EventTypeLogo.getEventType(rs.getString("type_event")));
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultLogo;
    }

    @Override
    public boolean update(Logo logo) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1,logo.getProductName());
            preparedStatement.setInt(2,logo.getIdBase());
            preparedStatement.setInt(3,logo.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ObservableList<Logo> getAll() {
        ObservableList<Logo> logoList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Logo rsLogo = null;
        try {
            preparedStatement = conn.prepareStatement(SELECT_ALL_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rsLogo = new Logo();
                rsLogo.setId(rs.getInt("id"));
                rsLogo.setSize(rs.getFloat("sizelogo"));
                rsLogo.setProductName(rs.getString("nameproduct"));
                rsLogo.setDate(rs.getString("date"));
                rsLogo.setIdBase(rs.getInt("idbase"));
                rsLogo.setEventTypeLogo(EventTypeLogo.getEventType(rs.getString("type_event")));
                logoList.add(rsLogo);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return logoList;
    }
}