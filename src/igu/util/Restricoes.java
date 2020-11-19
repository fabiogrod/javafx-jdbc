package igu.util;

import javafx.scene.control.TextField;

public class Restricoes {
	public static void setCampoTextoInteiro(TextField txt) {
		txt.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
			if(valorNovo != null && !valorNovo.matches("\\d*") ) {
				txt.setText(valorAntigo);
			}
		});
	}
	
	public static void setCampoTextoTamanhoMax(TextField txt, int max) {
		txt.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
			if(valorNovo != null && valorNovo.length() > max) {
				txt.setText(valorAntigo);
			}
		});
	}
	
	public static void setCampoTextoDouble(TextField txt) {
		txt.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
			if (valorNovo != null && !valorNovo.matches("\\d*([\\.]\\d*)?")) {
				txt.setText(valorAntigo);
			}
		});
	}
}
