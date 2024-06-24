package application.scenes;

import application.uiComponents.KHeadline;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class BodyExplore extends VBox {
	
	private KHeadline headline = new KHeadline("Entdecken", "h1");

	public BodyExplore() {
		this.getChildren().addAll(headline);
		
		
		applyStyle();
	}
	
	
	private void applyStyle() {
		this.setPadding(new Insets(31.25, 32, 0, 32));
		this.setSpacing(12);
	}
	
}
