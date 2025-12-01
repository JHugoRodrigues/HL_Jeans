package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class UsuarioDAO {
	// Substituiu o "ManipuladorArquivoUsuario"
	
    public void salvar(Usuario u) {
        String sql = "INSERT INTO usuarios (tipo, nome, email, senha, endereco, cargo) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (u instanceof Administrador) {
                stmt.setString(1, "ADMIN");
                stmt.setString(6, ((Administrador) u).getCargo());
                stmt.setNull(5, Types.VARCHAR);
            } else {
                stmt.setString(1, "CLIENTE");
                stmt.setString(5, ((Cliente) u).getEndereco());
                stmt.setNull(6, Types.VARCHAR);
            }
            stmt.setString(2, u.getNome());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getSenha()); 
            
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                Usuario u;
                String tipo = rs.getString("tipo");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                int id = rs.getInt("id");
                
                if (tipo.equals("ADMIN")) {
                    u = new Administrador(nome, email, senha, rs.getString("cargo"));
                } else {
                    u = new Cliente(nome, email, senha, rs.getString("endereco"));
                }
                
                u.setId(id); 
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return usuarios;
    }
}