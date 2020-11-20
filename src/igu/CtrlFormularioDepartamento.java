package igu;

import java.net.URL;
import java.util.ResourceBundle;

import igu.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.entidades.Departamento;
import modelo.entidades.Departamento;

public class CtrlFormularioDepartamento implements Initializable{

	private Departamento departamento;
	
	@FXML private TextField txtId;
	@FXML private TextField txtNome;
	@FXML private Label lblErroNome;
	@FXML private Button btoSalvar;
	@FXML private Button btoCancelar;
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@FXML public void onBtoSalvarAcao() {
		System.out.println("onBtoSalvarAcao");
	}
	
	@FXML public void onBtoCancelarAcao() {
		System.out.println("onBtoCancelarAcao");
	}
	
	@Override public void initialize(URL url, ResourceBundle rb) {		
		iniciaNos();
	}

	private void iniciaNos() {
		Restricoes.setCampoTextoInteiro(txtId);
		Restricoes.setCampoTextoTamanhoMax(txtNome, 30);
	}
	
	public void atualizaFormulario() {
		if (departamento == null) {
			throw new IllegalStateException("Departamento vazio");
		}
		
		if (departamento.getId() == null) {
			txtId.setText(String.valueOf("") );
		}
		else {
			txtId.setText(String.valueOf(departamento.getId() ) );
		}
		if (departamento.getNome() == null) {
			txtNome.setText(String.valueOf("") );
		}
		else {
			txtNome.setText(String.valueOf(departamento.getNome() ) );
		}
	}
}
