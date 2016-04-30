package forms;

import forms.Main;
import global.Vars;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import security.Encoder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


public class RegController  {

    @FXML
    private TextField unametf;
    @FXML
    private TextField pwdtf;
    @FXML
    private Label logerrlabel;
    @FXML
    private Button loginbutton;

    private Main main;
    PreparedStatement pwdQuery = null;
    StringProperty uname;
    StringProperty passwd;
    String encodedpwd;
    ResultSet rs;

    public boolean checkForValidity(String u, String p) {
        return (u.matches("[a-zA-Z]+[0-9]*") && p != null);
    }

    public boolean prepareHandling() {
        uname = new SimpleStringProperty(unametf.getText());
        passwd = new SimpleStringProperty(pwdtf.getText());
        uname = new SimpleStringProperty("Nagibator99");
        passwd = new SimpleStringProperty("lyambda");
        if (!checkForValidity(uname.get(), passwd.get())) {
            logerrlabel.setText("Username or password field has illegal symbols.");
            return false;
        }
        return true;
    }

    public void onLoginButtonClick(ActionEvent actionEvent) {
        if (prepareHandling()) {
            encodedpwd = Encoder.md5(passwd.get());
            String logInQuery = "select passwd from users where username = ?";
            try {
                pwdQuery = Vars.con.prepareStatement(logInQuery);
                pwdQuery.setString(1, uname.get());
                ResultSet rs = pwdQuery.executeQuery();
                if (!rs.next())
                    logerrlabel.setText("User with this username does not exist.");
                else if (!rs.getString("passwd").equals(encodedpwd))
                    logerrlabel.setText("Cannot find user identified by this password.");
                else
                    loginAdmin();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onRegButtonClick(ActionEvent actionEvent) {
        if (prepareHandling()) {
            encodedpwd = Encoder.md5(passwd.get());
            String logInQuery = "insert into users values(usersCnt.nextVal, ?, ?, 'User')";
            try {
                pwdQuery = Vars.con.prepareStatement(logInQuery);
                pwdQuery.setString(1, uname.get());
                pwdQuery.setString(2, encodedpwd);
                try {
                    pwdQuery.executeQuery();
                } catch (SQLIntegrityConstraintViolationException e) {
                    logerrlabel.setText("User with this username already exists.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loginAdmin() {
        try {
            Stage currentStage = (Stage)loginbutton.getScene().getWindow();
            AnchorPane root = FXMLLoader.load(getClass().getResource("../main/resources/admin-startform.fxml"));
            Scene scene = new Scene(root, 1000, 900);
            currentStage.setResizable(false);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
