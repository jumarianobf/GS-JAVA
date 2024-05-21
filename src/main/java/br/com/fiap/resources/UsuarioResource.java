package br.com.fiap.resources;

import br.com.fiap.model.*;
import br.com.fiap.vo.Usuario;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTransientConnectionException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.fiap.bo.UsuarioSalesforceBO;

@Path("/usuario")
public class UsuarioResource {

    private UsuarioSalesforceBO usuarioSalesforceBO = new UsuarioSalesforceBO();

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(Usuario usuario, @Context UriInfo uriInfo) {
        UsuarioSalesforce usuarioSalesforce = new UsuarioSalesforce();
        usuarioSalesforce.setNomeUsuario(usuario.getNomeUsuario());
        usuarioSalesforce.setTelefoneUsuario(usuario.getTelefoneUsuario());
        usuarioSalesforce.setEmail(usuario.getEmail());
        usuarioSalesforce.setRegiao(usuario.getRegiao());
        usuarioSalesforce.setEmpresaUsuario(usuario.getEmpresaUsuario());

        try {
            usuarioSalesforceBO.salvar(usuarioSalesforce);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (usuarioSalesforce.getId() != null) {
            	builder.path(Long.toString(usuarioSalesforce.getId()));
            }

            return Response.created(builder.build()).entity("Usuário cadastrado com sucesso!").build();

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() throws ClassNotFoundException, SQLException {
        try {
            List<UsuarioSalesforce> usuarios = usuarioSalesforceBO.listarUsuarios();
            return Response.ok(usuarios).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao listar usuários: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) throws ClassNotFoundException, SQLException {
        try {
            UsuarioSalesforce usuario = usuarioSalesforceBO.buscarPorId(id);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
            }
            return Response.ok(usuario).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao buscar usuário: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarUsuario(@PathParam("id") Long id, Usuario usuario) {
        UsuarioSalesforce usuarioSalesforce = new UsuarioSalesforce();
        usuarioSalesforce.setId(id);
        usuarioSalesforce.setNomeUsuario(usuario.getNomeUsuario());
        usuarioSalesforce.setTelefoneUsuario(usuario.getTelefoneUsuario());
        usuarioSalesforce.setEmail(usuario.getEmail());
        usuarioSalesforce.setRegiao(usuario.getRegiao());
        usuarioSalesforce.setEmpresaUsuario(usuario.getEmpresaUsuario());

        try {
            usuarioSalesforceBO.atualizarUsuario(usuarioSalesforce);
            return Response.ok("Atualizado com sucesso").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar usuário: " + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/excluir/{id}")
    public Response excluirUsuario(@PathParam("id") Long id) {
        try {
            usuarioSalesforceBO.excluirUsuario(id);
            return Response.ok("Usuário com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir: " + e.getMessage()).build();
        }
    }


}
