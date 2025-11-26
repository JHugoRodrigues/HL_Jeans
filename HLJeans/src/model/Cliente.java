package model;

public class Cliente extends Usuario {
    private String endereco;
    private Carrinho carrinho = new Carrinho();

    public Cliente(String nome, String email, String senha, String endereco) {
        super(nome, email, senha);
        this.endereco = endereco;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public String getEndereco() {
        return endereco;
    }
}
