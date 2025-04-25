/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Gabriel
 */
@Path("Produtos")
public class Api {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<com.adricosmeticos.modelo.Produto>> obterListaProdutos() throws SQLException {

        com.adricosmeticos.negocio.Produto produtoNeg = new com.adricosmeticos.negocio.Produto();
        Map<String, List<com.adricosmeticos.modelo.Produto>> listaProdutos = produtoNeg.obterLista();

        return listaProdutos;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarProduto(com.adricosmeticos.modelo.Produto produto) {
        try {
            com.adricosmeticos.negocio.Produto produtoNeg = new com.adricosmeticos.negocio.Produto();
            boolean sucesso = produtoNeg.salvarProduto(produto);

            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity(produto).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Erro ao inserir produto").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarProduto(com.adricosmeticos.modelo.Produto produto) {
        try {
            com.adricosmeticos.negocio.Produto produtoNeg = new com.adricosmeticos.negocio.Produto();
            boolean sucesso = produtoNeg.editarProduto(produto);

            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity(produto).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Erro ao inserir produto").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/{colecao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterProdutoPorId(@PathParam("id") String id, @PathParam("colecao") String colecao) throws SQLException {

        com.adricosmeticos.negocio.Produto produtoNeg = new com.adricosmeticos.negocio.Produto();
        Optional<com.adricosmeticos.modelo.Produto> produto = produtoNeg.obterProduto(id, colecao);

        if (produto.isPresent()) {
            return Response.ok(produto.get()).build(); // HTTP 200 com produto
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Produto não encontrado.")
                    .build(); // HTTP 404
        }
    }

    @DELETE
    @Path("/{id}/{colecao}")
    public Response deletarProduto(@PathParam("id") String id, @PathParam("colecao") String colecao) throws SQLException {
        com.adricosmeticos.negocio.Produto produtoNeg = new com.adricosmeticos.negocio.Produto();
        boolean removido = produtoNeg.excluirProduto(id, colecao);

        if (removido) {
            return Response.noContent().build(); // 204 No Content
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Produto não deletado, tente novamente!")
                    .build();
        }
    }

}
