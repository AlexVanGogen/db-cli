package forms.modal.auto;

import database.types.Auto;
import global.Alerts;
import global.DBUtils;
import global.Vars;
import global.struct.CyclicList;
import javafx.collections.FXCollections;
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
public class DeleteAutoController {
    @FXML public TextField deleteAutoID;
    @FXML public TextField deleteAutoCode;
    @FXML public TextField deleteAutoMark;
    @FXML public TextField deleteAutoColor;
    @FXML public Button deleteAutoButton;
    @FXML public TextField deleteAutoOwner;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;
    private CyclicList<Auto> autoData;

    public void initialize() {
        LinkedList<Auto> data = new LinkedList<>();
        String query = "select auto.id, num, mark, color, second_name, first_name, pather_name from auto " +
                "join auto_personnel on auto_personnel.id = auto.personnel_id";
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            while (rs.next())
                data.add(new Auto(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7)));
            autoData = new CyclicList<>(data);
            Auto a = autoData.curr();
            deleteAutoID.setText(a.getId());
            deleteAutoCode.setText(a.getCode());
            deleteAutoMark.setText(a.getMark());
            deleteAutoColor.setText(a.getColor());
            deleteAutoOwner.setText(a.getOwner());
            queryStmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "select auto_id from journal where time_in is null order by auto_id";
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            int ptr = 0;
            while (rs.next()) {
                while (!(autoData.get(ptr).getId().equals(rs.getString(1)))) ptr++;
                autoData.get(ptr).setOnTrip(true);
            }
            queryStmt.close();
            rs.close();
            deleteAutoButton.setDisable(autoData.curr().isOnTrip());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteAuto(ActionEvent actionEvent) {
        String id = autoData.curr().getId();
        String query = "delete from auto where id = ?";
        DBUtils.executeDeletionById(query, id);
    }

    public void onDeleteAutoPrev(ActionEvent actionEvent) {
        Auto a = autoData.prev();
        deleteAutoID.setText(a.getId());
        deleteAutoCode.setText(a.getCode());
        deleteAutoMark.setText(a.getMark());
        deleteAutoColor.setText(a.getColor());
        deleteAutoOwner.setText(a.getOwner());
        deleteAutoButton.setDisable(a.isOnTrip());

    }

    public void onDeleteAutoNext(ActionEvent actionEvent) {
        Auto a = autoData.next();
        deleteAutoID.setText(a.getId());
        deleteAutoCode.setText(a.getCode());
        deleteAutoMark.setText(a.getMark());
        deleteAutoColor.setText(a.getColor());
        deleteAutoOwner.setText(a.getOwner());
        deleteAutoButton.setDisable(a.isOnTrip());
    }
}
