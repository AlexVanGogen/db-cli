package forms.modal.routes;

import database.types.Routes;
import global.Alerts;
import global.Vars;
import global.struct.CyclicList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Steiner on 02.05.2016.
 */
public class EditRoutesController {
    @FXML public TextField editRoutesID;
    @FXML public TextField editRoutesName;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;
    private CyclicList<Routes> routesData;

    public void initialize() {
        LinkedList<Routes> data = new LinkedList<>();
        String query = "select id, name from routes";
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            while(rs.next())
                data.add(new Routes(rs.getString(1), rs.getString(2)));
            routesData = new CyclicList<>(data);
            Routes r = routesData.curr();
            editRoutesID.setText(r.getId());
            editRoutesName.setText(r.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onEditRoutesPrev(ActionEvent actionEvent) {
        Routes r = routesData.prev();
        editRoutesID.setText(r.getId());
        editRoutesName.setText(r.getName());
    }

    public void onEditRoutesNext(ActionEvent actionEvent) {
        Routes r = routesData.next();
        editRoutesID.setText(r.getId());
        editRoutesName.setText(r.getName());
    }

    public void onUpdateRoutes(ActionEvent actionEvent) {
        String id = editRoutesID.getText();
        String name = editRoutesName.getText();
        String query = "select * from routes where name = ? and not (id = ?)";
        try {
            queryStmt = Vars.con.prepareStatement(query);
            queryStmt.setString(1, name);
            queryStmt.setString(2, id);
            rs = queryStmt.executeQuery();
            if (rs.next())
                Alerts.error(null, "This route already exists.");
            else {
                queryStmt.close();
                query = "update routes set name = ? where id = ?";
                queryStmt = Vars.con.prepareStatement(query);
                queryStmt.setString(1, name);
                queryStmt.setString(2, id);
                rs = queryStmt.executeQuery();
                Alerts.info(null, "Updating was successful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
