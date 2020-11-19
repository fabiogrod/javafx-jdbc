package aplicacao;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage palcoPrincipal) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource("/igu/MainVisualizador.fxml") );
			Parent principal = carregador.load();
			
			Scene cenaPrincipal = new Scene(principal);
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
