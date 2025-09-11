-- Criação do banco
CREATE DATABASE Fiel
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE Fiel;

-- Tabelas de apoio (sem dependências externas)
CREATE TABLE genders (
    gen_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gen_gender VARCHAR(50) NOT NULL,
    gen_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    gen_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    gen_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment_types (
    pty_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pty_payment_type VARCHAR(100) NOT NULL,
    pty_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pty_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    pty_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE residence_types (
    rty_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rty_residence_type VARCHAR(100) NOT NULL,
    rty_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rty_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    rty_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE street_types (
    sty_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sty_street_type VARCHAR(100) NOT NULL,
    sty_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sty_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    sty_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de usuários (usa genders)
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
    usr_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usr_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usr_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_genders FOREIGN KEY (usr_gen_id) REFERENCES genders (gen_id)
);

-- Tabela de endereços (usa users, street_types, residence_types)
CREATE TABLE addresses (
    add_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    add_usr_id BIGINT NOT NULL,
    add_nickname VARCHAR(255) NOT NULL,
    add_number INT NOT NULL,
    add_complement VARCHAR(255),
    add_street VARCHAR(255) NOT NULL,
    add_neighborhood VARCHAR(255) NOT NULL,
    add_zip VARCHAR(20) NOT NULL,
    add_city VARCHAR(255) NOT NULL,
    add_state VARCHAR(255) NOT NULL,
    add_country VARCHAR(255) NOT NULL,
    add_sty_id BIGINT NOT NULL,
    add_rty_id BIGINT NOT NULL,
    add_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    add_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    add_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_addresses_users FOREIGN KEY (add_usr_id) REFERENCES users (usr_id) ON DELETE CASCADE,
    CONSTRAINT fk_addresses_street_types FOREIGN KEY (add_sty_id) REFERENCES street_types (sty_id),
    CONSTRAINT fk_addresses_residence_types FOREIGN KEY (add_rty_id) REFERENCES residence_types (rty_id)
);

-- Tabela de cartões (usa users, payment_types)
CREATE TABLE cards (
    car_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_usr_id BIGINT NOT NULL,
    car_principal TINYINT(1) NOT NULL,
    car_number VARCHAR(20) NOT NULL,
    car_ccv VARCHAR(10) NOT NULL,
    car_holder VARCHAR(255) NOT NULL,
    car_valid VARCHAR(8) NOT NULL,
    car_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    car_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    car_published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    car_pty_id BIGINT NOT NULL,
    CONSTRAINT fk_cards_payment_types FOREIGN KEY (car_pty_id) REFERENCES payment_types (pty_id),
    CONSTRAINT fk_cards_users FOREIGN KEY (car_usr_id) REFERENCES users (usr_id) ON DELETE CASCADE
);
