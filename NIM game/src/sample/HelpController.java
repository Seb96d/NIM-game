package sample;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    Scene scene;

    private GridPane grid;
    private ColumnConstraints columnConstraints;

    private Label rulesLbl;

    private Button backBtn;

    public HelpController(Stage stage) {

        this.grid = new GridPane();
        this.grid.setGridLinesVisible(true);
        this.grid.setVgap(20);
        this.grid.setHgap(10);

        this.rulesLbl = createNewLabel("NIM to gra \n bla bla bla \n Zasady gry \n bla bla",16L);
        GridPane.setConstraints(this.rulesLbl, 0, 0);

        this.backBtn = createNewButton("Powrot",16L);
        this.backBtn.setOnAction(e -> {
            new StartController(stage);
        });
        GridPane.setConstraints(this.backBtn, 0, 3);

        this.columnConstraints = new ColumnConstraints();
        this.grid.getColumnConstraints().add(this.columnConstraints);
        this.columnConstraints = new ColumnConstraints();
        this.columnConstraints.setPercentWidth(20);
        this.grid.getColumnConstraints().add(this.columnConstraints);

        this.grid.getChildren().addAll(rulesLbl, backBtn);
        this.grid.setAlignment(Pos.CENTER);


        this.scene = new Scene(this.grid);

        stage.setScene(this.scene);
        stage.show();
    }

    private Label createNewLabel(String labelText, Long fontSize) {
        Label label = new Label();
        if (labelText != null) {
            label.setText(labelText);
        }
        if (fontSize != null) {
            label.setFont(Font.font(fontSize));
        }
        return label;
    }

    private Button createNewButton(String buttonText, Long fontSize) {
        Button button = new Button();
        if (buttonText != null) {
            button.setText(buttonText);
        }
        if (fontSize != null) {
            button.setFont(Font.font(fontSize));
        }
        return button;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
