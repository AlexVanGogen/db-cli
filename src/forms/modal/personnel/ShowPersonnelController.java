package forms.modal.personnel;

import database.types.Personnel;
import global.Vars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Steiner on 01.05.2016.
 */
public class ShowPersonnelController {

    @FXML public TableColumn<Personnel, String> secNameCol;
    @FXML public TableColumn<Personnel, String> firNameCol;
    @FXML public TableColumn<Personnel, String> patNameCol;
    public TableView<Personnel> personnelTable;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;

    private void executeShow(String query) {
        ObservableList<Personnel> data = FXCollections.observableArrayList();
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            while (rs.next()) {
                data.add(new Personnel(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            secNameCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("SecondName"));
            firNameCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("FirstName"));
            patNameCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("PatherName"));
            personnelTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void initialize() {
        String showQuery = "select second_name, first_name, pather_name from auto_personnel";
        executeShow(showQuery);
    }
}
