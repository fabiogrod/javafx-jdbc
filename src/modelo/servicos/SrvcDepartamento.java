package modelo.servicos;

import java.util.ArrayList;
import java.util.List;

import modelo.dao.DepartamentoDao;
import modelo.dao.FabricaDao;
import modelo.entidades.Departamento;

public class SrvcDepartamento {
	
	private DepartamentoDao dao = FabricaDao.gerarDepartamentoDao();
	
	public List<Departamento> pesquisar() {
//		List<Departamento> lista = new ArrayList<>();
//		
//		lista.add(new Departamento(1, "Livros"));
//		lista.add(new Departamento(2, "Eletrônicos"));
//		lista.add(new Departamento(3, "Audio"));
//		lista.add(new Departamento(4, "Informática"));
//		lista.add(new Departamento(5, "Papelaria"));	
//		
//		return lista;
		
		return dao.pesquisar();
	}
}
