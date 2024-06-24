package application.uiComponents;

import application.controller.SceneController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class KMenuButton extends Button {
	
	private String styleBase = "-fx-background-radius: 8;  -fx-padding: 6 12 6 12;"; // -fx-font-weight: bold;
	private String defaultStyle = "-fx-background-color: transparent; -fx-text-fill: #686868;" + styleBase;
	private String hoverStyle = "-fx-background-color: transparent; -fx-text-fill: #FFFFFF;" + styleBase;
	private String pressStyle = "-fx-background-color: #292929; -fx-text-fill: #FFFFFF;" + styleBase;

	private SceneController sceneController = SceneController.getInstance();
	private ImageView icon;
	private VBox graphic = new VBox();

	public KMenuButton(String text, Image icon, Node body) {
		this.icon = new ImageView(icon);
		graphic.getChildren().addAll(this.icon, new Rectangle(32,0));
		this.setText(text);
		this.setGraphic(graphic);
		this.setOnAction((event) -> sceneController.changeBody(body));

		applyStyle();
	}
	
	private void applyStyle() {
		
		this.setStyle(defaultStyle);
		this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
		this.setOnMouseExited(e -> this.setStyle(defaultStyle));
		this.setOnMousePressed(e -> this.setStyle(pressStyle));
		this.setOnMouseReleased(e -> this.setStyle(hoverStyle));
		
		icon.setFitWidth(24);
		icon.setPreserveRatio(true);
		this.setFont(new Font(20));
		// TODO: bold font
		//this.setStyle("-fx-font-weight: bold");
	}
	
}
