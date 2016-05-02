package forms.modal.routes;

import database.types.Routes;
import global.DBUtils;
import global.Vars;
import global.struct.CyclicList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Steiner on 02.05.2016.
 */
public class DeleteRoutesController {
    @FXML public Button deleteRouteButton;
    @FXML TextField deleteRoutesID;
    @FXML public TextField deleteRoutesName;

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
            deleteRoutesID.setText(r.getId());
            deleteRoutesName.setText(r.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "select route_id from journal where time_in is null order by route_id";
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            int ptr = 0;
            while (rs.next()) {
                while (!(routesData.get(ptr).getId().equals(rs.getString(1)))) ptr++;
                routesData.get(ptr).setHasTrips(true);
            }
            queryStmt.close();
            rs.close();
            deleteRouteButton.setDisable(routesData.curr().hasTrips());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteRoutesPrev(ActionEvent actionEvent) {
        Routes r = routesData.prev();
        deleteRoutesID.setText(r.getId());
        deleteRoutesName.setText(r.getName());
        deleteRouteButton.setDisable(r.hasTrips());
    }

    public void onDeleteRoutesNext(ActionEvent actionEvent) {
        Routes r = routesData.next();
        deleteRoutesID.setText(r.getId());
        deleteRoutesName.setText(r.getName());
        deleteRouteButton.setDisable(r.hasTrips());
    }

    public void onDeleteRoutes(ActionEvent actionEvent) {
        String id = routesData.curr().getId();
        String query = "delete from routes where id = ?";
        DBUtils.executeDeletionById(query, id);
    }
}
