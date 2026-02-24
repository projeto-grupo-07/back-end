-- ==================================================================
-- SCRIPT DE CARGA COMPLETO (COMPATÍVEL COM H2)
-- ==================================================================

-- No H2, o comando equivalente ao do MySQL é este:
SET REFERENTIAL_INTEGRITY FALSE;

-- Se as tabelas já são criadas pelo Hibernate (ddl-auto),
-- o truncate costuma ser opcional, mas vamos manter a sua lógica:
TRUNCATE TABLE itens_venda;
TRUNCATE TABLE venda;
TRUNCATE TABLE produto;
TRUNCATE TABLE categoria;
TRUNCATE TABLE funcionario;
TRUNCATE TABLE empresa;
TRUNCATE TABLE endereco;
TRUNCATE TABLE perfil_tela;
TRUNCATE TABLE perfil;
TRUNCATE TABLE tela;

SET REFERENTIAL_INTEGRITY TRUE;

-- ==================================================================
-- 1. ENDEREÇO E EMPRESA
-- ==================================================================

INSERT INTO ENDERECO (cep, estado, cidade, bairro, logradouro, numero, complemento) VALUES
('01001-000', 'SP', 'São Paulo', 'Sé', 'Praça da Sé', '100', 'Bloco A');

INSERT INTO EMPRESA (razao_social, cnpj, responsavel, fk_endereco) VALUES
('SPTech Shoes S.A.', '12.345.678/0001-90', 'Pedro Admin', 1);



INSERT INTO tela (titulo, path, component_key, ordem) VALUES
('Vendas', '/vendas', 'VENDAS_PAGE', 1),
('Produtos', '/produtos', 'PRODUTOS_PAGE', 2),
('Funcionários', '/funcionarios', 'FUNCIONARIOS_PAGE', 3),
('Comissão', '/comissao', 'COMISSAO_PAGE', 4),
('Desempenho', '/desempenho', 'DESEMPENHO_PAGE', 5);

INSERT INTO tela (titulo, path, component_key, ordem)
VALUES ('Painel de Vendas', '/painel-vendas', 'PAINEL_VENDAS_PAGE', 0);

INSERT INTO perfil (nome, descricao) VALUES
('ADMIN', 'Acesso total'),
('GERENTE', 'Gestão'),
('VENDEDOR', 'Vendas');

INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(1, 6), -- ADMIN
(2, 6), -- GERENTE
(3, 6); -- VENDEDOR

INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),
(2,1),(2,2),(2,3),(2,4),(2,5),
(3,1),(3,2);

-- ==================================================================
-- 2. FUNCIONÁRIOS
-- ==================================================================

-- No H2, não usamos @PWD. Inserimos a string diretamente ou repetimos.
INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil_id) VALUES
('Maria Admin', '123.456.789-02', 'maria.admin@empresa.com', 8000.00, 0.00, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 1),
('Agenor Gerente', '111.111.111-11', 'agenor.gerente@empresa.com', 5000.00, 0.10, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 2),
('Rosangela Gerente', '222.222.222-22', 'rosangela.gerente@empresa.com', 5200.00, 0.10, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 2),
('Ana Vendedora', '123.456.789-00', 'ana.vendas@empresa.com', 2000, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3);

-- ==================================================================
-- 3. CATEGORIAS
-- ==================================================================

INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES
(1, 'Calçados', NULL),
(2, 'Outros', NULL),
(3, 'Sandália', 1),
(4, 'Tênis Esportivo', 1),
(5, 'Chinelo', 1);

-- ==================================================================
-- 4. PRODUTOS (Ajustado para os campos corretos)
-- ==================================================================

INSERT INTO PRODUTO (modelo, marca, numero, cor, valor_unitario, quantidade, fk_categoria) VALUES
('Havaianas Top', 'Havaianas', 38, 'Azul', 29.90, 100, 5),
('Nike Revolution', 'Nike', 42, 'Preto', 399.90, 50, 4);

-- ==================================================================
-- 5. VENDAS
-- ==================================================================

INSERT INTO venda (data_hora, valor_total, forma_pagamento, fk_vendedor) VALUES
('2025-09-01 10:00:00', 459.80, 'PIX', 4),
('2025-09-01 13:20:00', 29.90, 'CREDITO', 4); -- Note que 'CREDITO' não tem acento se for o Enum do Java

-- ==================================================================
-- 6. ITENS DE VENDA (Simplificado para H2)
-- ==================================================================
-- O H2 tem a função RAND(), mas a lógica de subquery complexa no INSERT
-- pode variar. Vamos inserir um exemplo fixo para garantir que funcione:

INSERT INTO itens_venda (fk_produto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(1, 1, 2, 59.80),

(2, 1, 1, 399.90);