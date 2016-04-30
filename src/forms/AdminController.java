package forms;

import database.types.Journal;
import global.Vars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Steiner on 30.04.2016.
 */
public class AdminController {
    @FXML private TableView<Journal> journaltable;
    @FXML private Menu menuJournal;
    @FXML private TableColumn<Journal, String> journalCarCol;
    @FXML private TableColumn<Journal, String> journalRouteCol;
    @FXML private TableColumn<Journal, String> journalDeriveCol;
    @FXML private TableColumn<Journal, String> journalArriveCol;
    private Scene scene;

    private PreparedStatement queryStmt = null;
    private ResultSet rs;

    public void onShowAllTrips(Event event) {
        String showQuery = "select auto.num, routes.name, time_out, time_in from journal " +
                "join auto on journal.auto_id = auto.id " +
                "join routes on journal.route_id = routes.id";
        executeShow(showQuery);
    }


    public void onShowUnfinishedTrips(ActionEvent actionEvent) {
        String showQuery = "select auto.num, routes.name, time_out, time_in from journal " +
                "join auto on journal.auto_id = auto.id " +
                "join routes on journal.route_id = routes.id " +
                "where time_in is null";
        executeShow(showQuery);
    }

    private void executeShow(String showQuery) {
        ObservableList<Journal> data = FXCollections.observableArrayList();
        try {
            queryStmt = Vars.con.prepareStatement(showQuery);
            rs = queryStmt.executeQuery();
            while (rs.next()) {
                data.add(new Journal(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            journalCarCol.setCellValueFactory(new PropertyValueFactory<Journal, String>("CarNum"));
            journalRouteCol.setCellValueFactory(new PropertyValueFactory<Journal, String>("RouteName"));
            journalDeriveCol.setCellValueFactory(new PropertyValueFactory<Journal, String>("Derive"));
            journalArriveCol.setCellValueFactory(new PropertyValueFactory<Journal, String>("Arrive"));
            journaltable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onSendToTrip(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../main/resources/modal/send-to-trip.fxml"));
            stage.setTitle("Send car to trip");
            stage.setMaxHeight(215);
            stage.setMaxWidth(600);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(journaltable.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onVerifyArriving(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../main/resources/modal/verify-arriving.fxml"));
            stage.setTitle("Verify arriving");
            stage.setMaxHeight(204);
            stage.setMaxWidth(495);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(journaltable.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBestTimeOnRoute(ActionEvent actionEvent) {

        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../main/resources/modal/best-time-on-route.fxml"));
            stage.setTitle("Get the best time on selected route");
            stage.setMaxHeight(159);
            stage.setMaxWidth(488);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(journaltable.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
