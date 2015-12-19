package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.UUID;

import base.RateDAL;
import ch.makery.address.MainApp;
import ch.makery.address.model.Rate;


public class MortgageController {
	
	ObservableList<Integer> termList = FXCollections.observableArrayList(15, 30); 
	
	@FXML
	private TextField income;
	
	@FXML
	private TextField expense;
	
	@FXML
	private TextField creditScore;
	
	@FXML
	private ComboBox term;
	
	@FXML
	private TextField HouseCost;
	
	@FXML
	private Label Mortgage;
	
	@FXML
	private void handleMortgage() {
		double hc = Double.parseDouble(HouseCost.getText());
		int cs = Integer.parseInt(creditScore.getText());
		int n = (int) term.getSelectionModel().getSelectedItem();
		double in = Double.parseDouble(income.getText());
		double ex = Double.parseDouble(expense.getText());
		
		double mortgage = RateDAL.CalculateMortgage(hc, cs, n);
		
		boolean eligible = RateDAL.isEligible(in, ex, mortgage);
		
		String str = null;
		if (eligible) {
			str = "Mortgage is $" + mortgage;
		} else {
			str = "Sorry, you do not qualify";
		}
		
		Mortgage.setText(str);
		Mortgage.setVisible(true);
	}


    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MortgageController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	term.setValue((int)15);
    	term.setItems(termList);
    	Mortgage.setVisible(false);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    
}