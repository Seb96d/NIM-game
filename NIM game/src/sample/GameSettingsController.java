package sample;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.parseInt;

public class GameSettingsController implements Initializable {
    Scene scene;

    private VBox vBox = new VBox();
    private HBox hBox = new HBox();

    private GridPane grid;
    private GridPane coinsGpn;
    private GridPane SpinnersGpn;

    private Button addRowBtn;
    private Button playBtn;
    private Button randomCoins;
    private Button backBtn;
    private ComboBox<String> diffCmbx;

    private ArrayList<Integer> coinsList;
    private Integer rowsQuantity;
    private Integer hasCoins = 0;

    public GameSettingsController(Stage stage, int enemy, boolean turn) {
        this.diffCmbx = new ComboBox();
        this.diffCmbx.getItems().addAll("Łatwy","Średni","Trudny","Mistrz");
        this.diffCmbx.getSelectionModel().select("Poziom trudności");
        this.diffCmbx.setPrefHeight(80);
        this.grid = new GridPane();
        this.grid.setGridLinesVisible(false);
        this.grid.setAlignment(Pos.CENTER_LEFT);
//        this.grid.setVgap(30);
        this.grid.setHgap(50);      //odleglosc monet od spinnerów
        this.grid.setMinHeight(stage.getHeight());
        this.grid.setMinWidth(stage.getWidth());
        this.coinsList = new ArrayList<>();
        this.rowsQuantity = 0;
        this.addRowBtn = createNewButton("Dodaj wiersz", 18L);
        addRowBtn.setOnAction(e -> {
            if (rowsQuantity < 10){
                Spinner spinner = createNewSpinner();
            }
        });
        this.playBtn = createNewButton("Graj!", 18L);
        this.playBtn.setOnAction(e -> {
            this.hasCoins = 0;
            if (this.coinsList.isEmpty()) {
                System.out.println("Brak monet");
                this.hasCoins = 0;
            }
            if (!this.coinsList.isEmpty())
                for (Integer coin : this.coinsList) {
                    if (coin != 0)
                        this.hasCoins++;
                }
            if (this.hasCoins > 1) {
                this.coinsList.removeAll(Collections.singleton(0));
                this.coinsList.trimToSize();
                new GameBoardController(stage, this.coinsList, enemy, turn, this.diffCmbx.getSelectionModel().getSelectedIndex());
            }
        });
        this.randomCoins = createNewButton("Losuj",18L);
        this.randomCoins.setOnAction(e ->{
            randomDraw();
            int xor = checkXOR();
            if (this.diffCmbx.getValue().equals("master")) {
                if (enemy == 0) { // PvsPC
                    if (turn) { // PC first
                        while (xor == 0) {
                            randomDraw();
                            xor = checkXOR();
                        }
                    } else {  //P first
                        while (xor != 0) {
                            randomDraw();
                            xor = checkXOR();
                        }
                    }
                }
            }
            showCoins(coinsList,coinsGpn);
        });
        this.backBtn = createNewButton("Powrót",18L);
        this.backBtn.setOnAction(e -> {
            new StartController(stage);
        });
        this.hBox.getChildren().addAll(this.addRowBtn, this.randomCoins,this.diffCmbx,this.playBtn,this.backBtn);
        if (enemy != 0){
            this.hBox.getChildren().remove(2);
        }
        this.vBox.setSpacing(20);   //odleglosc monet od przyciskow
        this.hBox.setSpacing(100);  //odleglosc przycisków
        this.hBox.setAlignment(Pos.TOP_CENTER);
        this.coinsGpn = new GridPane();
        this.SpinnersGpn = new GridPane();
        this.SpinnersGpn.setAlignment(Pos.TOP_CENTER);
        this.coinsGpn.setAlignment(Pos.CENTER_LEFT);
        GridPane.setConstraints(this.SpinnersGpn, 0, 0);
        GridPane.setConstraints(this.coinsGpn, 1, 0);
        this.grid.getChildren().addAll(this.coinsGpn, this.SpinnersGpn);
        this.grid.setAlignment(Pos.TOP_LEFT);
        GridPane.setMargin(this.SpinnersGpn, new Insets(0,0,0,80));    //przesuniecie spinnerów w prawo
        this.vBox.getChildren().addAll(this.hBox,this.grid);
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setPadding(new Insets(20,0,0,0));     //przesuniecie przycisków w dół
        this.scene = new Scene(this.vBox);
        this.coinsGpn.setAlignment(Pos.TOP_LEFT);
        this.SpinnersGpn.setVgap(10);       //odstep miedzy wierszami spinnerow
        this.coinsGpn.setVgap(10);          //odstep miedzy wierszami monet
        this.diffCmbx.setStyle("-fx-font: 17px \"Tahoma\";");
        stage.setScene(this.scene);
        stage.setTitle("NIM");
        stage.show();
    }

    private int checkXOR(){
        int xor = 0;
        int[] temp = new int[coinsList.size()];
        for (int x = 0; x < coinsList.size(); x++) {
            temp[x] = (int) coinsList.get(((x)));
        }
        for (int i = 0; i < temp.length; i++){
            xor ^= temp[i];
        }
        System.out.println(xor);
        return xor;
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

    private ToggleButton createNewToggleButton( ) {
        ToggleButton togglebutton = new ToggleButton();
        togglebutton.getStylesheets().add(this.getClass().getResource("imagetogglebutton1.css").toExternalForm());
        return togglebutton;
    }

    public void showCoins(ArrayList<Integer> coinsList, GridPane grid) {
        ArrayList<ToggleButton> monety = new ArrayList<>();
        grid.getChildren().clear();
        for (int x = 0; x < coinsList.size(); x++) {
            for (int y = 0; y < coinsList.get(x); y++) {
                ToggleButton coinBtn = createNewToggleButton();
                grid.add(coinBtn, y+1, x);
                monety.add(coinBtn);
                if (coinsList.get(x)==0) {
                    ToggleButton pusty = createNewToggleButton();
                    grid.add(pusty,y,0);
                }
            }
        }
    }

    private Spinner createNewSpinner(){
        Spinner<Integer> spinner = new Spinner(0,15,0,1);   //min,max,current,tick
        spinner.setPrefSize(70,40);
        spinner.setEditable(false);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        spinner.setId(String.valueOf(rowsQuantity));
        this.coinsList.add(rowsQuantity, 0);
        this.SpinnersGpn.add(spinner, 0, rowsQuantity);
        rowsQuantity++;
        spinner.setOnScroll(event -> {
            if (event.getDeltaY() < 0) {
                spinner.decrement();
            } else if (event.getDeltaY() > 0) {
                spinner.increment();
            }
            coinsList.set(parseInt(spinner.getId()), spinner.getValue());
            showCoins(coinsList,coinsGpn);
        });
        spinner.setOnMouseClicked(scrollEvent -> {
            coinsList.set(parseInt(spinner.getId()), spinner.getValue());
            showCoins(coinsList,coinsGpn);
        });
        return spinner;
    }

    private void randomDraw() {
        coinsGpn.getChildren().clear();
        SpinnersGpn.getChildren().clear();
        rowsQuantity = 0;
        coinsList.clear();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(2, 11); i++) {
            Spinner spinner = createNewSpinner();
            coinsList.set(i, ThreadLocalRandom.current().nextInt(1, 15));
            spinner.increment(coinsList.get(i));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
