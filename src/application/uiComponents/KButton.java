package application.uiComponents;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class KButton extends Button {

	private String styleBase = "-fx-background-radius: 8; -fx-text-fill: rgb(255, 255, 255);  -fx-padding: 6 12 6 12;";
	private String defaultStyle = "-fx-background-color: #131313;" + styleBase;
	private String hoverStyle = "-fx-background-color: #FF8A00;" + styleBase;
	private String pressStyle = "-fx-background-color: #C35200;" + styleBase;

	public void setBackgroundColor(String backgroundColor) {
		defaultStyle = "-fx-background-color: " + backgroundColor + ";" + styleBase;
		this.applyStyle();
	}

	public KButton() {		
		
		applyStyle();
	}
	
	public KButton(Image graphicImage) {
		this.setGraphic(new ImageView(graphicImage));

		applyStyle();
	}
	
	public KButton(String text) {
		this.setText(text);
		
		applyStyle();
	}
	
	private void applyStyle() {
//		this.setFont(new Font(18));

		this.setStyle(defaultStyle);
		this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
		this.setOnMouseExited(e -> this.setStyle(defaultStyle));
		this.setOnMousePressed(e -> this.setStyle(pressStyle));
		this.setOnMouseReleased(e -> this.setStyle(hoverStyle));

		
		// TODO: bold font
		//this.setStyle("-fx-font-weight: bold");
	}
	
}
