package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

import application.Table;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IO {

	static String userProfile = System.getProperty("user.name");
	String masterTablePath = "C:\\Users\\" + userProfile + "\\JustHike\\JustHike\\masterTable.txt";

	Writer writer = null;
	HikeData d = new HikeData();

	public void saveMasterTable(String date, String state, String location, String trailName, double miles, int hours, int minutes) {

		HikeData data = new HikeData();

		try {

			FileWriter fw = new FileWriter(masterTablePath);
			PrintWriter pw = new PrintWriter(fw);

			for (int i = 0; i < Table.hikeData.size(); i++) {
				data = Table.hikeData.get(i);
				pw.println(data.getDate());
				pw.println(data.getState());
				pw.println(data.getLocation());
				pw.println(data.getTrailName());
				pw.println(data.getMiles());
				pw.println(data.getHours());
				pw.println(data.getMinutes());
			}
	
			pw.close();
		} catch (IOException e) {

			new Alerts("Error", "There was an error saving the Master Table to file!");
		}
	}

	public void loadMasterTable() {

		try {
			FileReader fr = new FileReader(masterTablePath);
			BufferedReader br = new BufferedReader(fr);
			Scanner scan = new Scanner(fr);
			
			while (scan.hasNextLine()) {
				HikeData readData = new HikeData();

				Table.date = scan.nextLine();
				Table.state = scan.nextLine();
				Table.location = scan.nextLine();
				Table.trailName = scan.nextLine();
				Table.miles = scan.nextDouble();
				Table.hours = scan.nextInt();
				Table.minutes = scan.nextInt();

				scan.nextLine(); // ignore the result
				readData = new HikeData(Table.date, Table.state, Table.location, Table.trailName, Table.miles,
						Table.hours, Table.minutes);
				Table.table.getItems().addAll(readData);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void saveCSV(String date, String location, String trailName, double miles, ObservableList<HikeData> subentries) throws IOException {
		File file = new File("");
		String path = "C:\\Users\\" + userProfile + "\\Desktop\\Trail CSV\\temp";
		HikeData data = new HikeData();
		double milesTotal = 0;
		
		new HelperFunctions().createPath(file, path);

		FileChooser fileChooser = new FileChooser();		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialDirectory(new File(path));
		fileChooser.setTitle("Add Hiking Totals");
		
		
		
		File csvName = fileChooser.showSaveDialog(new Stage());
		Writer writer = new BufferedWriter(new FileWriter(csvName.getAbsolutePath()));
		String columns = "DATE" + "," + "HIKE LOCATION" + "," + "TRAIL HIKED" + "," + "MILES HIKED" + "\n";
		writer.write(columns);
		
		// Saves what user filtered
		try {
			if (subentries != null) {

				for (int i = 0; i < subentries.size(); i++) {
					
					data = subentries.get(i);
						data.getDate();
						data.getState();
						data.getLocation();
						data.getTrailName();
						data.getMiles();
						

					// Writing to the CSV
					String text = data.getDate() + "," + data.getLocation() + "," + data.getTrailName() + "," + data.getMiles() + "\n";
					writer.write(text);
					milesTotal = milesTotal + data.getMiles();
				}
			} else if (subentries == null) {

				for (int i = 0; i < Table.hikeData.size(); i++) {

					data = Table.hikeData.get(i);
						data.getDate();
						data.getState();
						data.getLocation();
						data.getTrailName();
						data.getMiles();
				
					// Writing to the CSV
					String text = data.getDate() + "," + data.getLocation() + "," + data.getTrailName() + "," + data.getMiles() + "\n";
					writer.write(text);
					milesTotal = milesTotal + data.getMiles();

				}
			}

		} catch (IOException e) {

			new Alerts("Error", "There was an error saving selection to file!");

		}

		finally {
			
			// Writing the total miles for the CSV
			String totals = "\n" + " " + "," + " " + "," + "Total Miles Hiked" + "," + milesTotal + "\n";
;			writer.write(totals);

			writer.flush();
			writer.close();

		}

	}

	public void deleteTableSelection(String path, TableView<HikeData> table) throws IOException {

		// This gets the selected line and removes it from the table
		ObservableList<HikeData> statsSelected, allStats;
		allStats = table.getItems();
		statsSelected = table.getSelectionModel().getSelectedItems();
		statsSelected.forEach(allStats::remove);

		// Here the file is being read in, the selected line to remove is found, trimmed
		// from file, and the file gets re-saved.
		File inputFile = new File(path);
		File tempFile = new File(path);

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		// Deletes selected row from file
		String lineToRemove = statsSelected.toString();
		String currentLine;

		while ((currentLine = reader.readLine()) != null) {

			// trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			if (trimmedLine.equals(lineToRemove))
				continue;

			writer.write(currentLine + System.getProperty("line.separator"));

		}
		writer.close();
		reader.close();
		boolean successful = tempFile.renameTo(inputFile);

	}

}