package fxsnaken;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class FxSnaken extends Application {

    int x = 5;
    int y = 5;
    
    int xd = 2;
    int yd = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("snake");
        Group root = new Group();
        Paint fill = Color.rgb(0, 191, 255);
        Scene scena = new Scene(root, 1024, 768, fill);
        Canvas canvas = new Canvas(1024, 768);
        root.getChildren().add(canvas);
        stage.setScene(scena);
        
        stage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        //gc.fillRect(1, 1, 99999, 99999);

        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                gc.setFill(Color.RED);
                gc.setStroke(Color.RED);
                gc.fillRect(x, y, 10, 10);
                x = x + xd;
                y = y + yd;
                
            }
        }, 0, 50);

        scena.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode() == KeyCode.DOWN) {
                    yd = 2;
                    xd = 0;
                }
                if (key.getCode() == KeyCode.RIGHT) {
                    yd = 0;
                    xd = 2;
                }
                if (key.getCode() == KeyCode.LEFT) {
                    yd = 0;
                    xd = -2;
                }
                 if (key.getCode() == KeyCode.UP) {
                    yd = -2;
                    xd = 0;
                }
            }
        });

    }

}
