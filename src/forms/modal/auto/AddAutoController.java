package forms.modal.auto;

import global.Alerts;
import global.Checker;
import global.Vars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Steiner on 02.05.2016.
 */
public class AddAutoController {
    @FXML public TextField addAutoCode;
    @FXML public ComboBox<String> addAutoMark = new ComboBox<>();
    @FXML public ComboBox<String> addAutoColor;
    @FXML public ComboBox<String> addAutoOwner;

    PreparedStatement stmt = null;
    ResultSet rs;

    public void initialize() {
        ObservableList<String> marks = FXCollections.observableArrayList("Mercedes", "Ford", "Gazelle");
        ObservableList<String> colors = FXCollections.observableArrayList("White", "Black", "Blue", "Red", "Green", "Yellow", "Violet", "Pink");
        addAutoMark.setItems(marks);
        addAutoColor.setItems(colors);
        ObservableList<String> owners = FXCollections.observableArrayList();
        String query = "select second_name, first_name, pather_name from auto_personnel";
        try {
            stmt = Vars.con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next())
                owners.add(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            addAutoOwner.setItems(owners);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onAddAuto(ActionEvent actionEvent) {
        boolean ok = true;
        String code = addAutoCode.getText();
        if (!Checker.checkNewAutoCode(code))
            Alerts.error(null, "Incorrect format of code");
        else {
            String query = "insert into auto values(cnt_auto.nextVal, ?, ?, ?, ?)";
            try {
                stmt = Vars.con.prepareStatement(query);
                stmt.setString(1, code);
                stmt.setString(2, addAutoColor.getValue());
                stmt.setString(3, addAutoMark.getValue());
                stmt.setString(4, getPersonnelId(addAutoOwner.getValue()));
                try {
                    rs = stmt.executeQuery();
                } catch (SQLException e) {
                    Alerts.error(null, "Car with this code already exists.");
                    ok = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ok) Alerts.info(null, "Adding was successful.");
        }
    }

    public String getPersonnelId(String data) {
        String[] personData = data.split(" ");
        String res = "none";
        String query = "select id from auto_personnel where second_name = ? and first_name = ? and pather_name = ?";
        try {
            PreparedStatement statement = Vars.con.prepareStatement(query);
            for (int i = 1; i <= personData.length; i++)
                statement.setString(i, personData[i-1]);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            res = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
