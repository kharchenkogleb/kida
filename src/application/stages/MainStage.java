package application.stages;

import application.controller.PlayerController;
import application.scenes.Scaffold;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainStage extends Stage {
		

	Scaffold scaffold = Scaffold.getInstance();
	
	public MainStage() {
		
		PlayerController playerController = PlayerController.getInstance();
		
		playerController.initCurrent();

		this.getIcons().add(new Image("/kida_icon.png"));
		this.setTitle("kida");
		this.setWidth(1100);
		this.setHeight(720);
		this.setMinWidth(920);
		this.setMinHeight(640);
		
		this.setScene(new Scene(scaffold));		
	}
	
}
