package igu;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import aplicacao.Main;
import igu.util.Alertas;
import igu.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.entidades.Departamento;
import modelo.servicos.SrvcDepartamento;

public class CtrlVisuListaDepartamento implements Initializable {

	private SrvcDepartamento srvcDepartamento; 
	
	@FXML private TableView<Departamento> tabelaDepartamento;
	@FXML private TableColumn<Departamento, Integer> colunaIdDepartamento;
	@FXML private TableColumn<Departamento, String> colunaNome;
	
	@FXML private Button btoNovo;
	
	private ObservableList<Departamento> listaObs;
	
	@FXML public void onBtoNovoAcao(ActionEvent evento) {
		Stage palcoPrincipal = Utils.palcoAtual(evento);
		Departamento departamento = new Departamento();
		geraDialogoFormulario(departamento, "/igu/VisuFormularioDepartamento.fxml", palcoPrincipal);
	}
	
	public void setSrvcDepartamento(SrvcDepartamento srvcDepartamento) {
		this.srvcDepartamento = srvcDepartamento;
	}
	
	@Override public void initialize(URL url, ResourceBundle rb) {
		iniciaNos();
	}
	
	public void iniciaNos() {
		colunaIdDepartamento.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
//		colunaNome.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getNome()));
		
		Stage palco = (Stage)Main.getCenaPrincipal().getWindow();
		tabelaDepartamento.prefHeightProperty().bind(palco.heightProperty());
	}
	
	public void atualizaVisuTabela() {
		if (srvcDepartamento == null) {
			throw new IllegalStateException("Serviço nulo.");
		}
		
		List<Departamento> lista = srvcDepartamento.pesquisar();
		
		listaObs = FXCollections.observableArrayList(lista);
		
		tabelaDepartamento.setItems(listaObs);
		
	}
	
	private void geraDialogoFormulario(Departamento departamento,String nomeAbsoluto, Stage palcoPrincipal) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = carregador.load();
			
			CtrlFormularioDepartamento ctrlDepartamento = carregador.getController();
			ctrlDepartamento.setDepartamento(departamento);
			ctrlDepartamento.setSrvcDepartamento(new SrvcDepartamento());
			ctrlDepartamento.atualizaFormulario();
			
			Stage palcoDialogo = new Stage();
			palcoDialogo.setTitle("Digite as informações do novo departamento: ");
			palcoDialogo.setScene(new Scene(painel));
			palcoDialogo.setResizable(false);
			palcoDialogo.initOwner(palcoPrincipal);
			palcoDialogo.initModality(Modality.WINDOW_MODAL);
			palcoDialogo.showAndWait();
		}
		catch (IOException e) {
			Alertas.mostraAlertas("IOException", "Erro ao carregar visualização", e.getMessage(), AlertType.ERROR);
		}
	}
}
