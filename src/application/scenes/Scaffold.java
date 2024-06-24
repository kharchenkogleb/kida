package application.scenes;

import application.uiComponents.BottomBar;
import application.uiComponents.Sidemenu;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Scaffold extends BorderPane {
	
	private static final Scaffold scaffold = new Scaffold();
	public static Scaffold getInstance() { 
		return scaffold; 
	}
	
	private BorderPane secondaryPane = new BorderPane();
	private BottomBar bottomBar = BottomBar.getInstance();
	private Sidemenu sidemenu = Sidemenu.getInstance();
	
	public Node getBody() {
		return secondaryPane.getCenter();
	}
	
	public void setBody(Node body) {
		secondaryPane.setCenter(body);
	}

	private Scaffold() {
		this.getStylesheets().add("application.css");
		
		secondaryPane.setBottom(bottomBar);
		secondaryPane.setCenter(new BodyHome());
		
		this.setLeft(sidemenu);
		this.setCenter(secondaryPane);
		
		applyStyle();
	}
	
	private void applyStyle() {
		// TODO: Override with common JavaFX Code
		this.setStyle("-fx-base: white; -fx-background: rgb(41, 41, 41); -fx-focus-color: transparent;");
		//this.setBackground(Background.fill(Color.rgb(41, 41, 41)));
	}

}