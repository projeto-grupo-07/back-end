--Script de teste
-- Insere Endereços, Empresa, Cliente e Categoria (MANTIDO)
INSERT INTO ENDERECO (cep, estado, cidade, bairro, logradouro, numero, complemento) VALUES
('01001-000', 'SP', 'São Paulo', 'Sé', 'Praça da Sé', '100', 'Bloco A'),
('04543-010', 'SP', 'São Paulo', 'Vila Olímpia', 'Rua Gomes de Carvalho', '1500', 'Apto 101');

INSERT INTO EMPRESA (razao_social, cnpj, responsavel, fk_endereco) VALUES
('SPTech Shoes S.A.', '12.345.678/0001-90', 'Pedro Admin', 1);

INSERT INTO CATEGORIA (descricao) VALUES ('Calçados');
-- CATEGORIA ID: 1

INSERT INTO PRODUTO (modelo, marca, tamanho, cor, preco_custo, preco_venda, categoria_id) VALUES
('Tênis Runner Pro', 'SpeedMax', '40', 'Preto/Vermelho', 299.99, 479.99, 1), -- ID 1
('Scarpin Salto Fino', 'Elegance', '36', 'Nude', 159.50, 259.90, 1),      -- ID 2
('Sapatênis Confort', 'UrbanWear', '42', 'Marrom', 99.00, 179.99, 1),       -- ID 3
('Bota Coturno Couro', 'Aventureiro', '38', 'Preto Fosco', 199.50, 350.00, 1); -- ID 4

---------------------------------------------------
-- NOVO PASSO: Insere um Funcionário (Vendedor) --
---------------------------------------------------
INSERT INTO FUNCIONARIO (nome, cpf, email, salario, comissao, senha, perfil)
VALUES ('VagaDev', '123.456.789-00', 'vaga.dev@brink.com', 2000.00, 0.05, '$2a$10$wvjZNbqbmybP4DTXgRvNLeVcAcWo3im2C2XogDRy5aNpQi2G7hZSi', 'ADMIN');
-- FUNCIONARIO ID: 1 (Usaremos este ID como fk_vendedor)

--------------------------------------------------
-- VENDAS CORRIGIDAS: Adiciona fk_vendedor e valor_total --
--------------------------------------------------

-- VENDA 1: Compra de Tênis (2x 299.99 = 599.98)
INSERT INTO VENDA (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES
('2025-09-26 12:30:00', 599.98, 'PIX', 1); -- fk_vendedor = 1
-- VENDA ID: 1

-- VENDA 2: Compra de Sapatos (159.50 + 99.00 = 258.50)
INSERT INTO VENDA (data_hora, valor_total, forma_pagamento, fk_vendedor)
VALUES
('2025-09-26 14:00:00', 258.50, 'DEBITO', 1); -- fk_vendedor = 1
-- VENDA ID: 2

----------------------------------------------------------------------
-- PRODUTOSVENDA: Mapeamento de Itens (MANTIDO, pois estava correto) --
----------------------------------------------------------------------

-- Itens da VENDA 1 (2x Tênis)
INSERT INTO ITENS_VENDA (fk_produto, fk_venda, quantidade, preco_venda)
VALUES
(1, 1, 2, 299.99);

-- Itens da VENDA 2 (1x Scarpin e 1x Sapatênis)
INSERT INTO ITENS_VENDA (fk_produto, fk_venda, quantidade, preco_venda)
VALUES
(2, 2, 1, 159.50),
(3, 2, 1, 99.00);

INSERT INTO CATEGORIA (descricao) VALUES ('Calçados');