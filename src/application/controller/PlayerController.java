package application.controller;

import java.io.File;
import java.net.MalformedURLException;

import application.Playlist;
import application.Song;
import application.uiComponents.BottomBar;
import application.uiComponents.ControlElements;
import application.uiComponents.KProgressSlider;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class PlayerController {

	private static final PlayerController playerController = new PlayerController();

	public static PlayerController getInstance() {
		return playerController;
	}

	private Media audio;
	private Media video;
	private MediaPlayer audioPlayer;
	private MediaPlayer videoPlayer;
	private MediaView videoView;

	private Song currentSong;
	

	private Playlist currentPlaylist;


	public Playlist getCurrentPlaylist() {
		return currentPlaylist;
	}

	public void setCurrentPlaylist(Playlist currentPlaylist) {
		this.currentPlaylist = currentPlaylist;
	}	

	public Song getCurrentSong() {
		MediaController mediaController = MediaController.getInstance();
		return currentSong == null ? mediaController.getAllSongs().getSongs().get(0) : currentSong;
	}
	
	public void setCurrentSong(Song currentSong) {
		this.currentSong = currentSong;
	}
	
	public void initCurrent() {
		MediaController mediaController = MediaController.getInstance();
		// TODO: ist allSongs (bzw. currentPlaylist?) empty??? =>> FEHLERMELDUNG: "Du hast noch keine Songs hinzugefügt..."
		File file = new File("./saves/currentSongFile.txt");
		
		if (!file.exists() /* || currentPlaylist.getSongs().isEmpty() */) {
			setCurrentPlaylist(mediaController.getAllSongs());
			updateCurrentSong(mediaController.getAllSongs().getSongs().get(0));
		}
	}

	public void updateCurrentSong(Song currentSong) {
		this.currentSong = currentSong;
		MediaController mediaController = MediaController.getInstance();
		if (getCurrentPlaylist() != null) mediaController.saveAllToFile();

		audio = new Media(this.currentSong.getAudioFilePath());
		// video = new Media("file:///" + song.getVideoFilePath());
		audioPlayer = new MediaPlayer(audio);
		// videoPlayer = new MediaPlayer(video);
		// videoView = new MediaView (videoPlayer);
	}
	
	public void startPlaylist() {
		MediaController mediaController = MediaController.getInstance();
		updateCurrentSong(mediaController.getAllSongs().getSongs().get(0));
		play();
	}

	public MediaPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public MediaPlayer getVideoPlayer() {
		return videoPlayer;
	}
	
//	public void playPause() {
//		if (audioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
//			pause();
//			
//			// videoPlayer.pause();
//			}
//	}

	public void play() {
		if(currentSong != null) {
			ControlElements controlElements = ControlElements.getInstance();
			controlElements.getTitle().setText(currentSong.getTitle());
			controlElements.getSubtitle().setText(currentSong.getArtist());
			audioPlayer.play();
		}
	}
	
	public void pause() {
//		if (audioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
			audioPlayer.pause();
			// videoPlayer.pause();
//		}
	}

	public void nextSong() {
		MediaController mediaController = MediaController.getInstance();
		Playlist tempPlaylist = mediaController.getAllSongs();
		if (currentPlaylist != null) tempPlaylist = currentPlaylist;
		if (tempPlaylist.getSongs().indexOf(currentSong) + 1 != tempPlaylist.getSongs().size()) {
			audioPlayer.stop();
			// videoPlayer.stop();
			audio = new Media(tempPlaylist.getSongs().get(tempPlaylist.getSongs().indexOf(currentSong) + 1).getAudioFilePath());
			setCurrentSong(tempPlaylist.getSongs().get(tempPlaylist.getSongs().indexOf(currentSong) + 1));
			// video = new Media ("file:///" +
			// allSongs.get(allSongs.indexOf(song)+1).getVideoFilePath());
			audioPlayer = new MediaPlayer(audio);
			// videoPlayer = new MediaPlayer(video);
			// videoView = new MediaView (videoPlayer);
			play();
		} else {
			audioPlayer.stop();
			// videoPlayer.stop();
			audio = new Media(tempPlaylist.getSongs().get(0).getAudioFilePath());
			setCurrentSong(tempPlaylist.getSongs().get(0));
			// video = new Media ("file:///" + allSongs.get(0).getVideoFilePath());
			audioPlayer = new MediaPlayer(audio);
			// videoPlayer = new MediaPlayer(video);
			// videoView = new MediaView (videoPlayer);
			play();
		}
	}

	public void previousSong() {
		MediaController mediaController = MediaController.getInstance();
		Playlist tempPlaylist = mediaController.getAllSongs();
		if (currentPlaylist != null) tempPlaylist = currentPlaylist;

		if (audioPlayer.getCurrentTime().greaterThanOrEqualTo(Duration.seconds(5)) || tempPlaylist.getSongs().indexOf(currentSong) == 0) {
			audioPlayer.seek(Duration.seconds(0));
			// videoPlayer.seek(Duration.seconds(0));
		}
		else {
			audioPlayer.stop();
			// videoPlayer.stop();
			audio = new Media(tempPlaylist.getSongs().get(tempPlaylist.getSongs().indexOf(currentSong) - 1).getAudioFilePath());
			setCurrentSong(tempPlaylist.getSongs().get(tempPlaylist.getSongs().indexOf(currentSong) - 1));

			// video = new Media ("file:///" +
			// allSongs.get(allSongs.indexOf(song)-1).getVideoFilePath());
			audioPlayer = new MediaPlayer(audio);
			// videoPlayer = new MediaPlayer(video);
			// videoView = new MediaView (videoPlayer);
			play();
		}
	}

	public void volumeChange(KProgressSlider volumeSlider) {
		if (audioPlayer != null)
			audioPlayer.setVolume(volumeSlider.getValue() * 0.01);
	}
	
	public void checkLike(Button button) {	
		ImageView view = new ImageView();
		view.setFitWidth(24);
		view.setPreserveRatio(true);
		
		if(getCurrentSong().isLike()){
			view.setImage(new Image("like.png"));          		
    	}else{
    		view.setImage(new Image("like_outline.png"));
    	}
		button.setGraphic(view);
	}
	
	public void onPlaylistPlay(Button playButton, Button pauseButton, HBox buttonArea) {	
		if (getAudioPlayer() == null || getCurrentSong() == null) updateCurrentSong(getCurrentSong());
		play();
		BottomBar bottomBar = BottomBar.getInstance();
		checkLike(bottomBar.getStarButton());
		buttonArea.getChildren().remove(playButton);
		if (!buttonArea.getChildren().contains(pauseButton)) buttonArea.getChildren().add(1, pauseButton);
	}
}
