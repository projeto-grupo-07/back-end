-- ==================================================================
-- SCRIPT DE CARGA (ATUALIZADO PARA VALOR_UNITARIO)
-- ==================================================================

-- 1. LIMPEZA SEGURA (Zera tudo para garantir IDs consistentes)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE itens_venda;
TRUNCATE TABLE venda;
TRUNCATE TABLE produto;
TRUNCATE TABLE categoria;
TRUNCATE TABLE funcionario;
TRUNCATE TABLE empresa;
TRUNCATE TABLE endereco;
SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================================
-- 2. DADOS BÁSICOS (Endereço, Empresa, Funcionário)
-- ==================================================================

INSERT INTO ENDERECO (cep, estado, cidade, bairro, logradouro, numero, complemento) VALUES
('01001-000', 'SP', 'São Paulo', 'Sé', 'Praça da Sé', '100', 'Bloco A');

INSERT INTO EMPRESA (razao_social, cnpj, responsavel, fk_endereco) VALUES
('SPTech Shoes S.A.', '12.345.678/0001-90', 'Pedro Admin', 1);

-- Login: ana.vendas@empresa.com / Senha (hash bcrypt): 123
INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha) VALUES
('Ana Vendedora', '123.456.789-00', 'ana.vendas@empresa.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi');

INSERT INTO PRODUTO (modelo, marca, tamanho, cor, preco_custo, preco_venda, categoria_id) VALUES
('Tênis Runner Pro', 'SpeedMax', '40', 'Preto/Vermelho', 299.99, 479.99, 1), -- ID 1
('Scarpin Salto Fino', 'Elegance', '36', 'Nude', 159.50, 259.90, 1),      -- ID 2
('Sapatênis Confort', 'UrbanWear', '42', 'Marrom', 99.00, 179.99, 1),       -- ID 3
('Bota Coturno Couro', 'Aventureiro', '38', 'Preto Fosco', 199.50, 350.00, 1); -- ID 4

---------------------------------------------------
-- NOVO PASSO: Insere um Funcionário (Vendedor) --
---------------------------------------------------
-- INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil)
-- VALUES ('VagaDev', '123.456.789-00', 'vaga.dev@brink.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 'ADMIN');
INSERT INTO tela (titulo, path, component_key, ordem) VALUES
('Vendas', '/vendas', 'VENDAS_PAGE', 1),
('Produtos', '/produtos', 'PRODUTOS_PAGE', 2),
('Funcionários', '/funcionarios', 'FUNCIONARIOS_PAGE', 3),
('Comissão', '/comissao', 'COMISSAO_PAGE', 4),
('Desempenho', '/desempenho', 'DESEMPENHO_PAGE', 5);

-- 2. Inserir Perfis
INSERT INTO perfil (nome, descricao) VALUES
('ADMIN', 'Acesso total ao sistema'),
('GERENTE', 'Gestão de pessoas e vendas'),
('VENDEDOR', 'Operacional de vendas');

INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5);

INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5);

INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(3, 1), (3, 2);
INSERT INTO funcionario (nome, email, senha, cpf, perfil_id) VALUES
('Vinicius Admin', 'admin@brink.com', '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', '12345678900', 1),
('Gerente Teste', 'gerente@brink.com', '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', '12345678901', 2),
('Vendedor Teste', 'vendedor@brink.com', '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', '12345678902', 3);
-- FUNCIONARIO ID: 1 (Usaremos este ID como fk_vendedor)

-- FILHOS (fk_pai aponta para a raiz)
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (3, 'Sandália', 1);
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (4, 'Tênis Esportivo', 1);
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (5, 'Meias', 2);

-- VENDA 1: Compra de Tênis (2x 299.99 = 599.98)
INSERT INTO VENDA (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES
('Havaianas Top', 'Havaianas', 38, 'Azul', 29.90, 100, 3);

-- PRODUTO 2 (CALÇADO - Tênis)
INSERT INTO PRODUTO
(modelo, marca, numero, cor, valor_unitario, quantidade, fk_categoria)
VALUES
('Nike Revolution', 'Nike', 42, 'Preto', 399.90, 50, 4);

-- PRODUTO 3 (OUTROS - Meia)
INSERT INTO PRODUTO
(nome, descricao, valor_unitario, quantidade, fk_categoria)
VALUES
('Meia Soquete', 'Algodão Branca', 15.90, 200, 5);

-- ==================================================================
-- 5. VENDAS E ITENS (ATUALIZADO AQUI)
-- ==================================================================

-- VENDA 1
INSERT INTO VENDA (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES ('2025-09-26 12:30:00', 415.80, 'PIX', 1);

-- VENDA 2
INSERT INTO VENDA (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES ('2025-09-26 14:00:00', 29.90, 'DEBITO', 1);

-- ITENS DA VENDA 1 (AQUI FOI TROCADO preco_venda POR valor_unitario)
INSERT INTO ITENS_VENDA (fk_produto, fk_venda, quantidade, valor_unitario) VALUES
(2, 1, 1, 399.90), -- Tênis Nike
(3, 1, 1, 15.90);  -- Meia

-- ITENS DA VENDA 2
INSERT INTO ITENS_VENDA (fk_produto, fk_venda, quantidade, valor_unitario) VALUES
(1, 2, 1, 29.90); -- Havaianas