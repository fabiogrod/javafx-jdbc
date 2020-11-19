module javafx {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens aplicacao to javafx.graphics, javafx.fxml;
	opens igu to javafx.graphics, javafx.fxml;
}
