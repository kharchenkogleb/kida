package application.uiComponents;

import java.util.regex.Pattern;

import application.Playlist;
import application.controller.SceneController;
import application.scenes.BodyPlaylist;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class KPlaylistButton extends VBox {

	private SceneController sceneController = SceneController.getInstance();
	
	private KButton button = new KButton();
	private Label nameLabel = new Label();
	
	public KButton getButton() {
		return button;
	}
	
	public KPlaylistButton(Playlist playlist) {
		button.setText(firstValidChar(playlist.getName()));
		nameLabel.setText(playlist.getName());
		this.getChildren().addAll(button, nameLabel);
		button.setOnAction(event -> sceneController.changeBody(new BodyPlaylist(playlist)));
		applyStyle();
	}
	
	public KPlaylistButton(Image graphicImage) {
		button.setGraphic(new ImageView(graphicImage));
		this.getChildren().addAll(button, nameLabel);

		applyStyle();
	}

	
	private void applyStyle() {
		//nameLabel.setFont(new Font(18));

		button.setMinHeight(96);
		button.setMinWidth(96);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setMaxWidth(Double.MAX_VALUE);
		
		button.setFont(new Font(36));
	}
	
	private String firstValidChar(String input) {
		String output = "";
		for (int i = 0; i < input.length() - 1; i++) {
			if (Pattern.matches("[a-zA-Z]+", input.substring(i, i + 1))) output += input.substring(i, i + 1);
		}
		return output.length() == 0 ? "O" : output.toUpperCase().substring(0,1);
	}
	
}
