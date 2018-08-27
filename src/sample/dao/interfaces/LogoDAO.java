package sample.dao.interfaces;

import javafx.collections.ObservableList;
import sample.entity.Logo;

import javax.sql.RowSet;
import java.util.Collection;

public interface LogoDAO {
    boolean insert(Logo logo);
    boolean delete(Logo logo);
    Logo find(Integer id);
    boolean update(Logo logo);
    ObservableList<Logo> getAll();
}
