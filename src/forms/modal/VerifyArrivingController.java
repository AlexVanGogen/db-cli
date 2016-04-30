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
public class VerifyArrivingController {

    @FXML private ComboBox<String> verifyArrivingComboBox = new ComboBox<>();
    private PreparedStatement queryStmt = null;
    private ResultSet rs;

    public void initialize() {
        String query = "select num from auto join journal on auto.id = journal.auto_id where time_in is null";
        ObservableList<String> data = DBUtils.executeSingleColonQuery(query);
        verifyArrivingComboBox.setItems(data);
    }

    public void onVerifyArriving(ActionEvent actionEvent) {
        String autoId;
        String updQuery = "update journal set time_in = CURRENT_TIMESTAMP where auto_id IN " +
                "(select id from auto where num = ?) and time_in is null";
        try {
            queryStmt = Vars.con.prepareStatement(updQuery);
            queryStmt.setString(1, verifyArrivingComboBox.getValue());
            rs = queryStmt.executeQuery();
            Alerts.info("Update successful", "Update has ended successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
