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
  ('Psiquiatria', 1),
  ('Fantasia', 1),
  ('Ação', 1),
  ('Aventura', 1),
  ('Romance', 1),
  ('Romance Contemporâneo', 1),
  ('Romance Histórico', 1),
  ('Ficção Científica', 1),
  ('Suspense', 1),
  ('Thriller', 1),
  ('Mistério', 1),
  ('Terror', 1),
  ('Drama', 1),
  ('Comédia', 1),
  ('Literatura Clássica', 1),
  ('Biografia', 1),
  ('História', 1),
  ('Desenvolvimento Pessoal', 1),
  ('Negócios', 1),
  ('Autoajuda', 1),
  ('Poesia', 1),
  ('Contos', 1),
  ('HQ / Graphic Novel', 1),
  ('Jovem Adulto', 1),
  ('Infantil', 1),
  ('Distopia', 1),
  ('Slice of Life', 1),
  ('Cyberpunk', 1),
  ('Steampunk', 1);


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

INSERT INTO books (
    bok_name, bok_author, bok_publisher, bok_edition, bok_year,
    bok_isbn, bok_barcode, bok_synopsis, bok_pages,
    bok_height_cm, bok_width_cm, bok_depth_cm, bok_weight_kg,
    bok_price, bok_stock, bok_prg_id
)
VALUES
('A Guerra dos Mundos','H. G. Wells','Clássicos SciFi','1ª',2010,
 '9780451528551','9988776655441',
 'Invasão alienígena clássica que definiu a ficção científica moderna.',
 260,21,14,2,0.4,49.90,20,1),

('Orgulho e Preconceito','Jane Austen','Literatura Clássica Press','3ª',2012,
 '9780141439518','9988776655442',
 'Romance clássico sobre relações sociais e escolhas pessoais.',
 432,20.5,13.5,2.5,0.42,39.90,30,1),

('Harry Potter e a Pedra Filosofal','J. K. Rowling','Rocco','1ª',2000,
 '9788532511010','9988776655443',
 'Primeiro livro da saga mundialmente famosa de fantasia.',
 310,23,16,2.8,0.5,59.90,40,1),

('Cyberpunk 2077: No Future','V. Kawaguchi','FuturoPress','1ª',2023,
 '9788532511019','9988776655449',
 'Conto distópico em Night City explorando tecnologia e humanidade.',
 290,22,15,2.2,0.43,69.90,25,2);

 INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id 
FROM books b 
JOIN categories c ON c.cat_category = 'Ficção Científica'
WHERE b.bok_name='A Guerra dos Mundos';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id 
FROM books b 
JOIN categories c ON c.cat_category = 'Romance'
WHERE b.bok_name='Orgulho e Preconceito';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id 
FROM books b 
JOIN categories c ON c.cat_category = 'Fantasia'
WHERE b.bok_name='Harry Potter e a Pedra Filosofal';

INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id 
FROM books b 
JOIN categories c ON c.cat_category = 'Cyberpunk'
WHERE b.bok_name='Cyberpunk 2077: No Future';

INSERT INTO sales (sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id, sal_created_at)
VALUES (
 (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 14.90, '2024-09-18',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 NULL,
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
 '2024-09-12'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s 
JOIN books b ON b.bok_name='Harry Potter e a Pedra Filosofal'
WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC LIMIT 1;

INSERT INTO sales (sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id, sal_created_at)
VALUES (
 (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
 12.90, '2024-10-05',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 NULL,
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')),
 '2024-10-01'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s
JOIN books b ON b.bok_name='A Guerra dos Mundos'
WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
ORDER BY s.sal_id DESC LIMIT 1;

INSERT INTO sales (sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id, sal_created_at)
VALUES (
 (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 9.90, '2024-12-22',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 NULL,
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
 '2024-12-18'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s 
JOIN books b ON b.bok_name='Orgulho e Preconceito'
WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC LIMIT 1;

INSERT INTO sales (sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id, sal_created_at)
VALUES (
 (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 11.90, '2025-01-14',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 NULL,
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
 '2025-01-10'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s 
JOIN books b ON b.bok_name='Cyberpunk 2077: No Future'
WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
ORDER BY s.sal_id DESC LIMIT 1;

INSERT INTO sales (sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id, sal_created_at)
VALUES (
 (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
 9.90, '2025-02-09',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 NULL,
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')),
 '2025-02-05'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT s.sal_id, b.bok_id, 1, b.bok_price
FROM sales s 
JOIN books b ON b.bok_name='Harry Potter e a Pedra Filosofal'
WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
ORDER BY s.sal_id DESC LIMIT 1;

INSERT INTO books (
    bok_name, bok_author, bok_publisher, bok_edition, bok_year,
    bok_isbn, bok_barcode, bok_synopsis, bok_pages,
    bok_height_cm, bok_width_cm, bok_depth_cm, bok_weight_kg,
    bok_price, bok_stock, bok_prg_id
)
VALUES
('O Senhor dos Anéis: A Sociedade do Anel',
 'J. R. R. Tolkien',
 'HarperCollins Brasil',
 '3ª',
 2019,
 '9788595084742',
 '9788595084742',
 'O início da jornada épica para destruir o Um Anel.',
 576,
 23.0, 16.0, 3.5, 0.90,
 69.90, 20, 1
),

('Duna',
 'Frank Herbert',
 'Aleph',
 '1ª',
 2020,
 '9788576574885',
 '9788576574885',
 'Clássico absoluto de ficção científica política.',
 680,
 23.5, 16.5, 4.0, 1.10,
 89.90, 15, 1
),

('It: A Coisa',
 'Stephen King',
 'Suma',
 '1ª',
 2014,
 '9788501401869',
 '9788501401869',
 'Um mal ancestral ressurge na cidade de Derry.',
 1104,
 24.0, 16.0, 5.2, 1.55,
 79.90, 18, 1
),

('A Cinco Passos de Você',
 'Rachael Lippincott',
 'Globo Alt',
 '1ª',
 2019,
 '9788525067423',
 '9788525067423',
 'Romance dramático entre dois jovens com fibrose cística.',
 288,
 21.0, 14.0, 2.0, 0.40,
 34.90, 25, 1
),

('Neuromancer',
 'William Gibson',
 'Aleph',
 '2ª',
 2016,
 '9788576573130',
 '9788576573130',
 'O livro que definiu o cyberpunk moderno.',
 320,
 22.0, 15.0, 2.2, 0.45,
 59.90, 12, 1
),

('O Sol é Para Todos',
 'Harper Lee',
 'J. B. Lippincott & Co.',
 '4ª',
 2018,
 '9780061120084',
 '9780061120084',
 'Um clássico sobre justiça e racismo no sul dos EUA.',
 376,
 21.0, 14.0, 2.3, 0.43,
 39.90, 28, 1
),

('A Seleção',
 'Kiera Cass',
 'Seguinte',
 '1ª',
 2012,
 '9788565765015',
 '9788565765015',
 'Um romance distópico envolvendo competição e monarquia.',
 360,
 23.0, 15.5, 2.8, 0.45,
 39.90, 30, 1
),

('O Hobbit',
 'J. R. R. Tolkien',
 'HarperCollins Brasil',
 '2ª',
 2020,
 '9788595084759',
 '9788595084759',
 'A aventura que antecede O Senhor dos Anéis.',
 320,
 23.0, 16.0, 2.8, 0.60,
 49.90, 22, 1
),

('Coraline',
 'Neil Gaiman',
 'Rocco',
 '1ª',
 2017,
 '9788532530782',
 '9788532530782',
 'Uma aventura sombria que mistura fantasia e terror.',
 224,
 21.0, 14.0, 1.8, 0.30,
 29.90, 18, 1
),

('O Conto da Aia',
 'Margaret Atwood',
 'Rocco',
 '2ª',
 2017,
 '9788532530799',
 '9788532530799',
 'Uma distopia sobre controle social e opressão feminina.',
 368,
 23.0, 15.5, 2.4, 0.48,
 54.90, 14, 1
),

('Sherlock Holmes: Um Estudo em Vermelho',
 'Arthur Conan Doyle',
 'Penguin Companhia',
 '1ª',
 2015,
 '9788563560910',
 '9788563560910',
 'A primeira aventura do detetive mais famoso do mundo.',
 224,
 20.5, 13.5, 1.8, 0.35,
 24.90, 19, 1
),

('As Crônicas de Nárnia: O Leão, a Feiticeira e o Guarda-Roupa',
 'C. S. Lewis',
 'HarperCollins Brasil',
 '4ª',
 2018,
 '9788595084322',
 '9788595084322',
 'A clássica fantasia infantil que marcou gerações.',
 208,
 22.0, 15.0, 2.0, 0.35,
 39.90, 27, 1
);

-- Senhor dos Anéis → Fantasia
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Fantasia'
WHERE b.bok_name='O Senhor dos Anéis: A Sociedade do Anel';

-- Duna → Ficção Científica
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Ficção Científica'
WHERE b.bok_name='Duna';

-- It → Terror
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Terror'
WHERE b.bok_name='It: A Coisa';

-- A Cinco Passos de Você → Drama
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Drama'
WHERE b.bok_name='A Cinco Passos de Você';

-- Neuromancer → Cyberpunk
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Cyberpunk'
WHERE b.bok_name='Neuromancer';

-- O Sol é Para Todos → Literatura Clássica
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Literatura Clássica'
WHERE b.bok_name='O Sol é Para Todos';

-- A Seleção → Romance
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Romance'
WHERE b.bok_name='A Seleção';

-- O Hobbit → Fantasia
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Fantasia'
WHERE b.bok_name='O Hobbit';

-- Coraline → Terror
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Terror'
WHERE b.bok_name='Coraline';

-- O Conto da Aia → Distopia
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Distopia'
WHERE b.bok_name='O Conto da Aia';

-- Sherlock Holmes → Mistério
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Mistério'
WHERE b.bok_name='Sherlock Holmes: Um Estudo em Vermelho';

-- As Crônicas de Nárnia → Fantasia
INSERT INTO books_categories (bca_bok_id, bca_cat_id)
SELECT b.bok_id, c.cat_id FROM books b
JOIN categories c ON c.cat_category='Fantasia'
WHERE b.bok_name='As Crônicas de Nárnia: O Leão, a Feiticeira e o Guarda-Roupa';

-- =============================
-- NOVOS 20 LIVROS
-- =============================
INSERT INTO books (
    bok_name, bok_author, bok_publisher, bok_edition, bok_year,
    bok_isbn, bok_barcode, bok_synopsis, bok_pages,
    bok_height_cm, bok_width_cm, bok_depth_cm, bok_weight_kg,
    bok_price, bok_stock, bok_prg_id
) VALUES
('Embriologia Clínica',
 'Paulo S. Martins',
 'MedEdu',
 '2ª', 2021,
 '9781234500001','8887776665551',
 'Estudo da formação embrionária e implicações clínicas.',
 540,24,17,3.5,1.10,149.90,15,1),

('Imunologia Médica Avançada',
 'Helena Costa',
 'BioPress',
 '1ª', 2023,
 '9781234500002','8887776665552',
 'Mecanismos imunológicos e doenças autoimunes.',
 680,25,18,4.0,1.25,199.00,12,1),

('Radiologia para Estudantes',
 'Marcelo Linardi',
 'ImageMed',
 '3ª', 2022,
 '9781234500003','8887776665553',
 'Guia completo de interpretação radiológica.',
 720,27,20,4.8,1.80,229.90,8,2),

('Traumatologia Essencial',
 'Eduardo N. Prado',
 'CirurgiaPro',
 '2ª', 2020,
 '9781234500004','8887776665554',
 'Tratamento prático de fraturas e traumas.',
 840,26,19,4.2,1.60,249.90,10,2),

('Ecocardiografia Moderna',
 'Lívia R. Mendes',
 'CardioBooks',
 '1ª', 2022,
 '9781234500005','8887776665555',
 'Técnicas de ecocardiografia e interpretação clínica.',
 430,23,17,2.8,0.95,179.90,13,1),

('Neurologia em Casos Clínicos',
 'Henrique Ramos',
 'NeuroScience Press',
 '4ª', 2021,
 '9781234500006','8887776665556',
 'Casos clínicos detalhados em neurologia.',
 520,24,17,3.2,1.10,159.90,17,1),

('Dermatologia Prática',
 'Fernanda Queiroz',
 'DermaMed',
 '5ª', 2020,
 '9781234500007','8887776665557',
 'Diagnóstico e tratamento de doenças dermatológicas.',
 600,25,18,3.8,1.30,189.90,14,1),

('Atlas de Farmacologia Clínica',
 'Marco A. Silva',
 'PharmaAtlas',
 '3ª', 2023,
 '9781234500008','8887776665558',
 'Atlas completo de drogas e mecanismos de ação.',
 710,27,20,4.5,1.95,259.90,6,2),

('Patologias Hepáticas',
 'Joana F. Duarte',
 'HepatoPress',
 '1ª', 2019,
 '9781234500009','8887776665559',
 'Estudo das doenças hepáticas e terapias atuais.',
 480,23,16,3.0,0.90,129.00,20,1),

('Pneumologia Clínica',
 'Roberta A. Mendes',
 'PulmoBooks',
 '1ª', 2021,
 '9781234500010','8887776665560',
 'Doenças respiratórias e tratamentos modernos.',
 540,24,18,3.7,1.20,159.90,18,1),

('O Código Da Vinci',
 'Dan Brown',
 'Arqueiro',
 '1ª', 2004,
 '9781234500011','8887776665561',
 'Suspense envolvendo mistério religioso e simbologia.',
 446,22,15,2.8,0.60,49.90,40,1),

('A Sombra do Vento',
 'Carlos Ruiz Zafón',
 'Suma',
 '2ª', 2009,
 '9781234500012','8887776665562',
 'Romance de mistério ambientado em Barcelona.',
 520,22,15,3.1,0.55,59.90,30,1),

('1984',
 'George Orwell',
 'Companhia das Letras',
 '6ª', 2010,
 '9781234500013','8887776665563',
 'Distopia política clássica.',
 336,21,14,2.0,0.48,39.90,50,1),

('O Morro dos Ventos Uivantes',
 'Emily Brontë',
 'Penguin',
 '4ª', 2016,
 '9781234500014','8887776665564',
 'Romance dramático clássico.',
 380,21,14,2.3,0.50,34.90,40,1),

('O Nome do Vento',
 'Patrick Rothfuss',
 'Arqueiro',
 '4ª', 2017,
 '9781234500015','8887776665565',
 'Fantasia épica de renome mundial.',
 750,23,16,4.2,0.90,69.90,25,1),

('Fundação',
 'Isaac Asimov',
 'Aleph',
 '3ª', 2015,
 '9781234500016','8887776665566',
 'Clássico absoluto de ficção científica.',
 356,21,14,2.1,0.45,49.90,35,1),

('O Exorcista',
 'William Peter Blatty',
 'HarperCollins',
 '2ª', 2018,
 '9781234500017','8887776665567',
 'Terror psicológico baseado em fatos reais.',
 420,22,15,3.1,0.55,44.90,28,1),

('Um de Nós Está Mentindo',
 'Karen McManus',
 'Galera',
 '1ª', 2020,
 '9781234500018','8887776665568',
 'Mistério juvenil com reviravoltas.',
 384,21,14,2.0,0.48,39.90,32,1),

('O Lar da Srta. Peregrine',
 'Ransom Riggs',
 'Leya',
 '1ª', 2013,
 '9781234500019','8887776665569',
 'Fantasia sombria com fotografia.',
 352,21,14,2.5,0.50,39.90,27,1),

('Cidades de Papel',
 'John Green',
 'Intrínseca',
 '2ª', 2015,
 '9781234500020','8887776665570',
 'Drama juvenil sobre autodescoberta.',
 368,21,14,2.3,0.52,34.90,22,1);

-- Médicos
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Embriologia Clínica' AND cat_category='Anatomia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Imunologia Médica Avançada' AND cat_category='Microbiologia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Radiologia para Estudantes' AND cat_category='Fisiologia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Traumatologia Essencial' AND cat_category='Cirurgia Geral';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Ecocardiografia Moderna' AND cat_category='Medicina Geral';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Neurologia em Casos Clínicos' AND cat_category='Patologia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Dermatologia Prática' AND cat_category='Medicina Geral';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Atlas de Farmacologia Clínica' AND cat_category='Farmacologia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Patologias Hepáticas' AND cat_category='Patologia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Pneumologia Clínica' AND cat_category='Medicina Geral';

-- Literatura
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='O Código Da Vinci' AND cat_category='Mistério';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='A Sombra do Vento' AND cat_category='Romance';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='1984' AND cat_category='Distopia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='O Morro dos Ventos Uivantes' AND cat_category='Drama';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='O Nome do Vento' AND cat_category='Fantasia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Fundação' AND cat_category='Ficção Científica';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='O Exorcista' AND cat_category='Terror';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Um de Nós Está Mentindo' AND cat_category='Mistério';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='O Lar da Srta. Peregrine' AND cat_category='Fantasia';
INSERT INTO books_categories SELECT bok_id, cat_id FROM books, categories WHERE bok_name='Cidades de Papel' AND cat_category='Drama';

-- =============================
-- NOVAS VENDAS
-- =============================

-- VENDA R1 – Renata – 2024-08-15
INSERT INTO sales (sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_add_id, sal_created_at)
VALUES (
 (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 12.90, '2024-08-20',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
 '2024-08-15'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price
FROM books WHERE bok_name='Embriologia Clínica';

INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT LAST_INSERT_ID(), car_id, 100
FROM cards
WHERE car_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
AND car_principal=1;


-- VENDA R2 – Renata – 2024-11-10
INSERT INTO sales (
  sal_id, sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id,
  sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
 NULL,(SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 9.90,'2024-11-15',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
 NULL,
 '2024-11-10'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price
FROM books WHERE bok_name='Neurologia em Casos Clínicos';

INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT LAST_INSERT_ID(), car_id, 100
FROM cards
WHERE car_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
AND car_principal=1;


-- VENDA M1 – Mahmoud – 2024-07-22
INSERT INTO sales (
  sal_id, sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id,
  sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
 NULL,(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
 14.90,'2024-07-28',
 (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
 (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')),
 NULL,
 '2024-07-22'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price
FROM books WHERE bok_name='Fundação';

INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT LAST_INSERT_ID(), car_id, 100
FROM cards
WHERE car_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')
AND car_principal=1;


-- VENDA R3 – Renata – 2025-03-02
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
    11.90, '2025-03-06',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
    NULL,
    '2025-03-02'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price
FROM books WHERE bok_name='Atlas de Farmacologia Clínica';



-- VENDA R4 – Renata – 2025-03-25 (2 itens)
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
    15.90, '2025-03-30',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
    NULL,
    '2025-03-25'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='O Código Da Vinci';

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='1984';


-- VENDA M2 – Mahmoud – 2025-04-12
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
    9.90, '2025-04-18',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')),
    NULL,
    '2025-04-12'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='O Exorcista';


-- VENDA R5 – Renata – 2025-05-05
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
    10.90, '2025-05-10',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
    NULL,
    '2025-05-05'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='Cidades de Papel';


-- VENDA R6 – Renata – 2025-06-20
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
    14.90, '2025-06-25',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
    NULL,
    '2025-06-20'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='O Morro dos Ventos Uivantes';


-- VENDA M3 – Mahmoud – 2025-06-29
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
    12.90, '2025-07-03',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')),
    NULL,
    '2025-06-29'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='Neuromancer';


-- VENDA R7 – Renata – 2025-07-18
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
    11.90, '2025-07-23',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
    NULL,
    '2025-07-18'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='Ecocardiografia Moderna';


-- VENDA M4 – Mahmoud – 2025-08-08
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
    12.90, '2025-08-14',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')),
    NULL,
    '2025-08-08'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='Pneumologia Clínica';


-- VENDA R8 – Renata – 2025-09-03
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
    10.90, '2025-09-08',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')),
    NULL,
    '2025-09-03'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='Radiologia para Estudantes';

-- VENDA M5 – Mahmoud – 2025-10-15
INSERT INTO sales (
    sal_usr_id, sal_freight, sal_delivery_date,
    sal_ssa_id, sal_add_id, sal_pco_id, sal_created_at
)
VALUES (
    (SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
    11.90, '2025-10-20',
    (SELECT ssa_id FROM status_sale WHERE ssa_status='DELIVERED'),
    (SELECT add_id FROM addresses WHERE add_usr_id=(SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com')),
    NULL,
    '2025-10-15'
);

INSERT INTO sales_books (sbo_sal_id, sbo_bok_id, sbo_quantity, sbo_price)
SELECT LAST_INSERT_ID(), bok_id, 1, bok_price FROM books WHERE bok_name='Um de Nós Está Mentindo';
