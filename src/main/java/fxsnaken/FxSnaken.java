package fxsnaken;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.Random;
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
    int maxRounds = 10;
    int map = 5;
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
    int s1speed = 1;
    int s2speed = 1;
    FloatControl gainControl;
    Timeline timeln;

    Timeline timeLine = new Timeline();
    Timeline gametimeln;

    Text rpointsShow;
    Text ypointsShow;
    Text timeShow;

    Instant startTime;

    GraphicsContext gc;
    VBox lostmenu = new VBox();
    EventHandler keysEvents;

    Image bgImage = new Image(FxSnaken.class.getResourceAsStream("/gold-bg.png"));

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

	timeln = new Timeline(
		new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
		    int pos = 0;

		    public void handle(ActionEvent event) {
			pos = pos + 1;
			BackgroundPosition bgpos = new BackgroundPosition(Side.LEFT, pos, false, Side.TOP, 0, false);
			BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
			BackgroundImage im = new BackgroundImage(bgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, bgpos, BackgroundSize.DEFAULT);
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

	HBox hBoxMap = new HBox();
	hBoxMap.setAlignment(Pos.TOP_LEFT);
	vBox.getChildren().add(hBoxMap);

	HBox hBoxMap2 = new HBox();
	hBoxMap2.setAlignment(Pos.CENTER_LEFT);
	vBox.getChildren().add(hBoxMap2);

	StackPane i1 = new StackPane();
	StackPane i2 = new StackPane();
	StackPane i3 = new StackPane();
	StackPane i4 = new StackPane();
	StackPane i5 = new StackPane();

	Image image = new Image(getClass().getResourceAsStream("/level1-thumb.png"));
	ImageView imv = new ImageView(image);
	i1.getChildren().add(imv);
	i1.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	hBoxMap.setMargin(i1, new Insets(20, 0, 0, 20));
	hBoxMap.getChildren().add(i1);
	Text l1Text = new Text("Plum Waterfalls");
	l1Text.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
	l1Text.setFill(Color.web("#68235F"));
	l1Text.setStroke(Color.WHITE);
	l1Text.setStrokeWidth(1.2);
	i1.getChildren().add(l1Text);
	i1.setOnMouseClicked(e -> {
	    map = 1;
	    i1.setStyle("-fx-border-color: red; -fx-border-width: 4; -fx-padding: 0px;");
	    i2.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i3.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i4.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i5.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	});

	Image image2 = new Image(getClass().getResourceAsStream("/level2-thumb.png"));
	ImageView imv2 = new ImageView(image2);

	i2.getChildren().add(imv2);
	i2.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	hBoxMap.setMargin(i2, new Insets(20, 0, 0, 20));
	hBoxMap.getChildren().add(i2);
	Text l2Text = new Text("Dark Forest");
	l2Text.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
	l2Text.setFill(Color.BLACK);
	l2Text.setStroke(Color.WHITE);
	l2Text.setStrokeWidth(1);
	i2.getChildren().add(l2Text);
	i2.setOnMouseClicked(e -> {
	    map = 2;
	    i2.setStyle("-fx-border-color: red; -fx-border-width: 4; -fx-padding: 0px;");
	    i1.setStyle("-fx-border-color:transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i3.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i4.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i5.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	});

	Image image3 = new Image(getClass().getResourceAsStream("/level3-thumb.png"));
	ImageView imv3 = new ImageView(image3);
	map = 3;

	i3.getChildren().add(imv3);
	i3.setStyle("-fx-border-color: red; -fx-border-width: 4; -fx-padding: 0px;");
	hBoxMap.setMargin(i3, new Insets(20, 0, 0, 20));
	hBoxMap.getChildren().add(i3);
	Text l3Text = new Text("Egyptian Sand");
	l3Text.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
	l3Text.setFill(Color.YELLOW);
	l3Text.setStroke(Color.BROWN);
	l3Text.setStrokeWidth(1);
	i3.getChildren().add(l3Text);
	i3.setOnMouseClicked(e -> {
	    map = 3;
	    i3.setStyle("-fx-border-color: red; -fx-border-width: 4; -fx-padding: 0px;");
	    i1.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i2.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i4.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i5.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	});

	Image image4 = new Image(getClass().getResourceAsStream("/level4-thumb.png"));
	ImageView imv4 = new ImageView(image4);

	i4.getChildren().add(imv4);
	i4.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	hBoxMap2.setMargin(i4, new Insets(20, 0, 0, 20));
	hBoxMap2.getChildren().add(i4);
	Text l4Text = new Text("Rocky Cliffs");
	l4Text.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
	l4Text.setFill(Color.FORESTGREEN);
	l4Text.setStroke(Color.LIGHTGREEN);
	l4Text.setStrokeWidth(1);
	i4.getChildren().add(l4Text);
	i4.setOnMouseClicked(e -> {
	    map = 4;
	    i4.setStyle("-fx-border-color: red; -fx-border-width: 4; -fx-padding: 0px;");
	    i3.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i1.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i2.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i5.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	});

	Image image5 = new Image(getClass().getResourceAsStream("/level5-thumb.png"));
	ImageView imv5 = new ImageView(image5);

	i5.getChildren().add(imv5);
	i5.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	hBoxMap2.setMargin(i5, new Insets(20, 0, 0, 20));
	hBoxMap2.getChildren().add(i5);
	Text l5Text = new Text("Candy Land");
	l5Text.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
	l5Text.setFill(Color.web("#FE96E3"));
	l5Text.setStroke(Color.web("#5D395B"));
	l5Text.setStrokeWidth(1.2);
	i5.getChildren().add(l5Text);
	i5.setOnMouseClicked(e -> {
	    map = 5;
	    i5.setStyle("-fx-border-color: red; -fx-border-width: 4; -fx-padding: 0px;");
	    i3.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i1.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i2.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	    i4.setStyle("-fx-border-color: transparent; -fx-border-width: 4; -fx-padding: 0px;");
	});

	hBox.getStylesheets().add(getClass().getResource("/slider.css").toExternalForm());

	vBox.getChildren().add(hBox);
	vBox.setMargin(hBox, new Insets(200, 0, 0, 0));
	addMenuItem(vBox, "START", () -> game(scene), Color.DARKGRAY);

	HBox hBoxQ = new HBox();
	VBox vBoxQ = new VBox();
	hBoxQ.setAlignment(Pos.BOTTOM_RIGHT);
	vBox.setMargin(hBoxQ, new Insets(20, 10, 0, 0));

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
	stage.getIcons().add(new Image(FxSnaken.class.getResourceAsStream("/snake-icon.png")));
	stage.show();

	try {
	    InputStream mp3Stream = FxSnaken.class.getResourceAsStream("/x.mp3");
	    InputStream bufferedIn = new BufferedInputStream(mp3Stream);
	    AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedIn);
	    AudioFormat format = stream.getFormat();
	    DataLine.Info info = new DataLine.Info(Clip.class, format);
	    Clip clip = (Clip) AudioSystem.getLine(info);
	    clip.open(stream);
	    clip.loop(Clip.LOOP_CONTINUOUSLY);

	    gainControl = (FloatControl) clip
		    .getControl(FloatControl.Type.MASTER_GAIN);
	    double gain = 0.5D;
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
	StackPane up = new StackPane();
	BorderPane borderPane = new BorderPane();
	scene.setRoot(borderPane);

	bottom.setPrefHeight(5);
	up.setPrefHeight(17);
	left.setPrefWidth(300);

	center.setPrefWidth(900);
	center.setPrefHeight(900);
	borderPane.setLeft(left);
	borderPane.setCenter(center);
	borderPane.setBottom(bottom);
	borderPane.setTop(up);

	left.setStyle("-fx-background-color: #202020;");

	gametimeln = new Timeline(
		new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
		    int pos = 0;

		    public void handle(ActionEvent event) {
			pos = pos + 1;
			BackgroundPosition bgpos = new BackgroundPosition(Side.LEFT, pos, false, Side.TOP, 0, false);
			BackgroundFill fill = new BackgroundFill(Color.web("#000000"), CornerRadii.EMPTY, Insets.EMPTY);
			BackgroundImage im = new BackgroundImage(bgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, bgpos, BackgroundSize.DEFAULT);
			center.setBackground(new Background(new BackgroundFill[]{fill}, new BackgroundImage[]{im}));

		    }
		}));
	gametimeln.setCycleCount(Timeline.INDEFINITE);
	gametimeln.play();

	bottom.setStyle("-fx-background-color: #000000;");
	up.setStyle("-fx-background-color: #000000;");
	Text copyrightText = new Text("©Andrei Vasilache");
	copyrightText.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
	copyrightText.setFill(Color.WHITE);

	bottom.getChildren().add(copyrightText);
	bottom.setAlignment(Pos.CENTER_RIGHT);

	Text redpointsShow = new Text();
	redpointsShow.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	redpointsShow.setFill(Color.RED);
	redpointsShow.setText("red ");

	rpointsShow = new Text();
	rpointsShow.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	rpointsShow.setFill(Color.RED);

	Text scorepointsShow = new Text();
	scorepointsShow.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	scorepointsShow.setFill(Color.WHITE);
	scorepointsShow.setText(" -score- ");

	ypointsShow = new Text();
	ypointsShow.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	ypointsShow.setFill(Color.YELLOW);

	Text yellpointsShow = new Text();
	yellpointsShow.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	yellpointsShow.setFill(Color.YELLOW);
	yellpointsShow.setText(" yellow");

//	"red " + pr + " -score- " + py + " yellow"
	HBox pointsBox = new HBox();
	pointsBox.getChildren().add(redpointsShow);
	pointsBox.getChildren().add(rpointsShow);
	pointsBox.getChildren().add(scorepointsShow);
	pointsBox.getChildren().add(ypointsShow);
	pointsBox.getChildren().add(yellpointsShow);
	pointsBox.setAlignment(Pos.CENTER);

	left.getChildren().add(pointsBox);
	left.setMargin(pointsBox, new Insets(20, 0, 0, 0));

	showScore();

	timeShow = new Text();
	timeShow.setFont(Font.font("Digital-7", FontWeight.BOLD, 50));
	timeShow.setFill(Color.WHITE);
	left.getChildren().add(timeShow);
	left.setMargin(timeShow, new Insets(80, 0, 0, 0));

	left.setAlignment(Pos.TOP_CENTER);

	Canvas canvas = new Canvas(1024, 768);
	center.getChildren().add(canvas);
	gc = canvas.getGraphicsContext2D();

	HBox rsgHbox = new HBox();
	rsgHbox.setAlignment(Pos.CENTER);

	center.getChildren().add(rsgHbox);

	Timeline rsgTimeline = new Timeline();
	KeyFrame rsgKF = new KeyFrame(Duration.seconds(0.80), new EventHandler<ActionEvent>() {
	    int nr = 1;

	    public void handle(ActionEvent event) {
		if (nr == 1) {
		    Image redyimage = new Image(getClass().getResourceAsStream("/ready.png"));
		    ImageView redyimageview = new ImageView(redyimage);
		    rsgHbox.getChildren().add(redyimageview);
		}
		if (nr == 2) {
		    Image setimage = new Image(getClass().getResourceAsStream("/set.png"));
		    ImageView setimageview = new ImageView(setimage);
		    rsgHbox.getChildren().add(setimageview);
		}
		if (nr == 3) {
		    Image goimage = new Image(getClass().getResourceAsStream("/go.png"));
		    ImageView goimageview = new ImageView(goimage);
		    rsgHbox.getChildren().add(goimageview);

		}
		if (nr == 4) {
		    timeLine.play();
		    startTime = Instant.now();
		    rsgTimeline.stop();
		    center.getChildren().remove(rsgHbox);

		}
		nr++;

	    }
	});
	rsgTimeline.getKeyFrames().add(rsgKF);
	rsgTimeline.setCycleCount(4);
	rsgTimeline.play();

	center.getChildren().add(lostmenu);
	lostmenu.setAlignment(Pos.CENTER);
	lostmenu.setVisible(false);

	Text lostText = new Text("");
	lostText.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
	lostText.setFill(Color.YELLOW);
	lostmenu.getChildren().add(lostText);

	HBox hBOx = new HBox();
	hBOx.setAlignment(Pos.CENTER);
	Button buttonSave = new Button("next round");
	Button buttonGQuit = new Button("quit");
	hBOx.getChildren().add(buttonGQuit);
	hBOx.getChildren().add(buttonSave);
	hBOx.setMargin(buttonGQuit, new Insets(0, 10, 0, 0));
	hBOx.getStylesheets().add(getClass().getResource("/button.css").toExternalForm());

	buttonSave.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {
		resetVariables();
		clearScreen();
		center.getChildren().remove(buttonSave);
		center.getChildren().remove(buttonGQuit);
		timeLine.play();
		lostmenu.setVisible(false);
	    }
	});
	lostmenu.getChildren().add(hBOx);

	buttonGQuit.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent a) {
		resetVariables();
		timeLine.stop();
		timeLine = new Timeline();
		timeln.stop();
		gametimeln.stop();
		lostmenu = new VBox();
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, keysEvents);
		py = 0;
		pr = 0;
		mainMenu(scene);

	    }
	});

	resetVariables();
	clearScreen();

	KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.023), new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		gc.setFill(Color.RED);
		gc.setStroke(Color.RED);

		if (matrix[s1x][s1y] != 0) {
		    redLost();
		}
		matrix[s1x][s1y] = 1;

		if (s1xd == 2) {
		    if (s1x < 206) {
			if (matrix[s1x + 1][s1y] != 0) {
			    redLost();
			}
			matrix[s1x + 1][s1y] = 1;
			gc.fillRect((s1x + 1) * 5, s1y * 5, 5, 5);
		    }
		}
		if (s1xd == -2) {
		    if (s1x > 0) {
			if (matrix[s1x - 1][s1y] != 0) {
			    redLost();
			}
			matrix[s1x - 1][s1y] = 1;
			gc.fillRect((s1x - 1) * 5, s1y * 5, 5, 5);
		    }
		}
		if (s1yd == 2) {
		    if (s1y < 155) {
			if (matrix[s1x][s1y + 1] != 0) {
			    redLost();
			}
			matrix[s1x][s1y + 1] = 1;
			gc.fillRect(s1x * 5, (s1y + 1) * 5, 5, 5);
		    }
		}
		if (s1yd == -2) {
		    if (s1y > 0) {
			if (matrix[s1x][s1y - 1] != 0) {
			    redLost();
			}
			matrix[s1x][s1y - 1] = 1;
			gc.fillRect(s1x * 5, (s1y - 1) * 5, 5, 5);
		    }
		}
		gc.fillRect(s1x * 5, s1y * 5, 5, 5);
		gc.setFill(Color.web("#FF4747"));
		gc.fillRect(s1x * 5 + 2, s1y * 5 + 2, 1, 1);
		if (s1yd == 0) {
		    gc.fillRect(s1x * 5 + 1, s1y * 5 + 3, 3, 2);
		} else {
		    gc.fillRect(s1x * 5 + 3, s1y * 5 + 1, 2, 3);
		}

		s1x = s1x + s1xd;
		s1y = s1y + s1yd;

		gc.setFill(Color.YELLOW);
		gc.setStroke(Color.YELLOW);
		if (matrix[s2x][s2y] != 0) {
		    yellowLost();
		}

		matrix[s2x][s2y] = 2;
		if (s2xd == 2) {
		    if (s2x < 206) {
			if (matrix[s2x + 1][s2y] != 0) {
			    yellowLost();
			}
			matrix[s2x + 1][s2y] = 2;
			gc.fillRect((s2x + 1) * 5, s2y * 5, 5, 5);
		    }
		}
		if (s2xd == -2) {
		    if (s2x > 0) {
			if (matrix[s2x - 1][s2y] != 0) {
			    yellowLost();
			}
			matrix[s2x - 1][s2y] = 2;
			gc.fillRect((s2x - 1) * 5, s2y * 5, 5, 5);
		    }
		}
		if (s2yd == 2) {
		    if (s2y < 155) {
			if (matrix[s2x][s2y + 1] != 0) {
			    yellowLost();
			}
			matrix[s2x][s2y + 1] = 2;
			gc.fillRect(s2x * 5, (s2y + 1) * 5, 5, 5);
		    }
		}
		if (s2yd == -2) {
		    if (s2y > 0) {
			if (matrix[s2x][s2y - 1] != 0) {
			    yellowLost();
			}
			matrix[s2x][s2y - 1] = 2;
			gc.fillRect(s2x * 5, (s2y - 1) * 5, 5, 5);
		    }
		}
		gc.fillRect(s2x * 5, s2y * 5, 5, 5);
		gc.setFill(Color.web("#699C1C"));
		gc.fillRect(s2x * 5 + 2, s2y * 5 + 2, 1, 1);
		if (s2yd == 0) {
		    gc.fillRect(s2x * 5 + 1, s2y * 5 + 3, 3, 2);
		} else {
		    gc.fillRect(s2x * 5 + 3, s2y * 5 + 1, 2, 3);
		}
		s2x = s2x + s2xd;
		s2y = s2y + s2yd;

		if (s1x > 1024 / 5) {
		    s1x = 0;
		}
		if (s1x < 0) {
		    s1x = 1024 / 5;
		}
		if (s1y > 768 / 5) {
		    s1y = 0;
		}
		if (s1y < 0) {
		    s1y = 768 / 5;
		}
		if (s2x > 1024 / 5) {
		    s2x = 0;
		}
		if (s2x < 0) {
		    s2x = 1024 / 5;
		}
		if (s2y > 768 / 5) {
		    s2y = 0;
		}
		if (s2y < 0) {
		    s2y = 768 / 5;
		}

		long seconds = java.time.Duration.between(startTime, Instant.now()).getSeconds();
		String clock_tx = String.format("%02d:%02d", seconds / 60, seconds % 60);
		timeShow.setText(clock_tx);

	    }

	    private void redLost() {
		lostText.setStroke(Color.WHITE);
		lostText.setStrokeWidth(1);
		lostText.setFill(Color.RED);
		lostText.setText("red lost");
		py++;
		showScore();
		timeLine.pause();
		lostmenu.setVisible(true);
		buttonSave.requestFocus();
	    }

	    private void yellowLost() {
		lostText.setStroke(Color.BLACK);
		lostText.setStrokeWidth(1);
		lostText.setFill(Color.YELLOW);
		lostText.setText("yellow lost");
		pr++;
		showScore();
		timeLine.pause();
		lostmenu.setVisible(true);
		buttonSave.requestFocus();
	    }
	});

	timeLine.getKeyFrames().add(keyFrame);
	timeLine.setCycleCount(Timeline.INDEFINITE);

	keysEvents = new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent key) {
		if (key.getCode() == KeyCode.DOWN) {
		    if (s1yd >= 0) {
			s1yd = s1speed;
			s1xd = 0;
		    }
		}
		if (key.getCode() == KeyCode.RIGHT) {
		    if (s1xd >= -0) {
			s1yd = 0;
			s1xd = s1speed;
		    }
		}
		if (key.getCode() == KeyCode.LEFT) {
		    if (s1xd <= 0) {
			s1yd = 0;
			s1xd = -1 * s1speed;
		    }
		}

		if (key.getCode() == KeyCode.UP) {
		    if (s1yd <= 0) {
			s1yd = -1 * s1speed;
			s1xd = 0;
		    }
		}

		if (key.getCode() == KeyCode.S) {
		    if (s2yd >= 0) {
			s2yd = s2speed;
			s2xd = 0;
		    }
		}

		if (key.getCode() == KeyCode.D) {
		    if (s2xd >= 0) {
			s2yd = 0;
			s2xd = s2speed;
		    }
		}

		if (key.getCode() == KeyCode.A) {
		    if (s2xd <= 0) {
			s2yd = 0;
			s2xd = -1 * s2speed;
		    }
		}

		if (key.getCode() == KeyCode.W) {
		    if (s2yd <= 0) {
			s2yd = -1 * s2speed;
			s2xd = 0;
		    }
		}

		if (key.getCode() == KeyCode.E) {
		    if (s2speed == 2) {
			s2speed = 1;
		    } else if (s2speed == 1) {
			s2speed = 2;
		    }
		    if (s2xd == 1) {
			s2xd = 2;
		    } else if (s2xd == -1) {
			s2xd = -2;
		    } else if (s2yd == 1) {
			s2yd = 2;
		    } else if (s2yd == -1) {
			s2yd = -2;
		    } else if (s2xd == 2) {
			s2xd = 1;
		    } else if (s2xd == -2) {
			s2xd = -1;
		    } else if (s2yd == 2) {
			s2yd = 1;
		    } else if (s2yd == -2) {
			s2yd = -1;
		    }
		}

		if (key.getCode() == KeyCode.NUMPAD0 || key.getCode() == KeyCode.INSERT) {
		    if (s1speed == 2) {
			s1speed = 1;
		    } else if (s1speed == 1) {
			s1speed = 2;
		    }
		    if (s1xd == 1 || s1xd == -1) {
			s1xd = s1xd * s1speed;
		    } else if (s1yd == 1 || s1yd == -1) {
			s1yd = s1yd * s1speed;
		    } else if (s1xd == 2) {
			s1xd = 1;
		    } else if (s1xd == -2) {
			s1xd = -1;
		    } else if (s1yd == 2) {
			s1yd = 1;
		    } else if (s1yd == -2) {
			s1yd = -1;
		    }
		}

	    }

	};
	scene.addEventFilter(KeyEvent.KEY_PRESSED, keysEvents);

    }

    private void clearScreen() {

	if (map == 1) {
	    Image image = new Image(this.getClass().getResourceAsStream("/mov.jpg"));
	    gc.drawImage(image, 0, 0);
	    for (int a = 5; a < 92; a++) {
		matrix[a][40] = 3;
		matrix[a][41] = 3;
		matrix[a][80] = 3;
		matrix[a][81] = 3;
		matrix[a][120] = 3;
		matrix[a][121] = 3;
	    }
	    for (int a = 105; a < 201; a++) {
		matrix[a][40] = 3;
		matrix[a][41] = 3;
		matrix[a][80] = 3;
		matrix[a][81] = 3;
		matrix[a][120] = 3;
		matrix[a][121] = 3;
	    }
	}

	if (map == 2) {
	    Image image = new Image(this.getClass().getResourceAsStream("/forest.png"));
	    gc.drawImage(image, 0, 0);
	    float y = 0;
	    for (int x = 0; x < 206; x++) {
		if (x < 103 - 10 || x > 103 + 10) {
		    matrix[x][Math.round(y)] = 3;
		    if (Math.round(y) < 154) {
			matrix[x][Math.round(y) + 1] = 3;
		    }
		}

		y = y + 155f / 206f;
	    }
	    y = 154 - 50 * 155f / 206f;
	    for (int x = 0; x < 206; x++) {

		if (y < 0) {
		    break;
		}
		if (x < 155 / 2 - 10 || x > 155 / 2 + 10) {
		    matrix[x][Math.round(y)] = 3;
		    if (Math.round(y) < 154) {
			matrix[x][Math.round(y) + 1] = 3;
		    }
		}

		y = y - 155f / 206f;
	    }
	    y = 154;
	    for (int x = 50; x < 206; x++) {
		if (y < 0) {
		    break;
		}
		if (x < 78 + 50 - 10 || x > 78 + 50 + 10) {
		    matrix[x][Math.round(y)] = 3;
		    if (Math.round(y) < 154) {
			matrix[x][Math.round(y) + 1] = 3;
		    }
		}

		y = y - 155f / 206f;
	    }
	}
	if (map == 3) {
	    Image image = new Image(this.getClass().getResourceAsStream("/egipt.png"));
	    gc.drawImage(image, 0, 0);
	}

	if (map == 4) {
	    Image image = new Image(this.getClass().getResourceAsStream("/MountainsBG.png"));
	    gc.drawImage(image, 0, 0);
	    Random random = new Random();
	    for (int a = 0; a < 150; a++) {
		int ry = random.nextInt(154);
		if (ry == 151 || ry == 2) {
		    continue;
		}
		matrix[random.nextInt(206)][ry] = 3;
	    }

	}
	if (map == 5) {
	    Image image = new Image(this.getClass().getResourceAsStream("/zahar.jpg"));
	    gc.drawImage(image, 0, 0);
	    int radius = 155 / 2 - 2;
	    int xCenter = 206 / 2;
	    int yCenter = 155 / 2 - 1;

	    int r2 = radius * radius;
	    for (float x = -radius; x <= radius; x = x + 2f) {

		int y = (int) (Math.sqrt(r2 - x * x) + 0.5);
		matrix[(int) (xCenter + x)][(int) (yCenter + y)] = 3;
		matrix[(int) (xCenter + x)][(yCenter - y)] = 3;

	    }

	}

	Image wallimage = new Image(this.getClass().getResourceAsStream("/wall.png"));
	for (int x = 0; x < 206; x++) {
	    for (int y = 0; y < 155; y++) {
		if (matrix[x][y] == 3) {
		    gc.drawImage(wallimage, x * 5, y * 5);
		}
	    }

	}
    }

    private void resetVariables() {
	startTime = Instant.now();
	if (map == 1) {
	    s2x = 2;
	    s2y = 2;
	    s2xd = 1;
	    s2yd = 0;
	}
	if (map == 2) {
	    s2x = 204;
	    s2y = 2;
	    s2xd = -1;
	    s2yd = 0;
	}
	if (map == 3) {
	    s2x = 2;
	    s2y = 2;
	    s2xd = 1;
	    s2yd = 0;
	}

	if (map == 4) {
	    s2x = 2;
	    s2y = 2;
	    s2xd = 1;
	    s2yd = 0;
	}
	if (map == 5) {
	    s2x = 2;
	    s2y = 2;
	    s2xd = 1;
	    s2yd = 0;
	}

	s1x = 2;
	s1y = 151;

	s1xd = 1;
	s1yd = 0;
	s1speed = 1;
	s2speed = 1;
	matrix = new int[206][155];
    }

    private void showScore() {
	rpointsShow.setText("" + pr);
	ypointsShow.setText("" + py);
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
	vBox.setMargin(volumebox, new Insets(200, 0, 0, 50));
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
//        vBox.setMargin(optionsMT, new Insets(200, 0, 0, 50));

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
