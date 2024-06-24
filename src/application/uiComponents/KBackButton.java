package application.uiComponents;

import application.controller.SceneController;
import application.scenes.BodyHome;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KBackButton extends Button {
	
	private SceneController sceneController = SceneController.getInstance();
	
	private String styleBase = "-fx-background-radius: 8;  -fx-padding: 4;"; // -fx-font-weight: bold;
	private String defaultStyle = "-fx-background-color: transparent;" + styleBase;
	private String pressStyle = "-fx-background-color: #181818;" + styleBase;

	
	public KBackButton() {
		this.setOnAction(event -> sceneController.changeBody(new BodyHome()));
		
		applyStyle();
	}
	
	public KBackButton(Node body) {
		this.setOnAction(event -> sceneController.changeBody(body));
		
		applyStyle();
	}
	

	private void applyStyle() {
		ImageView backGraphic = new ImageView(new Image("/back.png"));
		backGraphic.setFitWidth(10);
		backGraphic.setPreserveRatio(true);
		this.setGraphic(backGraphic);
		this.setStyle(defaultStyle);
		this.setOnMousePressed(e -> this.setStyle(pressStyle));
		this.setOnMouseReleased(e -> this.setStyle(defaultStyle));

	}
}
