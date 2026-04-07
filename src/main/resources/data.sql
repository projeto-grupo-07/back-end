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

INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil_id, ativo   ) VALUES
('Maria Admin', '116.580.380-10', 'maria.admin@brinks.com', 8000.00, 0.00, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 1, TRUE),
('Agenor Gerente', '188.116.470-53', 'agenor.gerente@brinks.com', 5000.00, 0.10, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 2,TRUE),
('Ana Vendedora', '864.793.360-54', 'ana.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3, TRUE),
('Roberto Vendas', '234.567.890-12', 'roberto.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3, TRUE),
('Juliana Caixa', '987.654.321-00', 'juliana.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3, TRUE);
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
-- 6. VENDAS (HISTÓRICO DENSO - OUT/2025 ATÉ MAR/2026)
-- ==================================================================

-- [OUTUBRO 2025] - Início moderado
INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(1, '2025-10-05 10:00:00', 1250.00, 'CREDITO', 3, 0.05, 62.50),
(2, '2025-10-12 15:30:00', 890.00, 'PIX', 4, 0.05, 44.50),
(3, '2025-10-15 11:20:00', 350.00, 'DEBITO', 5, 0.05, 17.50),
(4, '2025-10-28 17:45:00', 2100.00, 'CREDITO', 3, 0.05, 105.00);

-- [NOVEMBRO 2025] - Esquenta Black Friday
INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(5, '2025-11-02 14:00:00', 1800.00, 'PIX', 4, 0.05, 90.00),
(6, '2025-11-15 09:30:00', 2450.00, 'CREDITO', 5, 0.05, 122.50),
(7, '2025-11-28 10:00:00', 4200.00, 'CREDITO', 3, 0.05, 210.00), -- Pico BF
(8, '2025-11-28 16:20:00', 3150.00, 'PIX', 4, 0.05, 157.50),    -- Pico BF
(9, '2025-11-29 11:00:00', 1200.00, 'DEBITO', 5, 0.05, 60.00);

-- [DEZEMBRO 2025] - Explosão de Natal
INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(10, '2025-12-05 14:00:00', 3200.00, 'CREDITO', 3, 0.05, 160.00),
(11, '2025-12-10 10:00:00', 2800.00, 'PIX', 4, 0.05, 140.00),
(12, '2025-12-15 18:30:00', 5400.00, 'CREDITO', 5, 0.05, 270.00),
(13, '2025-12-20 12:00:00', 7100.00, 'CREDITO', 3, 0.05, 355.00),
(14, '2025-12-23 15:00:00', 8900.00, 'PIX', 4, 0.05, 445.00),   -- Recorde do ano
(15, '2025-12-24 10:00:00', 4500.00, 'DINHEIRO', 5, 0.05, 225.00);

-- [JANEIRO 2026] - Baixa temporada
INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(16, '2026-01-05 11:00:00', 900.00, 'DEBITO', 3, 0.05, 45.00),
(17, '2026-01-15 14:30:00', 1200.00, 'PIX', 4, 0.05, 60.00),
(18, '2026-01-25 16:00:00', 850.00, 'CREDITO', 5, 0.05, 42.50);

-- [FEVEREIRO 2026] - Recuperação (Volta às aulas)
INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(19, '2026-02-02 09:00:00', 2200.00, 'CREDITO', 3, 0.05, 110.00),
(20, '2026-02-10 13:00:00', 3100.00, 'PIX', 4, 0.05, 155.00),
(21, '2026-02-18 15:00:00', 1800.00, 'DEBITO', 5, 0.05, 90.00),
(22, '2026-02-25 17:00:00', 2500.00, 'CREDITO', 3, 0.05, 125.00);

-- [MARÇO 2026] - Semana Passada (Simulando variação diária)
INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(23, '2026-03-09 10:00:00', 450.00, 'PIX', 4, 0.05, 22.50),   -- Seg
(24, '2026-03-10 14:00:00', 890.00, 'CREDITO', 5, 0.05, 44.50), -- Ter
(25, '2026-03-11 11:00:00', 1200.00, 'DEBITO', 3, 0.05, 60.00), -- Qua
(26, '2026-03-12 16:00:00', 2100.00, 'PIX', 4, 0.05, 105.00),  -- Qui
(27, '2026-03-13 18:00:00', 4500.00, 'CREDITO', 5, 0.05, 225.00), -- Sex (Pico)
(28, '2026-03-14 10:00:00', 5200.00, 'CREDITO', 3, 0.05, 260.00), -- Sab (Pico)
(29, '2026-03-15 14:00:00', 1100.00, 'PIX', 4, 0.05, 55.00);    -- Dom

-- [MARÇO 2026] - HOJE (Terça-feira, 17/03/2026)
INSERT INTO venda (id, data_hora, valor_total, forma_pagamento, fk_vendedor, percentual_comissao_aplicado, valor_comissao) VALUES
(30, '2026-03-17 08:30:00', 450.00, 'PIX', 3, 0.05, 22.50),
(31, '2026-03-17 10:15:00', 1200.00, 'CREDITO', 4, 0.05, 60.00),
(32, '2026-03-17 13:00:00', 800.00, 'DEBITO', 5, 0.05, 40.00),
(33, '2026-03-17 15:45:00', 2300.00, 'PIX', 3, 0.05, 115.00),
(34, '2026-03-17 17:20:00', 150.00, 'DINHEIRO', 4, 0.05, 7.50),
(35, '2026-03-17 18:00:00', 650.00, 'CREDITO', 5, 0.05, 32.50);

ALTER TABLE venda ALTER COLUMN id RESTART WITH 50;

-- ==================================================================
-- 7. ITENS DE VENDA (DETALHAMENTO PARA RANKINGS)
-- ==================================================================

  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (2, 0.0, 1, 1, 399.90, 399.90);

  -- Venda 2: 2x Chuck Taylor (R$ 229.90 cada)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (9, 0.0, 2, 2, 459.80, 229.90);

  -- Venda 3: 1x Ultraboost (R$ 799.90) + 1x Meia (R$ 39.90, saiu de graça com desconto)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (3, 0.0, 3, 1, 799.90, 799.90),
  (11, 39.90, 3, 1, 0.00, 39.90);

  -- Venda 4: 3x Havaianas (R$ 29.90 cada, valor original 89.70 - Desconto 9.70 = 80.00)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (1, 9.70, 4, 3, 80.00, 29.90);

  -- Venda 5: 1x Vizzano (R$ 149.90) + 1x Cinto (R$ 79.90)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (4, 0.0, 5, 1, 149.90, 149.90),
  (12, 0.0, 5, 1, 79.90, 79.90);

  -- Venda 6: 1x Wave Titan (R$ 499.90) + 1x Social Pegada (R$ 229.90)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (7, 0.0, 6, 1, 499.90, 499.90),
  (6, 0.0, 6, 1, 229.90, 229.90);

  -- Venda 7: 1x Vans Old Skool (R$ 379.90)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (10, 0.0, 7, 1, 379.90, 379.90);

  -- Venda 8: 1x Chuck Taylor (R$ 229.90, com desconto de R$ 10.00 = 219.90)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (9, 10.00, 8, 1, 219.90, 229.90);

  -- Venda 9: 1x Vizzano (R$ 149.90)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (4, 0.0, 9, 1, 149.90, 149.90);

  -- Venda 10: 1x Chelsea Democrata (R$ 329.90) + 1x Mochila Nike (R$ 179.90)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (15, 0.0, 10, 1, 329.90, 329.90),
  (13, 0.0, 10, 1, 179.90, 179.90);

  -- Venda 11: 1x Puma Future (R$ 349.90)
  INSERT INTO itens_venda (fk_produto, valor_desconto, fk_venda, quantidade_venda_produto, valor_total_venda_produto,
  preco_unitario_na_venda) VALUES
  (8, 0.0, 11, 1, 349.90, 349.90);