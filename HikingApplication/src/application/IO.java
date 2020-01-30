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


public class IO {

	static String userProfile = System.getProperty("user.name");
	String lifetimePath =    "C:\\Users\\" + userProfile + "\\JustHike\\JustHike\\lifeTimeStats.txt";
	String masterTablePath = "C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\masterTable.txt";

	Writer writer = null;
	HikeData d = new HikeData();


	// Gets size of the List and then  	 
	public void saveTable( String date, String state, String location, String trailName, double miles, int hours, int minutes) {

		String tableFile = "C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\" + Table.state + "\\Table for " + Table.state + ".txt";
		HikeData data = new HikeData();

		try{

		FileWriter fw = new FileWriter (tableFile, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(Table.date);
		pw.println(Table.state);
		pw.println(Table.location);
		pw.println(Table.trailName);
		pw.println(Table.miles);
		pw.println(Table.hours);
		pw.println(Table.minutes);


		pw.close(); 
		} catch (IOException ex){

			ex.printStackTrace();

			new Alerts("Error", "There was an error saving the table to file!");

		}	
	}

	// read in variables into object, and then load that object into table
	public void loadTable(){

		try {
			FileReader fr = new FileReader("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\" + Table.state + "\\Table for " + Table.state + ".txt");
			BufferedReader br = new BufferedReader(fr);
			Scanner scan = new Scanner(fr);

			while (scan.hasNextLine())
			{
				HikeData readData = new HikeData();

				Table.date =  scan.nextLine();	
				Table.state =  scan.nextLine();		      
				Table.location = scan.nextLine();		      
				Table.trailName = scan.nextLine();		      
				Table.miles = scan.nextDouble();		      
				Table.hours = scan.nextInt();	      
				Table.minutes = scan.nextInt();
				scan.nextLine(); // ignore the result
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}


	public void saveMasterTable( String date, String state, String location, String trailName, double miles, int hours, int minutes) {

		HikeData data = new HikeData();

		try{

			FileWriter fw = new FileWriter (masterTablePath);
			PrintWriter pw = new PrintWriter(fw);
			for(int i = 0; i < Table.hikeData.size(); i++) {

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
		} catch (IOException e){

			new Alerts("Error", "There was an error saving the Master Table to file!");
		}	
	}

	// read in variables into object, and then load that object into table
	public void loadMasterTable(){

		try {
			FileReader fr = new FileReader(masterTablePath);
			BufferedReader br = new BufferedReader(fr);
			Scanner scan = new Scanner(fr);
			while (scan.hasNextLine())
			{
				HikeData readData = new HikeData();

				Table.date =  scan.nextLine();
				Table.state = scan.nextLine();
				Table.location = scan.nextLine();		      
				Table.trailName = scan.nextLine();		      
				Table.miles = scan.nextDouble();		      
				Table.hours = scan.nextInt();	      
				Table.minutes = scan.nextInt();
				// System.out.println("here");
				//System.out.println("Date: " + date + "\nlocation: " +location + "\nTrailname: " +trailName +"\nmiles: " +miles + "\nhours: " +hours + "\nminutes: " + minutes);
				scan.nextLine(); // ignore the result
				readData = new HikeData(Table.date, Table.state,  Table.location, Table.trailName, Table.miles, Table.hours, Table.minutes);
				Table.table.getItems().addAll(readData);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}


	public void saveStats(int hikes, double miles, int minutes, int hours, int days, int months, int years){			

		try{
			FileWriter fw = new FileWriter ("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\" + Table.state + "\\Stats for " + Table.state + ".txt");
			PrintWriter pw = new PrintWriter(fw);

			d.setHikeCounter(hikes);
			pw.println(d.getHikeCounter());

			d.setMiles(miles);
			pw.println(d.getMiles());

			d.setMinutes(minutes);
			pw.println(d.getMinutes());

			d.setHours(hours);
			pw.println(d.getHours());

			d.setDays(days);
			pw.println(d.getDays());

			d.setMonths(months);
			pw.println(d.getMonths());

			d.setYears(years);
			pw.println(d.getYears());

			pw.close(); 
		} catch (Exception e){
			e.printStackTrace();

			new Alerts("Error", "There was an error saving the stats to file!" );
		}	

	}

	public void loadStats(String state){

		try{			 

			FileReader fr = new FileReader("C:\\Users\\" + userProfile +"\\JustHike\\JustHike\\" + state + "\\Stats for " + state + ".txt");
			BufferedReader br = new BufferedReader(fr);
			Scanner scan = new Scanner(fr);
			while(scan.hasNext()){

				Table.totalHikes = scan.nextInt();
				Table.totalMilesHiked = scan.nextDouble();
				Table.totalMinutesHiked = scan.nextInt();
				Table.totalHoursHiked = scan.nextInt();
				Table.totalDaysHiked = scan.nextInt();
				Table.totalMonthsHiked = scan.nextInt();
				Table.totalYearsHiked = scan.nextInt();
				Table.data = new HikeData(Table.hikes, Table.miles, Table.minutes, Table.hours, Table.days, Table.months, Table.years);

			}

			br.close();
			scan.close();
			fr.close();

		} catch(Exception e){ 

			new Alerts("Error", "There was an error loading the stats from file!");
			e.printStackTrace();
		}
	}


	public void saveLifetimeStats(int hikes, double miles, int minutes, int hours, int days, int months, int years) {

		try{

			FileWriter fw = new FileWriter (lifetimePath);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(hikes);  //System.out.println("I am in Hikes Save Lifetime: "+ hikes);
			pw.println(miles);
			pw.println(minutes);
			pw.println(hours);
			pw.println(days);
			pw.println(months);
			pw.println(years);

			//System.out.println(("Im minutes in save: " + StateDisplayTab.totalLifetimeMinutes));

			pw.close(); 
		} catch (Exception e){

			new Alerts("Error", "There was an error writing the data to file!" );
		}	
	}

	public void loadLifetimeStats() {

		File f = new File(lifetimePath);
		if(!f.exists() && !f.isFile()) {

			File file = new File(lifetimePath);
		}

		else   {	
			try{			 

				FileReader fr = new FileReader(lifetimePath);

				BufferedReader br = new BufferedReader(fr);

				Scanner scan = new Scanner(fr);

				while(scan.hasNext()){

					StateDisplayTab.totalLifetimeHikes = scan.nextInt();// System.out.println("I am in hike in Load Lifetime: "+ StateDisplayTab.totalLifetimeHikes);
					StateDisplayTab.totalLifetimeMiles = scan.nextDouble();
					StateDisplayTab.totalLifetimeMinutes = scan.nextInt();  
					StateDisplayTab.totalLifetimeHours = scan.nextInt();
					StateDisplayTab.totalLifetimeDays =  scan.nextInt();
					StateDisplayTab.totalLifetimeMonths=  scan.nextInt();
					StateDisplayTab.totalLifetimeYears= scan.nextInt();
					//	System.out.println("total hikes in IO " + StateDisplayTab.totalLifetimeHikes);

				}

				br.close();
				scan.close();
				fr.close();

			} catch(Exception e){

				new Alerts("Error", "There was an error reading the lifetime data from file!");
				e.printStackTrace();
			}

		}

	}


	public void saveCSV( String date, String state, String location, String trailName, double miles, int hours, int minutes, ObservableList<HikeData> subentries) throws IOException{

		HikeData data = new HikeData();				

		File file = new File("C:\\Users\\" + userProfile + "\\Desktop\\Trail CSV\\" + Table.comboSelection() + " Trails.csv");
		Writer writer = new BufferedWriter(new FileWriter(file));
		String columns = "DATE" + "," + "HIKE LOCATION" + "," + "TRAIL HIKED" + "," + "MILES HIKED" +"\n";
		writer.write(columns);

		try{

			for(int i = 0; i < subentries.size(); i++) {			
				data = subentries.get(i);

				//System.out.print(data.getTrailName());

				data.getDate(); 
				data.getState();
				data.getLocation();
				data.getTrailName();
				data.getMiles();
				data.getHours();
				data.getMinutes();

				//Writing to the CSV
				String text = data.getDate() + "," + data.getLocation() + "," + data.getTrailName() + "," + data.getMiles() + "\n";
				writer.write(text);
			}

		} catch (IOException e){

			new Alerts("Error", "There was an error saving the Master Table to file!");

		}	

		finally{

			// Writing the total miles for the CSV
			String totals = "\n" + " " + "," + " " + "," + "Total Miles Hiked" + "," + d.getMiles() + "\n";
			writer.write(totals);

			writer.flush();
			writer.close();

		}


	}

	public void deleteTableSelection(String path, TableView<HikeData> table) throws IOException {

		//This gets the selected line and removes it from the table 
		ObservableList<HikeData> statsSelected, allStats;
		allStats = table.getItems();
		statsSelected = table.getSelectionModel().getSelectedItems();
		statsSelected.forEach(allStats::remove);	 

		//Here the file is being read in, the selected line to remove is found, trimmed from file, and the file gets re-saved.
		File inputFile = new File(path);
		File tempFile = new File(path);

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		// Deletes selected row from file 
		String lineToRemove = statsSelected.toString();
		String currentLine;

		while((currentLine = reader.readLine()) != null ) {

			// trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			if(trimmedLine.equals(lineToRemove)) continue;

			writer.write(currentLine + System.getProperty("line.separator"));

		}
		writer.close(); 
		reader.close(); 
		boolean successful = tempFile.renameTo(inputFile);

	}

}