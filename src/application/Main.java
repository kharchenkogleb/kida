package application;

import application.controller.TestController;
import application.stages.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{
	
	public void start(Stage stage) throws Exception {		
		MainStage mainStage = new MainStage();
		mainStage.show();
	}

	public static void main(String[] args) {
		TestController.generateExampleSongs();
		launch(args);
	}

}
	