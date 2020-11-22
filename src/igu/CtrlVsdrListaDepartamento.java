package igu;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import aplicacao.Main;
import bd.BDExcecaoIntegridade;
import igu.monitores.MntrMudancaDados;
import igu.util.Alertas;
import igu.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.entidades.Departamento;
import modelo.servicos.SrvcDepartamento;

public class CtrlVsdrListaDepartamento implements Initializable, MntrMudancaDados {

	private SrvcDepartamento srvcDepartamento; 
	
	@FXML private TableView<Departamento> tabelaDepartamento;
	@FXML private TableColumn<Departamento, Integer> colunaIdDepartamento;
	@FXML private TableColumn<Departamento, String> colunaNome;
	@FXML private TableColumn<Departamento, Departamento> colunaEditar;
	@FXML private TableColumn<Departamento, Departamento> colunaRemover;
	
	@FXML private Button btoNovo;
	
	private ObservableList<Departamento> listaObs;
	
	@FXML public void onBtoNovoAcao(ActionEvent evento) {
		Stage palcoPrincipal = Utils.palcoAtual(evento);
		Departamento departamento = new Departamento();
		geraDialogoFormulario(departamento, "/igu/VsdrFormularioDepartamento.fxml", palcoPrincipal);
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
	
	public void iniciaBotoesEditar() {
		
		colunaEditar.setCellValueFactory(colunaEditar.getCellValueFactory());
		
		colunaEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue() ) );
		colunaEditar.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button botaoEditar = new Button("Editar");
			
			@Override protected void updateItem(Departamento departamento, boolean vazio) {
				super.updateItem(departamento, vazio);
				if (departamento == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(botaoEditar);
				
				botaoEditar.setOnAction(evento -> geraDialogoFormulario(departamento, "/igu/VsdrFormularioDepartamento.fxml", Utils.palcoAtual(evento) ) );
			}			
		});
		colunaEditar.setStyle("-fx-alignment: center");
	}
	
	public void iniciaBotoesRemover() {
		
		colunaRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue() ) );
		colunaRemover.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button botaoRemover = new Button("Remover");
			
			@Override protected void updateItem(Departamento departamento, boolean vazio) {
				super.updateItem(departamento, vazio);
				if (departamento == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(botaoRemover);
				
				botaoRemover.setOnAction(evento -> removerDepartamento(departamento) );
			}				
		});
		colunaRemover.setStyle("-fx-alignment: center");
	}
	
	public void atualizaVsdrTabela() {
		if (srvcDepartamento == null) {
			throw new IllegalStateException("Serviço sem dados.");
		}
		
		List<Departamento> lista = srvcDepartamento.pesquisar();
		
		listaObs = FXCollections.observableArrayList(lista);
		
		tabelaDepartamento.setItems(listaObs);
		
		iniciaBotoesEditar();
		iniciaBotoesRemover();
		
	}
	
	private void geraDialogoFormulario(Departamento departamento,String nomeAbsoluto, Stage palcoPrincipal) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = carregador.load();
			
			CtrlFormularioDepartamento ctrlDepartamento = carregador.getController();
			ctrlDepartamento.setDepartamento(departamento);
			ctrlDepartamento.setSrvcDepartamento(new SrvcDepartamento());
			ctrlDepartamento.inscricaoMntrMudancaDados(this);
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

	@Override public void onDataChanged() {
		atualizaVsdrTabela();		
	}
	
	private void removerDepartamento(Departamento departamento) {
		
		Optional<ButtonType> confirma = Alertas.mostraConfirmacao("Confirmações", "Confirma exclusão");
		if(confirma.get() == ButtonType.OK) {
			
			if(srvcDepartamento == null) {
				throw new IllegalStateException("Serviço sem dados");
			}
			
			try {
				srvcDepartamento.remover(departamento);
				atualizaVsdrTabela();
			}
			catch(BDExcecaoIntegridade e) {
				Alertas.mostraAlertas("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}		
}


















