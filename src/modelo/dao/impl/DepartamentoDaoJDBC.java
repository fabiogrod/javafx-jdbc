package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.BD;
import bd.BDExcecao;
import modelo.dao.DepartamentoDao;
import modelo.entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao{
	
	private Connection con;
	
	public DepartamentoDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void insere(Departamento departamento) {
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("INSERT INTO departamento (Nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS );
			
//			pst.setInt(1, departamento.getId() );
			pst.setString(1, departamento.getNome() );
			
			int linhasInseridas = pst.executeUpdate();
			
			if (linhasInseridas > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				
				if(rs.next() ) {
					int id = rs.getInt(1);
					departamento.setId(id);
				}
				BD.fechaResultSet(rs);
			}
			else {
				throw new BDExcecao("Erro inesperado! Nehuma linha afetada! ");
			}
		}
		catch(SQLException e) {
			throw new BDExcecao(e.getMessage());
		}
		finally {
			BD.fechaStatement(pst);
		}
	}

	@Override
	public void atualiza(Departamento departamento) {
		PreparedStatement pst = null;
		
		try
		{
			pst = con.prepareStatement("UPDATE departamento SET Nome = ? WHERE Id= ? ", Statement.RETURN_GENERATED_KEYS );
			
			pst.setString(1, departamento.getNome() );
			pst.setInt(2, departamento.getId());
			
			pst.executeUpdate();	
		}
		catch(SQLException e) {
			throw new BDExcecao(e.getMessage());
		}
		finally {
			BD.fechaStatement(pst);
		}
		
	}

	@Override
	public void deletaId(Integer id) {
		PreparedStatement pst = null;
		try {
		
			pst = con.prepareStatement("DELETE FROM departamento WHERE Id = ?");
			
			pst.setInt(1, id);
			
			/*int linhas =*/ pst.executeUpdate();
			
//			if (linhas == 0) {
//				throw new BDExcecao("Número informado não foi encontrado.");
//			}
		}
		catch(SQLException e) {
			throw new BDExcecao(e.getMessage());
		}
		finally {
			BD.fechaStatement(pst);
		}
	}

	@Override
	public Departamento pesquisarId(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM departamento WHERE Id = ? ");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				Departamento departamento = instanciarDepartamento(rs);
				
				return departamento;
			}
			return null;
		}
		catch(SQLException e) {
			throw new BDExcecao(e.getMessage());
		}
		finally {
			BD.fechaStatement(pst);
			BD.fechaResultSet(rs);
		}		
	}
	
	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("Id") );
		departamento.setNome(rs.getString("Nome") );
		return departamento;
	}

	@Override
	public List<Departamento> pesquisar() {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM departamento ORDER By Nome");
			
			rs = pst.executeQuery();
			
			List<Departamento> lista = new ArrayList<>();
			
			while (rs.next()) {
				
				Departamento departamento = instanciarDepartamento(rs);
				
				lista.add(departamento);
			}
			return lista;
		}
		catch(SQLException e) {
			throw new BDExcecao(e.getMessage());
		}
		finally {
			BD.fechaStatement(pst);
			BD.fechaResultSet(rs);
		}
	}

	@Override
	public List<Departamento> pesquisarDepartamento(Departamento departamento) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM departamento WHERE IdDepartamento = ? ORDER BY Nome");
			pst.setInt(1, departamento.getId());
			rs = pst.executeQuery();
			
			List<Departamento> lista = new ArrayList<>();
			
			while (rs.next()) {
				
				departamento = instanciarDepartamento(rs);
				
				lista.add(departamento);
			}
			return lista;
		}
		catch(SQLException e) {
			throw new BDExcecao(e.getMessage());
		}
		finally {
			BD.fechaStatement(pst);
			BD.fechaResultSet(rs);
		}
	}
}
