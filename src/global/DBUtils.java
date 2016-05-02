package global;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Steiner on 01.05.2016.
 */
public class DBUtils {
    public static ObservableList<String> executeSingleColonQuery(String query) {
        ObservableList<String> data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            PreparedStatement stmt = Vars.con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next())
                data.add(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void executeDeletionById(String query, String id) {
        ResultSet rs;
        try {
            PreparedStatement stmt = Vars.con.prepareStatement(query);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            Alerts.info(null, "Deleting was successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
