package igu;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;
import modelo.excessoes.ExcecaoValidacao;
import modelo.servicos.SrvcDepartamento;
import modelo.servicos.SrvcVendedor;

public class CtrlFormularioVendedor implements Initializable {

	private Vendedor vendedor;
	private SrvcVendedor srvcVendedor;
	private SrvcDepartamento srvcDepartamento;
	private List<MntrMudancaDados> mntrMudancaDados = new ArrayList<>();

	@FXML private TextField txtId;
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private DatePicker dpDataAniversario;
	@FXML private TextField txtSalarioBase;
	@FXML private Button btoSalvar;
	@FXML private ComboBox<Departamento> cboxDepartamento;
	@FXML private Button btoCancelar;
	@FXML private Label lblErroNome;
	@FXML private Label lblErroEmail;
	@FXML private Label lblErroDataAniversario;
	@FXML private Label lblErroSalarioBase;

	private ObservableList<Departamento> obsListaDepartamento;

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public void setServicos(SrvcVendedor srvcVendedor, SrvcDepartamento srvcDepartamento) {
		this.srvcVendedor = srvcVendedor;
		this.srvcDepartamento = srvcDepartamento;
	}

	public void inscricaoMntrMudancaDados(MntrMudancaDados monitor) {
		mntrMudancaDados.add(monitor);
	}

	@FXML
	public void onBtoSalvarAcao(ActionEvent evento) {
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

			if (txtNome.getText().equals("null") || txtNome.getText().equals("")) {
				Utils.palcoAtual(evento).close();
			} else {
				srvcVendedor.salvarAtualizar(vendedor);
				notificaMntrMudancaDados();
				Utils.palcoAtual(evento).close();
			}
		} catch (BDExcecao e) {
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

	@FXML
	public void onBtoCancelarAcao(ActionEvent evento) {
		Utils.palcoAtual(evento).close();
	}

	private Vendedor getDadosFormulario() {
		Vendedor vendedor = new Vendedor();
		ExcecaoValidacao excecao = new ExcecaoValidacao("Erro de validacao");
		vendedor.setId(Utils.ParseInt(txtId.getText()));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			excecao.adicionarErro("nome", "Campo não pode ser vazio");
		}
		vendedor.setNome(txtNome.getText());
		
		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			excecao.adicionarErro("email", "Campo não pode ser vazio");
		}
		vendedor.setEmail(txtEmail.getText());
		
		if(dpDataAniversario.getValue() == null) {
			excecao.adicionarErro("dataAniversario", "Campo não pode ser vazio");
		}
		else {
			Instant instante = Instant.from(dpDataAniversario.getValue().atStartOfDay(ZoneId.systemDefault()));
			vendedor.setDataAniversario(Date.from(instante));
		}
		
		if (txtSalarioBase.getText() == null || txtSalarioBase.getText().trim().equals("")) {
			excecao.adicionarErro("salarioBase", "Campo não pode ser vazio");
		}
		vendedor.setSalarioBase(Utils.ParseDouble(txtSalarioBase.getText()));
		
		vendedor.setDepartamento(cboxDepartamento.getValue());

		if (excecao.getErros().size() > 0) {
			throw excecao;
		}

		return vendedor;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		iniciaNos();
	}

	private void iniciaNos() {
		Restricoes.setCampoTextoInteiro(txtId);
		Restricoes.setCampoTextoTamanhoMax(txtNome, 70);
		Restricoes.setCampoTextoDouble(txtSalarioBase);
		Restricoes.setCampoTextoTamanhoMax(txtEmail, 60);
		Utils.formatoData(dpDataAniversario, "dd/MM/yyyy");
		iniciaComboBoxDepartamento();
	}

	public void atualizaFormulario() {
		if (vendedor == null) {
			throw new IllegalStateException("Departamento vazio");
		}

		txtId.setText(String.valueOf(vendedor.getId()));

		if (String.valueOf(vendedor.getNome()).equals("null")) {
			txtNome.setText("");
		} else {
			txtNome.setText(String.valueOf(vendedor.getNome()));
		}

		if (String.valueOf(vendedor.getEmail()).equals("null")) {
			txtEmail.setText("");
		} else {
			txtEmail.setText(String.valueOf(vendedor.getEmail()));
		}

		if (vendedor.getDataAniversario() != null) {
			dpDataAniversario.setValue(LocalDate.ofInstant(vendedor.getDataAniversario().toInstant(), ZoneId.systemDefault()));
		}

		Locale.setDefault(Locale.US);
		txtSalarioBase.setText(String.format("%.2f", vendedor.getSalarioBase()));
		
		if(vendedor.getDepartamento() == null) {
			cboxDepartamento.getSelectionModel().selectFirst();
		}
		else {
			cboxDepartamento.setValue(vendedor.getDepartamento());
		}
	}

	public void carregaObjetosAssociados() {
		if (srvcDepartamento == null) {
			throw new IllegalStateException("Serviço de departamento vazio");

		}
		List<Departamento> lista = new ArrayList<>(srvcDepartamento.pesquisar());
		obsListaDepartamento = FXCollections.observableArrayList(lista);		
		cboxDepartamento.setItems(obsListaDepartamento);
	}

	private void setMsgsErro(Map<String, String> erros) {
		Set<String> campos = erros.keySet();

		//lblErroNome.setText( campos.contains("nome") ? erros.get("nome") : "");
		
		if (campos.contains("nome")) {
			lblErroNome.setText(erros.get("nome"));
		}
		else {
			lblErroNome.setText("");
		}
		
		if (campos.contains("email")) {
			lblErroEmail.setText(erros.get("email"));
		}
		else {
			lblErroEmail.setText("");
		}
		
		if (campos.contains("dataAniversario")) {
			lblErroDataAniversario.setText(erros.get("dataAniversario"));
		}
		else {
			lblErroDataAniversario.setText("");
		}
		
		if (campos.contains("salarioBase")) {
			lblErroSalarioBase.setText(erros.get("salarioBase"));
		}
		else {
			lblErroSalarioBase.setText("");
		}
	}

	private void iniciaComboBoxDepartamento() {
		Callback<ListView<Departamento>, ListCell<Departamento>> fabrica = param -> new ListCell<Departamento>() {
			@Override
			protected void updateItem(Departamento item, boolean vazio) {
				super.updateItem(item, vazio);
				setText(vazio ? "" : item.getNome());
			}
		};
		cboxDepartamento.setCellFactory(fabrica);
		cboxDepartamento.setButtonCell(fabrica.call(null));
	}
}
