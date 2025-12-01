package model.dao;

import java.sql.*;
import java.util.Map;
import model.*;
import model.entities.ProdutoAbstrato;

public class PedidoDAO {

    public void salvarPedido(Cliente cliente, Map<ProdutoAbstrato, Integer> itens, double total) {
        String sqlPedido = "INSERT INTO pedidos (id_usuario, valor_total) VALUES (?, ?)";
        String sqlItem = "INSERT INTO itens_pedido (id_pedido, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = ConexaoFactory.getConnection();
            conn.setAutoCommit(false);

            // Salva o pedido e recupera o ID que o banco vai gerar
            PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setInt(1, cliente.getId());
            stmtPedido.setDouble(2, total);
            stmtPedido.executeUpdate();

            int idPedido = 0;
            ResultSet rs = stmtPedido.getGeneratedKeys();
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }

            PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
            for (Map.Entry<ProdutoAbstrato, Integer> entry : itens.entrySet()) {
                ProdutoAbstrato prod = entry.getKey();
                int qtd = entry.getValue();

                stmtItem.setInt(1, idPedido);
                stmtItem.setInt(2, prod.getId());
                stmtItem.setInt(3, qtd);
                stmtItem.setDouble(4, prod.getPreco());
                stmtItem.executeUpdate();
            }

            conn.commit(); 
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Desfazer tudo se der algum erro
            } catch (SQLException ex) { ex.printStackTrace(); }
            throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage(), e);
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}