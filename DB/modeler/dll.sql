-- Gerado por Oracle SQL Developer Data Modeler 24.3.1.351.0831
--   em:        2025-10-06 08:57:29 BRT
--   site:      Oracle Database 21c
--   tipo:      Oracle Database 21c



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE addresses 
    ( 
     add_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_usr_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_principal    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     add_active       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     add_nickname     VARCHAR2 (255)  NOT NULL , 
     add_number       INTEGER  NOT NULL , 
     add_complement   VARCHAR2 (255) , 
     add_street       VARCHAR2 (255)  NOT NULL , 
     add_neighborhood VARCHAR2 (255)  NOT NULL , 
     add_zip          VARCHAR2 (20)  NOT NULL , 
     add_city         VARCHAR2 (255)  NOT NULL , 
     add_state        VARCHAR2 (255)  NOT NULL , 
     add_country      VARCHAR2 (255)  NOT NULL , 
     add_sty_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_rty_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     add_updated_at   TIMESTAMP , 
     add_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE addresses 
    ADD CONSTRAINT addresses_PK PRIMARY KEY ( add_id ) ;

CREATE TABLE books 
    ( 
     bok_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     bok_active   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    DEFAULT 1 , 
     bok_stock    INTEGER  NOT NULL , 
     bok_cat_id   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     updated_at   TIMESTAMP , 
     published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE books 
    ADD CONSTRAINT books_PK PRIMARY KEY ( bok_id ) ;

CREATE TABLE cards 
    ( 
     car_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_usr_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_principal    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_bin          VARCHAR2 (20)  NOT NULL , 
     car_last4        VARCHAR2 (10)  NOT NULL , 
     car_holder       VARCHAR2 (255)  NOT NULL , 
     car_exp_month    VARCHAR2 (8)  NOT NULL , 
     car_exp_year     UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     car_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     car_updated_at   TIMESTAMP , 
     car_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE cards 
    ADD CONSTRAINT cards_PK PRIMARY KEY ( car_id ) ;

CREATE TABLE carts 
    ( 
     cat_cli_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_bok_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_quantity     INTEGER  NOT NULL , 
     cat_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     cat_updated_at   TIMESTAMP , 
     cat_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE carts 
    ADD CONSTRAINT carts_PK PRIMARY KEY ( cat_cli_id, cat_bok_id ) ;

CREATE TABLE categories 
    ( 
     cat_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_name         VARCHAR2 (255)  NOT NULL , 
     cat_active       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    DEFAULT 1 , 
     cat_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     cat_updated_at   TIMESTAMP , 
     cat_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE categories 
    ADD CONSTRAINT categories_PK PRIMARY KEY ( cat_id ) ;

CREATE TABLE genders 
    ( 
     gen_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     gen_gender       VARCHAR2 (50)  NOT NULL , 
     gen_active       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    DEFAULT 1 , 
     gen_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     gen_updated_at   TIMESTAMP , 
     gen_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE genders 
    ADD CONSTRAINT genders_PK PRIMARY KEY ( gen_id ) ;

CREATE TABLE promotional_coupons 
    ( 
     pco_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pco_value        NUMBER (10,2)  NOT NULL , 
     pco_used         UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     pco_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     pco_updated_at   TIMESTAMP , 
     pco_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE promotional_coupons 
    ADD CONSTRAINT promotional_coupons_PK PRIMARY KEY ( pco_id ) ;

CREATE TABLE residence_types 
    ( 
     rty_id             UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     rty_residence_type VARCHAR2 (100)  NOT NULL , 
     rty_active         UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    DEFAULT 1 , 
     rty_created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     rty_updated_at     TIMESTAMP , 
     rty_published_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE residence_types 
    ADD CONSTRAINT residence_types_PK PRIMARY KEY ( rty_id ) ;

CREATE TABLE sales 
    ( 
     sal_id            UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_cli_id        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_freight       NUMBER (10,2)  NOT NULL , 
     sal_delivery_date DATE , 
     sal_ssa_id        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_cou_id        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     sal_pco_id        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     sal_add_id        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     sal_updated_at    TIMESTAMP , 
     sal_published_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE sales 
    ADD CONSTRAINT sales_PK PRIMARY KEY ( sal_id ) ;

CREATE TABLE sales_books 
    ( 
     sbo_sal_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_bok_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_quantity     INTEGER  NOT NULL , 
     sbo_price        NUMBER (10,2)  NOT NULL , 
     sbo_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     sbo_updated_at   TIMESTAMP , 
     sbo_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE sales_books 
    ADD CONSTRAINT sales_books_PK PRIMARY KEY ( sbo_bok_id, sbo_sal_id ) ;

CREATE TABLE sales_cards 
    ( 
     sca_sal_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sca_car_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sca_percent      NUMBER (5,2)  NOT NULL , 
     sca_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     sca_updated_at   TIMESTAMP , 
     sca_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE sales_cards 
    ADD CONSTRAINT sales_cards_PK PRIMARY KEY ( sca_sal_id, sca_car_id ) ;

CREATE TABLE status_sale 
    ( 
     ssa_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ssa_name         VARCHAR2 (100)  NOT NULL , 
     ssa_value        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ssa_used         UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     ssa_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     ssa_updated_at   TIMESTAMP , 
     ssa_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE status_sale 
    ADD CONSTRAINT status_sale_PK PRIMARY KEY ( ssa_id ) ;

CREATE TABLE street_types 
    ( 
     sty_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sty_street_type  VARCHAR2 (100)  NOT NULL , 
     sty_active       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    DEFAULT 1 , 
     sty_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     sty_updated_at   TIMESTAMP , 
     sty_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE street_types 
    ADD CONSTRAINT street_types_PK PRIMARY KEY ( sty_id ) ;

CREATE TABLE trade_coupons 
    ( 
     tco_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_sal_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_value        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_used         UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     tco_updated_at   TIMESTAMP , 
     tco_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE trade_coupons 
    ADD CONSTRAINT trade_coupons_PK PRIMARY KEY ( tco_id ) ;

CREATE TABLE users 
    ( 
     usr_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_email        VARCHAR2 (255)  NOT NULL , 
     usr_password     VARCHAR2 (255)  NOT NULL , 
     usr_name         VARCHAR2 (255)  NOT NULL , 
     usr_active       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    DEFAULT 1 , 
     usr_gen_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_birthday     DATE  NOT NULL , 
     usr_cpf          VARCHAR2 (20)  NOT NULL , 
     usr_phone_number VARCHAR2 (20)  NOT NULL , 
     usr_created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP , 
     usr_updated_at   TIMESTAMP , 
     usr_published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
    ) 
    LOGGING 
;

ALTER TABLE users 
    ADD CONSTRAINT users_PK PRIMARY KEY ( usr_id ) ;

ALTER TABLE users 
    ADD CONSTRAINT INDEX_1 UNIQUE ( usr_email ) ;

ALTER TABLE addresses 
    ADD CONSTRAINT fk_addresses_residence_types FOREIGN KEY 
    ( 
     add_rty_id
    ) 
    REFERENCES residence_types 
    ( 
     rty_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE addresses 
    ADD CONSTRAINT fk_addresses_street_types FOREIGN KEY 
    ( 
     add_sty_id
    ) 
    REFERENCES street_types 
    ( 
     sty_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE addresses 
    ADD CONSTRAINT fk_addresses_users FOREIGN KEY 
    ( 
     add_usr_id
    ) 
    REFERENCES users 
    ( 
     usr_id
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;

ALTER TABLE books 
    ADD CONSTRAINT fk_books_categories FOREIGN KEY 
    ( 
     bok_cat_id
    ) 
    REFERENCES categories 
    ( 
     cat_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE cards 
    ADD CONSTRAINT fk_cards_users FOREIGN KEY 
    ( 
     car_usr_id
    ) 
    REFERENCES users 
    ( 
     usr_id
    ) 
    ON DELETE CASCADE 
    NOT DEFERRABLE 
;

ALTER TABLE carts 
    ADD CONSTRAINT fk_carts_books FOREIGN KEY 
    ( 
     cat_bok_id
    ) 
    REFERENCES books 
    ( 
     bok_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE carts 
    ADD CONSTRAINT fk_carts_users FOREIGN KEY 
    ( 
     cat_cli_id
    ) 
    REFERENCES users 
    ( 
     usr_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales_books 
    ADD CONSTRAINT fk_sales_books_books FOREIGN KEY 
    ( 
     sbo_bok_id
    ) 
    REFERENCES books 
    ( 
     bok_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales_books 
    ADD CONSTRAINT fk_sales_books_sales FOREIGN KEY 
    ( 
     sbo_sal_id
    ) 
    REFERENCES sales 
    ( 
     sal_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales_cards 
    ADD CONSTRAINT fk_sales_cards_cards FOREIGN KEY 
    ( 
     sca_car_id
    ) 
    REFERENCES cards 
    ( 
     car_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales_cards 
    ADD CONSTRAINT fk_sales_cards_sales FOREIGN KEY 
    ( 
     sca_sal_id
    ) 
    REFERENCES sales 
    ( 
     sal_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales 
    ADD CONSTRAINT fk_sales_promotional_coupon FOREIGN KEY 
    ( 
     sal_pco_id
    ) 
    REFERENCES promotional_coupons 
    ( 
     pco_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales 
    ADD CONSTRAINT fk_sales_status FOREIGN KEY 
    ( 
     sal_ssa_id
    ) 
    REFERENCES status_sale 
    ( 
     ssa_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales 
    ADD CONSTRAINT fk_sales_trade_coupon FOREIGN KEY 
    ( 
     sal_cou_id
    ) 
    REFERENCES trade_coupons 
    ( 
     tco_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales 
    ADD CONSTRAINT fk_sales_users FOREIGN KEY 
    ( 
     sal_cli_id
    ) 
    REFERENCES users 
    ( 
     usr_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE trade_coupons 
    ADD CONSTRAINT fk_trade_coupons_sales FOREIGN KEY 
    ( 
     tco_sal_id
    ) 
    REFERENCES sales 
    ( 
     sal_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE users 
    ADD CONSTRAINT fk_users_genders FOREIGN KEY 
    ( 
     usr_gen_id
    ) 
    REFERENCES genders 
    ( 
     gen_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE sales 
    ADD CONSTRAINT sal_add_FK FOREIGN KEY 
    ( 
     sal_add_id
    ) 
    REFERENCES addresses 
    ( 
     add_id
    ) 
    NOT DEFERRABLE 
;



-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            15
-- CREATE INDEX                             0
-- ALTER TABLE                             34
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                  45
-- WARNINGS                                 0
