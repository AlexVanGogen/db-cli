package forms.modal.personnel;

import database.types.Personnel;
import global.Alerts;
import global.Checker;
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
 * Created by Steiner on 01.05.2016.
 */
public class DeletePersonController {
    @FXML public TextField perDeleteID;
    @FXML public TextField perDeleteSecondName;
    @FXML public TextField perDeleteFirstName;
    @FXML public TextField perDeletePatherName;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;
    private CyclicList<Personnel> personnelData;

    public void initialize() {
        LinkedList<Personnel> data = new LinkedList<>();
        String query = "select id, second_name, first_name, pather_name from auto_personnel";
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            while(rs.next())
                data.add(new Personnel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            personnelData = new CyclicList<>(data);
            Personnel p = personnelData.curr();
            perDeleteID.setText(p.getId());
            perDeleteSecondName.setText(p.getSecondName());
            perDeleteFirstName.setText(p.getFirstName());
            perDeletePatherName.setText(p.getPatherName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeletePerson(ActionEvent actionEvent) {
        String id = perDeleteID.getText();
        String query = "delete from auto_personnel where id = ?";
        try {
            queryStmt = Vars.con.prepareStatement(query);
            queryStmt.setString(1, id);
            rs = queryStmt.executeQuery();
            Alerts.info(null, "Deleting was successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeletePersonnelPrev(ActionEvent actionEvent) {
        Personnel p = personnelData.prev();
        perDeleteID.setText(p.getId());
        perDeleteSecondName.setText(p.getSecondName());
        perDeleteFirstName.setText(p.getFirstName());
        perDeletePatherName.setText(p.getPatherName());
    }

    public void onDeletePersonnelNext(ActionEvent actionEvent) {
        Personnel p = personnelData.next();
        perDeleteID.setText(p.getId());
        perDeleteSecondName.setText(p.getSecondName());
        perDeleteFirstName.setText(p.getFirstName());
        perDeletePatherName.setText(p.getPatherName());
    }
}
