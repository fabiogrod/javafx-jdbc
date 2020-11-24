package igu;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import modelo.entidades.Vendedor;
import modelo.servicos.SrvcDepartamento;
import modelo.servicos.SrvcVendedor;

public class CtrlVsdrListaVendedor implements Initializable, MntrMudancaDados {

	private SrvcVendedor srvcVendedor; 
	
	@FXML private TableView<Vendedor> tabelaVendedor;
	@FXML private TableColumn<Vendedor, Integer> colunaIdVendedor;
	@FXML private TableColumn<Vendedor, String> colunaNome;
	@FXML private TableColumn<Vendedor, String> colunaEmail;
	@FXML private TableColumn<Vendedor, Date> colunaDataAniversario;
	@FXML private TableColumn<Vendedor, Double> colunaSalarioBase;
	@FXML private TableColumn<Vendedor, Vendedor> colunaEditar;
	@FXML private TableColumn<Vendedor, Vendedor> colunaRemover;
	
	@FXML private Button btoNovo;
	
	private ObservableList<Vendedor> listaObs;
	
	@FXML public void onBtoNovoAcao(ActionEvent evento) {
		Stage palcoPrincipal = Utils.palcoAtual(evento);
		Vendedor vendedor = new Vendedor();
		geraDialogoFormulario(vendedor, "/igu/VsdrFormularioVendedor.fxml", palcoPrincipal);
	}
	
	public void setSrvcVendedor(SrvcVendedor srvcVendedor) {
		this.srvcVendedor = srvcVendedor;
	}
	
	@Override public void initialize(URL url, ResourceBundle rb) {
		iniciaNos();
	}
	
	public void iniciaNos() {
		colunaIdVendedor.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colunaDataAniversario.setCellValueFactory(new PropertyValueFactory<>("dataAniversario"));
		Utils.formataDataTabela(colunaDataAniversario, "dd/MM/yyyy");
		colunaSalarioBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
		Utils.formataDoubleTabela(colunaSalarioBase, 2);
		
//		colunaNome.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getNome()));
		
		Stage palco = (Stage)Main.getCenaPrincipal().getWindow();
		tabelaVendedor.prefHeightProperty().bind(palco.heightProperty());
		//tabelaVendedor.setColumnResizePolicy((param) -> true );
		
		tabelaVendedor.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		for(int i = 0; i < tabelaVendedor.getColumns().size(); i++) {			
			if (i == 1 || i == 2) {
				tabelaVendedor.getColumns().get(i).setStyle("-fx-alignment: center-left");
			}
			else {
				tabelaVendedor.getColumns().get(i).setStyle("-fx-alignment: center");
			}
		}
	}
	
	public void iniciaBotoesEditar() {
		
		colunaEditar.setCellValueFactory(colunaEditar.getCellValueFactory());
		
		colunaEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue() ) );
		colunaEditar.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
			private final Button botaoEditar = new Button("Editar");
			
			@Override protected void updateItem(Vendedor vendedor, boolean vazio) {
				super.updateItem(vendedor, vazio);
				if (vendedor == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(botaoEditar);
				
				botaoEditar.setOnAction(evento -> geraDialogoFormulario(vendedor, "/igu/VsdrFormularioVendedor.fxml", Utils.palcoAtual(evento) ) );
			}			
		});
		colunaEditar.setStyle("-fx-alignment: center");
	}
	
	public void iniciaBotoesRemover() {
		
		colunaRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue() ) );
		colunaRemover.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
			private final Button botaoRemover = new Button("Remover");
			
			@Override protected void updateItem(Vendedor vendedor, boolean vazio) {
				super.updateItem(vendedor, vazio);
				if (vendedor == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(botaoRemover);
				
				botaoRemover.setOnAction(evento -> removerVendedor(vendedor) );
			}				
		});
		colunaRemover.setStyle("-fx-alignment: center");
	}
	
	public void atualizaVsdrTabela() {
		if (srvcVendedor == null) {
			throw new IllegalStateException("Serviço sem dados.");
		}
		
		List<Vendedor> lista = srvcVendedor.pesquisar();
		
		listaObs = FXCollections.observableArrayList(lista);
		
		tabelaVendedor.setItems(listaObs);
		
		iniciaBotoesEditar();
		iniciaBotoesRemover();
		
	}
	
	private void geraDialogoFormulario(Vendedor vendedor,String nomeAbsoluto, Stage palcoPrincipal) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = carregador.load();
			
			CtrlFormularioVendedor ctrlVendedor = carregador.getController();
			ctrlVendedor.setVendedor(vendedor);
			ctrlVendedor.setServicos(new SrvcVendedor(), new SrvcDepartamento());
			ctrlVendedor.carregaObjetosAssociados();
			ctrlVendedor.inscricaoMntrMudancaDados(this);
			ctrlVendedor.atualizaFormulario();
			
			Stage palcoDialogo = new Stage();
			palcoDialogo.setTitle("Digite as informações do novo Vendedor: ");
			palcoDialogo.setScene(new Scene(painel));
			palcoDialogo.setResizable(false);
			palcoDialogo.initOwner(palcoPrincipal);
			palcoDialogo.initModality(Modality.WINDOW_MODAL);
			palcoDialogo.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
			Alertas.mostraAlertas("IOException", "Erro ao carregar visualização", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override public void onDataChanged() {
		atualizaVsdrTabela();		
	}
	
	private void removerVendedor(Vendedor vendedor) {
		
		Optional<ButtonType> confirma = Alertas.mostraConfirmacao("Confirmações", "Confirma exclusão");
		if(confirma.get() == ButtonType.OK) {
			
			if(srvcVendedor == null) {
				throw new IllegalStateException("Serviço sem dados");
			}
			
			try {
				srvcVendedor.remover(vendedor);
				atualizaVsdrTabela();
			}
			catch(BDExcecaoIntegridade e) {
				Alertas.mostraAlertas("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}		
}


















