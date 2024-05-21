package br.com.fiap.dao;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.model.CompraCurso;
import br.com.fiap.model.CursoSalesforce;
import br.com.fiap.model.FormaPagamento;
import br.com.fiap.model.UsuarioSalesforce;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraCursoDAO {
    public Connection conexao;

    public CompraCursoDAO() throws ClassNotFoundException, SQLException {
        this.conexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(CompraCurso compraCurso) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("INSERT INTO T_COMPRA_CURSO (id_curso, id_usuario, forma_pagamento, valor_compra) VALUES (?, ?, ?, ?)");
        stmt.setLong(1, compraCurso.getCursoSalesforce().getId());
        stmt.setLong(2, compraCurso.getUsuarioSalesforce().getId());
        stmt.setString(3, compraCurso.getFormaPagamento().toString());
        stmt.setDouble(4, compraCurso.getValorCompra());
        stmt.executeUpdate();
        stmt.close();
        return "Cadastro com sucesso!";
    }

    // Delete
    public String deletar(long idCompra) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_COMPRA_CURSO WHERE id_compra = ?");
        stmt.setLong(1, idCompra);
        stmt.execute();
        stmt.close();
        return "Deletado com sucesso!";
    }

    // Update
    public String atualizar(CompraCurso compraCurso) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("UPDATE T_COMPRA_CURSO SET id_curso = ?, id_usuario = ?, valor_compra = ?, forma_pagamento = ? WHERE id_compra = ?");
        stmt.setLong(1, compraCurso.getCursoSalesforce().getId());
        stmt.setLong(2, compraCurso.getUsuarioSalesforce().getId());
        stmt.setDouble(3, compraCurso.getValorCompra());
        stmt.setString(4, compraCurso.getFormaPagamento().toString());
        stmt.setLong(5, compraCurso.getIdCompraCurso());
        stmt.executeUpdate();
        stmt.close();
        return "Atualizado com sucesso!";
    }

    // Select
    public CompraCurso buscarPorId(long idCompra) throws SQLException {
        CompraCurso compraCurso = new CompraCurso();
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_COMPRA_CURSO WHERE id_compra = ?");
        stmt.setLong(1, idCompra);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            long idCurso = rs.getLong("id_curso");
            long idUsuario = rs.getLong("id_usuario");
            FormaPagamento formaPagamento = FormaPagamento.valueOf(rs.getString("forma_pagamento"));
            double valorCompra = rs.getDouble("valor_compra");

            CursoSalesforce curso = new CursoSalesforce();
            curso.setId(idCurso);
            UsuarioSalesforce usuario = new UsuarioSalesforce();
            usuario.setId(idUsuario);
            compraCurso = new CompraCurso(usuario, curso, formaPagamento, valorCompra);
        }
        rs.close();
        stmt.close();
        return compraCurso;
    }
    
    public List<CompraCurso> listarCompraCursos() throws SQLException {
        List<CompraCurso> compraCursos = new ArrayList<>();
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_COMPRA_CURSO");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Long id = rs.getLong("id_compra");
            Long idCurso = rs.getLong("id_curso");
            Long idUsuario = rs.getLong("id_usuario");
            double valorCompra = rs.getDouble("valor_compra");
            FormaPagamento formaPagamento = FormaPagamento.valueOf(rs.getString("forma_pagamento"));
            CursoSalesforce curso = new CursoSalesforce();
            curso.setId(idCurso);
            UsuarioSalesforce usuario = new UsuarioSalesforce();
            usuario.setId(idUsuario);
            CompraCurso compraCurso = new CompraCurso(usuario, curso, formaPagamento, valorCompra);
            compraCursos.add(compraCurso);

        }
        rs.close();
        stmt.close();
        return compraCursos;
    }
	
	public List<CursoSalesforce> buscarPorNome(String nome) throws SQLException {
	    List<CursoSalesforce> cursos = new ArrayList<>();
	    PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE WHERE nome_curso LIKE ?");
	    stmt.setString(1, "%" + nome + "%");
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	         CursoSalesforce curso = new CursoSalesforce();
	         curso.setId(rs.getLong("id_curso"));
	         curso.setNomeCurso(rs.getString("nome_curso"));
	         curso.setAreaCurso(rs.getString("area_curso"));
	         curso.setCertificadoCurso(rs.getString("certificado_curso"));
	         cursos.add(curso);
	    }
	    rs.close();
	    stmt.close();
	    return cursos;
	}
	
	public int contarCursos() throws SQLException{
		PreparedStatement stmt = conexao.prepareStatement("SELECT COUNT(*) FROM T_CURSO_SALESFORCE");
	    ResultSet rs = stmt.executeQuery();
	    if (rs.next()) {
	            return rs.getInt(1);
	        }
	    rs.close();
	    stmt.close();
	    return 0;
	}
	
	public List<CursoSalesforce> buscarPorArea(String area) throws SQLException {
	    List<CursoSalesforce> cursos = new ArrayList<>();
	    PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE WHERE area_curso = ?");
	    stmt.setString(1, area);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	    	CursoSalesforce curso = new CursoSalesforce();
	        curso.setId(rs.getLong("id_curso")); 
	        curso.setNomeCurso(rs.getString("nome_curso"));
	        curso.setAreaCurso(rs.getString("area_curso"));
	        curso.setCertificadoCurso(rs.getString("certificado_curso"));
	        cursos.add(curso);
	     }
	    rs.close();
	    stmt.close();
	    return cursos;
	 }
	
	public List<CursoSalesforce> buscarPorCertificado(String certificado) throws SQLException {
	    List<CursoSalesforce> cursos = new ArrayList<>();
	    PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_CURSO_SALESFORCE WHERE certificado_curso = ?");
	    stmt.setString(1, certificado);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	    	CursoSalesforce curso = new CursoSalesforce();
	        curso.setId(rs.getLong("id_curso"));
	        curso.setNomeCurso(rs.getString("nome_curso"));
	        curso.setAreaCurso(rs.getString("area_curso"));
	        curso.setCertificadoCurso(rs.getString("certificado_curso"));
	        cursos.add(curso);
	    }
	    rs.close();
	    stmt.close();
	    return cursos;
	}
	
	    
	    
}
	
	
	
	
		






