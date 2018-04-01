package fxsnaken;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FxSnaken extends Application {

    Button buttonM1;
    Button buttonM2;
    Button buttonM3;
    Button buttonMG1;
    Hyperlink P10;
    Hyperlink P20;
    Hyperlink P30;
    int s2x;
    int s2y;

    int s2xd;
    int s2yd;

    int s1x;
    int s1y;

    int s1xd;
    int s1yd;
    int[][] matrix;
    int py = 0;
    int pr = 0;

    Timeline timeLine = new Timeline();

    Text pointsShow;
    GraphicsContext gc;
    VBox lostmenu = new VBox();

    public static void main(String[] args) {
        launch(args);
    }

    public void mainMenu(Stage stage) {
        VBox vBox = new VBox();
        Scene scena = new Scene(vBox);
        BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundImage im = new BackgroundImage(new Image(FxSnaken.class.getResourceAsStream("/gold-bg.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        vBox.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));
        stage.setTitle("Snaken");
        
        vBox.setAlignment(Pos.TOP_LEFT);
        
        StackPane title =new StackPane();
        title.setAlignment(Pos.CENTER_LEFT);
        Text text =new Text("S N A K E N");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman",FontWeight.SEMI_BOLD,50));
        title.getChildren().add(text);
        title.setMargin(text, new Insets(0, 0, 0, 10));
        vBox.getChildren().add(title);
     vBox.setMargin(title, new Insets(200, 0, 0, 50));
        Rectangle rectangle =new Rectangle(285, 60);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(2);
        rectangle.setFill(null);
       title.getChildren().add(rectangle);
       
        
//        buttonM1 = new Button("NEW GAME");
//        buttonM1.setMinWidth(300);
//        vBox.getChildren().add(buttonM1);
//        vBox.setMargin(buttonM1, new Insets(400, 0, 0, 0));
//        buttonM2 = new Button("OPTIONS");
//        buttonM2.setMinWidth(300);
//        vBox.getChildren().add(buttonM2);
//        stage.setScene(scena);
//        vBox.setMargin(buttonM2, new Insets(10, 0, 0, 0));
//        buttonM3 = new Button("QUIT");
//        buttonM3.setMinWidth(300);
//        vBox.getChildren().add(buttonM3);
//        stage.setScene(scena);
//        vBox.setMargin(buttonM3, new Insets(10, 0, 0, 0));

//        buttonM3.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                stage.close();
//            }
//        });
//        buttonM1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                gameMake(scena);
//            }
//        });


 stage.setScene(scena);
        stage.setMaximized(true);
        stage.show();

    }

    public void gameMake(Scene scene) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        scene.setRoot(vBox);
        vBox.setAlignment(Pos.CENTER_LEFT);

        //text
        Text gameText = new Text("MATCH DURATION:");
        gameText.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        gameText.setFill(Color.BLACK);

        //btn
        buttonMG1 = new Button("START");

        //link
        P10 = new Hyperlink("10");
        P20 = new Hyperlink("/20");
        P30 = new Hyperlink("/30");
        vBox.setMargin(gameText, new Insets(0, 0, 0, 20));
        vBox.setMargin(buttonMG1, new Insets(20, 0, 0, 20));
        vBox.setMargin(P10, new Insets(20, 0, 0, 20));
        vBox.setMargin(P20, new Insets(20, 0, 0, 20));
        vBox.setMargin(P30, new Insets(20, 0, 0, 20));
        hBox.getChildren().add(gameText);
        hBox.getChildren().add(P10);
        hBox.getChildren().add(P20);
        hBox.getChildren().add(P30);
        vBox.getChildren().add(hBox);

        vBox.getChildren().add(buttonMG1);

        buttonMG1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game(scene);
            }
        });

    }

    @Override
    public void start(Stage stage) throws Exception {
        mainMenu(stage);
    }

    public void game(Scene scene) {

        VBox left = new VBox();
        HBox bottom = new HBox();
        StackPane center = new StackPane();
        StackPane right = new StackPane();
        StackPane up = new StackPane();
        BorderPane borderPane = new BorderPane();
        scene.setRoot(borderPane);

        bottom.setPrefHeight(5);
        up.setPrefHeight(17);
        left.setPrefWidth(300);

        center.setPrefWidth(900);
        center.setPrefHeight(900);
        borderPane.setLeft(left);
        borderPane.setRight(right);
        borderPane.setCenter(center);
        borderPane.setBottom(bottom);
        borderPane.setTop(up);

        left.setStyle("-fx-background-color: #202020;");
        center.setStyle("-fx-background-color: #000000;");
        right.setStyle("-fx-background-color: #000000;");
        bottom.setStyle("-fx-background-color: #000000;");
        up.setStyle("-fx-background-color: #000000;");
        Text copyrightText = new Text("©Andrei Vasilache");
        copyrightText.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        copyrightText.setFill(Color.WHITE);

        bottom.getChildren().add(copyrightText);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.setMargin(copyrightText, new Insets(0, 20, 0, 0));

        pointsShow = new Text();
        pointsShow.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        pointsShow.setFill(Color.WHITE);
        showScore();
        left.getChildren().add(pointsShow);
        left.setAlignment(Pos.TOP_CENTER);
        left.setMargin(pointsShow, new Insets(20, 0, 0, 0));

        Image image = new Image(getClass().getResourceAsStream("/23408.png"));
        Button buttonS = new Button("", new ImageView(image));
        right.getChildren().add(buttonS);
        right.setAlignment(Pos.TOP_RIGHT);
        right.setMargin(buttonS, new Insets(5, 5, 0, 0));

        Canvas canvas = new Canvas(1024, 768);
        center.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        center.getChildren().add(lostmenu);
        lostmenu.setAlignment(Pos.CENTER);
        lostmenu.setVisible(false);

        Text lostText = new Text("you lost");
        lostText.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        lostText.setFill(Color.YELLOW);
        lostmenu.getChildren().add(lostText);

        Button buttonSave = new Button("next round");
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearScreen();
                resetVariables();
                center.getChildren().remove(buttonSave);
                timeLine.play();
                lostmenu.setVisible(false);
            }
        });
        lostmenu.getChildren().add(buttonSave);

        clearScreen();
        resetVariables();

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.013), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gc.setFill(Color.RED);
                gc.setStroke(Color.RED);

                if (matrix[s1x][s1y] != 0) {
                    lostText.setFill(Color.RED);
                    lostText.setText("red lost");
                    py++;
                    showScore();
                    timeLine.pause();
                    lostmenu.setVisible(true);
                    buttonSave.requestFocus();
                }
                matrix[s1x][s1y] = 1;
                gc.fillRect(s1x * 2, s1y * 2, 2, 2);
                s1x = s1x + s1xd;
                s1y = s1y + s1yd;

                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.YELLOW);
                if (matrix[s2x][s2y] != 0) {
                    lostText.setFill(Color.YELLOW);
                    lostText.setText("yellow lost");
                    pr++;
                    showScore();
                    timeLine.pause();
                    lostmenu.setVisible(true);
                    buttonSave.requestFocus();
                }

                matrix[s2x][s2y] = 2;
                gc.fillRect(s2x * 2, s2y * 2, 2, 2);
                s2x = s2x + s2xd;
                s2y = s2y + s2yd;

                if (s1x > 1024 / 2) {
                    s1x = 0;
                }
                if (s1x < 0) {
                    s1x = 1024 / 2;
                }
                if (s1y > 768 / 2) {
                    s1y = 0;
                }
                if (s1y < 0) {
                    s1y = 768 / 2;
                }
                if (s2x > 1024 / 2) {
                    s2x = 0;
                }
                if (s2x < 0) {
                    s2x = 1024 / 2;
                }
                if (s2y > 768 / 2) {
                    s2y = 0;
                }
                if (s2y < 0) {
                    s2y = 768 / 2;
                }

            }
        });
        timeLine.getKeyFrames().add(keyFrame);
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode() == KeyCode.DOWN) {
                    if (s1yd != -1) {
                        s1yd = 1;
                        s1xd = 0;
                    }
                }
                if (key.getCode() == KeyCode.RIGHT) {
                    if (s1xd != -1) {
                        s1yd = 0;
                        s1xd = 1;
                    }
                }
                if (key.getCode() == KeyCode.LEFT) {
                    if (s1xd != 1) {
                        s1yd = 0;
                        s1xd = -1;
                    }
                }

                if (key.getCode() == KeyCode.UP) {
                    if (s1yd != 1) {
                        s1yd = -1;
                        s1xd = 0;
                    }
                }

                if (key.getCode() == KeyCode.S) {
                    if (s2yd != -1) {
                        s2yd = 1;
                        s2xd = 0;
                    }
                }

                if (key.getCode() == KeyCode.D) {
                    if (s2xd != -1) {
                        s2yd = 0;
                        s2xd = 1;
                    }
                }

                if (key.getCode() == KeyCode.A) {
                    if (s2xd != 1) {
                        s2yd = 0;
                        s2xd = -1;
                    }
                }

                if (key.getCode() == KeyCode.W) {
                    if (s2yd != 1) {
                        s2yd = -1;
                        s2xd = 0;
                    }
                }
            }
        }
        );

    }

    private void clearScreen() {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.fillRect(-10000, -10000, 20000, 20000);
    }

    private void resetVariables() {
        s2x = 2;
        s2y = 2;

        s2xd = 1;
        s2yd = 0;

        s1x = 2;
        s1y = 763 / 2;

        s1xd = 1;
        s1yd = 0;
        matrix = new int[513][385];
    }

    private void showScore() {
        pointsShow.setText("red " + pr + " -score- " + py + " yellow");

    }

}
