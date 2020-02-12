package application;

import java.io.File;
import java.net.URL;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;
import javafx.scene.layout.*;



public class Main extends Application {

	private Stage window;
	private Scene scene;
	StackPane pane = new StackPane();
	String userProfile = System.getProperty("user.name");
	final File dir = new File("C:\\Users\\"+userProfile+"\\JustHike\\Load_Screen_Images");

	
	public static void main(String[] args) {
		launch(args);	
	}


	@Override
	public void start(Stage stage) throws Exception{

		window = stage;
		window.setResizable(false);
		window.initStyle(StageStyle.UTILITY);
		
		createImageFolder(); // If file for saving user images doesnt exist, create one. 

		//--------------------Layout-------------------------------------------------------------------------


		//Chooses random photos for load screen.


		File[] files = dir.listFiles();
		Random rand = new Random();
		File file = files[rand.nextInt(files.length)];
		String imagePath = file.toURI().toURL().toExternalForm();
		Image img = new Image(imagePath);

		System.out.println("Using photo " + imagePath);

		ImageView v = new ImageView();
		v.setImage(img);
		v.autosize();
		v.setFitWidth(900);
		v.setFitHeight(600);

		v.setPreserveRatio(true);

		pane.getChildren().addAll(v);

		VBox layout = new VBox();
		layout.setSpacing(120);
		layout.getChildren().addAll(pane);

		//scene = new Scene(layout, 900,600);
		scene = new Scene(layout);

		window.setScene(scene);
		window.setTitle("JustHike");

		window.show();
		//image delay
		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(3000),
				ae -> createPaths(window)));
		timeline.play();
	}

	public void createPaths(Stage window) {
		IO io = new IO();

		File homeFolder = new File("C:\\Users\\" + userProfile + "\\JustHike\\JustHike\\temp");

		if(!homeFolder.exists() && !homeFolder.isFile()) {

			Table t;
			t = new Table();
			t.deleteButton.setDisable(true);

			homeFolder.mkdirs();


		}

		else   {	
			Table t;
			t = new Table();
			if(Table.miles == 0) {

				t.deleteButton.setDisable(true);
				//t.statsButton.setDisable(true);

			}
			io.loadMasterTable();
			io.loadLifetimeStats();

		}

		window.close();
	}

	public void createImageFolder() {

		File homeFolder = new File("C:\\Users\\" + System.getProperty("user.name") + "\\JustHike\\Load_Screen_Images");

		if(!homeFolder.exists() && !homeFolder.isFile()) {

			homeFolder.mkdirs();

		}
	}
}