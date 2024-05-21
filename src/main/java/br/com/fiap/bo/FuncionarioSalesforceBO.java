package br.com.fiap.bo;

import br.com.fiap.dao.FuncionarioSalesforceDAO;
import br.com.fiap.dao.ProfessorSalesforceDAO;
import br.com.fiap.model.FuncionarioSalesforce;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioSalesforceBO {

    private FuncionarioSalesforceDAO funcionarioSalesforceDAO;

    public FuncionarioSalesforceBO() {
        try {
            this.funcionarioSalesforceDAO = new FuncionarioSalesforceDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao inicializar SalesforceBO: " + e.getMessage(), e);
        }
    }

    public void salvar(FuncionarioSalesforce funcionario) throws ClassNotFoundException, SQLException {
        validarFuncionario(funcionario);
        if (idJaExiste(funcionario.getId())) {
            throw new IllegalArgumentException("ID já cadastrado.");
        }
        funcionarioSalesforceDAO.salvar(funcionario);
    }

    public FuncionarioSalesforce buscarPorId(Long id) throws ClassNotFoundException, SQLException {
        return funcionarioSalesforceDAO.buscarPorId(id);
    }

    public void atualizarFuncionario(FuncionarioSalesforce funcionario) throws ClassNotFoundException, SQLException {
        validarFuncionario(funcionario);
        FuncionarioSalesforce funcionarioExistente = funcionarioSalesforceDAO.buscarPorId(funcionario.getId());
        if (funcionarioExistente != null && !funcionarioExistente.getId().equals(funcionario.getId())) {
            throw new IllegalArgumentException("ID não pode ser alterado.");
        }
        funcionarioSalesforceDAO.atualizarFuncionario(funcionario);
    }

    public void excluirFuncionario(Long id) throws ClassNotFoundException, SQLException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        funcionarioSalesforceDAO.excluirFuncionario(id);
    }

    public List<FuncionarioSalesforce> listarFuncionarios() throws ClassNotFoundException, SQLException {
        return funcionarioSalesforceDAO.listarFuncionario();
    }

    private void validarFuncionario(FuncionarioSalesforce funcionario) {
        if (funcionario.getDescricaoCargo() == null || funcionario.getDescricaoCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do cargo não pode ser nula ou vazia.");
        }
        if (funcionario.getDescricaoFormacao() == null || funcionario.getDescricaoFormacao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da formação não pode ser nula ou vazia.");
        }
        if (funcionario.getValorSalarioFuncionario() <= 0) {
            throw new IllegalArgumentException("Valor do salário inválido.");
        }
    }

    private boolean idJaExiste(Long id) throws ClassNotFoundException, SQLException {
        List<FuncionarioSalesforce> funcionarios = funcionarioSalesforceDAO.listarFuncionario();
        for (FuncionarioSalesforce funcionario : funcionarios) {
            if (funcionario.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}

