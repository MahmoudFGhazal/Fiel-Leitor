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

INSERT INTO categories (cat_name, cat_active) VALUES
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

INSERT INTO books 
(bok_name, bok_price, bok_active, bok_stock, bok_cat_id, published_at) 
VALUES
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
