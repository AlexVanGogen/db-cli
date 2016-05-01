package forms.modal.auto;

import database.types.Auto;
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
 * Created by Steiner on 02.05.2016.
 */
public class ShowAutoController {

    @FXML public TableView<Auto> autoTable;
    @FXML public TableColumn<Auto, String> codeCol;
    @FXML public TableColumn<Auto, String> markCol;
    @FXML public TableColumn<Auto, String> colorCol;
    @FXML public TableColumn<Auto, String> ownerCol;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;

    private void executeShow(String query) {
        ObservableList<Auto> data = FXCollections.observableArrayList();
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            while (rs.next()) {
                data.add(new Auto(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6)));
            }
            codeCol.setCellValueFactory(new PropertyValueFactory<Auto, String>("Code"));
            markCol.setCellValueFactory(new PropertyValueFactory<Auto, String>("Mark"));
            colorCol.setCellValueFactory(new PropertyValueFactory<Auto, String>("Color"));
            ownerCol.setCellValueFactory(new PropertyValueFactory<Auto, String>("Owner"));
            autoTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        String showQuery = "select num, mark, color, second_name, first_name, pather_name from auto" +
                " join auto_personnel on auto_personnel.id = auto.personnel_id";
        executeShow(showQuery);
    }
}
