package br.com.fiap.resources;

import br.com.fiap.bo.LocalizacaoConsumidorBO;
import br.com.fiap.model.LocalizacaoConsumidor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Path("/localizacao-consumidor")
public class LocalizacaoConsumidorResource {
	
	private LocalizacaoConsumidorBO localizacaoConsumidorBO;;
	
	public LocalizacaoConsumidorResource() {
	    try {
	        this.localizacaoConsumidorBO = new LocalizacaoConsumidorBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar LocalizacaoConsumidorBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(LocalizacaoConsumidor localizacaoConsumidor, @Context UriInfo uriInfo) {
        try {
        	localizacaoConsumidorBO.salvar(localizacaoConsumidor);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (localizacaoConsumidor.getIdLocalizacaoConsumidor() != null) {
                builder.path(Long.toString(localizacaoConsumidor.getIdLocalizacaoConsumidor()));
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
            List<LocalizacaoConsumidor> contatos = localizacaoConsumidorBO.listarLocalizacao();
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
        	LocalizacaoConsumidor localizacaoConsumidor = localizacaoConsumidorBO.buscarPorId(id);
            if (localizacaoConsumidor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Localização não encontrado").build();
            }
            return Response.ok(localizacaoConsumidor).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar localização: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarLocalizacao(@PathParam("id") Long id, LocalizacaoConsumidor localizacaoConsumidor) throws ClientProtocolException, IOException {
    	localizacaoConsumidor.setIdLocalizacaoConsumidor(id);
        try {
        	localizacaoConsumidorBO.atualizarLocalizacao(localizacaoConsumidor);
            return Response.ok("Coletador atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar localização: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirLocalizacao(@PathParam("id") Long id) {
        try {
        	localizacaoConsumidorBO.excluirContatoColetador(id);
            return Response.ok("Contato com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir localização: " + e.getMessage()).build();
        }
    }

}
