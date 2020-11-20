package modelo.dao;

import bd.BD;
import modelo.dao.impl.DepartamentoDaoJDBC;
import modelo.dao.impl.VendedorDaoJDBC;

public class FabricaDao {
	
	public static VendedorDao gerarVendedorDao()
	{
		return new VendedorDaoJDBC(BD.abreConexao());
	}
	
	public static DepartamentoDao gerarDepartamentoDao()
	{
		return new DepartamentoDaoJDBC(BD.abreConexao());
	}

}
