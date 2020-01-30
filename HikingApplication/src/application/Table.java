package application;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;
import java.text.DecimalFormat;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Table  {

    Stage window;
    static  ObservableList<HikeData> subentries, hikeDataList;
    static TableView<HikeData> table;
    TextField  locationInput, trailNameInput, milesInput, hoursInput, minutesInput, filterInput;
    static ObservableList<HikeData> hikeData;
    private final String decimalPattern = "\\d{0,3}([\\.]\\d{0,2})?";
	private final String integerPattern = "\\d*";
	Button addButton, deleteButton;
	static int hikes, hours, minutes, days, months , years, lifetimeHikes = 0;
	static String state, date, location, trailName;
	static double totalMilesHiked, displayMiles, miles;
	static int totalHikes, totalMinutesHiked, totalHoursHiked, totalDaysHiked, totalMonthsHiked, totalYearsHiked;
    static HikeData data;
    DatePicker dp;
	static String userProfile = System.getProperty("user.name");
	static ComboBox<String> combo = new ComboBox<String>();
	static String stateCSV;
	Text filterCountLabel;


    @SuppressWarnings("unchecked")
	public Table(){
    	
    	//createPaths();
 
    	StackPane pane = new StackPane();
    	
    	window = new Stage();
    	window.setResizable(false);
		window.initModality(Modality.APPLICATION_MODAL); 
        window.setTitle("JustHike");
		window.initStyle(StageStyle.UTILITY);

//----- Columns
        
// State Column -----------------------------------------------------------------------------------------
        TableColumn<HikeData, String> stateColumn = new TableColumn<>("State");
        stateColumn.setMinWidth(200);
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        
        
// Date Column -----------------------------------------------------------------------------------------

        TableColumn<HikeData, DatePicker> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
     
// Location Column -----------------------------------------------------------------------------------------

        TableColumn<HikeData, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setMinWidth(200);
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
      
        
// Trails Column -----------------------------------------------------------------------------------------
		

        TableColumn<HikeData, String> trailNameColumn = new TableColumn<>("Trailname");
        trailNameColumn.setMinWidth(200);
        trailNameColumn.setCellValueFactory(new PropertyValueFactory<>("trailName"));
  
// Miles Column -----------------------------------------------------------------------------------------
       
        TableColumn<HikeData, Double> milesColumn = new TableColumn<>("Miles");
        milesColumn.setMinWidth(200);
        milesColumn.setCellValueFactory(new PropertyValueFactory<>("miles"));
        
		
// Hours Column -----------------------------------------------------------------------------------------
        TableColumn<HikeData, Integer> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setMinWidth(200);
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
        

// Minutes Column -----------------------------------------------------------------------------------------
        TableColumn<HikeData, Integer> minutesColumn = new TableColumn<>("Minutes");
        minutesColumn.setMinWidth(200);
        minutesColumn.setCellValueFactory(new PropertyValueFactory<>("minutes"));
        
      
//-- End of Columns
        
        
//------------------INPUT FIELDS ---------------------------------------------------------------------------------  
        
//ComboBox
        new ComboBoxFilter<>(combo);
		//sets focus to false so that you can see prompt text
		//combo.setFocusTraversable(false);
		
		//if you click the window the focus will be set to true, so that you can enter your state
		window.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
				combo.setFocusTraversable(true);
		    }
		});

		//Combo box that will get the selected state and store it in a static String variable to be use as the Table header. 
				
		combo.getItems().addAll("All", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida"
						, "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts"
						, "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",  "Nevada", "New Hampshire", "New Jersery", "New Mexico"
						, "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina"
						, "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming");
		//combo.getSelectionModel().select(48);		

//Date Picker 
        dp = new DatePicker();
        dp.setPromptText("Date Field");
        dp.setEditable(false);
        
//Location input
        locationInput = new TextField();
        locationInput.setPromptText("Location Hiked");
        setToolTip(locationInput, new Tooltip(), "Enter the location of the hike");

//trailname input
        trailNameInput = new TextField();
        
        trailNameInput.setPromptText("Trailname Hiked");
        setToolTip(trailNameInput, new Tooltip(), "Enter the name of the hike");


//miles input
        milesInput = new TextField();
        milesInput.setPromptText("Miles Hiked");
        inputCheck(milesInput, decimalPattern); // limits input to number/decimals
        setToolTip(milesInput, new Tooltip(), "Enter the miles hiked");


//hours input
        hoursInput = new TextField();
        hoursInput.setPromptText("Hours Hiked");
		inputCheck(hoursInput, integerPattern);// limits input to number
        setToolTip(hoursInput, new Tooltip(), "Enter the hours hiked");

//minutes input
        minutesInput = new TextField();
        minutesInput.setPromptText("Minutes Hiked");
		inputCheck(minutesInput, integerPattern);// limits input to number
        setToolTip(minutesInput, new Tooltip(), "Enter the minutes hiked");
        
//Filter input
        filterInput = new TextField();
        filterInput.setPromptText("Filter Content");
        setToolTip(filterInput, new Tooltip(), "Enter Data To Be Filtered");

//--------END OF INPUTFIELDS-----------------------------------------------------------------
        
//----------BUTTONS-----------------------------------------------------------------------------------------------
         addButton = new Button("Add");
        
// disables button until all fields have input
        addButton.disableProperty().bind(
         	    Bindings.isEmpty(trailNameInput.textProperty())
         	    .or(Bindings.isEmpty(milesInput.textProperty()))
         	    .or(Bindings.isNull(dp.valueProperty()))
         	    .or(Bindings.isEmpty(hoursInput.textProperty()))
         	   .or(Bindings.isEmpty(minutesInput.textProperty()))
         	);
//-- Action for Add Button.  Here the a method is called for different operations from addButtonClicked method and saving the stats and table are done

        
        addButton.setOnAction(e ->{
        	
        	lifetimeHikes++;
        	StateDisplayTab.totalLifetimeHikes = StateDisplayTab.totalLifetimeHikes + lifetimeHikes;
        	deleteButton.setDisable(false);            addButtonClicked();
    		new IO().saveMasterTable(date, state, location, trailName, miles, hours, minutes);

            });

//-- Deletes input from table    
         deleteButton = new Button("Delete");
        
        deleteButton.setOnAction(e -> {
        	
			try {
				
				deleteButtonClicked();
				
			} catch (IOException e2) {
				
				
				e2.printStackTrace();
			}
		});
        
    	//System.out.println("Using background photo " + imagePath);

		filterCountLabel = new Text();
		filterCountLabel.setFill(Color.WHITE);
		filterCountLabel.setFont(Font.font(15));

		Text resultLabel = new Text("Results Found ");
		resultLabel.setFill(Color.WHITE);
		resultLabel.setFont(Font.font(15));

        table = new TableView<>();
    	table.setEditable(true);
        table.setItems(getData()); // loads in the data to the table as observable list
        table.getColumns().addAll(dateColumn, stateColumn, locationColumn, trailNameColumn, milesColumn, hoursColumn, minutesColumn); // adds all the columns
        table.setMinHeight(600);
        
        HBox upperInputPanel = new HBox();
        upperInputPanel.setAlignment(Pos.CENTER);
        upperInputPanel.setStyle("-fx-background-color: black");
        upperInputPanel.setPadding(new Insets(10,10,10,10));
        upperInputPanel.setSpacing(10);
        upperInputPanel.getChildren().addAll(dp, combo,  locationInput);
            
        HBox lowerInputPanel = new HBox();
        lowerInputPanel.setAlignment(Pos.CENTER);
        lowerInputPanel.setStyle("-fx-background-color: black");
        lowerInputPanel.setPadding(new Insets(10,10,10,10));
        lowerInputPanel.setSpacing(10);
        lowerInputPanel.getChildren().addAll(trailNameInput, milesInput, hoursInput, minutesInput);
        
        HBox filterPanel = new HBox();
        filterPanel.setAlignment(Pos.TOP_RIGHT);
        filterPanel.setStyle("-fx-background-color: black");
        filterPanel.setPadding(new Insets(10,10,10,10));
        filterPanel.setSpacing(10);
        filterPanel.getChildren().addAll(resultLabel, filterCountLabel, filterInput, new Options());
        
        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setStyle("-fx-background-color: black");
        buttonPanel.setPadding(new Insets(10,10,10,10));
        buttonPanel.setSpacing(5);
        buttonPanel.getChildren().addAll(addButton, deleteButton);        
        
        HBox options = new HBox();
        options.setAlignment(Pos.TOP_CENTER);
        options.setStyle("-fx-background-color: black");
        options.setPadding(new Insets(10,10,10,10));
        options.setSpacing(5);
        options.getChildren().addAll(new Options());

        VBox vBox = new VBox();
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.getChildren().addAll(filterPanel,table, upperInputPanel, lowerInputPanel, buttonPanel);
        pane.getChildren().add(vBox);

        Scene scene = new Scene(pane);
        
        URL url = this.getClass().getResource("application.css");
        String css = url.toExternalForm(); 
        scene.getStylesheets().add(css);
        scene.getStylesheets().add("application.css");

        //scrolls to the bottom of the table to start with
        table.scrollTo(table.getItems().size()-1);
        
        window.setMinHeight(600);
        window.setScene(scene);
        window.show();
        filterTextbox(filterInput);
    }

    //Add button clicked
    // Save input and set it to the table
    public void addButtonClicked(){

    	HikeData hikeData = new HikeData();
        String temp = "";

        state = combo.getSelectionModel().getSelectedItem().toString();
        createStatePath(state);
        refreshValuesForState(temp, state);
        new IO().loadStats(state);

        date = dp.getValue().toString().trim();
        location = locationInput.getText().trim();
        trailName = trailNameInput.getText().trim();
        miles = Double.parseDouble(milesInput.getText().trim());
        minutes = Integer.parseInt(minutesInput.getText().trim());
        hours = Integer.parseInt(hoursInput.getText().trim());

        totalHikes = totalHikes + lifetimeHikes;
        totalMilesHiked = totalMilesHiked + miles;
        totalHoursHiked = totalHoursHiked + hours;
        totalMinutesHiked = totalMinutesHiked + minutes;

        StateDisplayTab.totalLifetimeMiles = StateDisplayTab.totalLifetimeMiles + miles;
      	StateDisplayTab.totalLifetimeMinutes = StateDisplayTab.totalLifetimeMinutes + minutes;
      	StateDisplayTab.totalLifetimeHours = StateDisplayTab.totalLifetimeHours + hours;

        File path = new File("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\" + state + "\\" );
        //if the path to the state table doesnt exists, save data, else load previous state data, that save it. 
        if(!path.exists() && !path.isFile()) {

	       	 path.mkdirs();

        }
        
        additionConversion();
		new IO().saveMasterTable(date, state, location, trailName, miles, hours, minutes);
		new IO().saveStats(totalHikes, totalMilesHiked, totalMinutesHiked, totalHoursHiked, totalDaysHiked, totalMonthsHiked, totalYearsHiked);
		new IO().saveLifetimeStats(StateDisplayTab.totalLifetimeHikes, StateDisplayTab.totalLifetimeMiles, StateDisplayTab.totalLifetimeMinutes, StateDisplayTab.totalLifetimeHours, 
   	        		StateDisplayTab.totalLifetimeDays, StateDisplayTab.totalLifetimeMonths, StateDisplayTab.totalLifetimeYears);

   	    //---------------------------------------------
		/*The IF checks for null statements from user. If null, a dialog appears, asking for estimated hiking speed.
		That info is sent through algorithm to be displayed, added to STATS and then convert() is called to tally input 
		else the input is displayed and tallied.
		ELSE just set the table as usual */
      	
        if(hours  <= 0 && minutes <= 0) {
         
       	 Alerts.nullFields( miles, hours, minutes);
        		}
        
        else {
        	
         setTable(hikeData);
        }

        dp.setValue(null); 
        locationInput.clear();
        trailNameInput.clear();
        milesInput.clear();
        hoursInput.clear();
        minutesInput.clear();
        lifetimeHikes = 0;

    }
    
  
    public static void additionConversion() {
    	// if total minutes are higher than given mod, add to hours, loop until minutes are lower than mod. 
        int mod = 60;
       	   while(totalMinutesHiked >= mod) {
       		   
       		   totalMinutesHiked =  totalMinutesHiked - mod;
       		   totalHoursHiked++;}

         // if total hours are higher than given mod, add to days, loop until hours are lower than mod. 	   									     
         mod = 24;
      	   while(totalHoursHiked >= mod) {
      		   
      		totalHoursHiked =  totalHoursHiked - mod;
      		totalDaysHiked++;}

         // if total days are higher than given mod, add to months, loop until days are lower than mod. 
         mod = 31;
      	   while(totalDaysHiked >= mod) {
      		   
      		totalDaysHiked =  totalDaysHiked - mod;
      		totalMonthsHiked++;}

         // if total months are higher than given mod, add to years, loop until months are lower than mod. 
         mod = 12;
             while(totalMonthsHiked >= mod) {
          		   
           	  totalMonthsHiked =  totalMonthsHiked - mod;
           	  totalYearsHiked++;}
             }

    public void subtractionConversion() {
    	
    	 int mod = -1;
        	   while(totalMinutesHiked <= mod) {
        		   
        		   totalHoursHiked--;
        		   totalMinutesHiked = totalMinutesHiked + 60;
        		   }
          // if total hours are higher than given mod, add to days, loop until hours are lower than mod. 	   									     

       	   while(totalHoursHiked <= mod) {
       		
       		totalDaysHiked--;
       		totalHoursHiked =  totalHoursHiked + 24;
       		}

          // if total days are higher than given mod, add to months, loop until days are lower than mod. 

       	   while(totalDaysHiked <= mod) {
       		  
       		totalMonthsHiked--;   
       		totalDaysHiked =  totalDaysHiked + 31;
       		}
          // if total months are higher than given mod, add to years, loop until months are lower than mod. 

              while(totalMonthsHiked<= mod) {
           		   
            	  totalYearsHiked--;
            	  totalMonthsHiked =  totalMonthsHiked + 12;
            	  }

    }

//Delete button clicked
    public void deleteButtonClicked() throws IOException{
    	//This gets the the user click and stores the chosen selections into temp variables
    	 HikeData data = table.getSelectionModel().getSelectedItem();    	 
        //Loads stats that have been saved thus far. 
    	 new IO().loadStats(data.getState());

	    //This reduces the overall state total by one. 
    	//Reduces state values by the values of the row selected. 
    	 
	    totalHikes = totalHikes - 1;	   
        totalMilesHiked = totalMilesHiked -  data.getMiles();
        totalHoursHiked = totalHoursHiked - data.getHours();
        totalMinutesHiked = totalMinutesHiked - data.getMinutes();
        
        //Reduce liftime hike total by 1
        //reduces  lifetime totals by selected values in row.
      	StateDisplayTab.totalLifetimeHikes = StateDisplayTab.totalLifetimeHikes - 1; 	
        StateDisplayTab.totalLifetimeMiles = StateDisplayTab.totalLifetimeMiles -  data.getMiles();;
      	StateDisplayTab.totalLifetimeMinutes = StateDisplayTab.totalLifetimeMinutes - data.getMinutes();
      	StateDisplayTab.totalLifetimeHours = StateDisplayTab.totalLifetimeHours - data.getHours();
      	
        //If the path to the state table doesnt exists, save data, else load previous state data, that save it. 
        File path = new File("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\" + state + "\\" );
        if(!path.exists() && !path.isFile()) {

	       	 path.mkdirs();

        }

        //Redo conversion for master totals for the modulus
	    subtractionConversion();
	    StateDisplayTab.lifetimeSubtractionConversion();
	    //Save new data for state table, master table, and master stats
	    new IO().saveMasterTable(data.getDate(), data.getState(), data.getLocation(), data.getTrailName(), data.getMiles(), data.getHours(), data.getMinutes());
	    new IO().saveStats(totalHikes, totalMilesHiked, totalMinutesHiked, totalHoursHiked, totalDaysHiked, totalMonthsHiked, totalYearsHiked);
	    new IO().saveLifetimeStats(StateDisplayTab.totalLifetimeHikes, StateDisplayTab.totalLifetimeMiles, StateDisplayTab.totalLifetimeMinutes, StateDisplayTab.totalLifetimeHours, StateDisplayTab.totalLifetimeDays,
	    		StateDisplayTab.totalLifetimeMonths, StateDisplayTab.totalLifetimeYears);
	    
	     
	     // if all values are equal to zero, then theres nothing for the user to delete. 
	    	if(totalYearsHiked == 0 && totalMonthsHiked == 0 &&  totalDaysHiked == 0 && totalHoursHiked == 0 && totalMinutesHiked == 0) {
	    		
	    		deleteButton.setDisable(true);

	    	}

	    	new IO().deleteTableSelection("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\masterTable.txt", table);
	    	new IO().saveMasterTable(date, state, location, trailName, miles, hours, minutes);
        
    }
    
    //-----END OF BUTTONS

    //Get all of the stats
     public ObservableList<HikeData> getData(){
      hikeData = FXCollections.observableArrayList();
   
        return hikeData;
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
 	 
	 // this is called in StatsDisplay to show stats
	 public static void displayStats(Text hikes, Text miles, Text minutes, Text hours, Text days, Text months, Text years) {
	 
		DecimalFormat milesFormat = new DecimalFormat("#.0");
		
		hikes.setText(Integer.toString(totalHikes));
	    miles.setText(milesFormat.format(totalMilesHiked)); 
    	minutes.setText(Integer.toString(totalMinutesHiked));
    	hours.setText(Integer.toString(totalHoursHiked));
    	days.setText(Integer.toString(totalDaysHiked));
    	months.setText(Integer.toString(totalMonthsHiked));
    	years.setText(Integer.toString(totalYearsHiked));

}

	//puts variables into the table
	public static void setTable(HikeData hikeData) {
	
	 hikeData.setState(state);	
   	 hikeData.setDate(date);
   	 hikeData.setLocation(location);
   	 hikeData.setTrailName(trailName);
   	 hikeData.setMiles(miles);
   	 hikeData.setHours(hours);
   	 hikeData.setMinutes(minutes);
     table.getItems().add(hikeData);
     additionConversion();
     StateDisplayTab.lifetimeAdditionConversion();

	}


	 // Method that creates tooltips	
	 public void setToolTip(TextField tf, Tooltip t, String message){
		
		t = new Tooltip();
		t.setText(message);
		tf.setTooltip(t);
		
	}
	
	 public void filterTextbox(TextField t) {
		   HikeData d = new HikeData();
		   hikeDataList =  table.getItems();
		   t.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
		               if (oldValue != null && (newValue.length() < oldValue.length())) {
		                   table.setItems(hikeDataList);
		               }
		               String value = newValue.toLowerCase();
		                subentries = FXCollections.observableArrayList();

		               long count = table.getColumns().stream().count();
		               for (int i = 0; i < table.getItems().size(); i++) {
		                   for (int j = 0; j < count; j++) {
		                       String entry = "" + table.getColumns().get(j).getCellData(i);
		                       if (entry.toLowerCase().contains(value)) {
		                           subentries.add(table.getItems().get(i));
		                           break;
		                       }
		                   }
		               }
	
		              table.setItems(subentries);
					d.setFilterCount(Integer.toString(table.getItems().size()));
		          	filterCountLabel.textProperty().bindBidirectional(new SimpleStringProperty(d.getFilterCount()));

					double total = 0;
					for (HikeData item : table.getItems()) {
						total = total + item.getMiles();
						System.out.println(total);
					}
		              
		       			System.out.print("Hike count at " +  table.getItems().size() +  "\n"); // i get amount of rows
		           });

		   }	 

	 // if user selects a different state while still using the same instance of Table, this will clear the values. 
	 public void refreshValuesForState(String temp, String state) {
		 
		 if(!temp.equals(state)){
			 
      	   totalMilesHiked = 0;
             totalHoursHiked = 0;
             totalMinutesHiked = 0;

      }
	 }

	 //if a path to state isn't already created, this will create one. 
	 public static void createStatePath(String state) {
		 
		   File dir = new File("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\" + state +"\\" );
	        dir.mkdirs();
	        File tmp = new File(dir, "Stats for " + state + ".txt");
	        try {
				tmp.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

	 static public String comboSelection() {
		 	 
		 return combo.getSelectionModel().getSelectedItem().toString();
	 }
		
}// EOC
