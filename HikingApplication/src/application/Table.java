package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Table  {

	Stage window;
	static  ObservableList<HikeData> subentries, hikeDataList;
	static TableView<HikeData> table;
	TextField  locationInput, trailNameInput, milesInput, hoursInput, minutesInput, filterInput;
	static ObservableList<HikeData> hikeData;
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
	Text totalCountLabel, totalMilesLabel, totalDaysLabel, totalHoursLabel;


	@SuppressWarnings("unchecked")
	public Table(){

		window = new Stage();
		window.setResizable(false);
		//window.initModality(Modality.APPLICATION_MODAL); 
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
		new HelperFunctions().setToolTip(locationInput, new Tooltip(), "Enter the location of the hike");

		//trailname input
		trailNameInput = new TextField();

		trailNameInput.setPromptText("Trailname Hiked");
		new HelperFunctions().setToolTip(trailNameInput, new Tooltip(), "Enter the name of the hike");

		//miles input
		milesInput = new TextField();
		milesInput.setPromptText("Miles Hiked");
		new HelperFunctions().inputCheck(milesInput, new HelperFunctions().decimalPattern); // limits input to number/decimals
		new HelperFunctions().setToolTip(milesInput, new Tooltip(), "Enter the miles hiked");


		//hours input
		hoursInput = new TextField();
		hoursInput.setPromptText("Hours Hiked");
		new HelperFunctions().inputCheck(hoursInput, new HelperFunctions().integerPattern);// limits input to number
		new HelperFunctions().setToolTip(hoursInput, new Tooltip(), "Enter the hours hiked");

		//minutes input
		minutesInput = new TextField();
		minutesInput.setPromptText("Minutes Hiked");
		new HelperFunctions().inputCheck(minutesInput, new HelperFunctions().integerPattern);// limits input to number
		new HelperFunctions().setToolTip(minutesInput, new Tooltip(), "Enter the minutes hiked");

		//Filter input
		filterInput = new TextField();
		filterInput.setPromptText("Filter Content");
		new HelperFunctions().setToolTip(filterInput, new Tooltip(), "Enter Data To Be Filtered");

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

			addButtonClicked();
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

		totalCountLabel = new Text();
		totalCountLabel.setFill(Color.WHITE);
		totalCountLabel.setFont(Font.font(15));

		totalMilesLabel = new Text();
		totalMilesLabel.setFill(Color.WHITE);
		totalMilesLabel.setFont(Font.font(15));

		totalDaysLabel = new Text();
		totalDaysLabel.setFill(Color.WHITE);
		totalDaysLabel.setFont(Font.font(15));

		totalHoursLabel = new Text();
		totalHoursLabel.setFill(Color.WHITE);
		totalHoursLabel.setFont(Font.font(15));

		table = new TableView<>();
		table.setMinHeight(600);
		table.setEditable(true);
		table.setItems(getData()); // loads in the data to the table as observable list
		table.getColumns().addAll(dateColumn, stateColumn, locationColumn, trailNameColumn, milesColumn, hoursColumn, minutesColumn); // adds all the columns

		HBox upperInputPanel = new HBox();
		HBox.setHgrow(upperInputPanel, Priority.ALWAYS);
		upperInputPanel.setAlignment(Pos.CENTER);
		upperInputPanel.setStyle("-fx-background-color: black");
		upperInputPanel.setPadding(new Insets(10,10,10,10));
		upperInputPanel.setSpacing(10);
		upperInputPanel.getChildren().addAll(dp, combo,  locationInput);

		HBox lowerInputPanel = new HBox();
		HBox.setHgrow(lowerInputPanel, Priority.ALWAYS);
		lowerInputPanel.setAlignment(Pos.CENTER);
		lowerInputPanel.setStyle("-fx-background-color: black");
		lowerInputPanel.setPadding(new Insets(10,10,10,10));
		lowerInputPanel.setSpacing(10);
		lowerInputPanel.getChildren().addAll(trailNameInput, milesInput, hoursInput, minutesInput);

		HBox filterPanel = new HBox();
		HBox.setHgrow(filterPanel, Priority.ALWAYS);
		filterPanel.setAlignment(Pos.TOP_RIGHT);
		filterPanel.setStyle("-fx-background-color: black");
		filterPanel.setPadding(new Insets(10,10,10,10));
		filterPanel.setSpacing(10);
		filterPanel.getChildren().addAll(filterInput, new Options());

		HBox buttonPanel = new HBox();
		HBox.setHgrow(buttonPanel, Priority.ALWAYS);
		buttonPanel.setAlignment(Pos.CENTER);
		buttonPanel.setStyle("-fx-background-color: black");
		buttonPanel.setPadding(new Insets(10,10,10,10));
		buttonPanel.setSpacing(5);
		buttonPanel.getChildren().addAll(addButton, deleteButton);      

		HBox options = new HBox();
		HBox.setHgrow(options, Priority.ALWAYS);
		options.setAlignment(Pos.TOP_CENTER);
		options.setStyle("-fx-background-color: black");
		options.setPadding(new Insets(10,10,10,10));
		options.setSpacing(5);
		options.getChildren().addAll(new Options());

		HBox filteredValueHeaders = new HBox();
		HBox.setHgrow(buttonPanel, Priority.ALWAYS);
		filteredValueHeaders.setSpacing(15);
		filteredValueHeaders.setAlignment(Pos.BOTTOM_CENTER);
		filteredValueHeaders.getChildren().addAll(new HelperFunctions().textFunction("Total Hike Count", 20, true), 
				new HelperFunctions().textFunction("Total Miles", 20, true), new HelperFunctions().textFunction("Total Days", 20, true), 
				new HelperFunctions().textFunction("Total Hours", 20, true));

		HBox filteredValues = new HBox();
		HBox.setHgrow(filteredValues, Priority.ALWAYS);
		filteredValues.setAlignment(Pos.BOTTOM_CENTER);
		filteredValues.setSpacing(120);
		filteredValues.setPadding(new Insets(0,0,15,0));
		filteredValues.getChildren().addAll(totalCountLabel, totalMilesLabel, totalDaysLabel, totalHoursLabel);

		VBox master = new VBox();
		VBox.setVgrow(master, Priority.ALWAYS);
		master.setStyle("-fx-background-color: black");
		master.setAlignment(Pos.TOP_CENTER);
		master.getChildren().addAll(filterPanel ,table,  filteredValueHeaders, filteredValues,upperInputPanel, lowerInputPanel, buttonPanel);

		StackPane pane = new StackPane();
		pane.getChildren().addAll(master);

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
		window.setMinWidth(1500);

		filterTextbox(filterInput);
	}
	
	//Add button clicked
	// Save input and set it to the table
	public void addButtonClicked(){

		String temp = "";
    	HikeData hikeData = new HikeData();


		state = combo.getSelectionModel().getSelectedItem().toString();
		new HelperFunctions().refreshValuesForState(temp, state, totalMilesHiked, totalHoursHiked, totalMinutesHiked);

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
		
		setTable(hikeData);

		new IO().saveMasterTable(date, state, location, trailName, miles, hours, minutes);
		new HelperFunctions().filterConversions(table, totalMilesLabel, totalHoursLabel, totalDaysLabel, totalCountLabel);
		
		deleteButton.setDisable(false);
		dp.setValue(null); 
		locationInput.clear();
	    trailNameInput.clear();
	    milesInput.clear();
	    hoursInput.clear();
	    minutesInput.clear();

	}

	//Delete button clicked
	public void deleteButtonClicked() throws IOException{
	
		new IO().deleteTableSelection("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\temp\\masterTable.txt", table);
		new IO().saveMasterTable(date, state, location, trailName, miles, hours, minutes);
		
		if (Table.hikeData.size() == 0 ) {
			
			deleteButton.setDisable(true);
		}
		
		new HelperFunctions().filterConversions(table, totalMilesLabel, totalHoursLabel, totalDaysLabel, totalCountLabel);

	}
	
	//-----END OF BUTTONS

	//Get all of the stats
	public ObservableList<HikeData> getData(){
		hikeData = FXCollections.observableArrayList();

		return hikeData;
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

	}

	public void filterTextbox(TextField t) {

		hikeDataList =  table.getItems();
		t.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			if (oldValue != null && (newValue.length() < oldValue.length())) {
				table.setItems(hikeDataList);
			}
			String value = newValue.toLowerCase();
			subentries = FXCollections.observableArrayList();
			new HelperFunctions ().disableFields(filterInput, locationInput, trailNameInput, milesInput, hoursInput, minutesInput, dp, combo);

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
			new HelperFunctions().filterConversions(table, totalMilesLabel, totalHoursLabel, totalDaysLabel, totalCountLabel);
		}); 
	}	 

	static public String comboSelection() {

		return combo.getSelectionModel().getSelectedItem().toString();
	}}// EOC
