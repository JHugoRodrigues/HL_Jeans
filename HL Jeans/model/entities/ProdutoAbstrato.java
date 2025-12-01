package model.entities;

public abstract class ProdutoAbstrato {
    
    protected int id;
    protected String nome;
    protected int qtdEstoque;
    protected double preco;

    public ProdutoAbstrato(String nome, int qtdEstoque, double preco) {
        this.nome = nome;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
    }

    public boolean verificarDisponibilidade(int qtdPedido) {
        return this.qtdEstoque >= qtdPedido;
    }

    public void darBaixa(int qtdComprada) {
        this.qtdEstoque -= qtdComprada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public double getPreco() {
        return preco;
    }
}