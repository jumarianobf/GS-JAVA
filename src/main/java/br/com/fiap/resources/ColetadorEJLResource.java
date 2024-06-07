package br.com.fiap.resources;

import br.com.fiap.bo.ColetadorEJLBO;
import br.com.fiap.model.ColetadorEJL;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTransientConnectionException;
import java.util.List;

@Path("/coletador")
public class ColetadorEJLResource {
    
    private ColetadorEJLBO coletadorEJLBO;

    public ColetadorEJLResource() throws ClassNotFoundException {
        this.coletadorEJLBO = new ColetadorEJLBO();
    }
    

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(ColetadorEJL coletador, @Context UriInfo uriInfo) {
        try {
            coletadorEJLBO.salvar(coletador);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (coletador.getIdColetador() != null) {
                builder.path(Long.toString(coletador.getIdColetador()));
            }

            return Response.created(builder.build()).entity("Coletador cadastrado com sucesso!").build();

        } catch (SQLRecoverableException | SQLTransientConnectionException e) {
            return Response.serverError().entity("Erro de conexão ao cadastrar coletador: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar coletador: " + e.getMessage()).build();
        }
    }




    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarColetadores() {
        try {
            List<ColetadorEJL> coletadores = coletadorEJLBO.listarColetadores();
            return Response.ok(coletadores).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar coletadores: " + e.getMessage()).build();
        }
    }
    
 

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            ColetadorEJL coletador = coletadorEJLBO.buscarPorId(id);
            if (coletador == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Coletador não encontrado").build();
            }
            return Response.ok(coletador).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar coletador: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarColetador(@PathParam("id") Long id, ColetadorEJL coletadorEJL) {
    	coletadorEJL.setIdColetador(id);
        try {
            coletadorEJLBO.atualizarColetador(coletadorEJL);
            return Response.ok("Coletador atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar coletador: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirColetador(@PathParam("id") Long id) {
        try {
            coletadorEJLBO.excluirColetador(id);
            return Response.ok("Coletador com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir coletador: " + e.getMessage()).build();
        }
    }

}

