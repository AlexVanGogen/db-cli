package forms.modal.personnel;

import global.Alerts;
import global.Checker;
import global.Vars;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Steiner on 01.05.2016.
 */
public class AddPersonController {

    @FXML public TextField addPerSecondName;
    @FXML public TextField addPerFirstName;
    @FXML public TextField addPerPatherName;
    PreparedStatement stmt = null;
    ResultSet rs;

    public void onAddPerson(ActionEvent actionEvent) {
        String fname = addPerFirstName.getText();
        String sname = addPerSecondName.getText();
        String pname = addPerPatherName.getText();
        if (!Checker.checkNewPersonnel(sname, fname, pname)) {
            Alerts.error(null, "Incorrect format of data");
        } else {
            String query = "insert into auto_personnel values(cnt_auto_personnel.nextVal, ?, ?, ?)";
            try {
                stmt = Vars.con.prepareStatement(query);
                stmt.setString(1, sname);
                stmt.setString(2, fname);
                stmt.setString(3, pname);
                rs = stmt.executeQuery();
                Alerts.info(null, "Adding was successful.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
