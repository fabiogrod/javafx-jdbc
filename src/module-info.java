module javafx {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens aplicacao to javafx.graphics, javafx.fxml;
	opens igu to javafx.graphics, javafx.fxml;
	opens modelo.servicos to javafx.graphics, javafx.fxml;	
	opens modelo.entidades to javafx.graphics, javafx.fxml, javafx.base;
}
