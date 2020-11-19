package modelo.servicos;

import java.util.ArrayList;
import java.util.List;

import modelo.entidades.Departamento;

public class SrvcDepartamento {
	
	public List<Departamento> pesquisar() {
		List<Departamento> lista = new ArrayList<>();
		
		lista.add(new Departamento(1, "Livros"));
		lista.add(new Departamento(2, "Eletr�nicos"));
		lista.add(new Departamento(3, "Audio"));
		lista.add(new Departamento(4, "Inform�tica"));
		lista.add(new Departamento(5, "Papelaria"));
		
		return lista;
	}
}
