package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class ManipuladorArquivoUsuario {
    private static ManipuladorArquivoUsuario instance;
    private static final String FILE_PATH = "usuarios.txt";

    private ManipuladorArquivoUsuario() {
        criarArquivoSeNaoExistir();
    }

    public static synchronized ManipuladorArquivoUsuario getInstance() {
        if (instance == null) {
            instance = new ManipuladorArquivoUsuario();
        }
        return instance;
    }

    private void criarArquivoSeNaoExistir() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar arquivo de usuários!", e);
        }
    }

    public void salvarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Usuario u : usuarios) {
                String tipo = (u instanceof Administrador) ? "ADMIN" : "CLIENTE";
                String extra = (u instanceof Administrador) ? ((Administrador) u).getCargo() : ((Cliente) u).getEndereco();
                writer.write(tipo + ";" + u.getNome() + ";" + u.getEmail() + ";" + u.getSenha() + ";" + extra);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar usuários!", e);
        }
    }

    public List<Usuario> carregarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    String tipo = partes[0];
                    String nome = partes[1];
                    String email = partes[2];
                    String senha = partes[3];
                    String extra = partes[4];

                    if (tipo.equals("ADMIN")) {
                        lista.add(new Administrador(nome, email, senha, extra));
                    } else {
                        lista.add(new Cliente(nome, email, senha, extra));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar usuários!", e);
        }
        return lista;
    }
}