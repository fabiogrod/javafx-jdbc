package igu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import aplicacao.Main;
import igu.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import modelo.servicos.SrvcDepartamento;

public class CtrlVisuMain implements Initializable {
	
	@FXML private MenuItem menuItemVendedor;
	@FXML private MenuItem menuItemDepartamento;
	@FXML private MenuItem menuItemSobre;
	
	@FXML public void onMenuItemVendedorAcao() {
		System.out.println("onMenuItemVendedorAcao");
	}
	
	@FXML public void onMenuItemDepartamentoAcao() {
		carregarVisu2("/igu/VisuListaDepartamento.fxml");;
	}
	
	@FXML public void onMenuItemSobreAcao() {
		carregarVisu("/igu/VisuSobre.fxml");
	}
	
	@Override public void initialize(URL uri, ResourceBundle rb) {
	
		
	}
	
	private synchronized void carregarVisu(String nomeAbsoluto) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox novoVbox = carregador.load();
			
			Scene cenaPrincipal = Main.getCenaPrincipal();
			VBox vboxPrincipal = (VBox) ((ScrollPane) cenaPrincipal.getRoot()).getContent();
			
			Node menuPrincipal = vboxPrincipal.getChildren().get(0);
			vboxPrincipal.getChildren().clear();
			vboxPrincipal.getChildren().add(menuPrincipal);
			vboxPrincipal.getChildren().addAll(novoVbox.getChildren());			
		}
		catch(IOException e) {
			Alertas.mostraAlertas("IOException", "Erro careegando visualizador", e.getLocalizedMessage(), AlertType.ERROR);
		}
	}
	
	private synchronized void carregarVisu2(String nomeAbsoluto) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox novoVbox = carregador.load();
			
			Scene cenaPrincipal = Main.getCenaPrincipal();
			VBox vboxPrincipal = (VBox) ((ScrollPane) cenaPrincipal.getRoot()).getContent();
			
			Node menuPrincipal = vboxPrincipal.getChildren().get(0);
			vboxPrincipal.getChildren().clear();
			vboxPrincipal.getChildren().add(menuPrincipal);
			vboxPrincipal.getChildren().addAll(novoVbox.getChildren());
			
			CtrlVisuListaDepartamento ctrlVisuListaDep = carregador.getController();
			ctrlVisuListaDep.setSrvcDepartamento(new SrvcDepartamento());
			ctrlVisuListaDep.atualizaVisuTabela();
		}
		catch(IOException e) {
			Alertas.mostraAlertas("IOException", "Erro careegando visualizador", e.getLocalizedMessage(), AlertType.ERROR);
		}
	}
}
