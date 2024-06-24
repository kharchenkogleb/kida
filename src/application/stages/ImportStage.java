package application.stages;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;

import application.Song;
import application.controller.MediaController;
import application.uiComponents.KButton;
import application.uiComponents.KTextField;
import application.uiComponents.KHeadline;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ImportStage extends Stage {

	private MediaController mediaController = MediaController.getInstance();

	private BorderPane primaryPane = new BorderPane();
	private BorderPane secondaryPane = new BorderPane();

	private TableView<File> fileTable = new TableView<>();
	private TableColumn<File, String> column;

	private VBox textFieldArea = new VBox();
	private StackPane buttonArea = new StackPane();

	private KHeadline headline = new KHeadline("", "h3");
	private KTextField titleField = new KTextField();
	private KTextField artistField = new KTextField();
	private KTextField albumField = new KTextField();
	private KTextField genreField = new KTextField();
	private KButton saveEntryButton = new KButton("Speichern");
	private KButton closeButton = new KButton("Beenden");

	
	private String selectedFilePath = "";	
	

	public ImportStage(List<File> files) {
		
		primaryPane.getStylesheets().add("application.css");

		column = new TableColumn<>("Dateien");
		column.setCellValueFactory(new PropertyValueFactory<>("path"));

		fileTable.setItems(FXCollections.observableArrayList(files));
		fileTable.getColumns().add(column);

		fileTable.getSelectionModel().selectedItemProperty().addListener((observableFile, oldFile, newFile) -> {
			if (fileTable.getSelectionModel().getSelectedItem() != null) updateHeadline(newFile);
			else toggleElements(false);
		});
		
		fileTable.getSelectionModel().setCellSelectionEnabled(true);
		fileTable.getSelectionModel().select(0, fileTable.getColumns().get(0));
		//fileTable.getSelectionModel().clearSelection();
		//toggleElements(false);

		saveEntryButton.setOnAction(event -> {
			if(titleField.getText().isEmpty()) {
				Alert titleAlert = new Alert(AlertType.ERROR);
				titleAlert.initOwner(((Node) event.getSource()).getScene().getWindow());
				titleAlert.setTitle("Fehlende Daten");
				titleAlert.setHeaderText(null);
				titleAlert.setContentText("Bitte gib einen Titel ein, damit du dein Lied wiederfindest.");
				titleAlert.showAndWait();
			} else {
				mediaController.addToAllSongsAndSave(new Song(
						titleField.getText(), 
						artistField.getText().isEmpty() ? "Unbekannter Interpret" : artistField.getText(), 
						albumField.getText().isEmpty() ? "Unbekanntes Album" : albumField.getText(), 
						genreField.getText().isEmpty() ? "Unbekanntes Genre" : genreField.getText(), 
						false, selectedFilePath, "video"));
				resetTextFields();
				fileTable.getItems().remove(fileTable.getSelectionModel().getSelectedItem());
				fileTable.getSelectionModel().select(0, fileTable.getColumns().get(0));	
			}
		});
		
		closeButton.setOnAction(event -> this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST)));

		initTextFields();
		textFieldArea.getChildren().addAll(titleField, new Rectangle(0, 12), artistField, new Rectangle(0, 12), albumField, new Rectangle(0, 12), genreField);
		buttonArea.getChildren().addAll(closeButton, saveEntryButton);

		secondaryPane.setTop(headline);
		secondaryPane.setCenter(textFieldArea);
		secondaryPane.setBottom(buttonArea);

		primaryPane.setCenter(fileTable);
		primaryPane.setRight(secondaryPane);


		this.setScene(new Scene(primaryPane));
		this.show();
		

		applyStyle();
	}

	private void applyStyle() {
		this.getIcons().add(new Image("/kida_icon.png"));
		this.setTitle("Füge Songdaten hinzu");
		this.setMinWidth(700);
		this.setMinHeight(450);

		saveEntryButton.setBackgroundColor("#686868");
		closeButton.setBackgroundColor("#686868");

		secondaryPane.setMinWidth(300);
		secondaryPane.setMaxWidth(300);

		textFieldArea.setAlignment(Pos.CENTER);
		secondaryPane.setBackground(Background.fill(Color.rgb(19, 19, 19)));
		buttonArea.setAlignment(Pos.CENTER);
		secondaryPane.setPadding(new Insets(32));

		column.setStyle("-fx-text-fill: white;");
		fileTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		fileTable.setStyle("-fx-base: transparent; -fx-background-color: #292929; -fx-selection-bar: #686868; -fx-selection-bar-non-focused: #686868;");
	}
	
	private void initTextFields() {
		titleField.setPromptText("Title");
		artistField.setPromptText("Interpret");
		albumField.setPromptText("Album");
		genreField.setPromptText("Genre");
	}
	
	private void resetTextFields() {
		titleField.clear();
		artistField.clear();
		albumField.clear();
		genreField.clear();
		titleField.requestFocus();
	}

	private void updateHeadline(File newFile) {
		try {
			selectedFilePath = Paths.get("").toAbsolutePath().relativize(Paths.get(newFile.getPath())).toUri().toURL().toExternalForm();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println(selectedFilePath);
		String[] splittedPath = selectedFilePath.split("/");
		String formatted = splittedPath[splittedPath.length - 1].replace(".mp3", "").replace("%20", " ").replace("-", " ").replace("_", " ");
		headline.setText(formatted.length() > 24 ? formatted.substring(0, 24) + " ..." : formatted);
		toggleElements(true);
	}

	private void toggleElements(boolean visible) {
		if (!visible) headline.setText("Fertig!");
		titleField.setVisible(visible);
		artistField.setVisible(visible);
		albumField.setVisible(visible);
		genreField.setVisible(visible);
		saveEntryButton.setVisible(visible);
		closeButton.setVisible(!visible);
	}

}
