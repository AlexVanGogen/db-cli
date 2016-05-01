package forms.modal;

import global.Alerts;
import global.DBUtils;
import global.Vars;
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
public class CarsOnRouteController {
    @FXML
    private ComboBox<String> carsOnRouteComboBox = new ComboBox<>();
    private PreparedStatement queryStmt = null;
    private ResultSet rs;

    public void initialize() {
        String query = "select name from routes";
        ObservableList<String> data = DBUtils.executeSingleColonQuery(query);
        carsOnRouteComboBox.setItems(data);
    }

    public void onBestTimeOnRoute(ActionEvent actionEvent) {
        String query = "select count(name) as autos_on_route from journal\n" +
                "join steiner.routes on route_id = steiner.routes.id\n" +
                "where name = ?" +
                "and time_in is null";
        try {
            String route = carsOnRouteComboBox.getValue();
            queryStmt = Vars.con.prepareStatement(query);
            queryStmt.setString(1, route);
            rs = queryStmt.executeQuery();
            if (rs.next())
                Alerts.info("Result", "There are " + rs.getString(1) + " cars on route " + route);
            else
                Alerts.error("Not exist", "Trips on route " + route + " do not exist.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
