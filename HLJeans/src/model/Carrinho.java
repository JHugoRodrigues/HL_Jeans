package model;

import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private Map<Produto, Integer> itens = new HashMap<>();

    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto.verificarDisponibilidade(quantidade)) {
            itens.put(produto, itens.getOrDefault(produto, 0) + quantidade);
            System.out.println(quantidade + "x " + produto.getNome() + " adicionado(s) ao carrinho.");
        } else {
            System.out.println("Quantidade indisponível em estoque!");
        }
    }

    public void removerProduto(Produto produto) {
        itens.remove(produto);
        System.out.println("Produto removido do carrinho.");
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : itens.keySet()) {
            total += p.getPreco() * itens.get(p);
        }
        return total;
    }

    public void exibirCarrinho() {
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }
        for (Produto p : itens.keySet()) {
            System.out.println(p.getNome() + " - Qtd: " + itens.get(p) + " - R$" + p.getPreco());
        }
        System.out.println("Total: R$" + calcularTotal());
    }

    public Map<Produto, Integer> getItens() {
        return itens;
    }

    public void limpar() {
        itens.clear();
    }
}
