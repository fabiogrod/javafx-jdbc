package bd;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BD {
	
	private static Connection con = null;
	
	public static Connection abreConexao() {
				
		if(con == null) {
			try {
				Properties propriedades = carregaProriedades();
				String url = propriedades.getProperty("dburl");
				con = DriverManager.getConnection(url, propriedades);
			}
			catch (SQLException e) {
				throw new BDExcecao(e.getMessage());
			}
		}			
		return con;
	}
	
	public static void fechaConexao() {
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				throw new BDExcecao(e.getMessage());
			}
		}
	}
	
	private static Properties carregaProriedades() {
		
		try (FileInputStream fs = new FileInputStream("db.properties") ){
			
			Properties propriedades = new Properties();
			
			propriedades.load(fs);
			
			return propriedades;
		}
		catch (IOException e)
		{
			throw new BDExcecao( e.getMessage() );
		}
	}
	
	public static void fechaStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			}
			catch (SQLException e) {
				throw new BDExcecao(e.getMessage());
			}
		}
	}
	
	public static void fechaResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				throw new BDExcecao(e.getMessage());
			}
		}
	}
}
