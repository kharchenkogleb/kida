package application.uiComponents;

import java.util.stream.Collectors;

import application.Playlist;
import application.Song;
import application.controller.MediaController;
import application.controller.PlayerController;
import application.controller.SceneController;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;

public class KSongTable extends TableView<Song> {
	
	private MediaController mediaController = MediaController.getInstance();
	private SceneController sceneController = SceneController.getInstance();
	private PlayerController playerController = PlayerController.getInstance();
	
    
    TableColumn<Song, String> titleColumn = new TableColumn<>("Titel");
	TableColumn<Song, String> artistColumn = new TableColumn<>("Interpret");
	TableColumn<Song, String> albumColumn = new TableColumn<>("Album");
	TableColumn<Song, String> genreColumn = new TableColumn<>("Genre");

	@SuppressWarnings("unchecked")
	public KSongTable(Playlist tablePlaylist) {
		
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		titleColumn.setOnEditCommit(event -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setTitle(event.getNewValue());
			mediaController.saveAllToFile();
		});
		artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
		artistColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		artistColumn.setOnEditCommit(event -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setArtist(event.getNewValue());
			mediaController.saveAllToFile();
		});
		albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
		albumColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		albumColumn.setOnEditCommit(event -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setAlbum(event.getNewValue());
			mediaController.saveAllToFile();
		});
		genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
		genreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		genreColumn.setOnEditCommit(event -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setGenre(event.getNewValue());
			mediaController.saveAllToFile();
		});
		
		this.setItems(tablePlaylist.getSongs());
		
		this.getColumns().addAll(titleColumn, artistColumn, albumColumn, genreColumn);
						
		this.setRowFactory(new Callback<TableView<Song>, TableRow<Song>>() {
	        @Override
	        public TableRow<Song> call(TableView<Song> tableView) {
	            final TableRow<Song> row = new TableRow<>();
	            
	            ContextMenu menu = new ContextMenu();
	            Menu deleteMenu = new Menu("Entfernen");
	            Menu addMenu = new Menu("Zu Playlist hinzufuegen");
	            
	            MenuItem allSongsItem = new MenuItem ("Aus Bibliothek entfernen");
	            deleteMenu.getItems().add(allSongsItem);
	           
	            
	            
	            for (Playlist playlist: mediaController.getAllPlaylists()) {
	            	MenuItem deleteP = new MenuItem(playlist.getName());
	            	
	            	deleteP.setOnAction(event -> {
		            	if(playlist.getSongs().contains(getSelectionModel().getSelectedItem())) {
		            		mediaController.removeSong(getSelectionModel().getSelectedItem(), playlist);
		            	} else {
		            		
		            	}
	            	});
		            	deleteMenu.getItems().add(deleteP);
	            }	            
	            allSongsItem.setOnAction(event -> {
	            	for(Playlist playlist : mediaController.getAllPlaylists()) {
	            		while(playlist.getSongs().contains(getSelectionModel().getSelectedItem())) {
	            			playlist.getSongs().remove(getSelectionModel().getSelectedItem());
	            		}
	            	}
	            	mediaController.deleteFromAllSongs(getSelectionModel().getSelectedItem());
	            });
	            
	            for (Playlist playlist: mediaController.getAllPlaylists()) {
	            	MenuItem addP = new MenuItem(playlist.getName()); 
	            	
	            	addP.setOnAction(event -> {
	            		if (!playlist.getSongs().contains(getSelectionModel().getSelectedItem())) {
		            		mediaController.addSong(getSelectionModel().getSelectedItem(), playlist);
	            		}
	            	});
	            	addMenu.getItems().add(addP);
	            }
	            	            	            
	            menu.getItems().addAll(deleteMenu, addMenu);
	            	            
	            row.setOnMouseClicked(event -> {
	            	BottomBar bottomBar = BottomBar.getInstance();
	            	ControlElements controlElements = ControlElements.getInstance();
					if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
						playerController.setCurrentPlaylist(tablePlaylist);
						if (playerController.getAudioPlayer() != null) {
							if (playerController.getAudioPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
								playerController.getAudioPlayer().stop();
							}
						}
						playerController.updateCurrentSong(getSelectionModel().getSelectedItem());
						playerController.play();
						playerController.checkLike(bottomBar.getStarButton());
						playerController.onPlaylistPlay(controlElements.getPlayButton(), controlElements.getPauseButton(), controlElements.getButtonArea());
						System.out.println(getSelectionModel().getSelectedItem().getAudioFilePath());
					}
	            }); 
	            
	    		ImageView nextGraphic = new ImageView(new Image("/next.png"));
	    		nextGraphic.setFitWidth(24);
	    		nextGraphic.setPreserveRatio(true);
	    		row.setGraphic(nextGraphic);
	            
	            row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(menu));
	            return row;
	        }
		});
		
		
		applyStyle();
	}
	
	
	private void applyStyle() {
		VBox.setVgrow(this, Priority.ALWAYS);
		this.setMaxHeight(Double.MAX_VALUE);

		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setStyle("-fx-base: transparent; -fx-background-color: transparent; -fx-selection-bar: #686868; -fx-selection-bar-non-focused: #686868;");
		
		// TODO: Table-Header rund
//		this.lookup(".column-header-background").setStyle("-fx-background-radius: 5; -fx-border-radius: 10;");
//		System.out.println(this.lookup(".column-header-background"));
		
		titleColumn.setStyle("-fx-text-fill: white;");
		artistColumn.setStyle("-fx-text-fill: white;");
		albumColumn.setStyle("-fx-text-fill: white;");
		genreColumn.setStyle("-fx-text-fill: white;");
	
	}	
	
	
	private boolean checkIfAllSongs() {
		return getItems().equals(mediaController.getAllSongs().getSongs());
	}
	
}
