package fxsnaken;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FxSnaken2 extends Application {

    private final long[] frameTimes = new long[4];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;
    Label label = new Label("FPS");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("snake");
        Pane center = new Pane();
        center.setStyle("-fx-background-color: #000000");

        BorderPane mainPane = new BorderPane();
        StackPane left = new StackPane();
        left.setStyle("-fx-background-color: #303030");
        left.setPrefWidth(200);
        mainPane.setLeft(left);
        mainPane.setCenter(center);

        Scene scena = new Scene(mainPane, 1284, 818, Color.rgb(0, 191, 255));

        stage.setScene(scena);

        stage.show();

        AnimationTimer animation = new AnimationTimer() {
            String text = "Each module contains some background information on major Kubernetes features and concepts, and includes an interactive online tutorial.";
            
            int[][] allFallingPoints = new int[][]{
                new int[]{text.length() / 3, 2 * text.length() / 3},
                new int[]{text.length() / 2},
                new int[]{text.length()-1},
                new int[]{0},
                new int[]{0 * text.length() / 8, 1 * text.length() / 8, 2 * text.length() / 8, 3 * text.length() / 8, 4 * text.length() / 8,
                5 * text.length() / 8, 6 * text.length() / 8, 7 * text.length() / 8, (8 * text.length() / 8)-1}
            };
            int currentFalling = 0;
            int[] fallingPoints = allFallingPoints[0];

            int[] bounceCount;
            int[] bounces = new int[]{40, 30, 20, 12, 7, 5, 3, 2, 1};
            int dropDistance = 2;
            int lettersDistanceY = 12;
            int lettersDistanceX = 7;
            int bottom = 100;

            Text[] texts = new Text[text.length()];

            int[] letterPosititions = new int[text.length()];
            int[] letterSpeed = new int[text.length()];
            
            long nextStart = 0;
            
            {
                for (int a = 0; a < text.length(); a++) {
                    texts[a] = new Text("" + text.charAt(a));
                    texts[a].relocate(a * lettersDistanceX, 0);
                    texts[a].setFill(Color.WHITE);
                    texts[a].setFont(Font.font("Courier New", FontWeight.BOLD, 12));
                    center.getChildren().add(texts[a]);
                }
                init();
            }
            
            private void init() {
                bounceCount = new int[text.length()];
                letterPosititions = new int[text.length()];
                letterSpeed = new int[text.length()];

                for (int startPoint : fallingPoints) {
                    letterSpeed[startPoint] = 1;
                }
//                for (int a = 0; a < text.length(); a++) {
//                    texts[a].relocate(a * lettersDistanceX, 0);
//                }
            }

            public void handle(long now) {
                if(System.currentTimeMillis() < nextStart) {
                    return;
                }
                boolean startNext = false;
                boolean hasDrops = false;
                for (int a = 0; a < text.length(); a++) {
                    if (letterSpeed[a] == -1 || letterSpeed[a] == 1) {
                        letterPosititions[a] = letterPosititions[a] + (dropDistance * letterSpeed[a]);
                        hasDrops = true;
                        if (a > 0 && letterSpeed[a - 1] == 0) {
                            letterSpeed[a - 1] = 1;
                        }
                        if (a < letterPosititions.length - 1) {
                            startNext = true;
                        }
                    }
                    if (letterSpeed[a] == 0 && startNext) {
                        letterSpeed[a] = 1;
                        startNext = false;
                    }

                    texts[a].relocate(a * lettersDistanceX, letterPosititions[a]);

                    if (letterPosititions[a] > bottom) {
                        letterSpeed[a] = -1;
                        bounceCount[a]++;
                        if (bounceCount[a] > bounces.length - 1) {
                            letterSpeed[a] = -100;
                        }
                    }

                    if (letterSpeed[a] == -1 && letterPosititions[a] < bottom - bounces[bounceCount[a]]) {
                        letterSpeed[a] = 1;
                    }

                }
                if (!hasDrops) {
                    currentFalling++;
                    if(currentFalling > allFallingPoints.length - 1) {
                        currentFalling = 0;
                    }
                    fallingPoints = allFallingPoints[currentFalling];
                    init();
                    nextStart = System.currentTimeMillis() + 2000;
                }
            }
        };

        animation.start();
        label.setTextFill(Color.WHITE);
        left.getChildren().add(label);

    }

}
