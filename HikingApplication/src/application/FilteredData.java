package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilteredData {
	
	 // Define a variable to store the property
    private StringProperty filterCount = new SimpleStringProperty();
    private StringProperty milesCount = new SimpleStringProperty();
    private StringProperty daysCount = new SimpleStringProperty();
    private StringProperty hoursCount = new SimpleStringProperty();
 
    // Define a getter for the property's value
    public final String getFilterCount(){return filterCount.get();}
 
    // Define a setter for the property's value
    public final void setFilterCount(String string){filterCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty filterCountProperty() {return filterCount;}
    //-----------------
    // Define a getter for the property's value
    public final String getMilesCount(){return milesCount.get();}
 
    // Define a setter for the property's value
    public final void setMilesCount(String string){milesCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty milesrCountProperty() {return milesCount;}
    //-------------------
    public final String getDaysCount(){return daysCount.get();}
    
    // Define a setter for the property's value
    public final void setDaysCount(String string){daysCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty daysCountProperty() {return daysCount;}
    //----------------------------
    public final String getHoursCount(){return hoursCount.get();}
    
    // Define a setter for the property's value
    public final void setHoursCount(String string){hoursCount.set(string);}
 
     // Define a getter for the property itself
    public StringProperty hoursCountProperty() {return hoursCount;}
    

}
