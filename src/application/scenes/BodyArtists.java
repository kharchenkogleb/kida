package application.scenes;

import application.Playlist;
import application.controller.MediaController;
import application.uiComponents.KHeadline;
import application.uiComponents.KPlaylistButton;
import javafx.geometry.Insets;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class BodyArtists extends VBox {
	
	private MediaController mediaController = MediaController.getInstance();
	
	private KHeadline headline = new KHeadline("Interpreten", "h1");
	private TilePane tiles = new TilePane();

	public BodyArtists() {
		mediaController.setArtistPlaylists();
		for(Playlist playlist : mediaController.getArtistPlaylists()) tiles.getChildren().add(new KPlaylistButton(playlist));
		this.getChildren().addAll(headline, tiles);
		
		applyStyle();
	}
	
	
	private void applyStyle() {
		this.setPadding(new Insets(31.25, 32, 0, 32));
		this.setSpacing(12);
		
		tiles.setHgap(16);
		tiles.setVgap(16);
	}
	
}
