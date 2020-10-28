//package sample;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.Initializable;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.ResourceBundle;
//
//import static java.lang.Integer.parseInt;
//
//public class GameSettingsController implements Initializable {
//    Scene scene;
//
//    private GridPane grid;
//    private ColumnConstraints columnConstraints;
//
//    private Label rowsAndCoinsQuantityLbl;
//
//    private Button addRowBtn;
//    private Button playBtn;
//
//    private GridPane coinsGpn;
//
//    private ArrayList<Integer> coinsList;
//    private Integer coinsQuantity;
//
//    public GameSettingsController(Stage stage) {
//        this.grid = new GridPane();
//        this.grid.setGridLinesVisible(true);
//        this.grid.setVgap(20);
//        this.grid.setHgap(10);
//
//        this.coinsList = new ArrayList<>();
//        this.coinsQuantity = 0;
//
//        this.rowsAndCoinsQuantityLbl = createNewLabel("Dobierz ilosc wierszy i monet", 16L);
//        GridPane.setConstraints(this.rowsAndCoinsQuantityLbl, 0, 0);
//
//        this.addRowBtn = createNewButton("Dodaj wiersz", 16L);
//
//        //TODO to refactor
//        addRowBtn.setOnAction(e -> {
//            Button addToRow = createNewButton("+", 16L);
//            addToRow.setId(String.valueOf(coinsQuantity));
//            this.coinsList.add(coinsQuantity, 0);
//            Label coinsQuantityInRowLbl = new Label(coinsList.get(parseInt(addToRow.getId())).toString());
//            class MyButtonHandler implements EventHandler<ActionEvent> {
//                @Override
//                public void handle(ActionEvent evt) {
//                    if (evt.getSource().equals(addToRow)) {
//                        Integer coinsQuantityInRow = coinsList.get(parseInt(addToRow.getId())) + 1;
//                        coinsList.set(parseInt(addToRow.getId()), coinsQuantityInRow);
//                        coinsQuantityInRowLbl.setText(coinsQuantityInRow.toString());
//                    }
//                }
//            }
//            addToRow.setOnAction(new MyButtonHandler());
//            this.coinsGpn.add(addToRow, 1, coinsQuantity);
//            this.coinsGpn.add(coinsQuantityInRowLbl, 2, coinsQuantity);
//            coinsQuantity++;
//        });
//        //TODO end
//
//        GridPane.setConstraints(this.addRowBtn, 3, 0);
//
//        this.playBtn = createNewButton("Graj!", 16L);
//        this.playBtn.setOnAction(e -> {
//            coinsList.removeAll(Collections.singleton(0));
//            coinsList.trimToSize();
//            new GameBoardController(stage, coinsList);
//        });
//        GridPane.setConstraints(this.playBtn, 3, 3);
//
//        this.coinsGpn = new GridPane();
//        this.coinsGpn.setAlignment(Pos.CENTER_LEFT);
//        GridPane.setConstraints(this.coinsGpn, 0, 1);
//
//        this.columnConstraints = new ColumnConstraints();
//        this.grid.getColumnConstraints().add(this.columnConstraints);
//        this.columnConstraints = new ColumnConstraints();
//        this.columnConstraints.setPercentWidth(20);
//        this.grid.getColumnConstraints().add(this.columnConstraints);
//
//        this.grid.getChildren().addAll(rowsAndCoinsQuantityLbl, addRowBtn, coinsGpn, playBtn);
//        this.grid.setAlignment(Pos.CENTER);
//
//        this.scene = new Scene(this.grid);
//
//        stage.setScene(this.scene);
//        stage.show();
//    }
//
//    private Label createNewLabel(String labelText, Long fontSize) {
//        Label label = new Label();
//
//        if (labelText != null) {
//            label.setText(labelText);
//        }
//        if (fontSize != null) {
//            label.setFont(Font.font(fontSize));
//        }
//
//        return label;
//    }
//
//    private Button createNewButton(String buttonText, Long fontSize) {
//        Button button = new Button();
//
//        if (buttonText != null) {
//            button.setText(buttonText);
//        }
//        if (fontSize != null) {
//            button.setFont(Font.font(fontSize));
//        }
//
//        return button;
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }
//
//}
