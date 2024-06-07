package br.com.fiap.resources;

import br.com.fiap.bo.ContatoProdutorBO;
import br.com.fiap.model.ContatoProdutor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/contato-produtor")
public class ContatoProdutorResource {
	
	private ContatoProdutorBO contatoProdutorBO;;
	
	public ContatoProdutorResource() {
	    try {
	        this.contatoProdutorBO = new ContatoProdutorBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar ContatoProdutorBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(ContatoProdutor contatoProdutor, @Context UriInfo uriInfo) {
        try {
        	contatoProdutorBO.salvar(contatoProdutor);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (contatoProdutor.getIdContatoProdutor() != null) {
                builder.path(Long.toString(contatoProdutor.getIdContatoProdutor()));
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
    public Response listarProdutor() {
        try {
            List<ContatoProdutor> contatos = contatoProdutorBO.listarProdutor();
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
        	ContatoProdutor contatoProdutor = contatoProdutorBO.buscarPorId(id);
            if (contatoProdutor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("contato não encontrado").build();
            }
            return Response.ok(contatoProdutor).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar contato: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarContato(@PathParam("id") Long id, ContatoProdutor contatoProdutor) {
    	contatoProdutor.setIdContatoProdutor(id);
        try {
        	contatoProdutorBO.atualizarProdutor(contatoProdutor);
            return Response.ok("Coletador atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar contato: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirContato(@PathParam("id") Long id) {
        try {
        	contatoProdutorBO.excluirContatoProdutor(id);
            return Response.ok("Contato com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir contato: " + e.getMessage()).build();
        }
    }

}
