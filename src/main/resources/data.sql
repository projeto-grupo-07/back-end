-- ==================================================================
-- SCRIPT DE CARGA COMPLETO (COMPATÍVEL COM H2) - BRINKS CALÇADOS
-- ==================================================================

SET REFERENTIAL_INTEGRITY FALSE;

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
('Brinks Calçados LTDA', '12.345.678/0001-90', 'Pedro Admin', 1);

-- ==================================================================
-- 2. TELAS E PERFIS
-- ==================================================================

INSERT INTO tela (titulo, path, component_key, ordem) VALUES
('Painel de Vendas', '/painel-vendas', 'PAINEL_VENDAS_PAGE', 0),
('Vendas', '/vendas', 'VENDAS_PAGE', 1),
('Produtos', '/produtos', 'PRODUTOS_PAGE', 2),
('Funcionários', '/funcionarios', 'FUNCIONARIOS_PAGE', 3),
('Comissão', '/comissao', 'COMISSAO_PAGE', 4),
('Desempenho', '/desempenho', 'DESEMPENHO_PAGE', 5);

INSERT INTO perfil (nome, descricao) VALUES
('ADMIN', 'Acesso total ao sistema'),
('GERENTE', 'Gestão de loja e relatórios'),
('VENDEDOR', 'Acesso apenas ao PDV e Vendas');

-- Permissões
INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6), -- Admin acessa tudo
(2,1),(2,2),(2,3),(2,4),(2,5),(2,6), -- Gerente acessa tudo
(3,1),(3,2);                         -- Vendedor só PDV e lista de Vendas

-- ==================================================================
-- 3. FUNCIONÁRIOS
-- ==================================================================

INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil_id) VALUES
('Maria Admin', '116.580.380-10', 'maria.admin@brinks.com', 8000.00, 0.00, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 1),
('Agenor Gerente', '188.116.470-53', 'agenor.gerente@brinks.com', 5000.00, 0.10, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 2),
('Ana Vendedora', '864.793.360-54', 'ana.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3),
('Roberto Vendas', '234.567.890-12', 'roberto.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3),
('Juliana Caixa', '987.654.321-00', 'juliana.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3);

-- ==================================================================
-- 4. CATEGORIAS
-- ==================================================================

-- Categorias Pai (fk_pai = NULL)
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES
(1, 'Calçados', NULL),
(2, 'Outros', NULL);

-- Subcategorias de Calçados (fk_pai = 1)
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES
(3, 'Tênis Esportivo', 1),
(4, 'Tênis Casual', 1),
(5, 'Sandália', 1),
(6, 'Chinelo', 1),
(7, 'Bota', 1),
(8, 'Sapato Social', 1),
(9, 'Chuteira', 1);

-- Subcategorias de Outros (fk_pai = 2)
INSERT INTO CATEGORIA (id, descricao, fk_pai) VALUES
(10, 'Acessórios', 2),
(11, 'Meias', 2),
(12, 'Mochilas', 2);

-- Previne conflito de ID ao criar novas categorias no FrontEnd
ALTER TABLE CATEGORIA ALTER COLUMN id RESTART WITH 20;

-- ==================================================================
-- 5. PRODUTOS
-- ==================================================================

INSERT INTO PRODUTO (id, nome, descricao, modelo, marca, numero, cor, preco_custo, valor_unitario, quantidade, fk_categoria) VALUES
(1, 'Chinelo Havaianas Top', 'Chinelo tradicional de borracha, confortável para o uso diário.', 'Top Clássico', 'Havaianas', 38, 'Azul', 14.50, 29.90, 100, 6),
(2, 'Tênis Nike Revolution 6', 'Tênis de corrida leve e respirável com amortecimento macio.', 'Revolution 6', 'Nike', 42, 'Preto', 195.00, 399.90, 50, 3),
(3, 'Tênis Adidas Ultraboost 22', 'Tênis de alta performance com retorno de energia excepcional.', 'Ultraboost 22', 'Adidas', 40, 'Branco', 450.00, 799.90, 20, 3),
(4, 'Sandália Vizzano Salto Fino', 'Sandália elegante com salto fino e fechamento ajustável.', 'Salto Fino', 'Vizzano', 37, 'Bege', 60.00, 149.90, 35, 5),
(5, 'Bota Coturno Dakota Tratorado', 'Coturno robusto com sola tratorada e acabamento resistente.', 'Coturno Tratorado', 'Dakota', 36, 'Preto', 120.00, 249.90, 15, 7),
(6, 'Sapato Social Pegada Couro', 'Sapato social masculino em couro legítimo, design clássico.', 'Social Couro', 'Pegada', 41, 'Marrom', 110.00, 229.90, 25, 8),
(7, 'Tênis Mizuno Wave Titan', 'Tênis esportivo com placa Wave para maior estabilidade.', 'Wave Titan', 'Mizuno', 43, 'Cinza', 200.00, 499.90, 40, 3),
(8, 'Chuteira Puma Future Match', 'Chuteira de campo para máximo controle de bola.', 'Future Match', 'Puma', 39, 'Laranja', 180.00, 349.90, 30, 9),
(9, 'Tênis Converse Chuck Taylor', 'O clássico tênis de lona unissex com cano baixo.', 'Chuck Taylor', 'Converse', 38, 'Branco', 100.00, 229.90, 60, 4),
(10, 'Tênis Vans Old Skool', 'Tênis casual de lona e camurça com a icônica sidestripe.', 'Old Skool', 'Vans', 40, 'Preto', 180.00, 379.90, 45, 4),
(11, 'Kit 3 Pares de Meia Lupo', 'Meias esportivas de algodão com cano médio.', 'Kit 3 Pares', 'Lupo', 40, 'Branca', 15.00, 39.90, 200, 11),
(12, 'Cinto Masculino Fasolo Couro', 'Cinto social masculino confeccionado em couro legítimo.', 'Cinto Couro', 'Fasolo', 100, 'Preto', 35.00, 79.90, 50, 10),
(13, 'Mochila Nike Brasilia JDI', 'Mochila compacta com compartimento principal espaçoso.', 'Brasilia JDI', 'Nike', 0, 'Preto', 80.00, 179.90, 20, 12),
(14, 'Sandália Kenner Rakuka', 'Sandália com palmilha extra macia e solado de borracha.', 'Rakuka', 'Kenner', 41, 'Vermelho', 45.00, 119.90, 80, 6),
(15, 'Bota Chelsea Democrata', 'Bota masculina premium com elástico lateral para fácil calce.', 'Chelsea', 'Democrata', 42, 'Marrom', 160.00, 329.90, 18, 7);

ALTER TABLE PRODUTO ALTER COLUMN id RESTART WITH 20;

-- ==================================================================
-- 6. VENDAS (Datas distribuídas para gerar gráficos)
-- ==================================================================

INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(1, '2026-02-15 10:30:00', 399.90, 'PIX', 3, 0.05, 19.99),     -- Vendedora Ana
(2, '2026-02-20 14:15:00', 459.80, 'CREDITO', 4, 0.05, 22.99), -- Vendedor Roberto
(3, '2026-02-28 09:00:00', 799.90, 'DEBITO', 5, 0.05, 39.99),  -- Vendedora Juliana
(4, '2026-03-02 11:20:00', 80.00, 'DINHEIRO', 3, 0.05, 4.00),  -- Vendedora Ana
(5, '2026-03-05 16:45:00', 229.80, 'PIX', 4, 0.05, 11.49),     -- Vendedor Roberto
(6, '2026-03-08 13:10:00', 729.80, 'CREDITO', 5, 0.05, 36.49), -- Vendedora Juliana
(7, '2026-03-10 10:05:00', 379.90, 'PIX', 3, 0.05, 18.99),     -- Vendedora Ana
(8, '2026-03-11 15:30:00', 219.90, 'DEBITO', 4, 0.05, 10.99),  -- Vendedor Roberto
(9, '2026-03-12 12:40:00', 149.90, 'PIX', 5, 0.05, 7.49),      -- Vendedora Juliana (Ontem)
(10, '2026-03-13 09:15:00', 509.80, 'CREDITO', 3, 0.05, 25.49),-- Vendedora Ana (HOJE!)
(11, '2026-03-13 14:20:00', 349.90, 'PIX', 4, 0.05, 17.49);    -- Vendedor Roberto (HOJE!)

ALTER TABLE venda ALTER COLUMN id RESTART WITH 20;
-- ==================================================================
-- 7. ITENS DE VENDA (Com descontos aplicados)
-- ==================================================================
-- Lembre-se: valor_total_venda_produto = (preco * qtd) - desconto

-- Venda 1: 1x Revolution 6
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(2, 0.0, 1, 1, 399.90);

-- Venda 2: 2x Chuck Taylor
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(9, 0.0, 2, 2, 459.80);

-- Venda 3: 1x Ultraboost + 1x Meia (Meia saiu de graça com R$ 39.90 de desconto)
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(3, 0.0, 3, 1, 799.90),
(11, 39.90, 3, 1, 0.00);

-- Venda 4: 3x Havaianas (Valor original 89.70 - Desconto 9.70 = 80.00)
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(1, 9.70, 4, 3, 80.00);

-- Venda 5: 1x Vizzano + 1x Cinto
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(4, 0.0, 5, 1, 149.90),
(12, 0.0, 5, 1, 79.90);

-- Venda 6: 1x Wave Titan + 1x Social Pegada
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(7, 0.0, 6, 1, 499.90),
(6, 0.0, 6, 1, 229.90);

-- Venda 7: 1x Vans Old Skool
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(10, 0.0, 7, 1, 379.90);

-- Venda 8: 1x Chuck Taylor (Valor original 229.90 - Desconto 10.00 = 219.90)
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(9, 10.00, 8, 1, 219.90);

-- Venda 9: 1x Vizzano
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(4, 0.0, 9, 1, 149.90);

-- Venda 10: 1x Chelsea Democrata + 1x Mochila Nike
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(15, 0.0, 10, 1, 329.90),
(13, 0.0, 10, 1, 179.90);

-- Venda 11: 1x Puma Future
INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto) VALUES
(8, 0.0, 11, 1, 349.90);