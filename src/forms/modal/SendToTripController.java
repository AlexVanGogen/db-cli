package forms.modal;

import global.Alerts;
import global.Vars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Steiner on 30.04.2016.
 */
public class SendToTripController {
    public ComboBox<String> sendCarComboBox = new ComboBox<String>();
    public ComboBox<String> sendRouteComboBox = new ComboBox<String>();
    private PreparedStatement queryStmt1 = null, queryStmt2 = null;
    private ResultSet rs;


    public void initialize() {
        ObservableList<String> data1 = FXCollections.observableArrayList();
        ObservableList<String> data2 = FXCollections.observableArrayList();
        String query1 = "select num from auto where num not in (select num from auto join journal on auto.id = journal.auto_id where time_in is null)";
        try {
            queryStmt1 = Vars.con.prepareStatement(query1);
            rs = queryStmt1.executeQuery();
            while (rs.next())
                data1.add(rs.getString(1));
            sendCarComboBox.setItems(data1);
            String query2 = "select name from routes";
            queryStmt2 = Vars.con.prepareStatement(query2);
            rs = queryStmt2.executeQuery();
            while (rs.next())
                data2.add(rs.getString(1));
            sendRouteComboBox.setItems(data2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void onSendToTrip(ActionEvent actionEvent) {
        String car = sendCarComboBox.getValue();
        String route = sendRouteComboBox.getValue();
        String carId, routeId;
        String queryGetAutoId = "select id from auto where num = ?";
        String queryGetRouteId = "select id from routes where name = ?";
        String queryInsert = "insert into journal values(cnt_journal.nextVal, CURRENT_TIMESTAMP, null, ?, ?)";
        try {
            queryStmt1 = Vars.con.prepareStatement(queryGetAutoId);
            queryStmt1.setString(1, car);
            rs = queryStmt1.executeQuery();
            rs.next();
            carId = rs.getString(1);
            queryStmt2 = Vars.con.prepareStatement(queryGetRouteId);
            queryStmt2.setString(1, route);
            rs = queryStmt1.executeQuery();
            rs.next();
            routeId = rs.getString(1);
            System.out.println(carId + " " + routeId);
            queryStmt1 = Vars.con.prepareStatement(queryInsert);
            queryStmt1.setString(1, carId);
            queryStmt1.setString(2, routeId);
            rs = queryStmt1.executeQuery();
            Alerts.info("Adding successful", "Adding was successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
