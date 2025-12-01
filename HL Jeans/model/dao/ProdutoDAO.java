package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.entities.*;
import model.factories.*;

public class ProdutoDAO {
    // Substituiu o "ManipuladorArquivoEstoque" e o "Estoque"
	
    // Instâncias das fábricas para recriar os objetos
    private FabricaAbstrata fabricaCalca = new FabricaCalcaJeans();
    private FabricaAbstrata fabricaShorts = new FabricaShortsJeans();
    private FabricaAbstrata fabricaJaqueta = new FabricaJaquetaJeans();

    public void salvar(ProdutoAbstrato p) {
        String sql = "INSERT INTO produtos (tipo, nome, preco, qtd_estoque) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String tipo = "OUTRO";
            if (p instanceof CalcaJeans) tipo = "CALCA";
            else if (p instanceof ShortsJeans) tipo = "SHORTS";
            else if (p instanceof JaquetaJeans) tipo = "JAQUETA";
            
            stmt.setString(1, tipo);
            stmt.setString(2, p.getNome());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getQtdEstoque());
            
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    public void atualizarEstoque(ProdutoAbstrato p) {
        String sql = "UPDATE produtos SET qtd_estoque = ? WHERE id = ?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, p.getQtdEstoque());
            stmt.setInt(2, p.getId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estoque", e);
        }
    }

    public List<ProdutoAbstrato> listar() {
        List<ProdutoAbstrato> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                String tipo = rs.getString("tipo");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                int qtd = rs.getInt("qtd_estoque");
                int id = rs.getInt("id");
                
                ProdutoAbstrato p = null;
                if (tipo.equals("CALCA")) p = fabricaCalca.criarProduto(nome, qtd, preco);
                else if (tipo.equals("SHORTS")) p = fabricaShorts.criarProduto(nome, qtd, preco);
                else if (tipo.equals("JAQUETA")) p = fabricaJaqueta.criarProduto(nome, qtd, preco);
                
                if (p != null) {
                    p.setId(id);
                    produtos.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return produtos;
    }
}