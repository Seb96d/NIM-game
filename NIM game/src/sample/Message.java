package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Message
{

    public static void infoBox(String player_wins, String game_over, String infoMessage, String titleBar)
    {
        /* By specifying a null headerMessage String, we cause the dialog to
           not have a header */
        infoBox(infoMessage, titleBar, null);
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setGraphic(null);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.setOnCloseRequest(e -> {

        });
        alert.showAndWait();
    }

}
