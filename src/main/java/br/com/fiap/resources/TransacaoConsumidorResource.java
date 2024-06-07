package br.com.fiap.resources;

import br.com.fiap.bo.TransacaoConsumidorBO;
import br.com.fiap.model.TransacaoConsumidor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/transacao-consumidor")
public class TransacaoConsumidorResource {
	
	private TransacaoConsumidorBO transacaoConsumidorBO;;
	
	public TransacaoConsumidorResource() {
	    try {
	        this.transacaoConsumidorBO = new TransacaoConsumidorBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar TransacaoConsumidorBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(TransacaoConsumidor transacaoConsumidor, @Context UriInfo uriInfo) {
        try {
        	transacaoConsumidorBO.salvar(transacaoConsumidor);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (transacaoConsumidor.getIdTransacaoConsumidor() != null) {
                builder.path(Long.toString(transacaoConsumidor.getIdTransacaoConsumidor()));
            }

            return Response.created(builder.build()).entity("Transação cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar transação: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar transação: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTransacao() {
        try {
            List<TransacaoConsumidor> transacoes = transacaoConsumidorBO.listarTransacao();
            return Response.ok(transacoes).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar transações: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
        	TransacaoConsumidor transacaoConsumidor = transacaoConsumidorBO.buscarPorId(id);
            if (transacaoConsumidor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Transação não encontrado").build();
            }
            return Response.ok(transacaoConsumidor).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar transação: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarTransacao(@PathParam("id") Long id, TransacaoConsumidor transacaoConsumidor) {
    	transacaoConsumidor.setIdTransacaoConsumidor(id);
        try {
        	transacaoConsumidorBO.atualizarTransacaoConsumidor(transacaoConsumidor);
            return Response.ok("Transação atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar transação: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirTransacao(@PathParam("id") Long id) {
        try {
        	transacaoConsumidorBO.excluirTransacaoConsumidor(id);
            return Response.ok("Transação com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir transação: " + e.getMessage()).build();
        }
    }

}
