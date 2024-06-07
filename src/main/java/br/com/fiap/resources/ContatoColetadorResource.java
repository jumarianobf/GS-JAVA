package br.com.fiap.resources;

import br.com.fiap.bo.ContatoColetadorBO;
import br.com.fiap.model.ContatoColetador;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/contato-coletador")
public class ContatoColetadorResource {
	
	private ContatoColetadorBO contatoColetadorBO;;
	
	public ContatoColetadorResource() {
	    try {
	        this.contatoColetadorBO = new ContatoColetadorBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar ContatoColetadorBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(ContatoColetador contatoColetador, @Context UriInfo uriInfo) {
        try {
        	contatoColetadorBO.salvar(contatoColetador);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (contatoColetador.getIdContatoColetador() != null) {
                builder.path(Long.toString(contatoColetador.getIdContatoColetador()));
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
            List<ContatoColetador> contatos = contatoColetadorBO.listarContato();
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
        	ContatoColetador contatoColetador = contatoColetadorBO.buscarPorId(id);
            if (contatoColetador == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("contato não encontrado").build();
            }
            return Response.ok(contatoColetador).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar contato: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarContato(@PathParam("id") Long id, ContatoColetador contatoColetador) {
    	contatoColetador.setIdContatoColetador(id);
        try {
        	contatoColetadorBO.atualizarContato(contatoColetador);
            return Response.ok("Coletador atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar contato: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirContato(@PathParam("id") Long id) {
        try {
        	contatoColetadorBO.excluirContatoColetador(id);
            return Response.ok("Contato com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir contato: " + e.getMessage()).build();
        }
    }

}
