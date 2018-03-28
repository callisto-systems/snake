package fxsnaken;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FxSnaken__ extends Application {

    int s2x;
    int s2y;

    int s2xd;
    int s2yd;

    int s1x;
    int s1y;

    int s1xd;
    int s1yd;
    int[][] matrix;
    
    GraphicsContext gc;

    public static void main_(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("snake");
        stage.setMaximized(true);
        StackPane center = new StackPane();
        center.setStyle("-fx-background-color: #111111");

        BorderPane mainPane = new BorderPane();
        StackPane left = new StackPane();
        left.setStyle("-fx-background-color: #303030");
        left.setPrefWidth(200);
        mainPane.setLeft(left);
        mainPane.setCenter(center);

        Scene scena = new Scene(mainPane, 1284, 818, Color.rgb(0, 191, 255));
        Canvas canvas = new Canvas(1024, 768);

        center.getChildren().add(canvas);
        stage.setScene(scena);

        stage.show();

        gc = canvas.getGraphicsContext2D();

        gc.setFont(new Font("Verdana", 20));

        clearScreen();
        resetVariables();

        Timeline timeLine = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gc.setFill(Color.RED);
                gc.setStroke(Color.RED);

                if (matrix[s1x][s1y] != 0) {
                    gc.fillText("RED LOST", 450, 200);
                    timeLine.pause();
                    Button buttonSave = new Button("next round");
                    center.getChildren().add(buttonSave);
                    buttonSave.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            clearScreen();
                            resetVariables();
                            center.getChildren().remove(buttonSave);
                            timeLine.play();
                            
                        }
                    });
                }
                matrix[s1x][s1y] = 1;
                gc.fillRect(s1x * 2, s1y * 2, 2, 2);
                s1x = s1x + s1xd;
                s1y = s1y + s1yd;

                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.YELLOW);
                if (matrix[s2x][s2y] != 0) {
                    gc.fillText("YELLOW LOST", 450, 200);
                    timeLine.pause();
                    Button buttonSave = new Button("next round");
                    center.getChildren().add(buttonSave);
                    buttonSave.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            clearScreen();
                            resetVariables();
                            center.getChildren().remove(buttonSave);
                            timeLine.play();
                            
                        }
                    });
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
        scena.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
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
    private void resetVariables(){
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

}
