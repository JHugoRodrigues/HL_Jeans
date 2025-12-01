package model.factories;

import model.entities.ShortsJeans;
import model.entities.ProdutoAbstrato;

public class FabricaShortsJeans extends FabricaAbstrata{
	
	@Override
	public ProdutoAbstrato criarProduto(String nome, int qtdEstoque, double preco) {
		return new ShortsJeans(nome, qtdEstoque, preco);
	}

}
