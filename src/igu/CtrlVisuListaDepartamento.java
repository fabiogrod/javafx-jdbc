package igu;

import java.net.URL;
import java.util.ResourceBundle;

import aplicacao.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.entidades.Departamento;

public class CtrlVisuListaDepartamento implements Initializable {

	@FXML private TableView<Departamento> tabelaDepartamento;
	@FXML private TableColumn<Departamento, Integer> colunaIdDepartamento;
	@FXML private TableColumn<Departamento, String> colunaNome;
	
	@FXML private Button btoNovo;
	
	@FXML public void onBtoNovoAcao() {
		System.out.println("onBtoNovoAcao");
	}	
	
	@Override public void initialize(URL url, ResourceBundle rb) {
		iniciaNos();
	}
	
	public void iniciaNos() {
		colunaIdDepartamento.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		Stage palco = (Stage)Main.getCenaPrincipal().getWindow();
		tabelaDepartamento.prefHeightProperty().bind(palco.heightProperty());
	}
}
