package br.com.fiap.resources;

import br.com.fiap.bo.ProdutorMaterialBO;
import br.com.fiap.model.ProdutorMaterial;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/produtor")
public class ProdutorMaterialResource {
	
	private ProdutorMaterialBO produtorMaterialBO;;
	
	public ProdutorMaterialResource() {
	    try {
	        this.produtorMaterialBO = new ProdutorMaterialBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar ProdutorMaterialBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(ProdutorMaterial produtorMaterial, @Context UriInfo uriInfo) {
        try {
        	produtorMaterialBO.salvar(produtorMaterial);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (produtorMaterial.getIdMaterial() != null) {
                builder.path(Long.toString(produtorMaterial.getIdMaterial()));
            }

            return Response.created(builder.build()).entity("Produtor de material cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar produtor de material: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar produtor de material: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProduto() {
        try {
            List<ProdutorMaterial> produtos = produtorMaterialBO.listarProdutor();
            return Response.ok(produtos).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar produtor de material: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
        	ProdutorMaterial produtorMaterial = produtorMaterialBO.buscarPorId(id);
            if (produtorMaterial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Produtor de material não encontrado").build();
            }
            return Response.ok(produtorMaterial).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar produtor de material: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarProdutoProdutor(@PathParam("id") Long id, ProdutorMaterial produtorMaterial) {
    	produtorMaterial.setIdProdutor(id);
        try {
        	produtorMaterialBO.atualizarProdutoProdutor(produtorMaterial);
            return Response.ok("Produtor de material atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar produtor de material: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirProdutoProdutor(@PathParam("id") Long id) {
        try {
        	produtorMaterialBO.excluirProdutoProdutor(id);
            return Response.ok("Produtor de material com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir produtor de material: " + e.getMessage()).build();
        }
    }

}
