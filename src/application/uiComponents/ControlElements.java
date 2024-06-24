package application.uiComponents;

import application.controller.PlayerController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ControlElements extends VBox {
	
	private static ControlElements controlElements = new ControlElements();
	public static ControlElements getInstance() {
		return controlElements;
	};


	private ImageView videoPlaceholder = new ImageView();
	private HBox infoArea = new HBox();
	private ImageView like = new ImageView();
	private VBox titles = new VBox();
	private Label title = new Label();
	public  Label getTitle() { return title; }
	private Label subtitle = new Label();
	public  Label getSubtitle() { return subtitle; }
	private HBox buttonArea = new HBox();
	public  HBox getButtonArea() { return buttonArea; }
	private Button prevButton = new Button();
	private Button playButton = new Button();
	public  Button getPlayButton() { return playButton; }
	private Button nextButton = new Button();
	private Button pauseButton = new Button();
	public  Button getPauseButton() { return pauseButton; }
	private PlayerController playerController = PlayerController.getInstance();
	
	private String styleBase = "-fx-background-radius: 8;  -fx-padding: 6 12 6 12;"; // -fx-font-weight: bold;
	private String defaultStyle = "-fx-background-color: transparent;" + styleBase;
	private String pressStyle = "-fx-background-color: #292929;" + styleBase;

	
	public ControlElements() {		
		title.setText("");
		subtitle.setText("");
		titles.getChildren().addAll(title, subtitle);
		
		infoArea.getChildren().addAll(/* like, new Rectangle(8, 0), */ titles);
		
		buttonArea.getChildren().addAll(prevButton, playButton, nextButton);
		
		prevButton.setOnAction((event) -> {
			playerController.previousSong();
			BottomBar bottomBar = BottomBar.getInstance();
			playerController.checkLike(bottomBar.getStarButton());
		});	
		
	
		playButton.setOnAction((event) -> {
			if (playerController.getAudioPlayer() == null || playerController.getCurrentSong() == null) playerController.updateCurrentSong(playerController.getCurrentSong());
			playerController.play();
			BottomBar bottomBar = BottomBar.getInstance();
			playerController.checkLike(bottomBar.getStarButton());
			buttonArea.getChildren().remove(playButton);
			buttonArea.getChildren().add(1, pauseButton);
			
		});
		
		pauseButton.setOnAction((event) -> {
			playerController.pause();
			BottomBar bottomBar = BottomBar.getInstance();
			playerController.checkLike(bottomBar.getStarButton());
			buttonArea.getChildren().remove(pauseButton);
			buttonArea.getChildren().add(1, playButton);
		});
		
		nextButton.setOnAction((event) -> {
			playerController.nextSong();
			BottomBar bottomBar = BottomBar.getInstance();
			playerController.checkLike(bottomBar.getStarButton());
		});
		
		this.getChildren().addAll(videoPlaceholder, new Rectangle(0, 16), infoArea, new Rectangle(0, 8), buttonArea);
		
		applyStyle();
	}
	
	public void applyStyle() {
		this.setAlignment(Pos.BOTTOM_CENTER);
		buttonArea.setAlignment(Pos.CENTER);
		titles.setAlignment(Pos.CENTER);
		infoArea.setAlignment(Pos.CENTER);
		
		like.setImage(new Image("/like.png"));
		like.setFitWidth(24);
		like.setPreserveRatio(true);
		
		title.setStyle("-fx-font-weight: bold");
		subtitle.setTextFill(Color.rgb(217, 217, 217));
		
		ImageView prevGraphic = new ImageView(new Image("/previous.png"));
		prevGraphic.setFitWidth(24);
		prevGraphic.setPreserveRatio(true);
		prevButton.setGraphic(prevGraphic);
		ImageView playGraphic = new ImageView(new Image("/play.png"));
		playGraphic.setFitWidth(36);
		playGraphic.setPreserveRatio(true);
		playButton.setGraphic(playGraphic);
		ImageView pauseGraphic = new ImageView(new Image("/pause.png"));
		pauseGraphic.setFitWidth(36);
		pauseGraphic.setPreserveRatio(true);
		pauseButton.setGraphic(pauseGraphic);
		ImageView nextGraphic = new ImageView(new Image("/next.png"));
		nextGraphic.setFitWidth(24);
		nextGraphic.setPreserveRatio(true);
		nextButton.setGraphic(nextGraphic);
		
		prevButton.setStyle(defaultStyle);
		prevButton.setOnMousePressed(e -> prevButton.setStyle(pressStyle));
		prevButton.setOnMouseReleased(e -> prevButton.setStyle(defaultStyle));
		playButton.setStyle(defaultStyle);
		playButton.setOnMousePressed(e -> playButton.setStyle(pressStyle));
		playButton.setOnMouseReleased(e -> playButton.setStyle(defaultStyle));
		pauseButton.setStyle(defaultStyle);
		pauseButton.setOnMousePressed(e -> pauseButton.setStyle(pressStyle));
		pauseButton.setOnMouseReleased(e -> pauseButton.setStyle(defaultStyle));
		nextButton.setStyle(defaultStyle);
		nextButton.setOnMousePressed(e -> nextButton.setStyle(pressStyle));
		nextButton.setOnMouseReleased(e -> nextButton.setStyle(defaultStyle));
				
		videoPlaceholder.setImage(new Image("/videoPlaceholder.jpg"));
		videoPlaceholder.setFitWidth(176);
		videoPlaceholder.setPreserveRatio(true);

	}
}
