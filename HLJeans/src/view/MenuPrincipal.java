package view;

import java.util.Scanner;
import controller.SistemaController;
import model.*;

public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaController sistema = new SistemaController();

        // Cadastro inicial
        Administrador admin = new Administrador("Admin", "admin@hljeans.com", "1234", "Gerente");
        sistema.cadastrarUsuario(admin);

        Cliente cliente = new Cliente("Hugo", "hugo@email.com", "123", "Rua A, 123");
        sistema.cadastrarUsuario(cliente);

        // Produtos de exemplo
        sistema.getEstoque().adicionarProduto(new Produto("Camisa Jeans", 10, 89.90));
        sistema.getEstoque().adicionarProduto(new Produto("Calça Skinny", 5, 149.90));

        System.out.println("=== HL JEANS ===");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario logado = sistema.login(email, senha);

        if (logado == null) {
            System.out.println("Credenciais inválidas!");
        } else if (logado instanceof Administrador) {
            System.out.println("\nBem-vindo, administrador!");
            sistema.getEstoque().listarProdutos();
        } else if (logado instanceof Cliente) {
            Cliente c = (Cliente) logado;
            int opcao;
            do {
                System.out.println("\n1 - Listar produtos");
                System.out.println("2 - Adicionar ao carrinho");
                System.out.println("3 - Ver carrinho");
                System.out.println("4 - Finalizar compra");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        sistema.getEstoque().listarProdutos();
                        break;
                    case 2:
                        System.out.print("Nome do produto: ");
                        String nomeProd = sc.nextLine();
                        Produto p = sistema.getEstoque().buscarProduto(nomeProd);
                        if (p == null) {
                            System.out.println("Produto não encontrado!");
                        } else {
                            System.out.print("Quantidade: ");
                            int qtd = sc.nextInt();
                            sc.nextLine();
                            c.getCarrinho().adicionarProduto(p, qtd);
                        }
                        break;
                    case 3:
                        c.getCarrinho().exibirCarrinho();
                        break;
                    case 4:
                        double total = c.getCarrinho().calcularTotal();
                        Pedido pedido = new Pedido(c, c.getCarrinho().getItens(), total);
                        pedido.exibirResumo();
                        c.getCarrinho().limpar();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } while (opcao != 0);
        }
        sc.close();
    }
}
