package controller;

import java.util.List;
import model.*;

public class SistemaController {
    private List<Usuario> usuarios;
    private ManipuladorArquivoEstoque estoqueGerenciador;
    private ManipuladorArquivoUsuario usuarioGerenciador;

    public SistemaController() {
        this.estoqueGerenciador = ManipuladorArquivoEstoque.getInstance();
        this.usuarioGerenciador = ManipuladorArquivoUsuario.getInstance();
        
        this.usuarios = usuarioGerenciador.carregarUsuarios();
        this.estoqueGerenciador.carregarEstoque();
        
        // Configura os Admins como observadores no estoque
        configurarObservadores();
    }
    
    private void configurarObservadores() {
        for(Usuario u : usuarios) {
            if(u instanceof Administrador) {
                estoqueGerenciador.adicionarObservador((Administrador) u);
            }
        }
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        usuarioGerenciador.salvarUsuarios(usuarios);
        
        if (usuario instanceof Administrador) {
            estoqueGerenciador.adicionarObservador((Administrador) usuario);
        }
    }

    public Usuario login(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.fazerLogin(email, senha)) return u;
        }
        return null;
    }
    
    public ManipuladorArquivoEstoque getEstoque() {
        return estoqueGerenciador;
    }
}