package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilteredData {
	
	 // Define a variable to store the property
    private StringProperty totalCount = new SimpleStringProperty();
    private StringProperty totalMilesCount = new SimpleStringProperty();
    private StringProperty totalDaysCount = new SimpleStringProperty();
    private StringProperty totaHoursCount = new SimpleStringProperty();
 
    // Define a getter for the property's value
    public final String getFilterCount(){return totalCount.get();}
 
    // Define a setter for the property's value
    public final void setFilterCount(String string){totalCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty filterCountProperty() {return totalCount;}
    //-----------------
    // Define a getter for the property's value
    public final String getMilesCount(){return totalMilesCount.get();}
 
    // Define a setter for the property's value
    public final void setMilesCount(String string){totalMilesCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty milesrCountProperty() {return totalMilesCount;}
    //-------------------
    public final String getDaysCount(){return totalDaysCount.get();}
    
    // Define a setter for the property's value
    public final void setDaysCount(String string){totalDaysCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty daysCountProperty() {return totalDaysCount;}
    //----------------------------
    public final String getHoursCount(){return totaHoursCount.get();}
    
    // Define a setter for the property's value
    public final void setHoursCount(String string){totaHoursCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty hoursCountProperty() {return totaHoursCount;}
    

}
