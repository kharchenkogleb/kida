package application;

import java.io.File;
import java.io.Serializable;

public class Song implements Serializable {
	private String title;
	private String artist;
	private String album;
	private String genre;
	private boolean like;
	private String audioFilePath;
	private String videoFilePath;
	
	
	public Song() {
		this.title = "Titel";
		this.artist = "Interpret";
		this.album = "Album";
		this.genre = "Genre";
		this.like = false;
		this.audioFilePath = "";
		this.videoFilePath = "";
	}
	
	public Song(String title, String artist, String album, String genre, boolean like, String audioFilePath, String videoFilePath) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.like = like;
		this.audioFilePath = audioFilePath;
		this.videoFilePath = videoFilePath;
	}

	
	public String getAudioFilePath() {
		return audioFilePath;
	}

	public void setAudioFilePath(String audioFilePath) {
		this.audioFilePath = audioFilePath;
	}

	public String getVideoFilePath() {
		return videoFilePath;
	}

	public void setVideoFilePath(String videoFilePath) {
		this.videoFilePath = videoFilePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

}
