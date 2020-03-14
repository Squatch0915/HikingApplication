package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
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
	ImageView v = new ImageView();

	
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
		File dir = new File("C:\\Users\\"+userProfile+"\\JustHike\\Load_Screen_Images");
		
			File[] files = dir.listFiles();
			Random rand = new Random();
			File file = files[rand.nextInt(files.length)];
			String imagePath = file.toURI().toURL().toExternalForm();
			System.out.println("Using photo " + imagePath);
			
			javaxt.io.Image img = new javaxt.io.Image(file);
			img.rotate();
			img.sharpen();
	
		    Image image = SwingFXUtils.toFXImage(img.getBufferedImage(), null);
		    
			v.setImage(image);
			v.autosize();
			v.setFitWidth(900);
			v.setFitHeight(600);
			v.setPreserveRatio(true);
			v.setSmooth(true);
			v.setCache(true);
	
			pane.getChildren().addAll(v);

		VBox layout = new VBox();
		layout.setSpacing(120);
		layout.getChildren().addAll(pane);

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

		/*
		 * File homeFolder = new
		 * File("C:\\Users\\" + userProfile + "\\JustHike\\JustHike\\temp\\");
		 */
		File homeFolder = new File("C:\\Users\\" + userProfile + "\\JustHike\\");
		if(!homeFolder.exists() && !homeFolder.isFile()) {

			Table t;
			t = new Table();
			t.deleteButton.setDisable(true);

			homeFolder.mkdirs();
		}

		else   {	
			Table t;
			t = new Table();
			io.loadMasterTable();
			if(Table.hikeData.size() == 0) {
				t.deleteButton.setDisable(true);
			}
			else { t.deleteButton.setDisable(false);}
			new HelperFunctions().filterConversions(Table.table, t.totalMilesLabel, t.totalHoursLabel, t.totalDaysLabel, t.totalCountLabel);

		}

		window.close();
	}

	public void createImageFolder() throws IOException {
		   
		File imageFolder = new File("C:\\Users\\" + System.getProperty("user.name") + "\\JustHike\\Load_Screen_Images");

		if(!imageFolder.exists() && !imageFolder.isFile()) {
			imageFolder.mkdirs();
			
			File file = new File(getClass().getClassLoader().getResource("trail.jpg").getFile());
			String k = file.toURI().toURL().toExternalForm();
			Image image = new Image(k);

			
			 File outputFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\JustHike\\Load_Screen_Images\\Images");
			    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
			    try {
			      ImageIO.write(bImage, "jpg", outputFile.getAbsoluteFile());
			    } catch (IOException e) {
			      throw new RuntimeException(e);
			    }
			  
	 
		}
	}
}