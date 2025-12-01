# 🧾 HL Jeans - Sistema de Vendas Online

# 👥 Integrantes

José Hugo Rodrigues de Souza

Lucas Nascimento Barros

# 🏫 Informações do Projeto

Projeto desenvolvido para o curso de Engenharia de Software da Universidade de Pernambuco (UPE). 

# 🎯 Contexto e Objetivo

O HL Jeans é um sistema de vendas online de roupas voltado para o ambiente de terminal. O objetivo é simular o funcionamento de uma loja virtual que realiza: cadastro e autenticação de usuários (clientes e administradores), gerenciamento de produtos e estoque, e processamento de compras.

O sistema visa aplicar os conceitos de engenharia de software, modelagem UML e padrões de projeto (Design Patterns) estudados na disciplina.

# 🧩 Casos de Uso Principais

👤 Cliente

1. Cadastrar-se no sistema.
2. Fazer login.
3. Pesquisar produtos no catálogo.
4. Adicionar produtos ao carrinho de compras.
5. Finalizar compra.

🧑‍💼 Administrador

1. Fazer login como administrador.
2. Adicionar produtos ao catálogo.
3. Modificar o estoque e preços..

# 🏗️ Funcionalidades Implementadas 

**1ª Entrega:**

- Cadastro de clientes.
- Login de usuários (clientes e administradores).
- Pesquisa de produtos no catálogo.
- Adicionar produtos ao carrinho.
- Finalizar compra.
- Modificação de estoque (apenas para administrador).

**2ª entrega:**

- Casos de uso do sistema devidamente implementados.
- Menu de opções interativo no terminal para os usuários criado.

**3ª entrega:**

- Modificação e implementação de novas classes.
- Integração com banco de dados relacional através do PostgreSQL.
- Fluxo do cliente e Administrador devidamente concluídos.

# 💻 Tecnologias Utilizadas

- Linguagem: Java (versão 17 ou superior)
- Modelo UML: PlantUML
- Execução: Linha de comando (Terminal)
- Banco de Dados: PostgreSQL
- Conectividade: JDBC (Java Database Connectivity)
- IDE Recomendada: Eclipse ou IntelliJ IDEA

# ⚙️ Como Executar o Projeto

**Pré-requisitos**

1. Ter o Java instalado.
2. Ter o PostgreSQL instalado e rodando.
3. Ter o driver PostgreSQL JDBC adicionado ao projeto.

**Passo 1: Configurar o Banco de Dados**

- Crie um banco de dados no PostgreSQL chamado hljeans.
- Execute o script SQL contido no arquivo banco_de_dados.sql (disponível na pasta "HL Jeans" deste repositório) para criar as tabelas e o usuário administrador padrão.

**Passo 2: Configurar a Conexão**

- Abra o arquivo src/model/dao/ConexaoFactory.java
- Verifique se a senha do banco (PASS) corresponde à senha do seu PostgreSQL local:
''' private static final String PASS = "sua_senha_aqui"; '''

**Passo 3: Rodar a Aplicação**

- Abra o projeto na sua IDE.
- Execute a classe principal: view.MenuPrincipal.java.
- Siga as instruções no terminal.

Para testar as funcionalidades de administrador, utilize as credenciais abaixo:

**Email: admin@hljeans.com**
**Senha: 1234**
