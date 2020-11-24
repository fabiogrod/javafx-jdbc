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
	
	public void salvarAtualizar(Vendedor vendedor) {
		if(vendedor.getId() == null) {
			dao.insere(vendedor);
		}
		else {
			dao.atualiza(vendedor);
		}
	}
	
	public void remover(Vendedor vendedor) {		
		dao.deletaId(vendedor.getId());		
	}
}
