package application;

import java.io.File;
import java.text.DecimalFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelperFunctions {

	final String decimalPattern = "\\d{0,3}([\\.]\\d{0,2})?";
	final String integerPattern = "\\d*";


	// Method checks for regex patterns for the input fields, will not allow any
	// input that does not meet the pattern.
	public void inputCheck(TextField tf, String pattern) {

		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches(pattern)) {
					tf.setText(oldValue);
				}
			}

		});
	}

	// if user selects a different state while still using the same instance of
	// Table, this will clear the values.
	public void refreshValuesForState(String temp, String state, double totalMilesHiked, int totalHoursHiked,
			int totalMinutesHiked) {

		if (!temp.equals(state)) {

			totalMilesHiked = 0;
			totalHoursHiked = 0;
			totalMinutesHiked = 0;
		}
	}

	// textFunction
	public Text textFunction(String text, int fontSize, boolean underline) {
		Text t = new Text(text);
		t.setFill(Color.WHITE);
		t.setFont(Font.font(fontSize));
		t.setUnderline(underline);

		return t;
	}

	// Method that creates tooltips
	public void setToolTip(TextField tf, Tooltip t, String message) {

		t = new Tooltip();
		t.setText(message);
		tf.setTooltip(t);

	}

	// Enables and disables all input when in filter
	public void disableFields(TextField filterInput, TextField location, TextField trailname, TextField miles,
			TextField hours, TextField minutes, DatePicker dp, ComboBox<String> combo) {

		if (filterInput.getLength() >= 1) {

			location.setDisable(true);
			trailname.setDisable(true);
			miles.setDisable(true);
			hours.setDisable(true);
			minutes.setDisable(true);
			dp.setDisable(true);
			combo.setDisable(true);
		}

		if (filterInput.getLength() < 1) {

			location.setDisable(false);
			trailname.setDisable(false);
			miles.setDisable(false);
			hours.setDisable(false);
			minutes.setDisable(false);
			dp.setDisable(false);
			combo.setDisable(false);
		}

	}

	// Converts minutes to hours and hours to days and throws them in a
	// simpleStringProperty for immediate results, also displays total table count
	// when inside filter.
	public void filterConversions(TableView<HikeData> table, Text totalMilesLabel, Text totalHoursLabel,
			Text totalDaysLabel, Text totalCountLabel) {

		int minutes = 0;
		int hoursTotal = 0;
		int days = 0;
		int mod = 0;
		double milesTotal = 0;
		FilteredData filtered = new FilteredData();
		DecimalFormat numberFormat = new DecimalFormat("#.00");

		for (HikeData item : table.getItems()) {
			milesTotal = milesTotal + item.getMiles();
			String formatedMiles = numberFormat.format(milesTotal);
			filtered.setMilesCount(formatedMiles);
			totalMilesLabel.textProperty().bindBidirectional(new SimpleStringProperty(filtered.getMilesCount()));

			minutes = minutes + item.getMinutes();

			mod = 60;
			while (minutes >= mod) {

				minutes = minutes - mod;
				hoursTotal++;

			}

			hoursTotal = hoursTotal + item.getHours();

			mod = 24;
			while (hoursTotal >= mod) {

				hoursTotal = hoursTotal - mod;
				days++;
			}

			filtered.setHoursCount(Integer.toString(hoursTotal));
			totalHoursLabel.textProperty().bindBidirectional(new SimpleStringProperty(filtered.getHoursCount()));

			filtered.setDaysCount(Integer.toString(days));
			totalDaysLabel.textProperty().bindBidirectional(new SimpleStringProperty(filtered.getDaysCount()));

			filtered.setFilterCount(Integer.toString(table.getItems().size()));
			totalCountLabel.textProperty().bindBidirectional(new SimpleStringProperty(filtered.getFilterCount()));

		}
	}

	public void createPath(File file, String path) {
		
		file = new File(path );

		if(!file.exists() && !file.isFile()) {

			file.mkdirs();
		}
	}
}
