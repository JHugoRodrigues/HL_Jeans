package model;

import java.util.Map;

import model.entities.ProdutoAbstrato;

public class Pedido {
    private Cliente cliente;
    private Map<ProdutoAbstrato, Integer> itens;
    private double valorTotal;

    public Pedido(Cliente cliente, Map<ProdutoAbstrato, Integer> itens, double valorTotal) {
        this.cliente = cliente;
        this.itens = itens;
        this.valorTotal = valorTotal;
    }

    public void exibirResumo() {
        System.out.println("\n=== Pedido Confirmado ===");
        System.out.println("Cliente: " + cliente.getNome());
        for (ProdutoAbstrato p : itens.keySet()) {
            System.out.println(p.getNome() + " x" + itens.get(p));
        }
        System.out.println("Total: R$" + valorTotal);
    }
}
