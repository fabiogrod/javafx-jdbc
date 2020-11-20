package igu;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bd.BDExcecao;
import igu.monitores.MntrMudancaDados;
import igu.util.Alertas;
import igu.util.Restricoes;
import igu.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.entidades.Departamento;
import modelo.servicos.SrvcDepartamento;

public class CtrlFormularioDepartamento implements Initializable{

	private Departamento departamento;
	private SrvcDepartamento srvcDepartamento;
	private List<MntrMudancaDados> mntrMudancaDados = new ArrayList<>();
	
	@FXML private TextField txtId;
	@FXML private TextField txtNome;
	@FXML private Label lblErroNome;
	@FXML private Button btoSalvar;
	@FXML private Button btoCancelar;
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public void setSrvcDepartamento(SrvcDepartamento srvcDepartamento) {
		this.srvcDepartamento = srvcDepartamento;
	}
	
	public void inscricaoMntrMudancaDados(MntrMudancaDados monitor) {
		mntrMudancaDados.add(monitor);
	}

	@FXML public void onBtoSalvarAcao(ActionEvent evento) {
		try {
			if (departamento == null)
			{
				throw new IllegalAccessException("Departamento vazio");
			}
			
			if (srvcDepartamento == null)
			{
				throw new IllegalAccessException("ServiçoDepartamento vazio");
			}
			
			departamento = getDadosFormulario();
			
//			String teste = String.valueOf( txtNome.getText() );
//			
//			System.out.println("String.valueOf( txtNome.getText() - " + String.valueOf( txtNome.getText() ) );			
//			System.out.println("txtNome: " + teste.equals("null") );
//			
//			System.out.println("departamento: " + departamento.getNome().equals("null") );	
							
			if (txtNome.getText().equals("null") || txtNome.getText().equals("") ) {
				Utils.palcoAtual(evento).close();
			}
			else {
				srvcDepartamento.salvarAtualizar(departamento);
				notificaMntrMudancaDados();
				Utils.palcoAtual(evento).close();
			}
		}
		catch (BDExcecao e) {
			Alertas.mostraAlertas("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
		catch (IllegalAccessException e) {			
			Alertas.mostraAlertas("IllegalAccessException", "Erro ao carregar visualização", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificaMntrMudancaDados() {
		
		for (MntrMudancaDados monitor : mntrMudancaDados) {
			monitor.onDataChanged();
		}
	}

	@FXML public void onBtoCancelarAcao(ActionEvent evento) {
		Utils.palcoAtual(evento).close();
	}	
	
	private Departamento getDadosFormulario() {
		Departamento departamento = new Departamento();
		departamento.setId(Utils.ParseInt(txtId.getText() ) );
		departamento.setNome(txtNome.getText() );
		return departamento;
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
		
		txtId.setText(String.valueOf(departamento.getId() ) );
		
		if ( String.valueOf(departamento.getNome() ).equals("null") ) {
			txtNome.setText("");			
		}
		else {
			txtNome.setText(String.valueOf(departamento.getNome() ) );
		}
	}
}
