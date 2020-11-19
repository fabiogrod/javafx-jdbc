package igu.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alertas {

	public static void mostraAlertas(String titulo, String cabecalho, String conteudo, AlertType tipo) {
		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(cabecalho);
		alerta.setContentText(conteudo);
		alerta.show();
	}
}
