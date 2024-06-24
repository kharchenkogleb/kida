module kida {
	
	requires java.desktop;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;
	
	opens application to javafx.base, javafx.controls, javafx.graphics;
	
}