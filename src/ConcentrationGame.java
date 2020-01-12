/**
 * Created by Emmanuel on 1/12/20.
 */
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConcentrationGame extends Application {
    private Card selected = null;
    private int clickCount = 2;
    private Scene introScreen, nameScreen, gameScreen;
    private BorderPane gamePane = new BorderPane();
    private Label lblGameStatus;
    Label lblMenuTitle = new Label("Concentration Game");
    private int[] score = new int[2];
    private boolean whoseTurn = true;
    private String player1 = "Player 1";
    private String player2 = "Player 2";
    private int pairs = 26;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane introPane = new BorderPane();
        Button btnPlay = new Button("Play Multiplayer");
        btnPlay.setOnMouseClicked(e -> createNameScreen(primaryStage));
        lblMenuTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 125));

        introPane.setTop(lblMenuTitle);
        introPane.setCenter(btnPlay);
        introPane.setStyle("-fx-border-color: black; -fx-background-color: DARKOLIVEGREEN");


        introScreen = new Scene(introPane, 1100, 600);
        primaryStage.setScene(introScreen);
        primaryStage.setTitle("ConcentrationGame");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void createNameScreen(Stage primaryStage){

        TextField txtNameField1 = new TextField();
        TextField txtNameField2 = new TextField();
        Button nameBtn = new Button("Submit Names");
        Button playBtn = new Button("Play");
        nameBtn.setOnMouseClicked(e -> {
            player1 = txtNameField1.getText();
            if(txtNameField1.getText().isEmpty()){
                player1 = "Player1";
            }
            player2 = txtNameField2.getText();
            if(txtNameField2.getText().isEmpty()){
                player2 = "Player2";
            }
            txtNameField1.setDisable(true);
            txtNameField2.setDisable(true);
            nameBtn.setDisable(true);
        });

        Label nameLbl1 = new Label("Player 1:");
        nameLbl1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        txtNameField1.setPromptText("Enter Player 1 Name");


        Label nameLbl2 = new Label("Player 2:");
        nameLbl2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        txtNameField2.setPromptText("Enter Player 2 Name");


        playBtn.setOnMouseClicked(e -> createSecondScreen(primaryStage));

        HBox hBox1 = new HBox();HBox hBox2 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setStyle("-fx-background-color: black;");
        hBox1.setSpacing(20);
        hBox1.getChildren().addAll(nameLbl1, txtNameField1, nameLbl2, txtNameField2, nameBtn);

        hBox2.getChildren().add(playBtn);
        hBox2.setAlignment(Pos.CENTER);

        BorderPane namePane = new BorderPane();
        namePane.setStyle("-fx-border-color: black; -fx-background-color: DARKOLIVEGREEN");
        namePane.setTop(lblMenuTitle);
        namePane.setCenter(hBox1);
        namePane.setBottom(hBox2);
        nameScreen = new Scene(namePane, 1100, 600);
        primaryStage.setScene(nameScreen);
    }

    private void createSecondScreen(Stage primaryStage){

        gamePane.setTop(createTopHeader(primaryStage));
        gamePane.setCenter(createMiddleBoard());
        gamePane.setBottom(createBottomStatus());

        gameScreen = new Scene(gamePane, 1100, 600);
        primaryStage.setScene(gameScreen);
    }

    private Parent createTopHeader(Stage primaryStage){

        Button button = new Button("Restart");
        button.setOnMouseClicked(e -> restart(primaryStage));
        BorderPane titlePane = new BorderPane();
        titlePane.setCenter(lblMenuTitle);
        titlePane.setRight(button);

        titlePane.setStyle("-fx-border-color: white; -fx-background-color: black;");
        lblMenuTitle.setTextFill(Color.WHITE);
        lblMenuTitle.setFont(Font.font(35));
        titlePane.setPadding(new Insets(10));

        return titlePane;
    }
    private Parent createMiddleBoard() {

        char cardID = '1';
        int cardCounter = 1;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(4);
        gridPane.setVgap(4);
        gridPane.setPrefSize(400, 400);
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.setStyle("-fx-border-color: black; -fx-background-image:url('background.jpg')");


        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            cardList.add(new Card(cardID + "", cardCounter));
            cardCounter++;
            cardList.add(new Card(cardID + "", cardCounter));
            cardCounter++;
            cardList.add(new Card(cardID + "", cardCounter));
            cardCounter++;
            cardList.add(new Card(cardID + "", cardCounter));
            cardCounter++;
            cardID++;

        }

        Collections.shuffle(cardList);
        int i = 0;
        for (int y = 0; y < 13; y++) {
            for(int x = 0; x < 4; x++){
                Card cards = cardList.get(i);
                gridPane.add(cards, y, x);
                gridPane.setAlignment(Pos.CENTER);
                i++;
            }
        }

        return gridPane;
    }

    private Parent createBottomStatus() {

        lblGameStatus = new Label(getPlayerString());
        HBox bottomPane = new HBox(lblGameStatus);
        bottomPane.setStyle("-fx-border-color: white; -fx-background-color: black;");
        lblGameStatus.setTextFill(Color.WHITE);
        lblGameStatus.setFont(Font.font(20));
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(10));


        VBox vbox = new VBox();
        vbox.getChildren().addAll(bottomPane);

        return vbox;
    }

    private void restart(Stage primaryStage){

        pairs = 26;score[0] = 0;score[1] = 0;

        gamePane.setTop(createTopHeader(primaryStage));
        gamePane.setCenter(createMiddleBoard());
        gamePane.setBottom(createBottomStatus());

        primaryStage.setScene(gameScreen);
    }

    private String getPlayerString() {

        return (whoseTurn) ? "It is " + player1 + "'s turn" + " \t|\t " + player1 + " Score: "  + score[0] + " \t|\t " + player2 + " Score: " + score[1] + " \t|\t " + "Pairs Left: " + pairs:
                "It is " + player2 + "'s turn" + " \t|\t " + player1 + " Score: "  + score[0] + " \t|\t " + player2 + " Score: " + score[1] + " \t|\t " + "Pairs Left: " + pairs;

    }

    private class Card extends StackPane {

        private ImageView backOfCardi = new ImageView();
        private ImageView frontOfCardi = new ImageView();


        public Card(String cardID, int cardCounter) {

            frontOfCardi = new ImageView(cardCounter + ".png");
            frontOfCardi.setFitHeight(100);
            frontOfCardi.setFitWidth(75);
            frontOfCardi.setId(cardID);

            backOfCardi = new ImageView("backCard.png");
            backOfCardi.setFitHeight(100);
            backOfCardi.setFitWidth(75);
            backOfCardi.setId(cardID);

            setAlignment(Pos.CENTER);
            getChildren().addAll(frontOfCardi, backOfCardi);

            setOnMouseClicked(e -> handleMouseClick(e));
            flipDown();

        }

        public void handleMouseClick(MouseEvent event) {

            if (isCardFlipped() || clickCount == 0)
                return;

            clickCount--;

            if (selected == null) {
                selected = this;
                flipUp(() -> {});
            }
            else {
                flipUp(() -> {
                    if (!isCardEqual(selected)) {
                        selected.flipDown();
                        this.flipDown();
                        whoseTurn = !whoseTurn;
                        lblGameStatus.setText(getPlayerString());
                    }else{
                        selected.removeCard();
                        this.removeCard();
                        pairs--;
                        if(pairs == 0){
                            lblGameStatus.setText(hasWon());
                        }else {
                            updateScore();
                            lblGameStatus.setText(getPlayerString());
                        }
                    }

                    selected = null;
                    clickCount = 2;
                });
            }
        }

        private String hasWon(){

            if(score[0] > score[1]){
                return player1 + " Has Won!";
            }else
            if(score[0] < score[1]){
                return player2 + " Has Won!";
            }else{
                return "It is a draw!";
            }

        }

        private void updateScore(){

            if(whoseTurn){
                score[0]++;
            }else{
                score[1]++;
            }
        }

        public void flipUp(Runnable action) {

            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), backOfCardi);
            ft.setToValue(0);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }

        public void flipDown() {

            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), backOfCardi);
            ft.setToValue(1);
            ft.play();
        }
        public void removeCard() {

            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), frontOfCardi);
            ft.setToValue(0);
            ft.play();
            backOfCardi.setOpacity(0);
        }

        public boolean isCardEqual(Card other) {

            String card1 = other.backOfCardi.getId();
            String card2 = backOfCardi.getId();

            return card1.equals(card2);

        }

        public boolean isCardFlipped() {

            return backOfCardi.getOpacity() == 0;
        }

    }

    public static void main(String[] args){

        launch(args);
    }
}
