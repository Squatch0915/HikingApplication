package application;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelperFunctions{
	
    final String decimalPattern = "\\d{0,3}([\\.]\\d{0,2})?";
	final String integerPattern = "\\d*";
	
	
	/*The IF checks for null input from user. If null, a dialog appears, asking for estimated hiking speed.
	That info is sent through algorithm to be displayed, added to STATS and then convert() is called to tally input 
	else the input is displayed and tallied.
	ELSE just set the table as usual */
	public void hoursMinutesNullCheck(int hours, int minutes, double miles,
			HikeData hike, TextField locationInput, TextField trailNameInput, TextField milesInput, TextField hoursInput, TextField minutesInput, int lifetimeHikes) {
		Object k = null;
		if(hours  <= 0 && minutes <= 0) {
	         
	       	 Alerts.nullFields( miles, hours, minutes);
	        		}
	        
	        else {
	        	
	         Table.setTable(hike);
	        }
		
	     locationInput.clear();
	     trailNameInput.clear();
	     milesInput.clear();
	     hoursInput.clear();
	     minutesInput.clear();
	     lifetimeHikes = 0;
		
	}
	
	public void createStatePath(String state) {
		
		   File path = new File("C:\\Users\\" + System.getProperty("user.name") +"\\JustHike\\JustHike\\temp\\" + state + "\\" );
	        //if the path to the state table doesnt exists, save data, else load previous state data, that save it. 
	        if(!path.exists() && !path.isFile()) {

		       	 path.mkdirs();

	        }
	}
	
    // Method checks for regex patterns for the input fields, will not allow any input that does not meet the pattern.
	 public void inputCheck(TextField tf, String pattern) {

		tf.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches(pattern)) {
			            tf.setText(oldValue);
			        }
			    }

			}); 
	 }	

	 // if user selects a different state while still using the same instance of Table, this will clear the values. 
	 public void refreshValuesForState(String temp, String state, double totalMilesHiked, int totalHoursHiked, int totalMinutesHiked) {
		 
		 if(!temp.equals(state)){
			 
      	   totalMilesHiked = 0;
             totalHoursHiked = 0;
             totalMinutesHiked = 0;
      }
	 }


	 public Text textFunction(String text, int fontSize, boolean underline) {
		 Text t = new Text(text);
		 t.setFill(Color.WHITE);
		 t.setFont(Font.font(fontSize));
		 t.setUnderline(underline);

		 return t;
	 }
	 
	 // Method that creates tooltips	
	 public void setToolTip(TextField tf, Tooltip t, String message){
		
		t = new Tooltip();
		t.setText(message);
		tf.setTooltip(t);
		
	}

}
