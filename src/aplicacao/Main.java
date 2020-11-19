package aplicacao;
	

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage palcoPrincipal) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource("/igu/MainVisualizador.fxml") );
			ScrollPane scrollPane = carregador.load();
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			Scene cenaPrincipal = new Scene(scrollPane);
			palcoPrincipal.setScene(cenaPrincipal);
			palcoPrincipal.setTitle("Modelo de aplicação JavaFX");
			palcoPrincipal.show();
		}
		catch(IOException e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
