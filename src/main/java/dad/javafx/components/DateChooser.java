package dad.javafx.components;

import java.net.URL;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class DateChooser extends HBox implements Initializable {

	// view
	// -----------------------

	@FXML
	private HBox view;

	@FXML
	private ComboBox<Integer> dayComboBox;

	@FXML
	private ComboBox<String> monthComboBox;

	@FXML
	private ComboBox<String> yearComboBox;

	// model
	// -----------------------
	private ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();

	private boolean bisiesto = false;

	private ArrayList<String> yearsArrayList = new ArrayList<String>();
	private ArrayList<String> monthsArrayList = new ArrayList<>(Arrays.asList("Enero", "Febrero", "Marzo", "Abril",
			"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"));

	ListProperty<Integer> daysList = new SimpleListProperty<>(FXCollections.observableArrayList());
	ListProperty<String> monthsList = new SimpleListProperty<>(FXCollections.observableArrayList());
	ListProperty<String> yearsList = new SimpleListProperty<>(FXCollections.observableArrayList());

	public DateChooser() {

		super();
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();

		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e); // testeo de otras formas de control de
																				// excepciones
			// e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		for (int i = LocalDate.now().getYear(); i >= 1900; i--)
			yearsArrayList.add(Integer.toString(i));

		yearsList.addAll(yearsArrayList);
		monthsList.addAll(monthsArrayList);

		yearComboBox.setItems(yearsList);
		yearComboBox.getSelectionModel().selectFirst();

		monthComboBox.setItems(monthsList);
		monthComboBox.getSelectionModel().selectFirst();

		modMonth();

		dayComboBox.getSelectionModel().selectFirst();

		monthComboBox.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				modMonth();

			}
		});

		yearComboBox.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				modYear(oldValue, newValue);

			}

		});

	}

	private void modMonth() {

		setNumbersOfDays(Month.of(monthComboBox.getSelectionModel().getSelectedIndex() + 1).length(bisiesto));

	}

	private void setNumbersOfDays(int max) {

		dayComboBox.getItems().clear();
		daysList.clear();

		for (int i = 1; i <= max; i++)
			daysList.add(i);

		dayComboBox.setItems(daysList);
		dayComboBox.getSelectionModel().selectFirst();

	}

	private void modYear(String oldValue, String newValue) {

		try {

			if (Integer.parseInt(newValue) < 1)
				throw new Exception();

		} catch (Exception e) {

			yearComboBox.setValue(oldValue);

		}

		if (Year.of(Integer.parseInt(yearComboBox.getValue())).isLeap())
			bisiesto = true;
		else
			bisiesto = false;

		modMonth();

	}

	public final ObjectProperty<LocalDate> datePropertyProperty() {
		return this.dateProperty;
	}

	public final LocalDate getDateProperty() {
		return this.datePropertyProperty().get();
	}

	public final void setDateProperty(final LocalDate dateProperty) {
		this.datePropertyProperty().set(dateProperty);
	}

}
