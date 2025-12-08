-- ==================================================================
-- SCRIPT DE CARGA COMPLETO E EXPANDIDO
-- ==================================================================

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
-- 1. ENDEREÇO E EMPRESA
-- ==================================================================

INSERT INTO ENDERECO (cep, estado, cidade, bairro, logradouro, numero, complemento) VALUES
('01001-000', 'SP', 'São Paulo', 'Sé', 'Praça da Sé', '100', 'Bloco A');

INSERT INTO EMPRESA (razao_social, cnpj, responsavel, fk_endereco) VALUES
('SPTech Shoes S.A.', '12.345.678/0001-90', 'Pedro Admin', 1);

-- Telas
INSERT INTO tela (titulo, path, component_key, ordem) VALUES
('Vendas', '/vendas', 'VENDAS_PAGE', 1),
('Produtos', '/produtos', 'PRODUTOS_PAGE', 2),
('Funcionários', '/funcionarios', 'FUNCIONARIOS_PAGE', 3),
('Comissão', '/comissao', 'COMISSAO_PAGE', 4),
('Desempenho', '/desempenho', 'DESEMPENHO_PAGE', 5);

-- Perfis
INSERT INTO perfil (nome, descricao) VALUES
('ADMIN', 'Acesso total'),
('GERENTE', 'Gestão'),
('VENDEDOR', 'Vendas');

-- Perfil-Tela
INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),
(2,1),(2,2),(2,3),(2,4),(2,5),
(3,1),(3,2);

-- ==================================================================
-- 2. FUNCIONÁRIOS (MUITOS)
-- ==================================================================

SET @PWD = '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi';

-- Administração
INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil_id) VALUES
('Maria Admin', '123.456.789-02', 'maria.admin@empresa.com', 8000.00, 0.00, @PWD, 1);

-- GERENTES FIXOS
INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil_id) VALUES
('Agenor Gerente', '111.111.111-11', 'agenor.gerente@empresa.com', 5000.00, 0.10, @PWD, 2),
('Rosangela Gerente', '222.222.222-22', 'rosangela.gerente@empresa.com', 5200.00, 0.10, @PWD, 2);

-- VENDEDORES (muitos)
INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil_id) VALUES
('Ana Vendedora', '123.456.789-00', 'ana.vendas@empresa.com', 2000, 0.05, @PWD, 3),
('Carlos Santos', '333.333.333-33', 'carlos@empresa.com', 2100, 0.05, @PWD, 3),
('João Lima', '444.444.444-44', 'joao@empresa.com', 2050, 0.05, @PWD, 3),
('Fernanda Alves', '555.555.555-55', 'fernanda@empresa.com', 2200, 0.05, @PWD, 3),
('Lucas Pereira', '666.666.666-66', 'lucas@empresa.com', 2300, 0.05, @PWD, 3),
('Mariana Costa', '777.777.777-77', 'mariana@empresa.com', 2150, 0.05, @PWD, 3),
('Tiago Silva', '888.888.888-88', 'tiago@empresa.com', 2400, 0.05, @PWD, 3),
('Bruna Oliveira', '999.999.999-99', 'bruna@empresa.com', 2250, 0.05, @PWD, 3),
('Paulo Ricardo', '101.010.101-10', 'paulo@empresa.com', 2000, 0.05, @PWD, 3),
('Juliana Matos', '202.020.202-20', 'juliana@empresa.com', 2100, 0.05, @PWD, 3);

-- ==================================================================
-- 3. CATEGORIAS EXPANDIDAS
-- ==================================================================

INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES
(1, 'Calçados', NULL),
(2, 'Outros', NULL),

(3, 'Sandália', 1),
(4, 'Tênis Esportivo', 1),
(5, 'Chinelo', 1),
(6, 'Bota', 1),
(7, 'Sapato Social', 1),

(8, 'Meias', 2),
(9, 'Acessórios', 2),
(10, 'Mochilas', 2);

-- ==================================================================
-- 4. PRODUTOS (MUITOS)
-- ==================================================================

INSERT INTO PRODUTO (modelo, marca, numero, cor, valor_unitario, quantidade, fk_categoria) VALUES
('Havaianas Top', 'Havaianas', 38, 'Azul', 29.90, 100, 5),
('Nike Revolution', 'Nike', 42, 'Preto', 399.90, 50, 4),
('Bota Adventure', 'Vulcano', 41, 'Marrom', 259.90, 40, 6),
('Sapato Oxford', 'Clark', 43, 'Preto', 349.90, 35, 7),
('Sandália Comfort', 'Beira Rio', 37, 'Bege', 89.90, 80, 3),
('Tênis RunFast', 'Adidas', 40, 'Branco', 499.90, 60, 4),
('Chinelo Slide', 'Rider', 42, 'Preto', 59.90, 120, 5),
('Sandália Flat', 'Arezzo', 36, 'Vermelha', 129.90, 50, 3),
('Tênis Street', 'Puma', 43, 'Cinza', 359.90, 45, 4),
('Bota Couro', 'Western', 42, 'Cafe', 399.90, 30, 6);

INSERT INTO PRODUTO (nome, descricao, valor_unitario, quantidade, fk_categoria) VALUES
('Meia Algodão', 'Meia branca confortável', 15.90, 300, 8),
('Carteira Couro', 'Carteira masculina preta', 49.90, 90, 9),
('Mochila Urbana', 'Nylon reforçado', 129.90, 70, 10);

-- ==================================================================
-- 5. VENDAS (50+ PARA DASHBOARD)
-- ==================================================================

-- Vendedores do ID 4 ao ID 13
SET @DATA_BASE = '2025-09-01 10:00:00';
SET @V = 4;

select * from venda;

-- Gerar 50 vendas fictícias
INSERT INTO venda (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES
('2025-09-01 10:00:00', 459.80, 'PIX', 4),
('2025-09-01 13:20:00', 29.90, 'CRÉDITO', 5),
('2025-09-02 11:10:00', 399.90, 'DÉBITO', 6),
('2025-09-02 16:40:00', 89.90, 'PIX', 7),
('2025-09-03 12:30:00', 259.90, 'CRÉDITO', 8),
('2025-09-03 15:00:00', 349.90, 'PIX', 9),
('2025-09-04 11:00:00', 59.90, 'DÉBITO', 9),
('2025-09-05 14:00:00', 499.90, 'PIX', 11),
('2025-09-05 18:45:00', 129.90, 'CRÉDITO', 12),
('2025-09-06 09:10:00', 359.90, 'DÉBITO', 13);

-- ==================================================================
-- 6. ITENS DE VENDA (Distribuição aleatória)
-- ==================================================================

-- Para todas as vendas já inseridas
INSERT INTO itens_venda (fk_produto, fk_venda, quantidade_venda_produto, valor_total_venda_produto)
SELECT
  FLOOR(RAND()*10)+1,
  id,
  FLOOR(RAND()*3)+1,
  (FLOOR(RAND()*3)+1) * (SELECT valor_unitario FROM produto ORDER BY RAND() LIMIT 1)
FROM venda;

