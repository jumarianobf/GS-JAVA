package br.com.fiap.resources;

import br.com.fiap.bo.ContatoConsumidorBO;
import br.com.fiap.model.ContatoConsumidor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/contato-consumidor")
public class ContatoConsumidorResource {
	
	private ContatoConsumidorBO contatoConsumidorBO;;
	
	public ContatoConsumidorResource() {
	    try {
	        this.contatoConsumidorBO = new ContatoConsumidorBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar ContatoConsumidorBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(ContatoConsumidor contatoConsumidor, @Context UriInfo uriInfo) {
        try {
        	contatoConsumidorBO.salvar(contatoConsumidor);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (contatoConsumidor.getIdContatoConsumidor() != null) {
                builder.path(Long.toString(contatoConsumidor.getIdContatoConsumidor()));
            }

            return Response.created(builder.build()).entity("Contato cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar contato: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar contato: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarContatos() {
        try {
            List<ContatoConsumidor> contatos = contatoConsumidorBO.listarConsumidor();
            return Response.ok(contatos).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar contatos: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
        	ContatoConsumidor contatoConsumidor = contatoConsumidorBO.buscarPorId(id);
            if (contatoConsumidor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("contato não encontrado").build();
            }
            return Response.ok(contatoConsumidor).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar contato: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarContato(@PathParam("id") Long id, ContatoConsumidor contatoConsumidor) {
    	contatoConsumidor.setIdContatoConsumidor(id);
        try {
        	contatoConsumidorBO.atualizarConsumidor(contatoConsumidor);
            return Response.ok("Coletador atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar contato: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirContato(@PathParam("id") Long id) {
        try {
        	contatoConsumidorBO.excluirContatoConsumidor(id);
            return Response.ok("Contato com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir contato: " + e.getMessage()).build();
        }
    }

}
