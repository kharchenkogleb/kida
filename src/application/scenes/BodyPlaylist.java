package application.scenes;

import application.Playlist;
import application.controller.MediaController;
import application.uiComponents.KTextField;
import application.uiComponents.KBackButton;
import application.uiComponents.KSongTable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BodyPlaylist extends VBox {
	
	private MediaController mediaController = MediaController.getInstance();
	
	private HBox header = new HBox();
	private KBackButton backButton = new KBackButton();
	private KTextField nameField = new KTextField();
	private KSongTable table;
	
	public BodyPlaylist(Playlist playlist) {
		
		table = new KSongTable(playlist);
		
		nameField.setText(playlist.getName());
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	if (nameField.getText().contains("Neue Playlist")) {
		    		nameField.requestFocus();
		    		nameField.selectAll();
		    	}
		    	if (mediaController.getArtistPlaylists().contains(playlist) ||
		    		mediaController.getAlbumPlaylists().contains(playlist) ||
		    		mediaController.getGenrePlaylists().contains(playlist)) {
			    	nameField.setEditable(false);
		    	}
		    }
		});
				
		header.getChildren().addAll(backButton, nameField);
		this.getChildren().addAll(header, table);
			
		nameField.textProperty().addListener((observableText, oldText, newText) -> {
			playlist.setName(newText);
			mediaController.saveAllToFile();
			int maxChars = 16;
			if (nameField.getText().length() > maxChars) nameField.setText(nameField.getText().substring(0, maxChars));
		});
		
		
		applyStyle();
	}
	
	private void applyStyle() {
		this.setPadding(new Insets(31.25, 32, 0, 32));
		this.setSpacing(12);
				
		header.setSpacing(12);
		header.setAlignment(Pos.CENTER_LEFT);
		
		nameField.setPromptText("Playlist-Namen eingeben");
		nameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 36; -fx-padding: 0;");
		HBox.setHgrow(nameField, Priority.ALWAYS);
	}

}
