package sample;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    private GridPane grid;
    private ColumnConstraints columnConstraints;

    Scene scene;

    private Label opponentTypeLbl;
    private Label gameStartingPlayerLbl;

    private ToggleGroup opponentTypeTgl;
    private ToggleGroup gameStartingPlayerTgl;

    private RadioButton firstPlayerBeginRbt;
    private RadioButton secondPlayerBeginRbt;

    private RadioButton gameWithAiRbt;
    private RadioButton gameWithSecondPlayerRbt;
    private RadioButton randomPlayerBeginRbt;


    private Button playBtn;

    private int enemy;  //0 - PC, 1 - P2,///////////////////// 2 - PCvPC
    private boolean turn;   //false - P1 first, true - P2/PC first


    public StartController(Stage stage) {
        this.grid = new GridPane();
        this.grid.setVgap(20);
        this.grid.setHgap(10);
        this.opponentTypeLbl = createNewLabel("Graj przeciwko ", 18L);
        GridPane.setConstraints(this.opponentTypeLbl, 0, 0);
        this.gameStartingPlayerLbl = createNewLabel("Pierwszeństwo ruchu", 18L);
        GridPane.setConstraints(this.gameStartingPlayerLbl, 0, 3);
        this.opponentTypeTgl = new ToggleGroup();
        this.gameStartingPlayerTgl = new ToggleGroup();
        this.firstPlayerBeginRbt = createNewRadioButton("Pierwszy Gracz", 18L, gameStartingPlayerTgl, true);
        GridPane.setConstraints(this.firstPlayerBeginRbt, 2, 3);
        this.secondPlayerBeginRbt = createNewRadioButton("Komputer", 18L, gameStartingPlayerTgl, false);
        GridPane.setConstraints(this.secondPlayerBeginRbt, 2, 4);
        this.randomPlayerBeginRbt = createNewRadioButton("Losowo", 18L, gameStartingPlayerTgl, false);
        GridPane.setConstraints(this.randomPlayerBeginRbt, 2, 5);
        this.gameWithAiRbt = createNewRadioButton("Komputer", 18L, opponentTypeTgl, true);
        this.gameWithAiRbt.setOnAction(e -> {
            this.secondPlayerBeginRbt.setText("Komputer");
        });
        GridPane.setConstraints(this.gameWithAiRbt, 2, 0);
        this.gameWithSecondPlayerRbt = createNewRadioButton("Drugi Gracz", 18L, opponentTypeTgl, false);
        this.gameWithSecondPlayerRbt.setOnAction(e -> {
            this.secondPlayerBeginRbt.setText("Drugi Gracz");
        });
        GridPane.setConstraints(this.gameWithSecondPlayerRbt, 2, 1);
        this.playBtn = createNewButton("Rozłóż monety", 18L);
        this.playBtn.setOnAction(e -> {
            if (this.randomPlayerBeginRbt.isSelected()) {
                Random rd = new Random();
                turn = rd.nextBoolean();
            } else if(this.firstPlayerBeginRbt.isSelected()) {
                turn = false;
            } else if (this.secondPlayerBeginRbt.isSelected()) {
                turn = true;
            }
            if (this.gameWithAiRbt.isSelected()) {
                enemy = 0;
            } else if (this.gameWithSecondPlayerRbt.isSelected()) {
                enemy = 1;
            }
            new GameSettingsController(stage,enemy, turn);
        });
        GridPane.setConstraints(this.playBtn, 2, 7);
        this.columnConstraints = new ColumnConstraints();
        this.grid.getColumnConstraints().add(this.columnConstraints);
        this.columnConstraints = new ColumnConstraints();
        this.columnConstraints.setPercentWidth(20);
        this.grid.getColumnConstraints().add(this.columnConstraints);
        this.grid.getChildren().addAll(
                this.opponentTypeLbl,
                this.gameStartingPlayerLbl,
                this.firstPlayerBeginRbt,
                this.secondPlayerBeginRbt,
                this.gameWithAiRbt,
                this.gameWithSecondPlayerRbt,
                this.randomPlayerBeginRbt,
                this.playBtn);

        this.grid.setAlignment(Pos.CENTER);
        this.scene = new Scene(this.grid);
        stage.setScene(this.scene);
        stage.setTitle("NIM");
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

    private RadioButton createNewRadioButton(String radioButtonText, Long fontSize, ToggleGroup toggleGroup, Boolean isSelected) {
        RadioButton radioButton = new RadioButton();
        if (radioButtonText != null) {
            radioButton.setText(radioButtonText);
        }
        if (fontSize != null) {
            radioButton.setFont(Font.font(fontSize));
        }
        if (toggleGroup != null) {
            radioButton.setToggleGroup(toggleGroup);
        }
        if (isSelected != null) {
            radioButton.setSelected(isSelected);
        }
        return radioButton;
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
