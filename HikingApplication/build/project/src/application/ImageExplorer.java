package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageExplorer {

	public ImageExplorer() {


		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPEG (*.jpg)", "*.jpg", 
				"PNG (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialDirectory(new File("C:\\Users\\" +  System.getProperty("user.name") + "\\Pictures"));
		fileChooser.setTitle("Add Hiking Images");

		List<File> multiplePhotos = fileChooser.showOpenMultipleDialog((new Stage()));

		if(multiplePhotos != null) {

			for(int i=0; i<multiplePhotos.size(); i++){
				Path image_Store_Folder = Paths.get("C:\\Users\\jlrog\\JustHike\\temp\\Load_Screen_Images\\Images" + multiplePhotos.get(i).getName());
				try {
					Files.copy(multiplePhotos.get(i).toPath(), image_Store_Folder);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}


	}

}
