package application.scenes;


import java.util.ArrayList;
import java.util.List;

import application.Playlist;
import application.controller.MediaController;
import application.controller.PlayerController;
import application.controller.SceneController;
import application.uiComponents.KButton;
import application.uiComponents.KHeadline;
import application.uiComponents.KPlaylistButton;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BodyHome extends ScrollPane {
	
	private MediaController mediaController = MediaController.getInstance();
	private PlayerController playerController = PlayerController.getInstance();
	private SceneController sceneController = SceneController.getInstance();
	
	private VBox primaryPane = new VBox();
	
	private KHeadline headline = new KHeadline("Home", "h1");
	private KButton adminButton = new KButton("Alle Songs verwalten");
	private KHeadline myPlaylistsHeadline = new KHeadline("Meine Playlists", "h3");
	
	private TilePane tiles = new TilePane();
	private KPlaylistButton addButton = new KPlaylistButton(new Image("/add.png"));
	private KPlaylistButton favoritesButton = new KPlaylistButton(new Image("/like_outline.png"));
	

	public BodyHome() {		
		adminButton.setOnAction((event) -> sceneController.changeBody(new BodyAdmin()));
		initTiles();
				
//		this.visibleProperty().addListener((obv, ov, nv) -> {
//			initTiles();
//			System.out.println("Hi");
//		});
		
		mediaController.getAllPlaylists().addListener(new ListChangeListener<Playlist>() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("change!");
                initTiles();
            }
        });
		
		addButton.getButton().setOnAction(event -> {
			String newPlaylistName = "Neue Playlist " + (mediaController.getAllPlaylists().size() + 1);
			mediaController.createPlaylist(newPlaylistName);
			initTiles();
//			setVisible(false);
			sceneController.changeBody(new BodyPlaylist(mediaController.getPlaylistByName(newPlaylistName)));
		});
		
		favoritesButton.getButton().setOnAction(event -> sceneController.changeBody(new BodyPlaylist(mediaController.getFavorites())));
				
		primaryPane.getChildren().addAll(headline, adminButton, new Rectangle(0, 6), myPlaylistsHeadline, tiles);
		
		this.setContent(primaryPane);
		
		
		applyStyle();
	}
	
	private void applyStyle() {

		this.setStyle("-fx-background-color: rgb(29, 29, 29);");
		
		this.setFitToWidth(true);
		primaryPane.setPadding(new Insets(31.25, 32, 0, 32));
		primaryPane.setSpacing(12);
		
		addButton.getButton().setBackgroundColor("#686868");
		
		tiles.setHgap(16);
		tiles.setVgap(16);
		

		HBox.setHgrow(adminButton, Priority.ALWAYS);
		adminButton.setMaxWidth(Double.MAX_VALUE);

	}
	
	private void initTiles() {
		tiles.getChildren().clear();
		tiles.getChildren().addAll(addButton, favoritesButton);
		for (Playlist playlist : mediaController.getAllPlaylists()) tiles.getChildren().add(new KPlaylistButton(playlist));
	}
	
}
