package forms.modal;

import global.Alerts;
import global.DBUtils;
import global.Vars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Steiner on 01.05.2016.
 */
public class BestTimeOnRouteController {

    @FXML private ComboBox<String> bestTimeOnRouteComboBox = new ComboBox<>();
    private PreparedStatement queryStmt = null;
    private ResultSet rs;

    public void initialize() {
        String query = "select name from routes";
        ObservableList<String> data = DBUtils.executeSingleColonQuery(query);
        bestTimeOnRouteComboBox.setItems(data);
    }

    public void onBestTimeOnRoute(ActionEvent actionEvent) {
        String query = "select fastest_time, num from\n" +
                "(select time_in - time_out as fastest_time, auto_id from journal\n" +
                "where route_id in\n" +
                "  (select id from routes where name = ?)\n" +
                "and time_in - time_out = (select min(time_in - time_out) from journal\n" +
                "where route_id in\n" +
                "  (select id from routes where name = ?))\n" +
                ") join auto on auto_id = auto.id";
        try {
            String route = bestTimeOnRouteComboBox.getValue();
            queryStmt = Vars.con.prepareStatement(query);
            queryStmt.setString(1, route);
            queryStmt.setString(2, route);
            rs = queryStmt.executeQuery();
            if (rs.next())
                Alerts.info("Result", "The best time on route " + route + " is " + rs.getString(1) + "\n" +
                    "Set by car " + rs.getString(2));
            else
                Alerts.error("Not exist", "Trips on route " + route + " do not exist.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
