package br.com.fiap.resources;

import br.com.fiap.bo.MaterialColetadoBO;
import br.com.fiap.model.MaterialColetado;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/material-coletado")
public class MaterialColetadoResource {
	
	private MaterialColetadoBO materialColetadoBO;;
	
	public MaterialColetadoResource() {
	    try {
	        this.materialColetadoBO = new MaterialColetadoBO();
	    } catch (ClassNotFoundException e) {
	    	 e.printStackTrace();
	         throw new RuntimeException("Erro ao criar MaterialColetadoBO", e);
	     }
	  }
	

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(MaterialColetado materialColetado, @Context UriInfo uriInfo) {
        try {
        	materialColetadoBO.salvar(materialColetado);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (materialColetado.getIdColetador() != null) {
                builder.path(Long.toString(materialColetado.getIdMaterial()));
            }

            return Response.created(builder.build()).entity("material coletado cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar material coletado: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar material coletado: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMaterialColetado() {
        try {
            List<MaterialColetado> coletados = materialColetadoBO.listarMaterialColetado();
            return Response.ok(coletados).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar materiais coletados: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
        	MaterialColetado materialColetado = materialColetadoBO.buscarPorId(id);
            if (materialColetado == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("material coletado não encontrado").build();
            }
            return Response.ok(materialColetado).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar material coletado: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarColetador(@PathParam("id") Long id, MaterialColetado materialColetado) {
    	materialColetado.setIdMaterial(id);
        try {
        	materialColetadoBO.atualizarMaterialColetado(materialColetado);
            return Response.ok("Coletador atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar coletador: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirColetador(@PathParam("id") Long id) {
        try {
        	materialColetadoBO.excluirMaterialColetado(id);
            return Response.ok("Coletador com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir coletador: " + e.getMessage()).build();
        }
    }

}
