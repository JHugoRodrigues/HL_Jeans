package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.*;
import model.entities.*;
import model.factories.*;

public class ManipuladorArquivoEstoque {
    // Factories
    FabricaAbstrata fabricaCalca = new FabricaCalcaJeans();
    FabricaAbstrata fabricaJaqueta = new FabricaJaquetaJeans();
    FabricaAbstrata fabricaShorts = new FabricaShortsJeans();

    private List<Observador> observadores = new ArrayList<>();
    public List<ProdutoAbstrato> produtoPesquisado = new ArrayList<>();

    private static ManipuladorArquivoEstoque instance;
    private static final String FILE_PATH = "estoque.txt"; 

    // Singleton
    public static synchronized ManipuladorArquivoEstoque getInstance() {
        if (instance == null) {
            instance = new ManipuladorArquivoEstoque();
        }
        return instance;
    }
    
    private ManipuladorArquivoEstoque() {
        criarArquivoSeNaoExistir();
    }

    private void criarArquivoSeNaoExistir() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void adicionarObservador(Observador obs) {
        observadores.add(obs);
    }

    public void notificarObservadores(String mensagem) {
        for (Observador obs : observadores) {
            obs.atualizar(mensagem);
        }
    }

    public void adicionarProduto(ProdutoAbstrato produto) {
        produtoPesquisado.add(produto);
        salvarEstoque();
    }

    public void atualizarEstoque(String nomeProd, int venda) {
        for (ProdutoAbstrato p : produtoPesquisado) {
            if (p.getNome().equalsIgnoreCase(nomeProd)) {
                int novaQuantidade = p.getQtdEstoque() - venda;
                p.setQtdEstoque(novaQuantidade);
                
                if (novaQuantidade < 5) {
                    notificarObservadores("Estoque baixo para o produto: " + p.getNome() + ". Restam apenas " + novaQuantidade);
                }
                
                salvarEstoque();
                break;
            }
        }
    }
    
    public ProdutoAbstrato pesquisarProduto(String nomeProduto) {
        for (ProdutoAbstrato p : produtoPesquisado) {
            if (p.getNome().equalsIgnoreCase(nomeProduto)) return p;
        }
        return null;
    }
    
    public void salvarEstoque() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ProdutoAbstrato p : produtoPesquisado) {
                String tipo = "OUTRO";
                if (p instanceof CalcaJeans) tipo = "CALCA";
                else if (p instanceof ShortsJeans) tipo = "SHORTS";
                else if (p instanceof JaquetaJeans) tipo = "JAQUETA";

                writer.write(tipo + ";" + p.getNome() + ";" + p.getPreco() + ";" + p.getQtdEstoque());
                writer.newLine();
            }
        } catch (IOException e) { throw new RuntimeException("Erro ao salvar produtos!", e); }
    }
    
    public void carregarEstoque() {
        produtoPesquisado.clear(); // Para limpar a memória antes de carregar
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    String tipo = partes[0];
                    String nome = partes[1];
                    double preco = Double.parseDouble(partes[2]);
                    int qtd = Integer.parseInt(partes[3]);                      
                    
                    ProdutoAbstrato p = null;
                    if (tipo.equals("CALCA")) p = fabricaCalca.criarProduto(nome, qtd, preco);
                    else if (tipo.equals("SHORTS")) p = fabricaShorts.criarProduto(nome, qtd, preco);
                    else if (tipo.equals("JAQUETA")) p = fabricaJaqueta.criarProduto(nome, qtd, preco);
                    
                    if (p != null) produtoPesquisado.add(p);
                }
            }
        } catch (IOException e) { throw new RuntimeException("Erro ao carregar produtos!", e); }
    }
    
    public void listarProdutosNoConsole() {
        if(produtoPesquisado.isEmpty()) System.out.println("Estoque vazio.");
        for(ProdutoAbstrato p : produtoPesquisado) {
            System.out.println(p.getClass().getSimpleName() + ": " + p.getNome() + " | R$ " + p.getPreco() + " | Qtd: " + p.getQtdEstoque());
        }
    }
}