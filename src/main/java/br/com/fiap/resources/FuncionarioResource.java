package br.com.fiap.resources;

import br.com.fiap.bo.FuncionarioSalesforceBO;
import br.com.fiap.model.FuncionarioSalesforce;
import br.com.fiap.model.UsuarioSalesforce;
import br.com.fiap.vo.Funcionario;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTransientConnectionException;
import java.util.List;

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    private final FuncionarioSalesforceBO funcionarioBO = new FuncionarioSalesforceBO();

    @POST
    @Path("/cadastrar")
    public Response cadastrar(Funcionario funcionario, @Context UriInfo uriInfo) throws ClassNotFoundException {
        
    	FuncionarioSalesforce funcionarioSalesforce = new FuncionarioSalesforce();
    	funcionarioSalesforce.setValorSalarioFuncionario(funcionario.getValorSalarioFuncionario());
    	funcionarioSalesforce.setFormacao(funcionario.getDescricaoFormacao());
    	funcionarioSalesforce.setDescricaoCargo(funcionario.getDescricaoCargo());


        try {
            funcionarioBO.salvar(funcionarioSalesforce);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (funcionarioSalesforce.getId() != null) {
            	builder.path(Long.toString(funcionarioSalesforce.getId()));
            }

            return Response.created(builder.build()).entity("Funcionario cadastrado com sucesso!").build();

        } catch (SQLException e) {
            if (e.getCause() instanceof SQLRecoverableException || e.getCause() instanceof SQLTransientConnectionException) {
                return Response.serverError().entity("Erro de conexão ao cadastrar funcionario: " + e.getMessage()).build();
            } else {
                return Response.serverError().entity("Erro ao cadastrar funcionario: " + e.getMessage()).build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar funcionario: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) throws ClassNotFoundException {
        try {
            FuncionarioSalesforce funcionario = funcionarioBO.buscarPorId(id);
            if (funcionario == null) {
            	 return Response.status(Response.Status.NOT_FOUND).entity("Funcionário não encontrado").build();
            
            }
            return Response.ok().entity(funcionario).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao buscar funcionário: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    public Response atualizar(FuncionarioSalesforce funcionario, @PathParam("id") Long id) throws ClassNotFoundException {
    	FuncionarioSalesforce funcionarioSalesforce = new FuncionarioSalesforce();
    	funcionarioSalesforce.setValorSalarioFuncionario(funcionario.getValorSalarioFuncionario());
    	funcionarioSalesforce.setFormacao(funcionario.getDescricaoFormacao());
    	funcionarioSalesforce.setDescricaoCargo(funcionario.getDescricaoCargo());
        try {
            funcionarioBO.atualizarFuncionario(funcionario);
            return Response.ok("Atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar usuário: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluir(@PathParam("id") Long id) throws ClassNotFoundException {
        try {
            funcionarioBO.excluirFuncionario(id);
            return Response.ok("Funcion com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir: " + e.getMessage()).build();
        }
    }

    @GET
    public Response listar() throws ClassNotFoundException {
        try {
            List<FuncionarioSalesforce> funcionarios = funcionarioBO.listarFuncionarios();
            return Response.ok().entity(funcionarios).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao listar usuários: " + e.getMessage()).build();
        }
    }
}
