package br.com.fiap.resources;

import br.com.fiap.vo.*;
import br.com.fiap.bo.SalesforceBO;
import br.com.fiap.model.Salesforce;
import br.com.fiap.model.UsuarioSalesforce;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTransientConnectionException;
import java.util.List;

@Path("/salesforce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalesforceResource {

    private final SalesforceBO salesforceBO = new SalesforceBO();

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(Salesforce salesforce, @Context UriInfo uriInfo) {
    	Salesforce salesforce2 = new Salesforce();
    	salesforce2.setComplemento(salesforce2.getComplemento());
    	salesforce2.setRua(salesforce2.getRua());
    	salesforce2.setCep(salesforce2.getCep());
    	salesforce2.setRegiao(salesforce2.getRegiao());
    	salesforce2.setBairro(salesforce2.getBairro());
    	salesforce2.setTelefone(salesforce2.getTelefone());
    	
        
        try {
            salesforceBO.salvar(salesforce);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (salesforce.getId() != null) {
                builder.path(Long.toString(salesforce.getId()));
            }

            return Response.created(builder.build()).entity("Cadastrado com sucesso!").build();
            
        } catch (SQLException | ClassNotFoundException e) {
            if (e.getCause() instanceof SQLRecoverableException || e.getCause() instanceof SQLTransientConnectionException) {
                return Response.serverError().entity("Erro de conexão ao cadastrar: " + e.getMessage()).build();
            } else {
                return Response.serverError().entity("Erro ao cadastrar: " + e.getMessage()).build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar: " + e.getMessage()).build();
        }
    }



    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Salesforce salesforce = salesforceBO.buscarPorId(id);
            if (salesforce == null) {
            	return Response.status(Response.Status.NOT_FOUND).entity("Registro não encontrado").build();
            } 
               
             return Response.ok().entity(salesforce).build();
            
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar por ID: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    public Response atualizar(Salesforce salesforce, @PathParam("id") Long id) {
    	Salesforce salesforce2 = new Salesforce();
    	salesforce2.setComplemento(salesforce2.getComplemento());
    	salesforce2.setRua(salesforce2.getRua());
    	salesforce2.setCep(salesforce2.getCep());
    	salesforce2.setRegiao(salesforce2.getRegiao());
    	salesforce2.setBairro(salesforce2.getBairro());
    	salesforce2.setTelefone(salesforce2.getTelefone());
    	
        try {
            salesforceBO.atualizarSalesforce(salesforce);
            return Response.status(Response.Status.NOT_FOUND).entity("Registro não encontrado").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluir(@PathParam("id") Long id) {
        try {
            salesforceBO.excluirSalesforce(id);
            return Response.ok("Salesforce com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir: " + e.getMessage()).build();
        }
    }
   


    @GET
    public Response listar() {
        try {
            List<Salesforce> salesforces = salesforceBO.listarSalesforces();
            return Response.ok().entity(salesforces).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar: " + e.getMessage()).build();
        }
    }
}
