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
public class EditPersonController {

    @FXML public TextField perEditID;
    @FXML public TextField perEditSecondName;
    @FXML public TextField perEditFirstName;
    @FXML public TextField perEditPatherName;

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
            perEditID.setText(p.getId());
            perEditSecondName.setText(p.getSecondName());
            perEditFirstName.setText(p.getFirstName());
            perEditPatherName.setText(p.getPatherName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUpdatePerson(ActionEvent actionEvent) {
        String id = perEditID.getText();
        String f = perEditFirstName.getText();
        String s = perEditSecondName.getText();
        String p = perEditPatherName.getText();
        if (!Checker.checkNewPersonnel(s, f, p))
            Alerts.error(null, "Incorrect format of data");
        else {
            String query = "update auto_personnel set second_name = ?, first_name = ?, pather_name = ? where id = ?";
            try {
                queryStmt = Vars.con.prepareStatement(query);
                queryStmt.setString(1, s);
                queryStmt.setString(2, f);
                queryStmt.setString(3, p);
                queryStmt.setString(4, id);
                rs = queryStmt.executeQuery();
                Alerts.info(null, "Updating was successful.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onEditPersonnelPrev(ActionEvent actionEvent) {
        Personnel p = personnelData.prev();
        perEditID.setText(p.getId());
        perEditSecondName.setText(p.getSecondName());
        perEditFirstName.setText(p.getFirstName());
        perEditPatherName.setText(p.getPatherName());
    }

    public void onEditPersonnelNext(ActionEvent actionEvent) {
        Personnel p = personnelData.next();
        perEditID.setText(p.getId());
        perEditSecondName.setText(p.getSecondName());
        perEditFirstName.setText(p.getFirstName());
        perEditPatherName.setText(p.getPatherName());
    }
}
