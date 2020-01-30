package application;

import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Stats extends Application{

	private Stage window;
	private Scene scene;
	private static String state;
	private HBox  box2; 
	private VBox box1;
	private Button loadButton;
	private StackPane pane = new StackPane();
	

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		ComboBox<String> combo = new ComboBox<String>();

		window = stage;
		window.setResizable(false);
		window.initStyle(StageStyle.UTILITY);

		loadButton = new Button("Load");
		loadButton.setMaxWidth(200);
        loadButton.setDisable(true);
		
		Label text = new Label();
		text.setText("");
		text.setFont(Font.font("ENGRAVERS MT", 10));
		text.setTextFill(Color.CHARTREUSE);
		
		new ComboBoxFilter<>(combo);
		//sets focus to false so that you can see promptext
		combo.setFocusTraversable(false);

		
		//if you click the window the focus will be set to true, so that you can enter your state
		window.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
				combo.setFocusTraversable(true);
		    }
		});

		state=null;
		
		//Combo box that will get the selected state and store it in a static String variable to be use as the Table header. 
		combo.getItems().addAll("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida"
				, "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts"
				, "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",  "Nevada", "New Hampshire", "New Jersery", "New Mexico"
				, "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina"
				, "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming");
		
		   combo.setOnAction((e) -> {
	             setState(combo.getSelectionModel().getSelectedItem());
	             Table.createStatePath(getState());
	             loadButton.setDisable(false);

	        });
		   
//-------------------LOAD BUTTON -------------------------------------------------------------------		   

				//Load button creates the table object and loads in the data associated with the chosen state.  State was got from the combo box
		   		//Checks if the state picked already exists as a file, is not, create a new table, if it exists create table with loaded info. 
				loadButton.setOnAction(e ->{
			    
		            	 	IO io = new IO();
		            	 	io.loadStats(getState());
		                    io.loadLifetimeStats();
		               
		        			StateDisplayTab s = new StateDisplayTab();    

		    	        	 	Stage stateWindow = new Stage();
		    	        	 	stateWindow.initModality(Modality.APPLICATION_MODAL); 
		    					try {
									s.start(stateWindow);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

		             window.close();
				});


//--------------------Layout-------------------------------------------------------------------------
				
				box1 = new VBox();
				box1.setPadding(new Insets(10,10,10,10));
				box1.setAlignment(Pos.TOP_CENTER);
				box1.getChildren().addAll(text, combo);
				box1.setSpacing(50);
				
				box2 = new HBox();
				box2.setAlignment(Pos.BOTTOM_CENTER);
				box2.getChildren().addAll(loadButton);
				
				//Adding image as background for state selection screen
				Image img = new Image(ResourceLoader.load("trail.jpg"));
				ImageView v = new ImageView();
				v.setImage(img);
				v.setFitWidth(250);
				v.setPreserveRatio(true);				

				pane.getChildren().addAll(v, box1, box2);
				
				VBox layout = new VBox();
				layout.setSpacing(120);
				layout.getChildren().addAll(pane);

				scene = new Scene(layout, 250,515);
				window.setScene(scene);
				window.setTitle("JustHike");
				window.show();

		}
	
	public static String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}



	
}