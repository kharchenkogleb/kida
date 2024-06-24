package application.uiComponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

public class KProgressSlider extends Slider {

	public KProgressSlider() {
		this.setId("progressSlider");
		applyStyle();
	}

	private void applyStyle() {
		// this.setStyle("-fx-inner-background: rgb(0, 217, 217);");
		this.valueProperty().addListener((observableValue, oldValue, newValue) -> {
			StackPane trackPane = (StackPane) lookup(".track");
			String style = String.format(
					"-fx-background-color: linear-gradient(to right, rgb(255, 138, 0) %d%%, rgb(217, 217, 217) %d%%); -fx-background-insets: 0 0 -1 0, 0, 1;",
					newValue.intValue(), newValue.intValue());
			if (trackPane != null) trackPane.setStyle(style);
		});
	}

}
