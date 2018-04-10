package fxsnaken;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Slider;
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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class FxSnaken extends Application {

    Stage stage;
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
    FloatControl gainControl;

    Timeline timeLine = new Timeline();

    Text pointsShow;
    GraphicsContext gc;
    VBox lostmenu = new VBox();

    public static void main(String[] args) {
        launch(args);
    }

    public void mainMenu(Scene scena) {
        VBox vBox = new VBox();
        scena.setRoot(vBox);

        BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundImage im = new BackgroundImage(new Image(FxSnaken.class.getResourceAsStream("/gold-bg.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        vBox.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));

        vBox.setAlignment(Pos.TOP_LEFT);

        StackPane title = new StackPane();
        title.setAlignment(Pos.CENTER_LEFT);
        Text text = new Text("S N A K E N");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));
        title.getChildren().add(text);
        title.setMargin(text, new Insets(0, 0, 0, 10));
        vBox.getChildren().add(title);
        vBox.setMargin(title, new Insets(200, 0, 0, 50));
        Rectangle rectangle = new Rectangle(285, 60);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(2);
        rectangle.setFill(null);
        title.getChildren().add(rectangle);
        addMenuItem(vBox, "NEW GAME", () -> gameMake(scena), Color.DARKGRAY);
        addMenuItem(vBox, "OPTIONS", () -> options(scena), Color.DARKGRAY);
        addMenuItem(vBox, "QUIT", () -> stage.close(), Color.DARKGRAY);

        Timeline timeln = new Timeline(
                new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                    int pos = 0;

                    public void handle(ActionEvent event) {
                        pos = pos + 1;
                        BackgroundPosition bgpos = new BackgroundPosition(Side.LEFT, pos, false, Side.TOP, 0, false);
                        BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
                        BackgroundImage im = new BackgroundImage(new Image(FxSnaken.class.getResourceAsStream("/gold-bg.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, bgpos, BackgroundSize.DEFAULT);
                        vBox.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));

                    }
                }));
        timeln.setCycleCount(Timeline.INDEFINITE);
        timeln.play();

    }

    public void gameMake(Scene scene) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        scene.setRoot(vBox);
        vBox.setAlignment(Pos.CENTER_LEFT);
        BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundImage im = new BackgroundImage(new Image(FxSnaken.class.getResourceAsStream("/gold-bg.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        vBox.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));

        Text gameText = new Text("MATCH DURATION:");
        gameText.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        gameText.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 30));
        gameText.setFill(Color.WHITE);

        P10 = new Hyperlink("10");
        P20 = new Hyperlink("/20");
        P30 = new Hyperlink("/30");

        vBox.setMargin(gameText, new Insets(0, 0, 0, 20));
        vBox.setMargin(P10, new Insets(20, 0, 0, 20));
        vBox.setMargin(P20, new Insets(20, 0, 0, 20));
        vBox.setMargin(P30, new Insets(20, 0, 0, 20));
        P10.setFont(Font.font(20));
        P20.setFont(Font.font(20));
        P30.setFont(Font.font(20));

        hBox.getChildren().add(gameText);
        hBox.getChildren().add(P10);
        hBox.getChildren().add(P20);
        hBox.getChildren().add(P30);
        vBox.getChildren().add(hBox);
        vBox.setMargin(hBox, new Insets(400, 0, 0, 0));
        addMenuItem(vBox, "START", () -> game(scene), Color.DARKGRAY);

        HBox hBoxQ = new HBox();
        VBox vBoxQ = new VBox();
        vBoxQ.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxQ.setAlignment(Pos.BOTTOM_RIGHT);

        hBoxQ.prefHeightProperty().bind(vBox.widthProperty());

        hBoxQ.getChildren().add(vBoxQ);
        vBox.getChildren().add(hBoxQ);
        addMenuItem(vBoxQ, "BACK", () -> mainMenu(scene), Color.DARKGRAY);

    }

    @Override
    public void start(Stage stage1) throws Exception {
        stage = stage1;
        VBox vBox = new VBox();
        Scene scena = new Scene(vBox);
        stage.setTitle("Snaken");
        stage.setScene(scena);
        stage.setMaximized(true);
        stage.show();
      
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(FxSnaken.class.getResourceAsStream("/x.mp3"));
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            gainControl = (FloatControl) clip
                    .getControl(FloatControl.Type.MASTER_GAIN);
            double gain = 0.5D; // number between 0 and 1 (loudest)
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
          mainMenu(scena);
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
        BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundImage im = new BackgroundImage(new Image(FxSnaken.class.getResourceAsStream("/gold-bg.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        center.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));
        right.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));

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

    private void addMenuItem(VBox vBox, String text, Runnable runable, Color colorel) {
        Line line = new Line();
        line.setEndX(250);
        line.setStroke(Color.DARKGRAY);
        vBox.getChildren().add(line);
        vBox.setMargin(line, new Insets(20, 0, 0, 60));

        StackPane menu1 = new StackPane();
        Rectangle menu1Bg = new Rectangle(250, 30);
        menu1Bg.setOpacity(0);

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
            new Stop(0, Color.GREENYELLOW),
            new Stop(0.1, Color.BLACK),
            new Stop(0.9, Color.BLACK),
            new Stop(1, Color.GREENYELLOW)

        });
        menu1Bg.setFill(gradient);

        Text textMen1 = new Text(text);
        textMen1.setFill(colorel);
        textMen1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        menu1.setAlignment(Pos.CENTER);
        menu1.setMaxWidth(250);

        menu1.getChildren().add(menu1Bg);
        menu1.getChildren().add(textMen1);

        vBox.getChildren().add(menu1);
        vBox.setMargin(menu1, new Insets(5, 0, 0, 60));

        menu1.setOnMouseEntered(event -> {
            menu1Bg.setFill(gradient);
            menu1Bg.setOpacity(0.4);
            textMen1.setFill(Color.WHITE);
        });
        menu1.setOnMouseExited(event -> {
            menu1Bg.setOpacity(0);
            textMen1.setFill(Color.DARKGRAY);
        });
        menu1.setOnMousePressed(event -> {
            runable.run();
        });

        Line line1 = new Line();
        line1.setEndX(250);
        line1.setStroke(Color.DARKGRAY);
        vBox.getChildren().add(line1);
        vBox.setMargin(line1, new Insets(0, 0, 0, 60));
    }

    private void options(Scene scena) {
        VBox vBox = new VBox();
        scena.setRoot(vBox);
        HBox volumebox = new HBox();

        Text optionsMT = new Text("MUSIC:");
        optionsMT.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        optionsMT.setFill(Color.WHITE);
        final Slider slider = new Slider(0, 100, 50);

        slider.setPrefWidth(200);
        vBox.setAlignment(Pos.BOTTOM_LEFT);
        vBox.getChildren().add(volumebox);
        slider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                float dB = (float) (Math.log(slider.getValue() / 100) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        });

        volumebox.getChildren().add(optionsMT);
        volumebox.getChildren().add(slider);
        volumebox.getStylesheets().add(getClass().getResource("/slider.css").toExternalForm());
        vBox.setMargin(optionsMT, new Insets(200, 0, 0, 50));

        BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundImage im = new BackgroundImage(new Image(FxSnaken.class.getResourceAsStream("/gold-bg.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        vBox.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));

        HBox hBoxQ = new HBox();
        VBox vBoxQ = new VBox();
        vBoxQ.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxQ.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxQ.prefHeightProperty().bind(vBox.widthProperty());
        hBoxQ.getChildren().add(vBoxQ);
        vBox.getChildren().add(hBoxQ);
        addMenuItem(vBoxQ, "BACK", () -> mainMenu(scena), Color.DARKGRAY);
    }

}
