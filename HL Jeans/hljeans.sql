-- TABELA DE USUÁRIOS --
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL, -- 'ADMIN' ou 'CLIENTE'
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    endereco VARCHAR(200), -- Apenas para Clientes
    cargo VARCHAR(50)      -- Apenas para Admins
);

-- TABELA DE PRODUTOS --
CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL, -- 'CALCA', 'SHORTS', 'JAQUETA'
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    qtd_estoque INT NOT NULL
);

-- TABELA DE PEDIDOS --
CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valor_total DECIMAL(10, 2),
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- TABELA DE ITENS DO PEDIDO --
CREATE TABLE itens_pedido (
    id SERIAL PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2),
    CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
    CONSTRAINT fk_produto FOREIGN KEY (id_produto) REFERENCES produtos(id)
);

-- USUÁRIO ADMINISTRADOR PADRÃO -- 
INSERT INTO usuarios (tipo, nome, email, senha, cargo) 
VALUES ('ADMIN', 'Admin', 'admin@hljeans.com', '1234', 'Gerente Geral');