package igu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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

public class CtrlVsdrMain implements Initializable {
	
	@FXML private MenuItem menuItemVendedor;
	@FXML private MenuItem menuItemDepartamento;
	@FXML private MenuItem menuItemSobre;
	
	@FXML public void onMenuItemVendedorAcao() {
		System.out.println("onMenuItemVendedorAcao");
	}
	
	@FXML public void onMenuItemDepartamentoAcao() {
		carregarVisu("/igu/VsdrListaDepartamento.fxml", (CtrlVsdrListaDepartamento ctrlVsdrListaDep) -> {
			ctrlVsdrListaDep.setSrvcDepartamento(new SrvcDepartamento());
			ctrlVsdrListaDep.atualizaVsdrTabela();			
		});
	}
	
	@FXML public void onMenuItemSobreAcao() {
		carregarVisu("/igu/VsdrSobre.fxml", null /*x -> {}*/ );
	}
	
	@Override public void initialize(URL uri, ResourceBundle rb) {
	
		
	}
	
	private synchronized <T> void carregarVisu(String nomeAbsoluto, Consumer<T> acaoIni) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox novoVbox = carregador.load();
			
			Scene cenaPrincipal = Main.getCenaPrincipal();
			VBox vboxPrincipal = (VBox) ((ScrollPane) cenaPrincipal.getRoot()).getContent();
			
			Node menuPrincipal = vboxPrincipal.getChildren().get(0);
			vboxPrincipal.getChildren().clear();
			vboxPrincipal.getChildren().add(menuPrincipal);
			vboxPrincipal.getChildren().addAll(novoVbox.getChildren());
			
			if (acaoIni != null) {
				T controlador = carregador.getController();
				acaoIni.accept(controlador);
			}
		}
		catch(IOException e) {
			Alertas.mostraAlertas("IOException", "Erro careegando visualizador", e.getLocalizedMessage(), AlertType.ERROR);
		}
	}
}
