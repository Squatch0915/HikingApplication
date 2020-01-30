package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Options extends ComboBox<String>{

	public Options() {
		
		setPromptText("Options");
		getItems().addAll(
				"Options",
				"Create CSV",
				"Add Photo's", 
				"Get Stats");

		setEditable(false);
		

		setOnAction( e ->{

			getChoice(getValue());
		});


	}

	public void getChoice(String choice) {

		if(choice == "Get Stats") {


			try {
				new IO().loadLifetimeStats();
				Stage stateWindow = new Stage();
				stateWindow.initModality(Modality.APPLICATION_MODAL); 
				new Stats().start(stateWindow);


			} catch (Exception e1) {
				e1.printStackTrace();
			}

			Platform.runLater(() -> getSelectionModel().select(0));


		}	

		else if(choice == "Create CSV") {

			try {
				System.out.println(Table.comboSelection()); 

				new IO().saveCSV(Table.date,  Table.comboSelection(), Table.location, Table.trailName, Table.miles, 
						Table.hours, Table.minutes, Table.subentries);
			} catch (IOException e1) {

				e1.printStackTrace();
			}	
			
			Platform.runLater(() -> getSelectionModel().select(0));



		}//End of Stats

		else if(choice == "Add Photo's") {

			new ImageExplorer();

			Platform.runLater(() -> getSelectionModel().select(0));


		}//End Add Photos
	}

}
