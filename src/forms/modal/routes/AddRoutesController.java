package forms.modal.routes;

import global.Alerts;
import global.Vars;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Steiner on 02.05.2016.
 */
public class AddRoutesController {
    @FXML public TextField addRouteName;

    PreparedStatement stmt = null;
    ResultSet rs;

    public void onAddRoute(ActionEvent actionEvent) {
        String routeName = addRouteName.getText();
        String query = "select * from routes where name = ?";
        try {
            stmt = Vars.con.prepareStatement(query);
            stmt.setString(1, routeName);
            rs = stmt.executeQuery();
            if (rs.next())
                Alerts.error(null, "This route already exists.");
            else {
                stmt.close();
                query = "insert into routes values(cnt_routes.nextVal, ?)";
                stmt = Vars.con.prepareStatement(query);
                stmt.setString(1, routeName);
                rs = stmt.executeQuery();
                Alerts.info(null, "Adding was successful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
