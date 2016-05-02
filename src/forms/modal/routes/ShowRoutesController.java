package forms.modal.routes;

import database.types.Routes;
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
public class ShowRoutesController {
    @FXML public TableView<Routes> routesTable;
    @FXML public TableColumn<Routes, String> nameCol;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;

    private void executeShow(String query) {
        ObservableList<Routes> data = FXCollections.observableArrayList();
        try {
            queryStmt = Vars.con.prepareStatement(query);
            rs = queryStmt.executeQuery();
            while (rs.next()) {
                data.add(new Routes(rs.getString(1)));
            }
            nameCol.setCellValueFactory(new PropertyValueFactory<Routes, String>("Name"));
            routesTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        String showQuery = "select name from routes";
        executeShow(showQuery);
    }
}
