package application.uiComponents;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class KHeadline extends Label {
	
	public KHeadline() {
		this.setFont(new Font(48));
		applyStyle();
	}
	
	public KHeadline(String text) {
		this.setText(text);
		this.setFont(new Font(48));
		applyStyle();
	}
	
	public KHeadline(String text, String type) {
		this.setText(text);
		applyStyle();

		switch (type) {
		case "h1":
			this.setFont(new Font(48));
			break;
		case "h2":
			this.setFont(new Font(36));
			break;
		case "h3":
			this.setFont(new Font(24));
			break;
		default:
			this.setFont(new Font(48));
			break;
		}
	}
	
	
	private void applyStyle() {
		this.setStyle("-fx-font-weight: bold");
		this.setWrapText(true);
		this.setTextFill(Color.web("#FFFFFF"));
	}
	
}
