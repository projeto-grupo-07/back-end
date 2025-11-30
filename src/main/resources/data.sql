-- ==================================================================
-- SCRIPT DE CARGA (ATUALIZADO PARA VISUALIZAÇÃO UNIFICADA)
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

-- ==================================================================
-- 3. CATEGORIAS
-- ==================================================================

-- RAÍZES
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (1, 'Calçados', NULL);
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (2, 'Outros', NULL);

-- FILHOS
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (3, 'Sandália', 1);
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (4, 'Tênis Esportivo', 1);
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES (5, 'Meias', 2);

-- ==================================================================
-- 4. PRODUTOS
-- ==================================================================

INSERT INTO PRODUTO (modelo, marca, numero, cor, valor_unitario, quantidade, fk_categoria) VALUES
('Havaianas Top', 'Havaianas', 38, 'Azul', 29.90, 100, 3);

INSERT INTO PRODUTO (modelo, marca, numero, cor, valor_unitario, quantidade, fk_categoria) VALUES
('Nike Revolution', 'Nike', 42, 'Preto', 399.90, 50, 4);

INSERT INTO PRODUTO (nome, descricao, valor_unitario, quantidade, fk_categoria) VALUES
('Meia Soquete', 'Algodão Branca', 15.90, 200, 5);

-- ==================================================================
-- 5. VENDAS E ITENS
-- ==================================================================

INSERT INTO VENDA (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES ('2025-09-26 12:30:00', 415.80, 'PIX', 1);

INSERT INTO VENDA (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES ('2025-09-26 14:00:00', 29.90, 'DEBITO', 1);

-- Inserção mantém os dados puros (Boas práticas)
INSERT INTO ITENS_VENDA (fk_produto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(2, 1, 1, 399.90), -- Tênis
(3, 1, 1, 15.90);  -- Meia

INSERT INTO ITENS_VENDA (fk_produto, fk_venda,quantidade_venda_produto, valor_total_venda_produto) VALUES
(1, 2, 1, 29.90); -- Havaianas

-- ==================================================================
-- 6. CONSULTAS (AQUI ESTÁ A ALTERAÇÃO SOLICITADA)
-- ==================================================================

--select * from produto;
--select * from categoria;
--select * from venda;
--select * from itens_venda;
--
---- Exibe QUANTIDADE e VALOR TOTAL num campo só com nome GRANDE/PRIMÁRIO
--SELECT
--    fk_venda,
--    fk_produto,
--    -- Concatena Qtd + Texto + Cálculo do Total
--    CONCAT(quantidade_venda_produto, ' un. x R$ ', valor_total_venda_produto, ' = Total: R$ ', (quantidade_venda_produto * valor_total_venda_produto))
--    AS 'QUANTIDADE_E_VALOR_TOTAL_VENDA_PRODUTO'
--FROM itens_venda;