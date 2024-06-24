package application.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import application.scenes.Scaffold;
import application.stages.ImportStage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SceneController {
	
	private static SceneController sceneController = new SceneController();	
	
	private Stage importStage;
	
	public static SceneController getInstance() {
		return sceneController;
	}
	
	private SceneController() {
		//System.out.println(previousBody);
	}
	
	public void changeBody(Node body) {
		Scaffold scaffold = Scaffold.getInstance();
		body.setVisible(true);
		scaffold.setBody(body);
	}

	public List<File> openFileChooser(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		chooser.setTitle("Wähle Songs zum Importieren aus");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("kida-Dateien", "*.mp3"));
		chooser.setInitialDirectory(new File("./examplesSongs")); // "C:/Users/" + System.getProperty("user.name") + "/Music"
		List<File> files = chooser.showOpenMultipleDialog(stage);
		return files;
	}
	
	public void openImportStage(List<File> files) {
		if (files == null) return;
		importStage = new ImportStage(files);	
		importStage.show();
	}

}
