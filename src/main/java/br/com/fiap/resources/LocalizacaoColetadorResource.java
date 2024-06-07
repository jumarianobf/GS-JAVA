package br.com.fiap.resources;

import br.com.fiap.bo.LocalizacaoColetadorBO;
import br.com.fiap.model.LocalizacaoColetador;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Path("/localizacao-coletador")
public class LocalizacaoColetadorResource {
	
	private LocalizacaoColetadorBO localizacaoColetadorBO;;
	
	public LocalizacaoColetadorResource() {
	    try {
	        this.localizacaoColetadorBO = new LocalizacaoColetadorBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar LocalizacaoColetadorBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(LocalizacaoColetador localizacaoColetador, @Context UriInfo uriInfo) {
        try {
        	localizacaoColetadorBO.salvar(localizacaoColetador);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (localizacaoColetador.getIdLocalizacaoColetador() != null) {
                builder.path(Long.toString(localizacaoColetador.getIdLocalizacaoColetador()));
            }

            return Response.created(builder.build()).entity("Localização cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar localização: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar localização: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarLocalizacao() {
        try {
            List<LocalizacaoColetador> contatos = localizacaoColetadorBO.listarLocalizacao();
            return Response.ok(contatos).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar localizações: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
        	LocalizacaoColetador localizacaoColetador = localizacaoColetadorBO.buscarPorId(id);
            if (localizacaoColetador == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Localização não encontrado").build();
            }
            return Response.ok(localizacaoColetador).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar localização: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarLocalizacao(@PathParam("id") Long id, LocalizacaoColetador localizacaoColetador) throws IOException {
    	localizacaoColetador.setIdLocalizacaoColetador(id);
        try {
        	localizacaoColetadorBO.atualizarContato(localizacaoColetador);
            return Response.ok("Coletador atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar localização: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirLocalizacao(@PathParam("id") Long id) {
        try {
        	localizacaoColetadorBO.excluirContatoColetador(id);
            return Response.ok("Contato com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir localização: " + e.getMessage()).build();
        }
    }

}
