package br.com.fiap.resources;

import br.com.fiap.model.CursoSalesforce;
import br.com.fiap.bo.CursoSalesforceBO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;

@Path("/curso")
public class CursoResource {

    private CursoSalesforceBO cursoSalesforceBO = new CursoSalesforceBO();

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(CursoSalesforce curso, @Context UriInfo uriInfo) {
        try {
            cursoSalesforceBO.salvar(curso);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (curso.getId() != null) {
                builder.path(Long.toString(curso.getId()));
            }

            return Response.created(builder.build()).entity("Curso cadastrado com sucesso!").build();

        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar curso: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar curso: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarCursos() throws ClassNotFoundException, SQLException {
        try {
            List<CursoSalesforce> cursos = cursoSalesforceBO.listarCursos();
            return Response.ok(cursos).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao listar cursos: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) throws ClassNotFoundException, SQLException {
        try {
            CursoSalesforce curso = cursoSalesforceBO.buscarPorId(id);
            if (curso == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Curso não encontrado").build();
            }
            return Response.ok(curso).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao buscar curso: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarCurso(@PathParam("id") Long id, CursoSalesforce curso) {
        curso.setId(id);
        try {
            cursoSalesforceBO.atualizarCurso(curso);
            return Response.ok("Curso atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar curso: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluirCurso(@PathParam("id") Long id) {
        try {
            cursoSalesforceBO.excluirCurso(id);
            return Response.ok("Curso com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir curso: " + e.getMessage()).build();
        }
    }

}

