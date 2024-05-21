package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.model.Salesforce;

public class SalesforceDAO {

    public Connection conexao;

    public SalesforceDAO() throws ClassNotFoundException, SQLException {
        this.conexao = new ConnectionFactory().conexao();
    }

    public String salvar(Salesforce salesforce) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("INSERT INTO T_SALESFORCE (regiao, cep, rua, bairro, complemento, telefone) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, salesforce.getRegiao());
        stmt.setLong(2, salesforce.getCep());
        stmt.setString(3, salesforce.getRua());
        stmt.setString(4, salesforce.getBairro());
        stmt.setString(5, salesforce.getComplemento());
        stmt.setLong(6, salesforce.getTelefone());
        stmt.executeUpdate();
        stmt.close();
        return "Salvo com sucesso";
    }

    public Salesforce buscarPorId(Long id) throws SQLException {
        if (id == null) {
            return null;
        }
        Salesforce salesforce = null;
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_SALESFORCE WHERE id_salesforce = ?");
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            salesforce = new Salesforce();
            salesforce.setId(rs.getLong("id_salesforce"));
            salesforce.setRegiao(rs.getString("regiao"));
            salesforce.setCep(rs.getLong("cep"));
            salesforce.setRua(rs.getString("rua"));
            salesforce.setBairro(rs.getString("bairro"));
            salesforce.setComplemento(rs.getString("complemento"));
            salesforce.setTelefone(rs.getLong("telefone"));
        }
        rs.close();
        stmt.close();
        return salesforce;
    }

    public String atualizarSalesforce(Salesforce salesforce) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("UPDATE T_SALESFORCE SET regiao = ?, cep = ?, rua = ?, bairro = ?, complemento = ?, telefone = ? WHERE id_salesforce = ?");
        stmt.setString(1, salesforce.getRegiao());
        stmt.setLong(2, salesforce.getCep());
        stmt.setString(3, salesforce.getRua());
        stmt.setString(4, salesforce.getBairro());
        stmt.setString(5, salesforce.getComplemento());
        stmt.setLong(6, salesforce.getTelefone());
        stmt.setLong(7, salesforce.getId());
        stmt.executeUpdate();
        stmt.close();
        return "Atualizado com sucesso";
    }

    public String excluirSalesforce(Long id) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_SALESFORCE WHERE id_salesforce = ?");
        stmt.setLong(1, id);
        stmt.executeUpdate();
        stmt.close();
        return "Deletado com sucesso";
    }

    public List<Salesforce> listarSalesforce() throws SQLException {
        List<Salesforce> salesforces = new ArrayList<>();
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_SALESFORCE");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Salesforce salesforce = new Salesforce();
            salesforce.setId(rs.getLong("id_salesforce"));
            salesforce.setRegiao(rs.getString("regiao"));
            salesforce.setCep(rs.getLong("cep"));
            salesforce.setRua(rs.getString("rua"));
            salesforce.setBairro(rs.getString("bairro"));
            salesforce.setComplemento(rs.getString("complemento"));
            salesforce.setTelefone(rs.getLong("telefone"));
            salesforces.add(salesforce);
        }
        rs.close();
        stmt.close();
        return salesforces;
    }
}
