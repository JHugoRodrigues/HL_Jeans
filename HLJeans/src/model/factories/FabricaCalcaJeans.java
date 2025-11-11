package model.factories;

import model.entities.CalcaJeans;
import model.entities.ProdutoAbstrato;

public class FabricaCalcaJeans extends FabricaAbstrata{
	
	@Override
	public ProdutoAbstrato criarProduto(String nome, int qtdEstoque, double preco) {
		return new CalcaJeans(nome, qtdEstoque, preco);
	}
	
}
