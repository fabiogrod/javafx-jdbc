package modelo.dao;

import java.util.List;

import modelo.entidades.Departamento;

public interface DepartamentoDao {
	
	void insere(Departamento departamento);
	
	void atualiza(Departamento departamento);
	
	void deletaId(Integer id);
	
	Departamento pesquisarId(Integer id);
	
	List<Departamento> pesquisar();
	List<Departamento> pesquisarDepartamento(Departamento departamento);
}
