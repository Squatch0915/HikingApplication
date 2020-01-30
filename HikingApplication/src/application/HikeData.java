package application;

import  java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HikeData implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2819859796634582090L;
	private String state;
	private String date;
	private String location;
	private String trailName;
	private int hikeCounter;
    private double miles;
    private int hours;
    private int minutes;
    private int days;
    private int months;
    private int years;
    
 // Define a variable to store the property
    private StringProperty filterCount = new SimpleStringProperty();
 
    // Define a getter for the property's value
    public final String getFilterCount(){return filterCount.get();}
 
    // Define a setter for the property's value
    public final void setFilterCount(String string){filterCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty filterCountProperty() {return filterCount;}
    

    public HikeData(){
    	this.hikeCounter = 0;
    	this.state="";
    	this.date ="";   
    	this.location = "";
        this.trailName = "";
        this.miles = 0;
        this.minutes = 0;
        this.hours = 0;
        this.days = 0;
        this.months = 0;
        this.years = 0;

  
    }

    public HikeData(int hikeCounter,double miles, int minutes, int hours, int days, int months, int years ){

    	this.hikeCounter = hikeCounter;
        this.miles = miles;
        this.minutes = minutes;
        this.hours = hours;
        this.days = days;
        this.months = months;
        this.years = years;
    }
    
    public HikeData(String date, String state, String location,  String trailName, double miles, int hours, int minutes){
    	
    	this.date = date;
    	this.state = state;
    	this.location = location;
        this.trailName = trailName;
        this.miles = miles;
        this.minutes = minutes;
        this.hours = hours;
    }
    
	public int getHikeCounter() {
		return hikeCounter;
	}

	public void setHikeCounter(int hikeCounter) {
		this.hikeCounter = hikeCounter;
	}
    
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public final String getTrailName() {
		return trailName;
	}

	public final void setTrailName(String trailName) {
		this.trailName = trailName;
	}

	public final double getMiles() {
		return miles;
	}

	public final void setMiles(double miles) {
		this.miles = miles;
	}

	public final int getHours() {
		return hours;
	}

	public final void setHours(int hours) {
		this.hours = hours;
	}

	public final int getMinutes() {
		return minutes;
	}

	public final void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public final int getDays() {
		return days;
	}
	public final void setDays(int days) {
		this.days = days;
	}
	public final int getMonths() {
		return months;
	}
	public final void setMonths(int months) {
		this.months = months;
	}
	public final int getYears() {
		return years;
	}
	public final void setYears(int years) {
		this.years = years;
	}
	

}