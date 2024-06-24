package application.scenes;

import application.Playlist;
import application.controller.MediaController;
import application.uiComponents.KHeadline;
import application.uiComponents.KPlaylistButton;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class BodyAlbums extends VBox {
	
	private MediaController mediaController = MediaController.getInstance();
	
	private KHeadline headline = new KHeadline("Alben", "h1");
	private TilePane tiles = new TilePane();

	public BodyAlbums() {
		
		initTiles();
		
		mediaController.getAlbumPlaylists().addListener(new ListChangeListener<Playlist>() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("change!");
                initTiles();
            }
        });
		
		this.getChildren().addAll(headline, tiles);
		
		
		applyStyle();
	}
	
	
	private void applyStyle() {
		this.setPadding(new Insets(31.25, 32, 0, 32));
		this.setSpacing(12);
		
		tiles.setHgap(16);
		tiles.setVgap(16);
	}
	
	
	private void initTiles() {
		mediaController.setAlbumPlaylists();
		for(Playlist playlist : mediaController.getAlbumPlaylists()) tiles.getChildren().add(new KPlaylistButton(playlist));
	}
	
}
