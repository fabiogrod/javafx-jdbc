package igu;

import java.net.URL;
import java.util.ResourceBundle;

import igu.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CtrlFormularioDepartamento implements Initializable{

	@FXML private TextField txtId;
	@FXML private TextField txtNome;
	@FXML private Label lblErroNome;
	@FXML private Button btoSalvar;
	@FXML private Button btoCancelar;
	
	@FXML public void onBtoSalvarAcao() {
		System.out.println("onBtoSalvarAcao");
	}
	
	@FXML public void onBtoCancelarAcao() {
		System.out.println("onBtoCancelarAcao");
	}
	
	@Override public void initialize(URL url, ResourceBundle rb) {
		
		
		
	}

	private void iniciaNos() {
		Restricoes.setCampoTextoInteiro(txtId);
		Restricoes.setCampoTextoTamanhoMax(txtNome, 30);
	}
}
