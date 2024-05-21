package br.com.fiap.bo;

import br.com.fiap.dao.SalesforceDAO;
import br.com.fiap.dao.UsuarioSalesforceDAO;
import br.com.fiap.model.Salesforce;

import java.sql.SQLException;
import java.util.List;

public class SalesforceBO {

    private SalesforceDAO salesforceDAO;

    public SalesforceBO() {
        try {
            this.salesforceDAO = new SalesforceDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao inicializar SalesforceBO: " + e.getMessage(), e);
        }
    }

    public void salvar(Salesforce salesforce) throws ClassNotFoundException, SQLException {
        validarSalesforce(salesforce);
        if (idJaExiste(salesforce.getId())) {
            throw new IllegalArgumentException("ID já cadastrado.");
        }
        salesforceDAO.salvar(salesforce);
    }

    public Salesforce buscarPorId(Long id) throws ClassNotFoundException, SQLException {
        return salesforceDAO.buscarPorId(id);
    }

    public void atualizarSalesforce(Salesforce salesforce) throws ClassNotFoundException, SQLException {
        validarSalesforce(salesforce);
        Salesforce salesforceExistente = salesforceDAO.buscarPorId(salesforce.getId());
        if (salesforceExistente != null && !salesforceExistente.getId().equals(salesforce.getId())) {
            throw new IllegalArgumentException("ID não pode ser alterado.");
        }
        salesforceDAO.atualizarSalesforce(salesforce);
    }

    public void excluirSalesforce(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        salesforceDAO.excluirSalesforce(id);
    }

    public List<Salesforce> listarSalesforces() throws ClassNotFoundException, SQLException {
        return salesforceDAO.listarSalesforce();
    }

    private void validarSalesforce(Salesforce salesforce) {
        if (salesforce.getRegiao() == null || salesforce.getRegiao().trim().isEmpty()) {
            throw new IllegalArgumentException("Região não pode ser nula ou vazia.");
        }
        if (salesforce.getCep() == null) {
            throw new IllegalArgumentException("CEP não pode ser nulo.");
        }
    }

    private boolean idJaExiste(Long id) throws ClassNotFoundException, SQLException {
        List<Salesforce> salesforces = salesforceDAO.listarSalesforce();
        for (Salesforce salesforce : salesforces) {
            if (salesforce.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}

