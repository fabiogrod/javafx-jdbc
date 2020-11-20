package modelo.dao;

import java.util.List;

import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public interface VendedorDao {

void insere(Vendedor Vendedor);
	
	void atualiza(Vendedor Vendedor);	
	void deletaId(Integer id);	
	Vendedor pesquisarId(Integer id);	
	List<Vendedor> pesquisar();
	List<Vendedor> pesquisarDepartamento(Departamento departamento);	
}
