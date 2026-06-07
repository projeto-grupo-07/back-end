-- ==================================================================
-- SCRIPT DE CARGA COMPLETO (BASE FIXA) - BRINKS CALÇADOS
-- ==================================================================

SET FOREIGN_KEY_CHECKS = 0;

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
TRUNCATE TABLE cliente;


-- ==================================================================
-- 1. ENDEREÇO E EMPRESA
-- ==================================================================

INSERT INTO endereco (id, cep, estado, cidade, bairro, logradouro, numero, complemento) VALUES
(1, '01001-000', 'SP', 'São Paulo', 'Sé', 'Praça da Sé', '100', 'Bloco A'),
(2, '20000-000', 'RJ', 'Rio de Janeiro', 'Centro', 'Av Rio Branco', '200', 'Apto 12'),
(3, '30000-000', 'MG', 'Belo Horizonte', 'Savassi', 'Rua Pernambuco', '300', NULL),
(4, '04000-000', 'SP', 'São Paulo', 'Vila Mariana', 'Rua Vergueiro', '400', 'Casa 2'),
(5, '80000-000', 'PR', 'Curitiba', 'Batel', 'Av Batel', '500', NULL),
(6, '90000-000', 'RS', 'Porto Alegre', 'Moinhos de Vento', 'Rua Padre Chagas', '600', 'Sala 4'),
(7, '40000-000', 'BA', 'Salvador', 'Pelourinho', 'Largo do Pelourinho', '700', NULL),
(8, '50000-000', 'PE', 'Recife', 'Boa Viagem', 'Av Boa Viagem', '800', 'Apto 101'),
(9, '70000-000', 'DF', 'Brasília', 'Asa Sul', 'SQS 101', '900', 'Bloco B')
ON DUPLICATE KEY UPDATE cidade=cidade;

INSERT INTO empresa (razao_social, cnpj, responsavel, fk_endereco) VALUES
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
('Desempenho', '/desempenho', 'DESEMPENHO_PAGE', 5),
('Estrategica', '/estrategica', 'ESTRATEGICA_PAGE', 6),
('Campanha', '/campanha', 'CAMPANHA_PAGE', 7),
('Clientes', '/clientes', 'CLIENTES_PAGE', 8);

INSERT INTO perfil (nome, descricao) VALUES
('ADMIN', 'Acesso total ao sistema'),
('GERENTE', 'Gestão de loja e relatórios'),
('VENDEDOR', 'Acesso apenas ao PDV e Vendas');

-- Permissões
INSERT INTO perfil_tela (perfil_id, tela_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6), (1,7), (1,8), (1, 9), -- Admin acessa tudo
(2,1),(2,2),(2,3),(2,4),(2,5),(2,6), (2,7), -- Gerente acessa tudo
(3,1),(3,2);                         -- Vendedor só PDV e lista de Vendas

-- ==================================================================
-- 3. FUNCIONÁRIOS
-- ==================================================================

INSERT INTO funcionario (nome, cpf, email, salario, comissao, senha, perfil_id, ativo) VALUES
('Maria Admin', '116.580.380-10', 'maria.admin@brinks.com', 8000.00, 0.00, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 1, TRUE),
('Agenor Gerente', '188.116.470-53', 'agenor.gerente@brinks.com', 5000.00, 0.10, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 2,TRUE),
('Ana Vendedora', '864.793.360-54', 'ana.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3, TRUE),
('Roberto Vendas', '234.567.890-12', 'roberto.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3, TRUE),
('Juliana Caixa', '987.654.321-00', 'juliana.vendas@brinks.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 3, TRUE);

-- ==================================================================
-- 4. CATEGORIAS
-- ==================================================================

-- Categorias Pai (fk_pai = NULL)
INSERT INTO categoria (id, descricao, fk_pai) VALUES
(1, 'Calçados', NULL),
(2, 'Outros', NULL);

-- Subcategorias de Calçados (fk_pai = 1)
INSERT INTO categoria (id, descricao, fk_pai) VALUES
(3, 'Tênis Esportivo', 1),
(4, 'Tênis Casual', 1),
(5, 'Sandália', 1),
(6, 'Chinelo', 1),
(7, 'Bota', 1),
(8, 'Sapato Social', 1),
(9, 'Chuteira', 1);

-- Subcategorias de Outros (fk_pai = 2)
INSERT INTO categoria (id, descricao, fk_pai) VALUES
(10, 'Acessórios', 2),
(11, 'Meias', 2),
(12, 'Mochilas', 2);

ALTER TABLE categoria AUTO_INCREMENT = 20;

-- ==================================================================
-- 5. PRODUTOS
-- ==================================================================

INSERT INTO produto (id, nome, descricao, modelo, marca, numero, cor, preco_custo, valor_unitario, quantidade, fk_categoria, ativo) VALUES
(1, 'Chinelo Havaianas Top', 'Chinelo tradicional de borracha.', 'Top Clássico', 'Havaianas', 38, 'Azul', 14.50, 29.90, 100, 6, true),
(2, 'Tênis Nike Revolution 6', 'Tênis de corrida leve e respirável.', 'Revolution 6', 'Nike', 42, 'Preto', 195.00, 399.90, 50, 3, true),
(3, 'Tênis Adidas Ultraboost 22', 'Tênis de alta performance.', 'Ultraboost 22', 'Adidas', 40, 'Branco', 450.00, 799.90, 20, 3, true),
(4, 'Sandália Vizzano Salto Fino', 'Sandália elegante com salto fino.', 'Salto Fino', 'Vizzano', 37, 'Bege', 60.00, 149.90, 35, 5, true),
(5, 'Bota Coturno Dakota', 'Coturno robusto com sola tratorada.', 'Coturno Tratorado', 'Dakota', 36, 'Preto', 120.00, 249.90, 15, 7, true),
(6, 'Sapato Social Pegada', 'Sapato social em couro legítimo.', 'Social Couro', 'Pegada', 41, 'Marrom', 110.00, 229.90, 25, 8, true),
(7, 'Tênis Mizuno Wave Titan', 'Tênis esportivo com placa Wave.', 'Wave Titan', 'Mizuno', 43, 'Cinza', 200.00, 499.90, 40, 3, true),
(8, 'Chuteira Puma Future', 'Chuteira de campo para máximo controle.', 'Future Match', 'Puma', 39, 'Laranja', 180.00, 349.90, 30, 9, true),
(9, 'Tênis Converse Chuck Taylor', 'O clássico tênis de lona unissex.', 'Chuck Taylor', 'Converse', 38, 'Branco', 100.00, 229.90, 60, 4, true),
(10, 'Tênis Vans Old Skool', 'Tênis casual de lona e camurça.', 'Old Skool', 'Vans', 40, 'Preto', 180.00, 379.90, 45, 4, true),
(11, 'Kit 3 Pares de Meia Lupo', 'Meias esportivas de algodão.', 'Kit 3 Pares', 'Lupo', 40, 'Branca', 15.00, 39.90, 200, 11, true),
(12, 'Cinto Masculino Fasolo', 'Cinto social em couro legítimo.', 'Cinto Couro', 'Fasolo', 100, 'Preto', 35.00, 79.90, 50, 10, true),
(13, 'Mochila Nike Brasilia', 'Mochila com compartimento espaçoso.', 'Brasilia JDI', 'Nike', 0, 'Preto', 80.00, 179.90, 20, 12, true),
(14, 'Sandália Kenner Rakuka', 'Sandália com palmilha extra macia.', 'Rakuka', 'Kenner', 41, 'Vermelho', 45.00, 119.90, 80, 6, true),
(15, 'Bota Chelsea Democrata', 'Bota premium com elástico lateral.', 'Chelsea', 'Democrata', 42, 'Marrom', 160.00, 329.90, 18, 7, true);

ALTER TABLE produto AUTO_INCREMENT = 20;

-- ==================================================================
-- 6. CLIENTES (FIXOS PARA DEMONSTRAÇÃO)
-- ==================================================================

INSERT INTO cliente (dt_cadastro, dt_nasc, endereco_id, genero, cpf, email, nome, telefone) VALUES
('2026-02-15', '1985-08-22', 2, 'M', '22233344455', 'joao.souza@email.com', 'João Souza', '11988882222'),
('2026-03-20', '1995-12-05', 3, 'F', '33344455566', 'ana.costa@email.com', 'Ana Costa', '21977773333'),
('2026-04-05', '1988-05-10', 4, 'M', '44455566677', 'pedro.oliveira@email.com', 'Pedro Oliveira', '31966664444'),
('2026-04-25', '2000-01-30', 5, 'O', '55566677788', 'alex.santos@email.com', 'Alex Santos', '41955555555'),
('2026-05-01', '1992-05-20', NULL, 'F', '66677788899', 'carla.dias@email.com', 'Carla Dias', '51944446666'),
('2026-05-10', '1978-10-12', 6, 'M', '77788899900', 'marcos.lima@email.com', 'Marcos Lima', '61933337777'),
('2026-05-15', '1999-02-28', 7, 'F', '88899900011', 'juliana.mendes@email.com', 'Juliana Mendes', '71922228888'),
('2026-05-17', '1982-07-07', 8, 'M', '99900011122', 'lucas.fernandes@email.com', 'Lucas Fernandes', '81911119999'),
(NULL, '1996-05-01', 9, 'O', NULL, 'sam.ribeiro@email.com', 'Sam Ribeiro', '91900000000');

SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================================
-- FIM DO SCRIPT FIXO.
-- AS VENDAS SERÃO GERADAS VIA COMMANDLINERUNNER (FAKER) NO SPRING.
-- ==================================================================