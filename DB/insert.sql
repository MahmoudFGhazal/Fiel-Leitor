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

INSERT INTO books (bok_name, bok_price, bok_active, bok_stock, bok_cat_id) VALUES
  ('Introdução à Medicina', 89.90, 1, 15, 1),
  ('Farmacologia Básica', 129.50, 1, 10, 2),
  ('Anatomia Humana Completa', 299.00, 1, 5, 3),
  ('Fisiologia Moderna', 159.90, 1, 8, 4),
  ('Patologia Geral', 189.00, 1, 12, 5),
  ('Microbiologia Clínica', 139.90, 1, 7, 6),
  ('Pediatria Essencial', 179.90, 1, 9, 7),
  ('Ginecologia e Obstetrícia', 199.90, 1, 6, 8),
  ('Cirurgia Geral: Princípios e Prática', 249.00, 1, 4, 9),
  ('Psiquiatria Clínica', 169.00, 1, 10, 10);

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
('renata@example.com', '$2a$10$hashFake1', 'Renata Giannini', 1,
   (SELECT gen_id FROM genders WHERE gen_gender='Female' LIMIT 1),
   '2000-05-10', '123.456.789-00', '+55 11 90000-0001'
),
('mahmoud@example.com', '$2a$10$hashFake2', 'Mahmoud A.', 1,
   (SELECT gen_id FROM genders WHERE gen_gender='Male' LIMIT 1),
   '1998-03-22', '987.654.321-00', '+55 11 90000-0002'
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
((SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 'Casa', 1, 1, 123, 'Ap 51', 'Av. Paulista', 'Bela Vista', '01310-100',
 'São Paulo', 'SP', 'Brasil',
 (SELECT sty_id FROM street_types WHERE sty_street_type='Avenue' LIMIT 1),
 (SELECT rty_id FROM residence_types WHERE rty_residence_type='Apartment' LIMIT 1)
),
((SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
 'Trabalho', 1, 1, 450, NULL, 'Rua Vergueiro', 'Vila Mariana', '04101-300',
 'São Paulo', 'SP', 'Brasil',
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
((SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 1, '411111', '1111', 'RENATA L G', '12', '2028'
),
((SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
 0, '555555', '2222', 'RENATA L G', '06', '2027'
),
((SELECT usr_id FROM users WHERE usr_email='mahmoud@example.com'),
 1, '401288', '4242', 'MAHMOUD A', '09', '2029'
);

-- =============================
-- CARTS (carrinho) - usa prefixos cat_* (modelo 1)
-- =============================
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
("abvffdf", 20.00, 0),
("abvfdsagrsfff", 35.00, 0),
("abvffhgfhfg", 50.00, 0);

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
INSERT INTO sales_cards (sca_sal_id, sca_car_id, sca_percent)
SELECT s.sal_id, c.car_id, 70.00
  FROM sales s
  JOIN cards c ON c.car_usr_id = s.sal_usr_id AND c.car_principal=1
 WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
 ORDER BY s.sal_id DESC
 LIMIT 1;

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
INSERT INTO trader_coupons (tco_code, tco_value, tco_used, tco_origin_sal_id, tco_applied_sal_id)
  SELECT 
      CONCAT('TCO', LPAD(s.sal_id, 8, '0')) AS tco_code,  -- gere um código
      50.00                                  AS tco_value,
      0                                      AS tco_used,
      s.sal_id                                AS tco_origin_sal_id,
      NULL                                    AS tco_applied_sal_id
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
    WHERE s2.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
    ORDER BY s2.sal_id DESC
    LIMIT 1), -- última venda (SALE #2)
  (SELECT t.tco_id
     FROM trader_coupons t
    WHERE t.tco_used=0
      AND t.tco_origin_sal_id = (
          SELECT s1.sal_id
            FROM sales s1
           WHERE s1.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
           ORDER BY s1.sal_id DESC
           LIMIT 1 OFFSET 1  -- penúltima venda (SALE #1)
      )
    ORDER BY t.tco_id DESC
    LIMIT 1
  );

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
UPDATE trader_coupons t
   SET t.tco_used = 1
 WHERE t.tco_id = (
   SELECT sat_tco_id
     FROM sales_trader_coupons
    WHERE sat_sal_id = (
      SELECT s2.sal_id
        FROM sales s2
       WHERE s2.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
       ORDER BY s2.sal_id DESC
       LIMIT 1
    )
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
   SELECT sal_pco_id FROM sales s
    WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
    ORDER BY s.sal_id DESC
    LIMIT 1
 );

 INSERT INTO sales (
  sal_usr_id, sal_freight, sal_delivery_date, sal_ssa_id, sal_pco_id, sal_add_id
)
VALUES (
  (SELECT usr_id FROM users WHERE usr_email='renata@example.com'),
  15.90,
  DATE_ADD(CURDATE(), INTERVAL -10 DAY),
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
   SELECT sal_pco_id FROM sales s
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
  (SELECT s2.sal_id
     FROM sales s2
    WHERE s2.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
    ORDER BY s2.sal_id DESC
    LIMIT 1), -- última venda (SALE #6)
  (SELECT t.tco_id
     FROM trader_coupons t
    WHERE t.tco_used=0
      AND t.tco_usr_id IS NULL
    ORDER BY t.tco_id ASC
    LIMIT 1
  );

UPDATE trader_coupons t
   SET t.tco_used = 1, t.tco_applied_sal_id = (
     SELECT s.sal_id FROM sales s
      WHERE s.sal_usr_id=(SELECT usr_id FROM users WHERE usr_email='renata@example.com')
      ORDER BY s.sal_id DESC LIMIT 1
   )
 WHERE t.tco_id = (
   SELECT t2.tco_id FROM trader_coupons t2
    WHERE t2.tco_used=0
    ORDER BY t2.tco_id ASC LIMIT 1
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
INSERT INTO trader_coupons (tco_code, tco_value, tco_used, tco_origin_sal_id, tco_usr_id)
  SELECT CONCAT('TCO', LPAD(s.sal_id, 8, '0')),
         80.00,
         0,
         s.sal_id,
         s.sal_usr_id
    FROM sales s
   WHERE s.sal_usr_id = (SELECT usr_id FROM users WHERE usr_email='renata@example.com')
   ORDER BY s.sal_id DESC
   LIMIT 1;

