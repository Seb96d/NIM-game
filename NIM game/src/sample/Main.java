package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setHeight(640.0);
        stage.setWidth(1000);
        stage.setTitle("NIM");
        new StartController(stage);
    }
}
