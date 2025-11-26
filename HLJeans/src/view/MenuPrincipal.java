package view;

import java.util.Map;
import java.util.Scanner;
import controller.SistemaController;
import model.*;
import model.entities.ProdutoAbstrato;
import model.factories.*;

public class MenuPrincipal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaController sistema = new SistemaController();

        // Para criar um Admin na primeira execução
        if (sistema.login("admin@hljeans.com", "1234") == null) {
        	sistema.cadastrarUsuario(new Administrador("Admin", "admin@hljeans.com", "1234", "Gerente"));
        }

        while (true) {
            System.out.println("\n=== BEM-VINDO AO HL JEANS ===");
            System.out.println("1 - Fazer Login");
            System.out.println("2 - Cadastrar Novo Cliente");
            System.out.println("0 - Sair do Sistema");
            System.out.print("Opção: ");
            
            int opcaoInicial = sc.nextInt();
            sc.nextLine();

            if (opcaoInicial == 0) {
                System.out.println("Encerrando sistema...");
                break;
            }

            if (opcaoInicial == 2) {
                // Cadastro
                System.out.println("\n--- CADASTRO DE CLIENTE ---");
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Senha: ");
                String senha = sc.nextLine();
                System.out.print("Endereço: ");
                String endereco = sc.nextLine();

                Cliente novoCliente = new Cliente(nome, email, senha, endereco);
                sistema.cadastrarUsuario(novoCliente);
                System.out.println("Cadastro realizado com sucesso! Faça login para continuar.");       
                continue;
            }

            if (opcaoInicial == 1) {
                //Login
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Senha: ");
                String senha = sc.nextLine();

                Usuario logado = sistema.login(email, senha);

                if (logado == null) {
                    System.out.println("Credenciais inválidas! Tente novamente.");
                } else {
                    // Diferenciar entre admin e cliente
                    if (logado instanceof Administrador) {
                        menuAdministrador(sc, sistema, logado);
                    } else if (logado instanceof Cliente) {
                        menuCliente(sc, sistema, (Cliente) logado);
                    }
                }
            }
        }
        sc.close();
    }

    public static void menuAdministrador(Scanner sc, SistemaController sistema, Usuario logado) {
        System.out.println("\nBem-vindo, Administrador " + logado.getNome() + "!");
        int opcao;
        do {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Adicionar novo produto");
            System.out.println("0 - Deslogar");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    sistema.getEstoque().listarProdutosNoConsole();
                    break;
                case 2:
                    System.out.println("Qual o tipo do produto?");
                    System.out.println("1 - Calça Jeans | 2 - Shorts Jeans | 3 - Jaqueta Jeans");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Quantidade: ");
                    int qtd = sc.nextInt();
                    System.out.print("Preço: ");
                    double preco = sc.nextDouble();

                    FabricaAbstrata fabrica = null;
                    if (tipo == 1) fabrica = new FabricaCalcaJeans();
                    else if (tipo == 2) fabrica = new FabricaShortsJeans();
                    else if (tipo == 3) fabrica = new FabricaJaquetaJeans();

                    if (fabrica != null) {
                        ProdutoAbstrato novoProd = fabrica.criarProduto(nome, qtd, preco);
                        sistema.getEstoque().adicionarProduto(novoProd);
                        System.out.println("Produto cadastrado com sucesso!");
                    } else {
                        System.out.println("Tipo inválido!");
                    }
                    break;
                case 0:
                    System.out.println("Deslogando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public static void menuCliente(Scanner sc, SistemaController sistema, Cliente c) {
        System.out.println("\nBem-vindo, Cliente " + c.getNome() + "!");
        int opcao;
        do {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Adicionar ao carrinho");
            System.out.println("3 - Ver carrinho");
            System.out.println("4 - Finalizar compra");
            System.out.println("0 - Deslogar");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    sistema.getEstoque().listarProdutosNoConsole();
                    break;
                case 2:
                    System.out.print("Nome do produto: ");
                    String nomeProd = sc.nextLine();
                    ProdutoAbstrato p = sistema.getEstoque().pesquisarProduto(nomeProd);
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
                    if (c.getCarrinho().getItens().isEmpty()) {
                        System.out.println("Carrinho vazio.");
                        break;
                    }
                    double total = c.getCarrinho().calcularTotal();
                    Pedido pedido = new Pedido(c, c.getCarrinho().getItens(), total);
                    pedido.exibirResumo();

                    // Atualiza o estoque
                    Map<ProdutoAbstrato, Integer> itensComprados = c.getCarrinho().getItens();
                    for (Map.Entry<ProdutoAbstrato, Integer> entry : itensComprados.entrySet()) {
                        ProdutoAbstrato prod = entry.getKey();
                        int qtdComprada = entry.getValue();
                        sistema.getEstoque().atualizarEstoque(prod.getNome(), qtdComprada);
                    }

                    c.getCarrinho().limpar();
                    System.out.println("Compra realizada com sucesso!");
                    break;
                case 0:
                    System.out.println("Deslogando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}