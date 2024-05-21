package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.model.FuncionarioSalesforce;


public class FuncionarioSalesforceDAO  {
    public Connection conexao;

    public FuncionarioSalesforceDAO() throws ClassNotFoundException, SQLException {
        this.conexao = new ConnectionFactory().conexao();
    }


    public String salvar(FuncionarioSalesforce funcionario) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("INSERT INTO T_FUNCIONARIO_SALESFORCE (descricao_cargo_funcionario, descricao_formacao_funcionario, valor_salario_funcionario) VALUES (?, ?, ?)");
        stmt.setString(1, funcionario.getDescricaoCargo());
        stmt.setString(2, funcionario.getDescricaoFormacao());
        stmt.setDouble(3, funcionario.getValorSalarioFuncionario());
        stmt.executeUpdate();
        stmt.close();
        return "Cadastrado com sucesso!";
        
    }

    
    public FuncionarioSalesforce buscarPorId(Long id) throws SQLException {
        if (id == null) {
            return null;
        }
        FuncionarioSalesforce funcionario = null;
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_FUNCIONARIO_SALESFORCE WHERE id_funcionario = ?");
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
        	funcionario = new FuncionarioSalesforce();
            funcionario.setId(rs.getLong("id_funcionario"));
            funcionario.setDescricaoCargo(rs.getString("descricao_cargo_funcionario"));
            funcionario.setFormacao(rs.getString("descricao_formacao_funcionario"));
            funcionario.setValorSalarioFuncionario(rs.getDouble("valor_salario_funcionario"));
        }
        rs.close();
        stmt.close();
        return funcionario;
                	
     }
       
  
    public String atualizarFuncionario(FuncionarioSalesforce funcionario) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("UPDATE T_FUNCIONARIO_SALESFORCE SET descricao_cargo_funcionario = ?, descricao_formacao_funcionario = ?,"
        		+ " valor_salario_funcionario = ? WHERE id_funcionario = ?");
        stmt.setString(1, funcionario.getDescricaoCargo());
        stmt.setString(2, funcionario.getDescricaoFormacao());
        stmt.setDouble(3, funcionario.getValorSalarioFuncionario());
        stmt.setLong(4, funcionario.getId());
        stmt.executeUpdate();
        stmt.close();
        return "Atualizado com sucesso!";
        
    }

    
    public String excluirFuncionario(Long id) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_FUNCIONARIO_SALESFORCE WHERE id_funcionario = ?");
        stmt.setLong(1, id);
        stmt.executeUpdate();
        stmt.close();
        return "Deletado com sucesso";
    
    }

    
    public List<FuncionarioSalesforce> listarFuncionario() throws SQLException {
        List<FuncionarioSalesforce> funcionarios = new ArrayList<>();
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_FUNCIONARIO_SALESFORCE");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
        	FuncionarioSalesforce funcionario = new FuncionarioSalesforce();
            funcionario.setId(rs.getLong("id_funcionario"));
            funcionario.setDescricaoCargo(rs.getString("descricao_cargo_funcionario"));
            funcionario.setFormacao(rs.getString("descricao_formacao_funcionario"));
            funcionario.setValorSalarioFuncionario(rs.getDouble("valor_salario_funcionario"));
            funcionarios.add(funcionario);
         }
        rs.close();
        stmt.close();
        return funcionarios;
    }
}

