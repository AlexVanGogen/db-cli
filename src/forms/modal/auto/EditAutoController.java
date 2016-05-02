package forms.modal.auto;

import database.types.Auto;
import global.Alerts;
import global.Checker;
import global.Vars;
import global.struct.CyclicList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by Steiner on 02.05.2016.
 */
public class EditAutoController {

    @FXML public TextField editAutoID;
    @FXML public TextField editAutoCode;
    @FXML public TextField editAutoMark;
    @FXML public ComboBox<String> editColorComboBox;
    @FXML public TextField editAutoOwner;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;
    private CyclicList<Auto> autoData;

    public void initialize() {
        editColorComboBox.setItems(FXCollections.observableArrayList("White", "Black", "Blue", "Red", "Green", "Yellow", "Violet", "Pink"));
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
            editAutoID.setText(a.getId());
            editAutoCode.setText(a.getCode());
            editAutoMark.setText(a.getMark());
            editColorComboBox.setValue(a.getColor());
            editAutoOwner.setText(a.getOwner());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onEditAutoPrev(ActionEvent actionEvent) {
        Auto a = autoData.prev();
        editAutoID.setText(a.getId());
        editAutoCode.setText(a.getCode());
        editAutoMark.setText(a.getMark());
        editColorComboBox.setValue(a.getColor());
        editAutoOwner.setText(a.getOwner());
    }

    public void onEditAutoNext(ActionEvent actionEvent) {
        Auto a = autoData.next();
        editAutoID.setText(a.getId());
        editAutoCode.setText(a.getCode());
        editAutoMark.setText(a.getMark());
        editColorComboBox.setValue(a.getColor());
        editAutoOwner.setText(a.getOwner());
    }

    public void onUpdateAuto(ActionEvent actionEvent) {
        String code = editAutoCode.getText();
        String id = editAutoID.getText();
        if (Checker.checkNewAutoCode(code)) {
            String query = "select num from auto where num = ? and not (id = ?)";
            try {
                queryStmt = Vars.con.prepareStatement(query);
                queryStmt.setString(1, code);
                queryStmt.setString(2, id);
                rs = queryStmt.executeQuery();
                if (rs.next())
                    Alerts.error(null, "Car with this code already exists.");
                else {
                    queryStmt.close();
                    query = "update auto set num = ?, color = ? where id = ?";
                    queryStmt = Vars.con.prepareStatement(query);
                    queryStmt.setString(1, code);
                    queryStmt.setString(2, editColorComboBox.getValue());
                    queryStmt.setString(3, id);
                    rs = queryStmt.executeQuery();
                    Alerts.info(null, "Updating was successful.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
            Alerts.error(null, "Incorrect format of code");
    }
}
