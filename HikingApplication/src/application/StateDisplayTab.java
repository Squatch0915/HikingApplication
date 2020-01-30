
    
package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import application.Stats;


public class StateDisplayTab extends Application{
	
	private Stage window;
    Button mainButton;
	static Scene scene1;
	private HBox textPanel;
	private VBox master;
	private Text calculatedHikes, calculatedHours,calculatedMinutes,calculatedMiles, calculatedDays, calculatedMonths, calculatedYears;
	private Text  hikeText, milesText, yearText, monthText, dayText, hourText, minuteText;
	static int totalLifetimeHikes, totalLifetimeMinutes, totalLifetimeHours, totalLifetimeDays, totalLifetimeMonths, totalLifetimeYears;
	static double totalLifetimeMiles;
	private Separator separator;
	TabPane tabPane = new TabPane();
	Tab tab = new Tab();
	IO io = new IO();
	GridPane grid;


	
	public void start(Stage stage) throws Exception{

		
		window = stage;

		Text text = new Text();
		StackPane pane = new StackPane();
		text.setText(Stats.getState());
		text.setFont(Font.font("ENGRAVERS MT", 50));
		text.setFill(Color.WHITE);
		
		mainButton = new Button("Main");

		separator = new Separator();
		separator.setMaxWidth(2000);
		separator.setPadding(new Insets(10,10,290,10));
		//insets:: top, right, bottom, left
		
		textPanel = new HBox();
		textPanel.setAlignment(Pos.TOP_CENTER);
		textPanel.getChildren().addAll(text);
		textPanel.setPadding(new Insets(20,10,10,10));
		
		//Adding image as background for state selection screen
		Image img = new Image(ResourceLoader.load(Stats.getState() +".jpg"));

		ImageView v = new ImageView();
		v.setImage(img);
		v.setFitWidth(900);
		v.setFitHeight(500);
		
//-- MID HBOX AREA
		
		calculatedHikes = new Text("00");
		calculatedHikes.setFont(Font.font(20));
		calculatedHikes.setFill(Color.WHITE);
		
		calculatedMiles = new Text("00");
		calculatedMiles.setFont(Font.font(20));
		calculatedMiles.setFill(Color.WHITE);
		
		calculatedYears = new Text("00");
		calculatedYears.setFont(Font.font(20));
		calculatedYears.setFill(Color.WHITE);
		
		calculatedMonths = new Text("00");
		calculatedMonths.setFont(Font.font(20));
		calculatedMonths.setFill(Color.WHITE);
		
		calculatedDays = new Text("00");
		calculatedDays.setFont(Font.font(20));
		calculatedDays.setFill(Color.WHITE);
		
		calculatedHours = new Text("00");
		calculatedHours.setFont(Font.font(20));
		calculatedHours.setFill(Color.WHITE);

		calculatedMinutes = new Text("00");
		calculatedMinutes.setFont(Font.font(20));
		calculatedMinutes.setFill(Color.WHITE);
		
//-- BOTTOM HBOX AREA	
		hikeText = new Text("Hikes");
		hikeText.setFont(Font.font(20));
		hikeText.setFill(Color.WHITE);
		
		milesText = new Text("Miles");
		milesText.setFont(Font.font(20));
		milesText.setFill(Color.WHITE);
		
		yearText = new Text("Years");
		yearText.setFont(Font.font(20));
		yearText.setFill(Color.WHITE);
		
		monthText = new Text("Months");
		monthText.setFont(Font.font(20));
		monthText.setFill(Color.WHITE);
		
		dayText = new Text("Days");
		dayText.setFont(Font.font(20));
		dayText.setFill(Color.WHITE);
		
		hourText = new Text("Hours");
		hourText.setFont(Font.font(20));
		hourText.setFill(Color.WHITE);
		
		minuteText = new Text("Minutes");
		minuteText.setFont(Font.font(20));
		minuteText.setFill(Color.WHITE);

		//h - v
		//Top Row
		grid = new GridPane();
		grid.add(calculatedHikes, 0, 0);
		grid.add(calculatedMiles, 1, 0);
		grid.add(calculatedYears, 2, 0);
		grid.add(calculatedMonths, 3, 0);
		grid.add(calculatedDays, 4, 0);
		grid.add(calculatedHours, 5, 0);
		grid.add(calculatedMinutes, 6, 0);
		
		//Bottom Row
		grid.add(hikeText, 0, 1);
		grid.add(milesText, 1, 1);
		grid.add(yearText, 2, 1);
		grid.add(monthText, 3, 1);
		grid.add(dayText, 4, 1);
		grid.add(hourText, 5, 1);
		grid.add(minuteText, 6, 1);
		
		grid.setHgap(70);
		grid.setPadding(new Insets(10,30,50,30));
		grid.setAlignment(Pos.CENTER_LEFT);

		pane.getChildren().addAll(v, textPanel, separator, grid);

		master = new VBox();
		master.getChildren().addAll(pane);
//-----------------------------------TAB-------------------------------------------		
		
		tab.setText("State Stats");
		tab.setContent(master);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        LifetimeDisplayTab l = new LifetimeDisplayTab();
		
		tabPane.getTabs().addAll(tab, l.lifetimeDisplay());
		scene1 = new Scene(tabPane, 800,500);


//---------------------------------------------------------------------------------		
		window.setScene(scene1);
		window.setTitle("Just Hike");
		window.setWidth(900);
		window.setHeight(500);
		window.show();
		
		lifetimeAdditionConversion();
		lifetimeSubtractionConversion();

	  //-- 	Show saved Data on initial load
		Table.displayStats(calculatedHikes, calculatedMiles, calculatedMinutes, calculatedHours, calculatedDays, calculatedMonths, calculatedYears);	

	}
	
	  public static void lifetimeAdditionConversion() {
	    	// if total minutes are higher than given mod, add to hours, loop until minutes are lower than mod. 
	        int mod = 60;
	       	   while(totalLifetimeMinutes >= mod) {
	       		   
	       		totalLifetimeMinutes =  totalLifetimeMinutes - mod;
	       		totalLifetimeHours++;}

	         // if total hours are higher than given mod, add to days, loop until hours are lower than mod. 	   									     
	         mod = 24;
	      	   while(totalLifetimeHours >= mod) {
	      		   
	      		 totalLifetimeHours =  totalLifetimeHours - mod;
	      		totalLifetimeDays++;}

	         // if total days are higher than given mod, add to months, loop until days are lower than mod. 
	         mod = 31;
	      	   while(totalLifetimeDays >= mod) {
	      		   
	      		 totalLifetimeDays =  totalLifetimeDays - mod;
	      		totalLifetimeMonths++;}

	         // if total months are higher than given mod, add to years, loop until months are lower than mod. 
	         mod = 12;
	             while(totalLifetimeMonths >= mod) {
	          		   
	            	 totalLifetimeMonths =  totalLifetimeMonths - mod;
	            		totalLifetimeYears++;}
	             }

	    public static void lifetimeSubtractionConversion() {
	    	
	    	 int mod = -1;
	        	   while(totalLifetimeMinutes <= mod) {
	        		   
	        		   totalLifetimeHours--;
	        		   totalLifetimeMinutes = totalLifetimeMinutes + 60;
	        		   }
	          // if total hours are higher than given mod, add to days, loop until hours are lower than mod. 	   									     

	       	   while(totalLifetimeHours  <= mod) {
	       		
	       		 totalLifetimeDays--;
	       		totalLifetimeHours  =  totalLifetimeHours  + 24;
	       		}

	          // if total days are higher than given mod, add to months, loop until days are lower than mod. 

	       	   while( totalLifetimeDays <= mod) {
	       		  
	       		totalLifetimeMonths--;   
	       	 totalLifetimeDays =   totalLifetimeDays + 31;
	       		}
	          // if total months are higher than given mod, add to years, loop until months are lower than mod. 

	              while(totalLifetimeMonths<= mod) {
	           		   
	            	  totalLifetimeYears--;
	            	  totalLifetimeMonths =  totalLifetimeMonths + 12;
	            	  }

	    }


}//EOC


