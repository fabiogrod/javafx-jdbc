package igu;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import aplicacao.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	@FXML public void onBtoNovoAcao() {
		System.out.println("onBtoNovoAcao");
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
}
