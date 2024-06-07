package br.com.fiap.resources;

import br.com.fiap.bo.ProdutoProdutorBO;
import br.com.fiap.model.ProdutoProdutor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/produto-produtor")
public class ProdutoProdutorResource {
	
	private ProdutoProdutorBO produtoProdutorBO;;
	
	public ProdutoProdutorResource() {
	    try {
	        this.produtoProdutorBO = new ProdutoProdutorBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar ProdutoProdutorBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(ProdutoProdutor produtoProdutor, @Context UriInfo uriInfo) {
        try {
        	produtoProdutorBO.salvar(produtoProdutor);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (produtoProdutor.getIdProdutoProdutor() != null) {
                builder.path(Long.toString(produtoProdutor.getIdProdutoProdutor()));
            }

            return Response.created(builder.build()).entity("Produto cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar produto: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar produto: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProduto() {
        try {
            List<ProdutoProdutor> produtos = produtoProdutorBO.listarProduto();
            return Response.ok(produtos).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar produtos: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
        	ProdutoProdutor produtoProdutor = produtoProdutorBO.buscarPorId(id);
            if (produtoProdutor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Produtos não encontrado").build();
            }
            return Response.ok(produtoProdutor).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar produtos: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarProduto(@PathParam("id") Long id, ProdutoProdutor produtoProdutor) {
    	produtoProdutor.setIdProdutoProdutor(id);
        try {
        	produtoProdutorBO.atualizarProdutoProdutor(produtoProdutor);
            return Response.ok("Produto atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar produto: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirProduto(@PathParam("id") Long id) {
        try {
        	produtoProdutorBO.excluirProdutoProdutor(id);
            return Response.ok("Produto com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir produto: " + e.getMessage()).build();
        }
    }

}
