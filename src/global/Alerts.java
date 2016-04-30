package global;

import javafx.scene.control.Alert;

/**
 * Created by Steiner on 30.04.2016.
 */
public class Alerts {
    public static void info(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void error(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
