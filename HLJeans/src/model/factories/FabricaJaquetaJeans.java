package model.factories;

import model.entities.ProdutoAbstrato;
import model.entities.JaquetaJeans;

public class FabricaJaquetaJeans extends FabricaAbstrata{
	
	@Override
	public ProdutoAbstrato criarProduto(String nome, int qtdEstoque, double preco) {
		return new JaquetaJeans(nome, qtdEstoque, preco);
	}
}
