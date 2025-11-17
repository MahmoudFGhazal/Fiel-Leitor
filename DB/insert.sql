USE Fiel;

USE Fiel;

INSERT INTO genders (gen_gender) VALUES
  ('Male'),
  ('Female'),
  ('Other'),
  ('Prefer not to say');

INSERT INTO residence_types (rty_residence_type) VALUES
  ('House'),
  ('Apartment'),
  ('Townhouse'),
  ('Studio'),
  ('Farmhouse');

INSERT INTO street_types (sty_street_type) VALUES
  ('Street'),
  ('Avenue'),
  ('Alley'),
  ('Boulevard'),
  ('Highway'),
  ('Square');

INSERT INTO categories (cat_category, cat_active) VALUES
  ('Medicina Geral', 1),
  ('Farmacologia', 1),
  ('Anatomia', 1),
  ('Fisiologia', 1),
  ('Patologia', 1),
  ('Microbiologia', 1),
  ('Pediatria', 1),
  ('Ginecologia e Obstetrícia', 1),
  ('Cirurgia Geral', 1),
  ('Psiquiatria', 1);

INSERT INTO price_groups (prg_name, prg_margin_pct)
VALUES
 ('Padrão', 10.00),
 ('Premium', 15.00),
 ('Acadêmico', 5.00);

 INSERT INTO books (
    bok_name, bok_author, bok_publisher, bok_edition, bok_year,
    bok_isbn, bok_barcode, bok_synopsis, bok_pages,
    bok_height_cm, bok_width_cm, bok_depth_cm, bok_weight_kg,
    bok_price, bok_stock, bok_prg_id
)
VALUES
('Introdução à Medicina',
 'José Carlos Souza',
 'Editora Ciência Médica',
 '3ª',
 2022,
 '9781234567001',
 '1234567890001',
 'Livro introdutório sobre fundamentos da prática médica moderna.',
 480,
 23.5, 16.0, 3.2, 0.850,
 89.90, 15, 1
),

('Farmacologia Básica',
 'Maria Fernanda Prado',
 'MedBooks',
 '2ª',
 2021,
 '9781234567002',
 '1234567890002',
 'Princípios gerais da farmacologia, incluindo farmacocinética e farmacodinâmica.',
 720,
 24.0, 17.0, 4.0, 1.200,
 129.50, 10, 1
),

('Anatomia Humana Completa',
 'Roberto Cavalcanti',
 'Atlas Saúde',
 '6ª',
 2020,
 '9781234567003',
 '1234567890003',
 'Estudo detalhado do corpo humano com imagens e descrições anatômicas.',
 1024,
 28.0, 21.0, 5.5, 2.000,
 299.00, 5, 2
),

('Fisiologia Moderna',
 'Carlos H. J. Lima',
 'Editora FisioMed',
 '5ª',
 2021,
 '9781234567004',
 '1234567890004',
 'A fisiologia do corpo humano com foco em mecanismos clínicos e aplicados.',
 840,
 25.0, 18.0, 4.2, 1.450,
 159.90, 8, 1
),

('Patologia Geral',
 'Luciana Barros',
 'Editora Médica Nacional',
 '4ª',
 2019,
 '9781234567005',
 '1234567890005',
 'Fundamentos da patologia celular, inflamação, cicatrização e imunopatologia.',
 680,
 24.0, 17.0, 4.0, 1.300,
 189.00, 12, 1
),

('Microbiologia Clínica',
 'Mariana S. Tavares',
 'HealthScience Publisher',
 '3ª',
 2020,
 '9781234567006',
 '1234567890006',
 'Agentes infecciosos, diagnóstico laboratorial e antimicrobianos.',
 560,
 24.0, 17.5, 3.8, 1.100,
 139.90, 7, 1
),

('Pediatria Essencial',
 'Ana Paula Ribeiro',
 'Editora Infância Médica',
 '2ª',
 2022,
 '9781234567007',
 '1234567890007',
 'Cuidados essenciais, emergências e desenvolvimento pediátrico.',
 780,
 25.0, 18.0, 4.0, 1.400,
 179.90, 9, 1
),

('Ginecologia e Obstetrícia',
 'Fernanda P. Martins',
 'GinecoMed',
 '3ª',
 2021,
 '9781234567008',
 '1234567890008',
 'Conteúdo completo sobre saúde da mulher, gestação e parto.',
 760,
 25.5, 19.0, 4.3, 1.500,
 199.90, 6, 1
),

('Cirurgia Geral: Princípios e Prática',
 'Eduardo F. Rocha',
 'CirurgiaPro',
 '7ª',
 2020,
 '9781234567009',
 '1234567890009',
 'Abordagem abrangente das técnicas, fundamentos e protocolos cirúrgicos.',
 1100,
 28.0, 21.0, 5.7, 2.300,
 249.00, 4, 2
),

('Psiquiatria Clínica',
 'Henrique A. Nogueira',
 'Saúde Mental Press',
 '4ª',
 2022,
 '9781234567010',
 '1234567890010',
 'Transtornos mentais, diagnóstico e abordagens terapêuticas.',
 620,
 24.0, 17.0, 3.5, 1.200,
 169.00, 10, 1
);

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Medicina Geral'
WHERE b.bok_name = 'Introdução à Medicina';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Farmacologia'
WHERE b.bok_name = 'Farmacologia Básica';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Anatomia'
WHERE b.bok_name = 'Anatomia Humana Completa';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Fisiologia'
WHERE b.bok_name = 'Fisiologia Moderna';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Patologia'
WHERE b.bok_name = 'Patologia Geral';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Microbiologia'
WHERE b.bok_name = 'Microbiologia Clínica';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Pediatria'
WHERE b.bok_name = 'Pediatria Essencial';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Ginecologia e Obstetrícia'
WHERE b.bok_name = 'Ginecologia e Obstetrícia';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Cirurgia Geral'
WHERE b.bok_name = 'Cirurgia Geral: Princípios e Prática';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id
FROM books b
JOIN categories c ON c.cat_category = 'Psiquiatria'
WHERE b.bok_name = 'Psiquiatria Clínica';


-- =============================
-- STATUS (corrigido: ssa_status)
-- =============================
INSERT INTO status_sale (ssa_status) VALUES
('PROCESSING'),
('APPROVED'),
('DECLINED'),
('IN_TRANSIT'),
('DELIVERED'),
('EXCHANGE_REQUESTED'),
('EXCHANGE_AUTHORIZED'),
('EXCHANGED');

-- =============================
-- USERS
-- =============================
INSERT INTO users (
  usr_email, usr_password, usr_name, usr_active, usr_gen_id,
  usr_birthday, usr_cpf, usr_phone_number
)
VALUES
(
  'renata@example.com',
  '$2a$10$hashFake1',
  'Renata Giannini',
  1,
  (SELECT gen_id FROM genders WHERE gen_gender='Female' LIMIT 1),
  '2000-05-10',
  '123.456.789-00',
  '+55 11 90000-0001'
),
(
  'mahmoud@example.com',
  '$2a$10$hashFake2',
  'Mahmoud A.',
  1,
  (SELECT gen_id FROM genders WHERE gen_gender='Male' LIMIT 1),
  '1998-03-22',
  '987.654.321-00',
  '+55 11 90000-0002'
);
-- =============================
-- ADDRESSES
-- =============================
INSERT INTO addresses (
  add_usr_id, add_nickname, add_principal, add_active, add_number,
  add_complement, add_street, add_neighborhood, add_zip, add_city,
  add_state, add_country, add_sty_id, add_rty_id
)
VALUES
(
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  'Casa',
  1,
  1,
  123,
  'Ap 51',
  'Av. Paulista',
  'Bela Vista',
  '01310-100',
  'São Paulo',
  'SP',
  'Brasil',
  (SELECT sty_id FROM street_types WHERE sty_street_type='Avenue' LIMIT 1),
  (SELECT rty_id FROM residence_types WHERE rty_residence_type='Apartment' LIMIT 1)
),
(
  (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
  'Trabalho',
  1,
  1,
  450,
  NULL,
  'Rua Vergueiro',
  'Vila Mariana',
  '04101-300',
  'São Paulo',
  'SP',
  'Brasil',
  (SELECT sty_id FROM street_types WHERE sty_street_type='Street' LIMIT 1),
  (SELECT rty_id FROM residence_types WHERE rty_residence_type='House' LIMIT 1)
);
-- =============================
-- CARDS
-- =============================
INSERT INTO cards (
  car_usr_id, car_principal, car_bin, car_last4, car_holder,
  car_exp_month, car_exp_year
)
VALUES
(
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  1,
  '411111',
  '1111',
  'RENATA L G',
  '12',
  '2028'
),
(
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  0,
  '555555',
  '2222',
  'RENATA L G',
  '06',
  '2027'
),
(
  (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
  1,
  '401288',
  '4242',
  'MAHMOUD A',
  '09',
  '2029'
);


-- =============================
-- CARTS (carrinho) - usa prefixos cat_* (modelo 1)
-- =============================
-- Renata coloca 2 livros no carrinho
-- Renata coloca 2 livros no carrinho
INSERT INTO carts (crt_usr_id, crt_bok_id, crt_quantity)
SELECT u.usr_id, b.bok_id, 1
FROM users u
JOIN books b ON b.bok_name='Introdução à Medicina'
WHERE u.usr_email='renata@example.com';

INSERT INTO carts (crt_usr_id, crt_bok_id, crt_quantity)
SELECT u.usr_id, b.bok_id, 2
FROM users u
JOIN books b ON b.bok_name='Ginecologia e Obstetrícia'
WHERE u.usr_email='renata@example.com';

-- Mahmoud coloca 1 livro
INSERT INTO carts (crt_usr_id, crt_bok_id, crt_quantity)
SELECT u.usr_id, b.bok_id, 1
FROM users u
JOIN books b ON b.bok_name='Pediatria Essencial'
WHERE u.usr_email='mahmoud@example.com';


-- =============================
-- PROMOTIONAL COUPONS (pco_*)
-- =============================
INSERT INTO promotional_coupons (pco_code, pco_value, pco_used) VALUES
('abvffdf', 20.00, 0),
('abvfdsagrsfff', 35.00, 0),
('abvffhgfhfg', 50.00, 0);

-- =============================
-- SALE #1 (Renata) – PROCESSING, sem cupom ainda
-- =============================
INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  19.90,
  DATE_ADD(CURDATE(), INTERVAL 5 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='PROCESSING'),
  NULL,
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
      AND add_nickname='Casa'
    LIMIT 1)
);

-- itens da venda #1 (usa preços do catálogo no momento do pedido)
INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Introdução à Medicina'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 2, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Ginecologia e Obstetrícia'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;


-- pagamento da venda #1: 70% no cartão principal, 30% no secundário
-- 70% no cartão principal
INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 70.00
FROM sales s
JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=1
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

-- 30% no cartão secundário
INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 30.00
FROM sales s
JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=0
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;


-- =============================
-- TRADER COUPON gerado a partir da SALE #1
-- (tco_sal_id referencia a venda origem)
-- Ex.: devolução parcial de R$ 50,00
-- =============================
INSERT INTO trader_coupons (tco_code, tco_value, tco_used, tco_origin_sal_id, tco_usr_id)
SELECT 
    CONCAT('TCO', LPAD(s.sal_id, 8, '0')),
    50.00,
    0,
    s.sal_id,
    s.sal_usr_id
FROM sales s
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;


-- =============================
-- SALE #2 (Renata) – usa 1 promotional_coupon direto e 1 trader_coupon via tabela de ligação
-- =============================
INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  14.90,
  DATE_ADD(CURDATE(), INTERVAL 7 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='APPROVED'),
  (SELECT pco_id FROM promotional_coupons WHERE pco_used=0 ORDER BY pco_id LIMIT 1),
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
      AND add_nickname='Casa'
    LIMIT 1)
);


-- itens da venda #2
INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Pediatria Essencial'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;


-- pagamento da venda #2: 100% no cartão principal
INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 100.00
FROM sales s
JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=1
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;


-- vincula o trader_coupon (gerado na venda #1) para ser usado na venda #2
INSERT INTO sales_trader_coupons (sat_sal_id, sat_tco_id)
SELECT
  (SELECT s2.sal_id
   FROM sales s2
   WHERE s2.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
   ORDER BY s2.sal_id DESC
   LIMIT 1),
  (SELECT t.tco_id
     FROM trader_coupons t
    WHERE t.tco_used = 0
    ORDER BY t.tco_id
    LIMIT 1);


-- marca o promotional_coupon usado na SALE #2 como utilizado
UPDATE promotional_coupons p
   SET p.pco_used = 1
 WHERE p.pco_id = (
   SELECT sal_pco_id
     FROM sales
    WHERE sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
    ORDER BY sal_id DESC
    LIMIT 1
 );

-- marca o trader_coupon como usado (aplicado na SALE #2)
UPDATE trader_coupons
JOIN (
    SELECT tco_id
    FROM trader_coupons
    WHERE tco_used = 0
    ORDER BY tco_id
    LIMIT 1
) AS sel ON trader_coupons.tco_id = sel.tco_id
SET trader_coupons.tco_used = 1,
    trader_coupons.tco_applied_sal_id = (
        SELECT s2.sal_id
        FROM sales s2
        WHERE s2.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
        ORDER BY s2.sal_id DESC
        LIMIT 1
    );

-- =============================
-- SALE #3 (Mahmoud) – sem cupons
-- =============================
INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
  12.90,
  DATE_ADD(CURDATE(), INTERVAL 6 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='PROCESSING'),
  NULL,
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
      AND add_nickname='Trabalho'
    LIMIT 1)
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Pediatria Essencial'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 100.00
FROM sales s
JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=1
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  15.90,
  DATE_ADD(CURDATE(), INTERVAL -3 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
  (SELECT pco_id FROM promotional_coupons WHERE pco_used=0 ORDER BY pco_id LIMIT 1),
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
      AND add_nickname='Casa'
    LIMIT 1)
);

-- Itens da SALE #4
INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Fisiologia Moderna'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;


-- Pagamento 100% no cartão principal
INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 100.00
FROM sales s
JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=1
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

-- Marca o cupom como usado
UPDATE promotional_coupons p
SET p.pco_used = 1
WHERE p.pco_id = (
    SELECT sal_pco_id
    FROM sales s
    WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
    ORDER BY s.sal_id DESC
    LIMIT 1
);

INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
  10.90,
  DATE_ADD(CURDATE(), INTERVAL -7 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
  NULL,
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
      AND add_nickname='Trabalho'
    LIMIT 1)
);

-- Itens da SALE #4
INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Fisiologia Moderna'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
AND NOT EXISTS (
    SELECT 1 FROM sales_books sb
    WHERE sb.sbo_sal_id = s.sal_id
      AND sb.sbo_bok_id = b.bok_id
)
ORDER BY s.sal_id DESC
LIMIT 1;

-- Pagamento 100% no cartão principal
INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 100.00
FROM sales s
JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=1
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

UPDATE promotional_coupons p
SET p.pco_used = 1
WHERE p.pco_id = (
    SELECT sal_pco_id
    FROM sales s
    WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
    ORDER BY s.sal_id DESC
    LIMIT 1
);

 -- SALE #5 (Mahmoud) – entregue há 7 dias
INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
  10.90,
  DATE_ADD(CURDATE(), INTERVAL -7 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
  NULL,
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
      AND add_nickname='Trabalho'
    LIMIT 1)
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Patologia Geral'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 100.00
FROM sales s
JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=1
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

-- SALE #6 (Renata) – entregue há 3 dias, cupom de troca usado
INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  0.00,
  DATE_ADD(CURDATE(), INTERVAL -3 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
  NULL,
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
      AND add_nickname='Casa'
    LIMIT 1)
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Microbiologia Clínica'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

-- Pagamento com cupom de troca (gerado da venda anterior)
INSERT INTO sales_trader_coupons (sat_sal_id, sat_tco_id)
SELECT
  (SELECT s2.sal_id FROM sales s2
   WHERE s2.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
   ORDER BY s2.sal_id DESC LIMIT 1),
  t.tco_id
FROM trader_coupons t
WHERE t.tco_used = 0
ORDER BY t.tco_id ASC
LIMIT 1;


UPDATE trader_coupons t
JOIN (
    SELECT tco_id
    FROM trader_coupons
    WHERE tco_used = 0
    ORDER BY tco_id ASC
    LIMIT 1
) AS x ON t.tco_id = x.tco_id
SET 
    t.tco_used = 1,
    t.tco_applied_sal_id = (
        SELECT s.sal_id
        FROM sales s
        WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
        ORDER BY s.sal_id DESC
        LIMIT 1
    );

 -- SALE #7 (Renata) – troca concluída ontem, gera novo cupom de crédito
INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  0.00,
  DATE_ADD(CURDATE(), INTERVAL -1 DAY),
  (SELECT ssa_id FROM status_sale WHERE ssa_status='EXCHANGED'),
  NULL,
  (SELECT add_id
     FROM addresses
    WHERE add_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
      AND add_nickname='Casa'
    LIMIT 1)
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='Cirurgia Geral: Princípios e Prática'
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;

-- Gera novo trader coupon (crédito da troca)
INSERT INTO trader_coupons (
  tco_code, tco_value, tco_used, tco_origin_sal_id, tco_usr_id
)
SELECT
  CONCAT('TCO', LPAD(s.sal_id, 8, '0')),
  80.00,
  0,
  s.sal_id,
  s.sal_usr_id
FROM sales s
WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC
LIMIT 1;
