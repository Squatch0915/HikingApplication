package application;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class Alerts {

	public Alerts(String title, String message) {

		Stage window = new Stage();

		// This piece of code forces you to take care of current open window;
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(100);
		window.setHeight(120);

		Label label = new Label();
		label.setText(message);
		Button b = new Button("Close Window");
		b.setOnAction(e -> window.close());

		VBox layout = new VBox(10); // Vertical box layout with seperation of 10 pixels
		layout.getChildren().addAll(label, b); // adds objects created to the layout
		layout.setAlignment(Pos.CENTER); // sets alignment of everything to CENTER

		Scene s = new Scene(layout); // Puts the layout into the scene

		window.setScene(s);
		window.showAndWait(); // Displays window and needs to be closed before returning to previous window.

	}

}