package application.scenes;

import application.controller.MediaController;
import application.controller.PlayerController;
import application.controller.SceneController;
import application.stages.MainStage;
import application.uiComponents.KBackButton;
import application.uiComponents.KButton;
import application.uiComponents.KHeadline;
import application.uiComponents.KProgressSlider;
import application.uiComponents.KSongTable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BodyAdmin extends VBox {
	
	private PlayerController playerController = PlayerController.getInstance();
	private SceneController sceneController = SceneController.getInstance();
	private MediaController mediaController = MediaController.getInstance();

	private HBox header = new HBox();
	private KHeadline headline = new KHeadline("Alle Songs verwalten", "h2");
	
	private Label editLabel = new Label("Bearbeiten");
	//private KButton editButton = new KButton(new Image("/toggle_off.png"));
	private KProgressSlider editSlider = new KProgressSlider();
	
	private KButton importButton = new KButton("Importieren");
	private KBackButton backButton = new KBackButton();
	
		
	private KSongTable table = new KSongTable(mediaController.getAllSongs());
	
	
	public BodyAdmin() {

		editSlider.setMaxWidth(32);
		editSlider.setMinWidth(32);
		editSlider.setMax(1);
		editSlider.setBlockIncrement(1);
		editSlider.setMajorTickUnit(1);
		editSlider.setMinorTickCount(0);
		editSlider.setSnapToTicks(true);
				
		editSlider.valueProperty().addListener((obv, ov, nv) -> {
			if(editSlider.getValue() == 1) {
				table.setEditable(true);
			}
			else {
				table.setEditable(false);
			}
		});
		
				
		importButton.setOnAction((event) -> sceneController.openImportStage(sceneController.openFileChooser(event)));
				
		header.getChildren().addAll(backButton, headline, editLabel, editSlider, new Rectangle(12,0), importButton);
		this.getChildren().addAll(header, table);
		
		applyStyle();
	}
	
	private void applyStyle() {
		this.setPadding(new Insets(31.25, 32, 0, 32));
		this.setSpacing(12);
		
		header.setSpacing(12);
		header.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(headline, Priority.ALWAYS);
		headline.setMaxWidth(Double.MAX_VALUE);

	}
	
}
