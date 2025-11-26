package controller;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class SistemaController {
    private List<Usuario> usuarios = new ArrayList<>();
    //private Estoque estoque = new Estoque();
    private ManipuladorArquivoEstoque estoque = new ManipuladorArquivoEstoque();

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario login(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.fazerLogin(email, senha)) {
                return u;
            }
        }
        return null;
    }
    
    public ManipuladorArquivoEstoque getEstoque() {
        return estoque;
    }
}
