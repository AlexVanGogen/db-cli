package forms;

import database.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import security.Encoder;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Connect connect = new Connect();
        connect.go();
        Parent root = FXMLLoader.load(getClass().getResource("../main/resources/registration-form.fxml"));
        String passwd = "tygydyktygydyk";
        Scene scene = new Scene(root, 600, 400);
        System.out.println(Encoder.md5(passwd));
        primaryStage.setTitle("Hello World");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
