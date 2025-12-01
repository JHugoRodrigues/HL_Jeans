package view;

import java.util.Scanner;
import controller.SistemaController;
import model.*;
import model.entities.ProdutoAbstrato;
import model.factories.*;

public class MenuPrincipal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaController sistema = new SistemaController();

        while (true) {
            System.out.println("\n=== BEM-VINDO AO HL JEANS (SISTEMA SQL) ===");
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
                
                try {
                    sistema.cadastrarUsuario(novoCliente);
                    System.out.println("Cliente cadastrado com sucesso!");
                } catch (Exception e) {
                    System.out.println("Erro ao cadastrar: " + e.getMessage());
                }
                continue;
            }

            if (opcaoInicial == 1) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Senha: ");
                String senha = sc.nextLine();

                Usuario logado = sistema.login(email, senha);

                if (logado == null) {
                    System.out.println("Credenciais inválidas ou usuário não encontrado.");
                } else {
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
            System.out.println("\n--- PAINEL ADMIN ---");
            System.out.println("1 - Listar produtos (Do Banco)");
            System.out.println("2 - Adicionar novo produto");
            System.out.println("0 - Deslogar");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Estoque Atual ---");
                    for (ProdutoAbstrato p : sistema.listarProdutos()) {
                        System.out.println("ID: " + p.getId() + " | " + p.getNome() + " | R$" + p.getPreco() + " | Qtd: " + p.getQtdEstoque());
                    }
                    break;
                case 2:
                    System.out.println("Tipo: 1-Calça | 2-Shorts | 3-Jaqueta");
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
                        try {
                            sistema.cadastrarProduto(novoProd);
                            System.out.println("Produto salvo!");
                        } catch (Exception e) {
                            System.out.println("Erro ao salvar produto: " + e.getMessage());
                        }
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
        System.out.println("\nBem-vindo, " + c.getNome() + "!");
        int opcao;
        do {
            System.out.println("\n--- LOJA ---");
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
                    for (ProdutoAbstrato p : sistema.listarProdutos()) {
                        System.out.println(p.getNome() + " | R$" + p.getPreco() + " | Disp: " + p.getQtdEstoque());
                    }
                    break;
                case 2:
                    System.out.print("Digite o nome do produto: ");
                    String nomeProd = sc.nextLine();
                    ProdutoAbstrato p = sistema.buscarProdutoPorNome(nomeProd);
                    
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
                        System.out.println("Seu carrinho está vazio.");
                        break;
                    }
                    
                    double total = c.getCarrinho().calcularTotal();
                    Pedido pedido = new Pedido(c, c.getCarrinho().getItens(), total);
                    pedido.exibirResumo();

                    System.out.print("Confirmar compra? (S/N): ");
                    String confirmacao = sc.nextLine();

                    if (confirmacao.equalsIgnoreCase("S")) {
                        try {
                            //Controller
                            sistema.realizarVenda(c, c.getCarrinho().getItens(), total);
                            
                            c.getCarrinho().limpar();
                            System.out.println("Compra realizada com sucesso!");
                        } catch (Exception e) {
                            System.out.println("Erro ao processar venda: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Compra cancelada.");
                    }
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