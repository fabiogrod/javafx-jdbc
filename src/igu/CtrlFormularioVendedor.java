package igu;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.entidades.Vendedor;
import modelo.excessoes.ExcecaoValidacao;
import modelo.servicos.SrvcVendedor;

public class CtrlFormularioVendedor implements Initializable{

	private Vendedor vendedor;
	private SrvcVendedor srvcVendedor;
	private List<MntrMudancaDados> mntrMudancaDados = new ArrayList<>();
	
	@FXML private TextField txtId;
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private DatePicker dpDataAniversario;
	@FXML private TextField txtSalarioBase;
	@FXML private Label lblErroNome;
	@FXML private Label lblErroEmail;
	@FXML private Label lblErroDataAniversario;
	@FXML private Label lblErroSalarioBase;
	@FXML private Button btoSalvar;
	@FXML private Button btoCancelar;
	
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	
	public void setSrvcVendedor(SrvcVendedor srvcVendedor) {
		this.srvcVendedor = srvcVendedor;
	}
	
	public void inscricaoMntrMudancaDados(MntrMudancaDados monitor) {
		mntrMudancaDados.add(monitor);
	}

	@FXML public void onBtoSalvarAcao(ActionEvent evento) {
		try {
//			if (vendedor == null)
//			{
//				throw new IllegalAccessException("Departamento vazio");
//			}
//			
//			if (srvcDepartamento == null)
//			{
//				throw new IllegalAccessException("ServiçoDepartamento vazio");
//			}
			
			vendedor = getDadosFormulario();
			
//			String teste = String.valueOf( txtNome.getText() );
//			
//			System.out.println("String.valueOf( txtNome.getText() - " + String.valueOf( txtNome.getText() ) );			
//			System.out.println("txtNome: " + teste.equals("null") );
//			
//			System.out.println("vendedor: " + vendedor.getNome().equals("null") );	
							
			if (txtNome.getText().equals("null") || txtNome.getText().equals("") ) {
				Utils.palcoAtual(evento).close();
			}
			else {
				srvcVendedor.salvarAtualizar(vendedor);
				notificaMntrMudancaDados();
				Utils.palcoAtual(evento).close();
			}
		}
		catch (BDExcecao e) {
			Alertas.mostraAlertas("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
//		catch (IllegalAccessException e) {			
//			Alertas.mostraAlertas("IllegalAccessException", "Erro ao carregar visualização", e.getMessage(), AlertType.ERROR);
//		}
		catch (ExcecaoValidacao e) {
			setMsgsErro(e.getErros());
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
	
	private Vendedor getDadosFormulario() {
		Vendedor vendedor = new Vendedor();
		
		ExcecaoValidacao excecao = new ExcecaoValidacao("Erro de validacao");
		
		vendedor.setId(Utils.ParseInt(txtId.getText() ) );
		
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			excecao.adicionarErro("nome", "Campo não pode ser vazio");
		}
			
		vendedor.setNome(txtNome.getText() );
		
		if (excecao.getErros().size() > 0) {
			throw excecao;
		}
		
		return vendedor;
	}
	
	@Override public void initialize(URL url, ResourceBundle rb) {		
		iniciaNos();
	}

	private void iniciaNos() {
		Restricoes.setCampoTextoInteiro(txtId);
		Restricoes.setCampoTextoTamanhoMax(txtNome, 70);
		Restricoes.setCampoTextoDouble(txtSalarioBase);
		Restricoes.setCampoTextoTamanhoMax(txtEmail, 60);
		Utils.formatoData(dpDataAniversario, "dd/MM/yyyy");
	}
	
	public void atualizaFormulario() {
		if (vendedor == null) {
			throw new IllegalStateException("Departamento vazio");
		}	
		
		txtId.setText(String.valueOf(vendedor.getId() ) );
		
		if ( String.valueOf(vendedor.getNome() ).equals("null") ) {
			txtNome.setText("");			
		}
		else {
			txtNome.setText(String.valueOf(vendedor.getNome() ) );
		}
		
		if ( String.valueOf(vendedor.getEmail() ).equals("null") ) {
			txtEmail.setText("");			
		}
		else {
			txtEmail.setText(String.valueOf(vendedor.getEmail() ) );
		}
		
		if (vendedor.getDataAniversario() != null) {
			dpDataAniversario.setValue(LocalDate.ofInstant(vendedor.getDataAniversario().toInstant(), ZoneId.systemDefault() ) );
		}
		
		Locale.setDefault(Locale.US);
		txtSalarioBase.setText(String.format("%.2f", vendedor.getSalarioBase() ) );
	}
	
	private void setMsgsErro(Map<String, String> erros) {
		Set<String> campos = erros.keySet();
		
		if(campos.contains("nome")) {
			lblErroNome.setText(erros.get("nome"));
		}
	}
}
