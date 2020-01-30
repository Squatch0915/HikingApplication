package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class Alerts {
	
	
	
	public Alerts(String title, String message){

		
		Stage window  = new Stage();
		
		// This piece of code forces you to take care of current open window; 
		window.initModality(Modality.APPLICATION_MODAL); 
		window.setTitle(title);
		window.setMinWidth(100);
		window.setHeight(120); 
		
		Label label = new Label();
		label.setText(message);
		Button b = new Button("Close Window");
		b.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);  //Vertical box layout with seperation of 10 pixels
		layout.getChildren().addAll(label, b); // adds objects created to the layout
		layout.setAlignment(Pos.CENTER); 		//sets alignment of everything to CENTER
		
		Scene s = new Scene(layout); // Puts the layout into the scene

		window.setScene(s);
		window.showAndWait();  // Displays window and needs to be closed before returning to previous window. 
		
		
	}
	

	public static void nullFields(double milesHiked, int hours, int minutes) {

		 double time, speed;
		 double  temp = 0;
		 
			List<String> choices = new ArrayList<>();
		
			choices.add("1.0");
			choices.add("1.5");
			choices.add("2.0");
			choices.add("2.5");
			choices.add("3.0");
			choices.add("3.5");
			
			ChoiceDialog<String> dialog = new ChoiceDialog<>("1.0", choices);
			dialog.setTitle("Hiked Miles Per Hour");
			
			dialog.setHeaderText("			  IF TIME HIKED IS UNKNOWN"
					+ "\n Choose an average Hiked Miles Per Hour (HMPH) below.");
			dialog.setContentText("Choose your HMPH:");
			HikeData hikeData = new HikeData();
			Optional<String> result = dialog.showAndWait();

		      if (result.isPresent()){ // this means "ok" was clicked 

		            	dialog.close();
		            	System.out.println("still got here");
						
					    speed = Double.parseDouble(result.get()); // -- get selected input and convert to double
					    time =  milesHiked / speed; // -- time  = distance Hiked / average speed of hike 
					    System.out.println(time);
					    
					    // if there is atleast 1 hour, add it to the totalHoursHiked.  
					    // Add it to Table.hours for display purposes
					   
					    if(time >= 1) {
					    	
					    	 Table.totalHoursHiked = (int) (Table.totalHoursHiked + time);
							 System.out.println("I'm hours getting added: " + Table.totalHoursHiked);
							 Table.hours = (int)time;
							 
							 // this gets the remainder of time if needed from hours
							 // multiplication converts it to correct time
							 // add it to Table.Minutes for display and totalMinutes for Stats
							 
							  temp = time - Table.hours;

							  temp = temp * 60;

							 Table.minutes = (int)temp;
							 Table.totalMinutesHiked = Table.totalMinutesHiked + (int)temp;
				
					    }
					    
					    else {
					    time = time * 60; // -- this gives us the representation of minutes (Ex: if .25 is left over, it converts it to 15 )
					    
					    Table.totalMinutesHiked = (int) (Table.totalMinutesHiked + time); 
					   // System.out.println("I'm minutes getting added: " + Table.totalMinutesHiked);
					    Table.minutes = (int)time;
					    	}
					    
					Table.setTable(hikeData);	// THis is being called here to set data to the table, if not called here if would set data when the user clicks cancel which is logically wrong. 
					} // End of first IF
		      
		      else {
		    	  
		    	  //Below I am taking away total number of lifetime hikes because one has already been added because the user had selected "ADD".  Since their new decision is to cancel, then we need to deduct
		    	  // from all the views
		    	  StateDisplayTab.totalLifetimeHikes = StateDisplayTab.totalLifetimeHikes - Table.lifetimeHikes--;
		    	  StateDisplayTab.totalLifetimeMiles = StateDisplayTab.totalLifetimeMiles - Table.miles;
		    	  Table.totalMilesHiked =  Table.totalMilesHiked - Table.miles;
		    	  
		      }

			}// end of nullcheck

}