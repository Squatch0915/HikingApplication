package application;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LifetimeDisplayTab {
	
	  HBox  midBox, lowBox, panel;
	  VBox master;
	  Text lifetimeHikes, lifetimeHours, lifetimeMinutes,lifetimeMiles, lifetimeDays, lifetimeMonths, lifetimeYears;
	  Text  hikeText, milesText, yearText, monthText, dayText, hourText, minuteText;
	  static int totalLifetimeHikes, totalLifetimeMinutes, totalLifetimeHours, totalLifetimeDays, totalLifetimeMonths, totalLifetimeYears;
	  static double totalLifetimeMiles;
	  Separator separator;
      IO io = new IO();
      StackPane pane = new StackPane();
      GridPane grid;
      int totalHikes = 0;
      StateDisplayTab s; 
  	  final File dir = new File("C:\\Users\\"+System.getProperty("user.name")+"\\JustHike\\Lifetime_Images");



	public  Tab lifetimeDisplay() throws MalformedURLException {

			Tab tab = new Tab();
			File[] files = dir.listFiles();
			Random rand = new Random();
			File file = files[rand.nextInt(files.length)];
			String imagePath = file.toURI().toURL().toExternalForm();
			Image img = new Image(imagePath);


			Text text = new Text();
			text.setText("Lifetime");
			text.setFont(Font.font("ENGRAVERS MT", 50));
			text.setFill(Color.WHITE);

			panel = new HBox();
			panel.setAlignment(Pos.TOP_CENTER);
			panel.getChildren().add(text);
			panel.setPadding(new Insets(20,10,10,10));
			
			ImageView v = new ImageView();
			v.setImage(img);
			v.setFitWidth(900);
			v.setFitHeight(500);
			
			separator = new Separator();
			separator.setMaxWidth(2000);
			separator.setPadding(new Insets(10,10,290,10));

			
	//-- MID HBOX AREA
			
			lifetimeHikes = new Text("0");
			lifetimeHikes.setFont(Font.font(20));
			lifetimeHikes.setFill(Color.WHITE);
			
			lifetimeMiles = new Text("0");
			lifetimeMiles.setFont(Font.font(20));
			lifetimeMiles.setFill(Color.WHITE);
			
			lifetimeYears = new Text("0");
			lifetimeYears.setFont(Font.font(20));
			lifetimeYears.setFill(Color.WHITE);
			
			lifetimeMonths = new Text("0");
			lifetimeMonths.setFont(Font.font(20));
			lifetimeMonths.setFill(Color.WHITE);
			
			lifetimeDays = new Text("0");
			lifetimeDays.setFont(Font.font(20));
			lifetimeDays.setFill(Color.WHITE);
			
			lifetimeHours = new Text("0");
			lifetimeHours.setFont(Font.font(20));
			lifetimeHours.setFill(Color.WHITE);

			lifetimeMinutes = new Text("0");
			lifetimeMinutes.setFont(Font.font(20));
			lifetimeMinutes.setFill(Color.WHITE);

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
			grid.add(lifetimeHikes, 0, 0);
			grid.add(lifetimeMiles, 1, 0);
			grid.add(lifetimeYears, 2, 0);
			grid.add(lifetimeMonths, 3, 0);
			grid.add(lifetimeDays, 4, 0);
			grid.add(lifetimeHours, 5, 0);
			grid.add(lifetimeMinutes, 6, 0);
			
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
			
			pane.getChildren().addAll(v, panel, separator, grid);

			master = new VBox();
			master.getChildren().addAll(pane);
	//-----------------------------------TAB-------------------------------------------		
			 tab.setText("Lifetime Stats");
			 tab.setContent(master);
			 
			 StateDisplayTab.lifetimeAdditionConversion();
			 StateDisplayTab.lifetimeSubtractionConversion();

    		displayStats(lifetimeHikes, lifetimeMiles, lifetimeMinutes, lifetimeHours, lifetimeDays, lifetimeMonths, lifetimeYears);		


	        return tab;
	}
	 // this is called in StatsDisplay to show 
	public static void displayStats(Text hikes, Text miles, Text minutes, Text hours, Text days, Text months, Text years) {
	 
		DecimalFormat milesFormat = new DecimalFormat("#.0");
		
	hikes.setText(Integer.toString(StateDisplayTab.totalLifetimeHikes));
	miles.setText(milesFormat.format(StateDisplayTab.totalLifetimeMiles)); 
  	minutes.setText(Integer.toString(StateDisplayTab.totalLifetimeMinutes));
  	hours.setText(Integer.toString(	StateDisplayTab.totalLifetimeHours));
  	days.setText(Integer.toString(	StateDisplayTab.totalLifetimeDays));
  	months.setText(Integer.toString(	StateDisplayTab.totalLifetimeMonths));
  	years.setText(Integer.toString(	StateDisplayTab.totalLifetimeYears));

	}
	
	  
}
