package application.uiComponents;

import java.util.Timer;
import java.util.TimerTask;

import application.controller.MediaController;
import application.controller.PlayerController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BottomBar extends HBox {
	
	private static BottomBar bottomBar = new BottomBar();
	public static BottomBar getInstance() {
		return bottomBar;
	};
	
	
	private Label currentTimeLabel = new Label("00:00");
	private KProgressSlider seekbar = new KProgressSlider();
	private Label endTimeLabel = new Label("00:00");
	private ImageView volumeIcon = new ImageView();
	private KProgressSlider volumeSlider = new KProgressSlider();
	
	private Button starButton = new Button();
	public Button getStarButton(){return starButton;}
	
	private PlayerController playerController = PlayerController.getInstance();
	private MediaController mediaController = MediaController.getInstance();
	
	Timer timer = new Timer();
	boolean dragDetected = false;

	
	
	public BottomBar() {
		
		this.getChildren().addAll(
				starButton,
				new Rectangle(35, 0),
				currentTimeLabel,
				seekbar,
				endTimeLabel,
				new Rectangle(48, 0),
				volumeIcon,
				new Rectangle(12, 0),
				volumeSlider,
				new Rectangle(32, 0)
		);
		
		volumeSlider.setValue(10);		


		
		timer.scheduleAtFixedRate(getSeekbarProgressTask(), 0, 250);
		
		starButton.setOnAction(event -> {
				if(!playerController.getCurrentSong().isLike()) {
					mediaController.setOnFavorites(playerController.getCurrentSong());
				}else{
		    		mediaController.deleteOnFavorites(playerController.getCurrentSong());
				}
				playerController.checkLike(starButton);
	    });
		
		
		seekbar.setOnDragDetected(event -> {playerController.getAudioPlayer().pause();
        	if (timer != null) timer.cancel();
            dragDetected=true;     
	    });

		seekbar.setOnMouseReleased(event -> {
            if(dragDetected){
				double totalTime = playerController.getAudioPlayer().getTotalDuration().toSeconds();
				playerController.getAudioPlayer().seek(Duration.seconds(totalTime * 0.01 * ((double) seekbar.getValue() )));
				playerController.getAudioPlayer().play();
            	timer = new Timer();
        		timer.scheduleAtFixedRate(getSeekbarProgressTask(), 0, 250);
                dragDetected=false;
            }
	    });
		
		applyStyle();
	}
	
	
	public TimerTask getSeekbarProgressTask() {
		return new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if (playerController.getAudioPlayer() != null) {
						double currentTime = playerController.getAudioPlayer().getCurrentTime().toSeconds();
						double totalTime = playerController.getAudioPlayer().getTotalDuration().toSeconds();
						int currentMinutes = (int) Math.abs(currentTime / 60);
						int currentSeconds = (int) Math.abs(currentTime - currentMinutes * 60);
						int endMinutes = (int) Math.abs((totalTime - currentTime) / 60);
						int endSeconds = (int) Math.abs((totalTime - currentTime) - endMinutes * 60);
						currentTimeLabel.setText(String.format("%02d:%02d", currentMinutes, currentSeconds));
						endTimeLabel.setText(String.format("%02d:%02d", endMinutes, endSeconds));
						seekbar.setValue(100 * (currentTime / totalTime));
						
						playerController.volumeChange(volumeSlider);
					}
	            });
			};
		};

	}
	
	public boolean switchStar() {
		return playerController.getCurrentSong().isLike();
	}
	
	private void applyStyle() {
		this.setAlignment(Pos.CENTER);
		this.setMinHeight(64);
		this.setBackground(Background.fill(Color.rgb(19, 19, 19)));
		
		volumeIcon.setImage(new Image("/volume.png"));
		volumeIcon.setFitWidth(24);
		volumeIcon.setPreserveRatio(true);
		
		starButton.setStyle("-fx-background-color:transparent;");
		playerController.checkLike(starButton);
		
		volumeSlider.setMaxWidth(64);
				
		HBox.setHgrow(seekbar, Priority.ALWAYS);
	}

}
