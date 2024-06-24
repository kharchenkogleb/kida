package application.scenes;

import application.controller.MediaController;
import application.uiComponents.KTextField;
import application.uiComponents.KChoiceBox;
import application.uiComponents.KHeadline;
import application.uiComponents.KSongTable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class BodySearch extends VBox {
	
	private MediaController mediaController = MediaController.getInstance();
	
	private KHeadline headline = new KHeadline("Suche", "h1");
	private HBox searchArea = new HBox();
	private KTextField searchField = new KTextField();
	private KSongTable table = new KSongTable(mediaController.getAllSongs());
	private KChoiceBox choiceBox = new KChoiceBox();
	
	public BodySearch() {
		
		searchField.setPromptText("Gib einen Suchbegriff ein");
		
		choiceBox.setValue("Titel");
		choiceBox.getItems().addAll("Titel", "Interpret", "Album", "Genre");
		
		searchArea.getChildren().addAll(searchField, choiceBox);
		this.getChildren().addAll(headline, searchArea, table);
			
		searchField.textProperty().addListener((observableText, oldText, newText) -> {
			table.setItems(mediaController.search(newText,choiceBox.getValue()));
		});
		
		
		applyStyle();
	}
	
	private void applyStyle() {
		this.setPadding(new Insets(31.25, 32, 0, 32));
		this.setSpacing(12);
		searchArea.setSpacing(12);
		HBox.setHgrow(searchField, Priority.ALWAYS);
	}

}
