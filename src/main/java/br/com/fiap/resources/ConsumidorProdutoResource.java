package br.com.fiap.resources;

import br.com.fiap.bo.ConsumidorProdutoBO;
import br.com.fiap.model.ConsumidorProduto;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/consumidor")
public class ConsumidorProdutoResource {
	
	private ConsumidorProdutoBO consumidorProdutoBO;;
	
	public ConsumidorProdutoResource() {
	    try {
	        this.consumidorProdutoBO = new ConsumidorProdutoBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar ConsumidorProduto", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(ConsumidorProduto consumidorProduto, @Context UriInfo uriInfo) {
        try {
        	consumidorProdutoBO.salvar(consumidorProduto);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (consumidorProduto.getIdConsumidorProduto() != null) {
                builder.path(Long.toString(consumidorProduto.getIdConsumidorProduto()));
            }

            return Response.created(builder.build()).entity("Consumidor cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar consumidor: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar consumidor: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarConsumidores() {
        try {
            List<ConsumidorProduto> consumidores = consumidorProdutoBO.listarConsumidor();
            return Response.ok(consumidores).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar consumidores: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
        	ConsumidorProduto coletador = consumidorProdutoBO.buscarPorId(id);
            if (coletador == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Consumidor não encontrado").build();
            }
            return Response.ok(coletador).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar consumidor: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarConsumidor(@PathParam("id") Long id, ConsumidorProduto consumidorProduto) {
    	consumidorProduto.setIdConsumidorProduto(id);
        try {
        	consumidorProdutoBO.atualizarConsumidorProduto(consumidorProduto);
            return Response.ok("Consumidor atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar consumidor: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirConsumidor(@PathParam("id") Long id) {
        try {
        	consumidorProdutoBO.excluirConsumidor(id);
            return Response.ok("Consumidor com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir consumidor: " + e.getMessage()).build();
        }
    }

}
