package model.factories;

import model.entities.ProdutoAbstrato;

public abstract class FabricaAbstrata {
	
	public ProdutoAbstrato criarProduto(String nome, int qtdEstoque, double preco) {
		return null;
	}
}
