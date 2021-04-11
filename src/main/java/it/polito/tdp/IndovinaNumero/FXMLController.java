/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.IndovinaNumero;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import it.polito.tdp.IndovinaNumero.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="layoutTentativo"
    private HBox layoutTentativo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNuovaPartita"
    private Button btnNuovaPartita; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativi"
    private TextField txtTentativi; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativoUtente"
    private TextField txtTentativoUtente; // Value injected by FXMLLoader

    @FXML // fx:id="btnProva"
    private Button btnProva; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader
    
    @FXML
    private ProgressBar barTentativi;
    
    @FXML
    private ChoiceBox<String> boxDifficolta;
    
    @FXML
    private ChoiceBox<String> boxAssistito;
    
    int nmin;
    int nmax;

    @FXML
    void doNuovaPartita(ActionEvent event) {
    	
    	int difficolta = 0;
    	
    	if (boxDifficolta.getValue()==null) {
    		txtRisultato.setText("Scegliere la difficoltà.");
    		return;
    	} else if (boxDifficolta.getValue().equals("Facile")) {
    		difficolta = 1;
    	} else if (boxDifficolta.getValue().equals("Medio")) {
    		difficolta = 2;
    	} else if (boxDifficolta.getValue().equals("Difficile")) {
    		difficolta = 3;
    	}
    	
    	if (boxAssistito.getValue()==null) {
    		txtRisultato.setText("Inserire una modalità.");
    		return;
    	}
    	
    	//inizio la partita
    	this.model.nuovaParita(difficolta);
    	
    	nmin = 0;
    	nmax = this.model.getNMAX();
    	
    	//gestione dell'interfaccia
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.barTentativi.setProgress(0);
    	this.txtRisultato.clear();
    	this.txtTentativoUtente.clear();
    	
    }

    @FXML
    void doTentativo(ActionEvent event) {

    	//lettura input dell'utente
    	String ts = txtTentativoUtente.getText();
    	int tentativo;
    	try {
    		tentativo = Integer.parseInt(ts);
    	} catch (NumberFormatException e) {
    		txtRisultato.setText("Devi inserire un numero!"); //appendText se voglio aggiungere senza cancellare cosa c'era prima
    		return;
    	}
    	
    	this.txtTentativoUtente.setText("");
    	
    	int result;
    	try {
    		result = this.model.tentativo(tentativo);
    	}
    	catch (IllegalStateException se){
    		this.txtRisultato.setText(se.getMessage());
    		this.layoutTentativo.setDisable(true);
    		this.barTentativi.setProgress(1);
    		this.txtTentativi.setText("0");
    		return;
    	}
    	catch (InvalidParameterException pe) {
    		this.txtRisultato.setText(pe.getMessage());
    		return;
    	}
    	
    	if (result == 0) {
    		//Ho indovinato!
    		txtRisultato.setText("Hai vinto con " + this.model.getTentativiFatti() +" tentativi!");
    		this.layoutTentativo.setDisable(true);
    		return;
    	}
    	else if (result <0) {
    		txtRisultato.setText("Tentativo troppo basso\n");
    		if (boxAssistito.getValue().equals("Assistito")) {
    			if (nmin<Integer.parseInt(ts)) {
    				nmin = Integer.parseInt(ts);
    			}
    			txtRisultato.appendText("Prova a inserire un numero da " + nmin + " a " + nmax);
    		}
    	}
    	else {
    		txtRisultato.setText("Tentativo troppo alto\n");
    		if (boxAssistito.getValue().equals("Assistito")) {
    			if (nmax>Integer.parseInt(ts)) {
    				nmax = Integer.parseInt(ts);
    			}
    			txtRisultato.appendText("Prova a inserire un numero da " + nmin + " a " + nmax);
    		}
    	}
    	
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()-this.model.getTentativiFatti()));
    	this.barTentativi.setProgress((double) this.model.getTentativiFatti()/this.model.getTMAX());
    	
 }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnNuovaPartita != null : "fx:id=\"btnNuovaPartita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert layoutTentativo != null : "fx:id=\"layoutTentativo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativoUtente != null : "fx:id=\"txtTentativoUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert barTentativi != null : "fx:id=\"barTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxDifficolta != null : "fx:id=\"boxDifficolta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAssistito != null : "fx:id=\"boxAssistito\" was not injected: check your FXML file 'Scene.fxml'.";
        
        this.boxDifficolta.getItems().add("Facile");
        this.boxDifficolta.getItems().add("Medio");
        this.boxDifficolta.getItems().add("Difficile");
        
        this.boxAssistito.getItems().add("Normale");
        this.boxAssistito.getItems().add("Assistito");
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}