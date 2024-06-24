package application.uiComponents;

import javafx.scene.control.ChoiceBox;

public class KChoiceBox extends ChoiceBox<String> {
	

	public KChoiceBox() {
		applyStyle();
	}
	
	
	private void applyStyle() {
		this.setStyle("-fx-background-color: transparent; -fx-border-width: 2; -fx-border-radius: 8; -fx-border-color: #686868; -fx-padding: 2 4 2 4; -fx-mark-color: rgb(255, 138, 0);");
		//this.setStyle("-fx-background-color: rgb(19, 19, 19); -fx-background-radius: 8; -fx-padding: 3 4 3 4; -fx-mark-color: rgb(255, 138, 0);");
	}
	
}
