package sample;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class GameBoardController implements Initializable {
    private VBox vBox = new VBox();
    private HBox hBox = new HBox();

    Scene scene;

    private GridPane grid;

    private Button backBtn;
    private Button okBtn;
    private Button redoBtn;

    private ArrayList<Integer> coinsList;
    private ArrayList<ToggleButton> selectedCoins = new ArrayList<>();
    private ArrayList<ArrayList> movesList = new ArrayList<ArrayList>();

    private int movesCounts = 0;
    private int level = 0; //3 - master, 2 - hard, 1 - medium, else easy

    private int enemy;  //0 - PC, 1 - P2
    private boolean turn;   //false - P1 first, true - P2/PC first

    public GameBoardController(Stage stage, ArrayList<Integer> coinsListOrig, int enemy, boolean  turn, int level) {
        this.enemy = enemy;
        this.turn = turn;
        this.level = level;

        this.grid = new GridPane();
        this.grid.setGridLinesVisible(false);
        this.grid.setVgap(10);

        this.grid.setAlignment(Pos.CENTER);

        this.coinsList = coinsListOrig;
        this.backBtn = createNewButton("Powrót",18L);
        this.backBtn.setOnAction(e -> {
            new StartController(stage);
        });

        this.redoBtn = createNewButton("Cofnij ruch",18L);
        this.redoBtn.setOnAction(e -> {
                if(movesCounts > 0){
                this.turn = !this.turn;
                this.movesCounts--;
                this.coinsList=movesList.get(this.movesCounts);
                this.movesList.remove(this.movesCounts);
                showCoins(this.coinsList,this.grid);
                }
        });

        this.okBtn = createNewButton("OK",18L);
        this.okBtn.setOnAction(e ->{
            System.out.println("moveslistsize: "+movesList.size());
            if (this.enemy == 0){
                if (!this.turn) {
                    playerMove(this.coinsList,this.grid);
                } else {
                    pcMove(this.coinsList,this.grid);
                }
                System.out.println(this.coinsList);
                System.out.println(this.movesList);
            } else {
                playerMove(this.coinsList,this.grid);
                System.out.println(this.coinsList);
                System.out.println(this.movesList);
            }
        });
        this.vBox.setPadding(new Insets(20,0,0,0));

        showCoins(this.coinsList, this.grid);
        System.out.println(this.coinsList);
        this.hBox.getChildren().addAll(this.backBtn,this.okBtn,this.redoBtn);
        this.hBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.getChildren().addAll(this.hBox,this.grid);
        this.vBox.setSpacing(20);   //odlegloc monet od przyciskow
        this.hBox.setSpacing(100);  //odleglosc przycisków
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.scene = new Scene(this.vBox);
        stage.setScene(this.scene);
        stage.setTitle("NIM");
        stage.show();
    }


    public void machinePlays(int[] coinsSet) {
        int xor = 0;
        for (int i = 0; i < coinsSet.length; i++) {
            xor ^= coinsSet[i];
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, 101);
        if (this.level == 3)  //master
        { if (xor == 0) {
                randomMove(coinsSet);
            } else {  // find move leaving xor == 0
                properMove(coinsSet, xor);
            }
        }
        else if (this.level == 2)  //hard
        {
            if (randomNum < 26){
                randomMove(coinsSet);
            }
            else {
                if (xor == 0) {
                    randomMove(coinsSet);
                } else {  // find move leaving xor == 0
                    properMove(coinsSet, xor);
                }
            }
        }
        else if (this.level == 1)  //medium
        {
            if (randomNum < 51){
                randomMove(coinsSet);
            }
            else {
                if (xor == 0) {
                    randomMove(coinsSet);
                } else {  // find move leaving xor == 0
                    properMove(coinsSet, xor);
                }
            }
        }
        else {
            if (randomNum < 76){
                randomMove(coinsSet);
            }
            else {
                if (xor == 0) {
                    randomMove(coinsSet);
                } else {  // find move leaving xor == 0
                    properMove(coinsSet, xor);
                }
            }
        }
    }

    private void properMove(int[] coinsSet,int xor) {
        int[] coinsSetandxor = new int[coinsSet.length];
        for (int i = 0; i < coinsSet.length; i++) {
            coinsSetandxor[i] = coinsSet[i] & xor; //bit AND
        }
        int heap = maxIdx(coinsSetandxor);
        int pctakes = (coinsSet[heap] - (xor ^ coinsSet[heap])); //bit XOR
        coinsSet[heap] -= pctakes;
    }

    private void randomMove(int[] coinsSet) {
        int heap = ThreadLocalRandom.current().nextInt(0, coinsSet.length);
        int pctakes = ThreadLocalRandom.current().nextInt(1, coinsSet[heap] + 1);;
        coinsSet[heap] -= pctakes;
    }

    public void humanPlays(int[] coinsSet, int heap) {
        coinsSet[heap] -= selectedCoins.size();
        selectedCoins.clear();
    }

    static int maxIdx(int[] ar) {
        int idx = 0;
        int max = ar[0];
        for (int i = 1; i < ar.length; i++)
            if (max < ar[i]) {
                max = ar[i];
                idx = i;
            }
        return idx;
    }

    public void pcMove(ArrayList monety, GridPane grid ) {
        this.movesList.add(createNewArrayList(monety));
        this.movesCounts++;
        System.out.println(movesCounts);
        int[] temp = new int[monety.size()];
        for (int x = 0; x < monety.size(); x++) {
            temp[x] = (int) monety.get(((x)));
        }
        int j = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != 0) j++;
        }
        if (!monety.isEmpty()) {
            machinePlays(temp);
            for (int x = 0; x < temp.length; x++) {
                monety.set(x, temp[x]);
            }
            for (int x = 0; x < monety.size(); x++) {
                temp[x] = (int) monety.get(x);
            }
            for (int x = 0; x < temp.length; x++) {
                monety.set(x, temp[x]);
            }
            j = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != 0) j++;
            }
            if (j == 0) {
                this.okBtn.setText("Wygrywa komputer");
            }
        }
        this.turn = !this.turn;
        showCoins(monety, grid);
    }

    public void playerMove(ArrayList monety, GridPane grid ) {
        if (selectedCoins.isEmpty()){
            System.out.println("No coins selected");
        }
        else {
            this.movesList.add(createNewArrayList(monety));
            this.movesCounts++;
            System.out.println(this.movesCounts);
            int[] temp = new int[monety.size()];
            for (int x = 0; x < monety.size(); x++) {
                temp[x] = (int) monety.get(((x)));
            }
            int j = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != 0) j++;
            }
            humanPlays(temp,Integer.valueOf(check_line(selectedCoins.get(0))));
            for (int x = 0; x < temp.length; x++) {
                monety.set(x, temp[x]);
            }
            for (int x = 0; x < monety.size(); x++) {
                temp[x] = (int) monety.get(x);
            }
            for (int x = 0; x < temp.length; x++) {
                monety.set(x, temp[x]);
            }
            j = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != 0) j++;
            }
            if (j == 0) {
                if(enemy == 0){
                    if(!turn){
                        this.okBtn.setText("Wygrywa Gracz");
                    }
                }
                else {
                    if(!turn){
                        this.okBtn.setText("Wygrywa Gracz 1");
                    }
                    else {
                        this.okBtn.setText("Wygrywa Gracz 2");
                    }
                }
            }
            this.turn = !this.turn;
            showCoins(monety, grid);
        }
    }

    public void showCoins(ArrayList<Integer> coinsList, GridPane grid) {
        coinsList.removeAll(Collections.singleton(Integer.valueOf(0)));
        ArrayList<ToggleButton> monety = new ArrayList<>();
        grid.getChildren().clear();
        for (int x = 0; x < coinsList.size(); x++) {
            for (int y = 0; y < coinsList.get(x); y++) {
                ToggleButton coinsBtn = createNewToggleButton(coinsList.get(x).toString());
                if (enemy == 0) {
                    if (this.turn) {
                        coinsBtn.setDisable(true);
                        this.okBtn.setText("Ruch Komputera");
                        if (coinsList.isEmpty()){
                        }
                    } else {
                        coinsBtn.setDisable(false);
                        this.okBtn.setText("Ruch Gracza");
                    }
                } else {
                    if (this.turn) {
                        this.okBtn.setText("Ruch Gracza 2");
                    } else {
                        this.okBtn.setText("Ruch Gracza 1");
                    }
                }
                coinsBtn.setId((Integer.toString(x)) + "/" + (Integer.toString(y)));
                coinsBtn.setVisible(false);
                coinsBtn.setVisible(true);
                grid.add(coinsBtn, y, x);
                monety.add(coinsBtn);
                coinsBtn.setOnAction(e -> {
                    if(coinsBtn.isSelected()){
                        selectedCoins.add(coinsBtn);
//                        coinsBtn.setStyle("-fx-background-color:white");
                    }
                    else {
//                        coinsBtn.setStyle("-fx-background-color:null");
                        selectedCoins.remove(coinsBtn);
                        selectedCoins.trimToSize();
                    }
                    odznacz_inne_linie(monety,(coinsBtn));
                });
            }
        }
    }

    private void odznacz_inne_linie(ArrayList<ToggleButton> monety, ToggleButton LilewwG){
        for(ToggleButton moneta : monety){
            String checking = check_line(moneta);
            if(!checking.equals(check_line(LilewwG))){
                if(moneta.isSelected()){
                    System.out.println(checking + "--" + check_line(LilewwG));
                    System.out.println("Odznaczanie monet w wierszu : " + check_line(moneta));
                    moneta.setSelected(false);
                    selectedCoins.clear();
                    selectedCoins.add(LilewwG);
                }
            }
        }
        selectedCoins.trimToSize();
    }

    private String check_line(ToggleButton ToggleButton){
        int posA = ToggleButton.getId().indexOf("/");
        if (posA == -1) {
            return "";
        }
        return ToggleButton.getId().substring(0, posA);
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private ToggleButton createNewToggleButton(String ToggleButtonID  ) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.getStylesheets().add(this.getClass().getResource("imagetogglebutton.css").toExternalForm());
        toggleButton.setMinSize(40, 40); toggleButton.setMaxSize(50, 50);
        if (ToggleButtonID != null){
            toggleButton.setText(ToggleButtonID);
        }
        toggleButton.setId(String.valueOf(ToggleButtonID));
        return toggleButton;
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

    private Label createNewLabel(String labelText ){
        Label label = new Label();
        if(labelText != null) {
            label.setText(labelText);
        }
        return label;
    }

    private ArrayList<Integer> createNewArrayList(ArrayList<Integer> move){
        ArrayList<Integer> lista = new ArrayList<>();
        if (move != null){
            lista.addAll(move);
        }
        return lista;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}


