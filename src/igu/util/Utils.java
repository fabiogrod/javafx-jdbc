package igu.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class Utils {

	public static Stage palcoAtual(ActionEvent evento) {
		return (Stage) ((Node) evento.getSource()).getScene().getWindow();
	}

	public static Integer ParseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <T> void formataDataTabela(TableColumn<T, Date> colunaTabela, String formato) {
		colunaTabela.setCellFactory(coluna -> {
			TableCell<T, Date> celula = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(formato);

				@Override
				protected void updateItem(Date item, boolean vazio) {
					super.updateItem(item, vazio);
					if (vazio) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return celula;
		});
	}

	public static <T> void formataDoubleTabela(TableColumn<T, Double> colunaTabela, int decimal) {
		colunaTabela.setCellFactory(coluna -> {
			TableCell<T, Double> celula = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean vazio) {
					super.updateItem(item, vazio);
					if (vazio) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + decimal + "f", item));
					}
				}
			};
			return celula;
		});
	}
}
