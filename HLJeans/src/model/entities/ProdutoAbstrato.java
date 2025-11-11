package model.entities;

public abstract class ProdutoAbstrato {
	protected String nome;
	protected int qtdEstoque;
	protected double preco;

	protected ProdutoAbstrato(String nome, int qtdEstoque, double preco) {
        this.nome = nome;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
    }

	protected boolean verificarDisponibilidade(int qtdPedido) {
        return this.qtdEstoque >= qtdPedido;
    }

	protected void darBaixa(int qtdComprada) {
        this.qtdEstoque -= qtdComprada;
    }

	protected String getNome() {
        return nome;
    }

	protected int getQtdEstoque() {
        return qtdEstoque;
    }

	protected double getPreco() {
        return preco;
    }

	protected void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }
}
