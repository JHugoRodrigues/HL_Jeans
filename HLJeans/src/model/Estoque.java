package model;

import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void listarProdutos() {
        System.out.println("\n=== Produtos Disponíveis ===");
        for (Produto p : produtos) {
            System.out.println(p.getNome() + " - Estoque: " + p.getQtdEstoque() + " - R$" + p.getPreco());
        }
    }

    public Produto buscarProduto(String nome) {
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }
}
