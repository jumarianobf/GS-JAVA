package br.com.fiap.resources;

import br.com.fiap.bo.CompraCursoBO;
import br.com.fiap.vo.*;
import br.com.fiap.bo.CursoSalesforceBO;
import br.com.fiap.bo.UsuarioSalesforceBO;
import br.com.fiap.model.CompraCurso;
import br.com.fiap.model.CursoSalesforce;
import br.com.fiap.model.UsuarioSalesforce;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTransientConnectionException;
import java.util.List;

@Path("/compra-curso")
public class CompraCursoResource {

    private final CompraCursoBO compraCursoBO;
    private final CursoSalesforceBO cursoSalesforceBO;
    private final UsuarioSalesforceBO usuarioSalesforceBO;

    public CompraCursoResource() throws ClassNotFoundException {
        try {
            this.compraCursoBO = new CompraCursoBO();
            this.cursoSalesforceBO = new CursoSalesforceBO();
            this.usuarioSalesforceBO = new UsuarioSalesforceBO();
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing BOs", e);
        }
    }

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(CompraCurso compra, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
    	CompraCurso compraCurso = new CompraCurso();
    	
    	
    	compra.setCursoSalesforce(cursoSalesforceBO.buscarPorId(compra.getCursoSalesforce().getId()));
        compra.setUsuarioSalesforce(usuarioSalesforceBO.buscarPorId(compra.getUsuarioSalesforce().getId()));
        try {
            
            compraCursoBO.salvar(compra);
            
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (compraCurso.getIdCompraCurso() != null) {
            builder.path(Long.toString(compra.getIdCompraCurso()));
            }

            return Response.created(builder.build()).entity("Compra cadastrada com sucesso!").build();
        } catch (SQLException e) {
            if (e.getCause() instanceof SQLRecoverableException || e.getCause() instanceof SQLTransientConnectionException) {
                return Response.serverError().entity("Erro de conexão ao cadastrar usuário: " + e.getMessage()).build();
            } else {
                return Response.serverError().entity("Erro ao cadastrar usuário: " + e.getMessage()).build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao cadastrar usuário: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) throws ClassNotFoundException {
        try {
            CompraCurso compra = compraCursoBO.buscarPorId(id);
            if (compra != null) {
                return Response.ok().entity(compra).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Compra não encontrada").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar por ID: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(CompraCurso compra, @PathParam("id") Long id) throws ClassNotFoundException {
        compra.setIdCompraCurso(id);
        try {
            compra.setCursoSalesforce(cursoSalesforceBO.buscarPorId(compra.getCursoSalesforce().getId()));
            compra.setUsuarioSalesforce(usuarioSalesforceBO.buscarPorId(compra.getUsuarioSalesforce().getId()));
            compraCursoBO.atualizarCompraCurso(compra);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluir(@PathParam("id") Long id) throws ClassNotFoundException {
        try {
            compraCursoBO.excluirCompraCurso(id);
            return Response.ok("CompraCurso com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() throws ClassNotFoundException {
        try {
            List<CompraCurso> compras = compraCursoBO.listarCompraCursos();
            return Response.ok().entity(compras).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar: " + e.getMessage()).build();
        }
    }
}
