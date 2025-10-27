-- Criação do banco
CREATE DATABASE Fiel
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE Fiel;

-- =============================
-- Tabelas de apoio
-- =============================
CREATE TABLE genders (
    gen_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gen_gender VARCHAR(50) NOT NULL,
    gen_active TINYINT(1) DEFAULT 1,
    gen_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    gen_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    gen_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE residence_types (
    rty_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rty_residence_type VARCHAR(100) NOT NULL,
    rty_active TINYINT(1) DEFAULT 1,
    rty_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rty_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    rty_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE street_types (
    sty_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sty_street_type VARCHAR(100) NOT NULL,
    sty_active TINYINT(1) DEFAULT 1,
    sty_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sty_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    sty_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    cat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cat_category VARCHAR(255) NOT NULL,
    cat_active TINYINT(1) DEFAULT 1,
    cat_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cat_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    cat_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =============================
-- Tabelas de apoio
-- =============================
CREATE TABLE users (
    usr_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usr_email VARCHAR(255) NOT NULL UNIQUE,
    usr_password VARCHAR(255) NOT NULL,
    usr_name VARCHAR(255) NOT NULL,
    usr_active TINYINT(1) DEFAULT 1,
    usr_gen_id BIGINT NOT NULL,
    usr_birthday DATE NOT NULL,
    usr_cpf VARCHAR(20) NOT NULL,
    usr_phone_number VARCHAR(20) NOT NULL,
    usr_is_delete TINYINT(1) DEFAULT 0,
    usr_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usr_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usr_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_genders FOREIGN KEY (usr_gen_id) REFERENCES genders (gen_id)
);

-- =============================
-- Tabelas de endereço
-- =============================
CREATE TABLE addresses (
    add_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    add_usr_id BIGINT NOT NULL,
    add_nickname VARCHAR(255) NOT NULL,
    add_principal TINYINT(1) DEFAULT 1,
    add_active TINYINT(1) DEFAULT 1,
    add_number VARCHAR(10) NOT NULL,
    add_complement VARCHAR(255),
    add_street VARCHAR(255) NOT NULL,
    add_neighborhood VARCHAR(255) NOT NULL,
    add_zip VARCHAR(20) NOT NULL,
    add_city VARCHAR(255) NOT NULL,
    add_state VARCHAR(255) NOT NULL,
    add_country VARCHAR(255) NOT NULL,
    add_sty_id BIGINT NOT NULL,
    add_rty_id BIGINT NOT NULL,
    add_is_delete TINYINT(1) DEFAULT 0,
    add_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    add_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    add_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_addresses_users FOREIGN KEY (add_usr_id) REFERENCES users (usr_id) ON DELETE CASCADE,
    CONSTRAINT fk_addresses_street_types FOREIGN KEY (add_sty_id) REFERENCES street_types (sty_id),
    CONSTRAINT fk_addresses_residence_types FOREIGN KEY (add_rty_id) REFERENCES residence_types (rty_id)
);

-- =============================
-- Catálogos e livros
-- =============================
CREATE TABLE books (
    bok_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bok_name VARCHAR(100) NOT NULL,
    bok_price DECIMAL(10, 2) NOT NULL,
    bok_active TINYINT(1) DEFAULT 1,
    bok_stock INT DEFAULT 0,
    bok_cat_id BIGINT NOT NULL,
    bok_is_delete TINYINT(1) DEFAULT 0,
    bok_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    bok_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    bok_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_books_categories FOREIGN KEY (bok_cat_id) REFERENCES categories (cat_id)
);

-- =============================
-- Pagamentos
-- =============================
CREATE TABLE cards (
    car_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_usr_id BIGINT NOT NULL,
    car_principal TINYINT(1) NOT NULL,
    car_bin VARCHAR(6) NOT NULL,
    car_last4 VARCHAR(5) NOT NULL,
    car_holder VARCHAR(255) NOT NULL,
    car_exp_month VARCHAR(8) NOT NULL,
    car_exp_year VARCHAR(8) NOT NULL,
    car_brand VARCHAR(20),
    car_is_delete TINYINT(1) DEFAULT 0,
    car_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    car_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    car_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cards_users FOREIGN KEY (car_usr_id) REFERENCES users (usr_id) ON DELETE CASCADE
);


-- =============================
-- Cupons
-- =============================
CREATE TABLE promotional_coupons (
    pco_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pco_code VARCHAR(20) NOT NULL,
    pco_value DECIMAL(10,2) NOT NULL,
    pco_used TINYINT(1) DEFAULT 0,
    pco_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pco_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    pco_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE trader_coupons (
    tco_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tco_code VARCHAR(20) NOT NULL,
    tco_value DECIMAL(10,2) NOT NULL,
    tco_used TINYINT(1) DEFAULT 0,
    tco_origin_sal_id BIGINT NOT NULL,
    tco_applied_sal_id BIGINT NULL,
    tco_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tco_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    tco_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

-- =============================
-- Carrinho e vendas
-- =============================
CREATE TABLE carts (
    crt_usr_id BIGINT NOT NULL,
    crt_bok_id BIGINT NOT NULL,
    crt_quantity INT NOT NULL,
    crt_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    crt_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    crt_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (crt_usr_id, crt_bok_id),
    CONSTRAINT fk_carts_users FOREIGN KEY (crt_usr_id) REFERENCES users (usr_id),
    CONSTRAINT fk_carts_books FOREIGN KEY (crt_bok_id) REFERENCES books (bok_id)
);

CREATE TABLE status_sale (
    ssa_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ssa_status VARCHAR(100) NOT NULL,
    ssa_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ssa_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ssa_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales (
    sal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sal_usr_id BIGINT NOT NULL,
    sal_freight DECIMAL(10,2),
    sal_delivery_date DATE,
    sal_ssa_id BIGINT NOT NULL,
    sal_add_id BIGINT,
    sal_pco_id BIGINT,
    sal_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sal_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    sal_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sales_users FOREIGN KEY (sal_usr_id) REFERENCES users (usr_id),
    CONSTRAINT fk_sales_addresses FOREIGN KEY (sal_add_id) REFERENCES addresses (add_id),
    CONSTRAINT fk_sales_status FOREIGN KEY (sal_ssa_id) REFERENCES status_sale (ssa_id)
);

CREATE TABLE sales_books (
    sbo_sal_id BIGINT NOT NULL,
    sbo_bok_id BIGINT NOT NULL,
    sbo_quantity INT NOT NULL,
    sbo_price DECIMAL(10,2) NOT NULL,
    sbo_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sbo_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    sbo_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (sbo_bok_id, sbo_sal_id),
    CONSTRAINT fk_sales_books_sales FOREIGN KEY (sbo_sal_id) REFERENCES sales (sal_id),
    CONSTRAINT fk_sales_books_books FOREIGN KEY (sbo_bok_id) REFERENCES books (bok_id)
);

CREATE TABLE sales_cards (
    sca_sal_id BIGINT NOT NULL,
    sca_car_id BIGINT NOT NULL,
    sca_percent DECIMAL(5,2) NOT NULL,
    sca_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sca_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    sca_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sales_cards_sales FOREIGN KEY (sca_sal_id) REFERENCES sales (sal_id),
    CONSTRAINT fk_sales_cards_cards FOREIGN KEY (sca_car_id) REFERENCES cards (car_id)
);

CREATE TABLE sales_trader_coupons (
  sat_sal_id BIGINT NOT NULL,
  sat_tco_id BIGINT NOT NULL,
  PRIMARY KEY (sat_sal_id, sat_tco_id),
  CONSTRAINT sat_sal_FK FOREIGN KEY (sat_sal_id) REFERENCES sales (sal_id),
  CONSTRAINT sat_pco_FK FOREIGN KEY (sat_tco_id) REFERENCES trader_coupons (tco_id)
);

ALTER TABLE trader_coupons
    ADD CONSTRAINT uq_trader_coupons_origin UNIQUE (tco_origin_sal_id),
    ADD CONSTRAINT fk_trader_coupons_origin  FOREIGN KEY (tco_origin_sal_id)  REFERENCES sales (sal_id),
    ADD CONSTRAINT fk_trader_coupons_applied FOREIGN KEY (tco_applied_sal_id) REFERENCES sales (sal_id);