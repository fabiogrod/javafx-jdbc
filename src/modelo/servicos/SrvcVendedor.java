package modelo.servicos;

import java.util.ArrayList;
import java.util.List;

import modelo.dao.VendedorDao;
import modelo.dao.FabricaDao;
import modelo.entidades.Vendedor;

public class SrvcVendedor {
	
	private VendedorDao dao = FabricaDao.gerarVendedorDao();
	
	public List<Vendedor> pesquisar() {
//		List<Vendedor> lista = new ArrayList<>();
//		
//		lista.add(new Vendedor(1, "Livros"));
//		lista.add(new Vendedor(2, "Eletrônicos"));
//		lista.add(new Vendedor(3, "Audio"));
//		lista.add(new Vendedor(4, "Informática"));
//		lista.add(new Vendedor(5, "Papelaria"));	
//		
//		return lista;
		
		return dao.pesquisar();
	}
	
	public void salvarAtualizar(Vendedor Vendedor) {
		if(Vendedor.getId() == null) {
			dao.insere(Vendedor);
		}
		else {
			dao.atualiza(Vendedor);
		}
	}
	
	public void remover(Vendedor Vendedor) {		
		dao.deletaId(Vendedor.getId());		
	}
}
