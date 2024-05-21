package br.com.fiap.resources;

import br.com.fiap.bo.ProfessorSalesforceBO;

import br.com.fiap.vo.*;
import br.com.fiap.model.ProfessorSalesforce;
import br.com.fiap.model.UsuarioSalesforce;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLTransientConnectionException;
import java.util.List;

@Path("/professor")
public class ProfessorResource {

    private final ProfessorSalesforceBO professorBO = new ProfessorSalesforceBO();

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(Professor professor, @Context UriInfo uriInfo) {
    	
    	ProfessorSalesforce professorsalesforce = new ProfessorSalesforce();
    	professorsalesforce.setEspecializacaoProfessor(professor.getEspecializacaoProfessor());
    	professorsalesforce.setQtdAulas(professor.getQtdAulas());
    	professorsalesforce.setQtdMaterias(professor.getQtdMaterias());
    	professorsalesforce.setDescricaoMaterias(professor.getDescricaoMaterias());
    	
    	
    	try {
 
            professorBO.salvar(professorsalesforce);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            if (professorsalesforce.getId() != null) {
                builder.path(Long.toString(professorsalesforce.getId()));
            }

            return Response.created(builder.build()).entity("Cadastrado com sucesso!").build();
            
    	  } catch (SQLException e) {
              if (e.getCause() instanceof SQLRecoverableException || e.getCause() instanceof SQLTransientConnectionException) {
                  return Response.serverError().entity("Erro de conexão ao cadastrar professor: " + e.getMessage()).build();
              } else {
                  return Response.serverError().entity("Erro ao cadastrar professor: " + e.getMessage()).build();
              }
          } catch (Exception e) {
              return Response.serverError().entity("Erro ao cadastrar professor: " + e.getMessage()).build();
          }
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            ProfessorSalesforce professor = professorBO.buscarPorId(id);
            if (professor != null) {
                return Response.ok().entity(professor).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Professor não encontrado").build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao buscar por ID: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(ProfessorSalesforce professor, @PathParam("id") Long id) {
        professor.setId(id);
        try {
            professorBO.atualizarProfessor(professor);
            return Response.ok().build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao atualizar: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/excluir/{id}")
    public Response excluir(@PathParam("id") Long id) {
        try {
            professorBO.excluirProfessor(id);
            return Response.ok("Professor com ID " + id + " excluído com sucesso.").build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao excluir: " + e.getMessage()).build();
        }
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            List<ProfessorSalesforce> professores = professorBO.listarProfessores();
            return Response.ok().entity(professores).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.serverError().entity("Erro ao listar: " + e.getMessage()).build();
        }
    }
}
