package controller;

import java.util.List;
import java.util.Map;

import model.*;
import model.dao.*;
import model.entities.ProdutoAbstrato;

public class SistemaController {
    
    private List<Usuario> usuarios;
    
    // Comunicação com o Banco de Dados
    private UsuarioDAO usuarioDAO;
    private ProdutoDAO produtoDAO;
    private PedidoDAO pedidoDAO;

    public SistemaController() {
        this.usuarioDAO = new UsuarioDAO();
        this.produtoDAO = new ProdutoDAO();
        this.pedidoDAO = new PedidoDAO();

        this.usuarios = usuarioDAO.listar();
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarioDAO.salvar(usuario);
        
        this.usuarios = usuarioDAO.listar();
    }

    public Usuario login(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.fazerLogin(email, senha)) {
                return u;
            }
        }
        return null;
    }

    public void cadastrarProduto(ProdutoAbstrato produto) {
        produtoDAO.salvar(produto);
    }
    
    public List<ProdutoAbstrato> listarProdutos() {
        return produtoDAO.listar();
    }

    public ProdutoAbstrato buscarProdutoPorNome(String nome) {
        List<ProdutoAbstrato> lista = produtoDAO.listar();
        for (ProdutoAbstrato p : lista) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    public void realizarVenda(Cliente cliente, Map<ProdutoAbstrato, Integer> itens, double total) {
        pedidoDAO.salvarPedido(cliente, itens, total);

        for (Map.Entry<ProdutoAbstrato, Integer> entry : itens.entrySet()) {
            ProdutoAbstrato produto = entry.getKey();
            int quantidadeVendida = entry.getValue();

            int novoEstoque = produto.getQtdEstoque() - quantidadeVendida;
            produto.setQtdEstoque(novoEstoque);

            produtoDAO.atualizarEstoque(produto);
        }
    }
}