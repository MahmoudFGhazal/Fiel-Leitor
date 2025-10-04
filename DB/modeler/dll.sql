-- Gerado por Oracle SQL Developer Data Modeler 24.3.1.351.0831
--   em:        2025-10-03 21:19:26 BRT
--   site:      Oracle Database 21c
--   tipo:      Oracle Database 21c



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE Addresses 
    ( 
     add_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_number      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_complement  UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     add_rty_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_cit_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_str_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_sty_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Addresses 
    ADD CONSTRAINT Addresses_PK PRIMARY KEY ( add_id ) ;

CREATE TABLE Administrator 
    ( 
     adm_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     adm_email       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     adm_password    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     adm_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     adm_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     adm_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Administrator 
    ADD CONSTRAINT Administrator_PK PRIMARY KEY ( adm_id ) ;

CREATE TABLE Books 
    ( 
     bok_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     bok_active  UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     bok_stock   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     bok_cat_id  UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Books 
    ADD CONSTRAINT Books_PK PRIMARY KEY ( bok_id ) ;

CREATE TABLE Card_Banners 
    ( 
     cba_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cba_name        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cba_active      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     cba_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cba_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cba_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Card_Banners 
    ADD CONSTRAINT Card_Banners_PK PRIMARY KEY ( cba_id ) ;

CREATE TABLE Cards 
    ( 
     car_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_usr_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_principal   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_number      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_ccv         UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_holder      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_valid       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_pty_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     car_cba_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Cards 
    ADD CONSTRAINT Cards_PK PRIMARY KEY ( car_id ) ;

CREATE TABLE Carts 
    ( 
     cat_cli_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_bok_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_quantity    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_createAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Carts 
    ADD CONSTRAINT Carts_PK PRIMARY KEY ( cat_cli_id, cat_bok_id ) ;

CREATE TABLE Categories 
    ( 
     cat_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_name        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_active      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     cat_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cat_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Categories 
    ADD CONSTRAINT Categories_PK PRIMARY KEY ( cat_id ) ;

CREATE TABLE Genders 
    ( 
     gen_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     gen_name        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     gen_active      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     gen_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     gen_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     gen_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Genders 
    ADD CONSTRAINT Genders_PK PRIMARY KEY ( gen_id ) ;

CREATE TABLE Payment_Types 
    ( 
     pty_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pty_payment_type UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pty_active       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     pty_createdAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pty_updatedAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pty_publishedAt  UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Payment_Types 
    ADD CONSTRAINT Payment_Types_PK PRIMARY KEY ( pty_id ) ;

CREATE TABLE Promotional_Coupons 
    ( 
     pco_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pco_value       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pco_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pco_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     pco_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Promotional_Coupons 
    ADD CONSTRAINT Promotional_Coupons_PK PRIMARY KEY ( pco_id ) ;

CREATE TABLE Residence_Types 
    ( 
     rty_id             UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     rty_residence_type UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     rty_active         UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     rty_createdAt      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     rty_updatedAt      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     rty_publishedAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Residence_Types 
    ADD CONSTRAINT residence_types_PK PRIMARY KEY ( rty_id ) ;

CREATE TABLE Sales 
    ( 
     sal_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_cli_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_freight      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_deliveryDate UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     sal_ssa_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_cou_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_pco_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_add_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_createdAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_updatedAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_publishedAt  UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Sales 
    ADD CONSTRAINT Sales_PK PRIMARY KEY ( sal_id ) ;

CREATE TABLE Sales_Books 
    ( 
     sbo_sal_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_bok_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_quantity    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_price       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Sales_Books 
    ADD CONSTRAINT Sales_Books_PK PRIMARY KEY ( sbo_bok_id, sbo_sal_id ) ;

CREATE TABLE Sales_Cards 
    ( 
     sca_sal_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sca_car_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sca_percent     UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sca_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sca_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sca_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

CREATE TABLE Status_Sale 
    ( 
     ssa_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ssa_name        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ssa_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ssa_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ssa_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Status_Sale 
    ADD CONSTRAINT Status_Sale_PK PRIMARY KEY ( ssa_id ) ;

CREATE TABLE Street_Types 
    ( 
     sty_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sty_street_type UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sty_active      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     sty_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sty_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sty_publishedAT UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Street_Types 
    ADD CONSTRAINT Street_Types_PK PRIMARY KEY ( sty_id ) ;

CREATE TABLE Trade_Coupons 
    ( 
     tco_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_sal_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     tco_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE Trade_Coupons 
    ADD CONSTRAINT Coupons_PK PRIMARY KEY ( tco_id ) ;

CREATE TABLE "User" 
    ( 
     usr_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_name         UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_password     UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_active       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_gen_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_birthday     UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_cpf          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_phone_number UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_createdAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_updatedAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     usr_publishedAt  UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE "User" 
    ADD CONSTRAINT Clients_PK PRIMARY KEY ( usr_id ) ;

CREATE TABLE User_Address 
    ( 
     uad_usr_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     uad_add_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     uad_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     uad_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     uad_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
    LOGGING 
;

ALTER TABLE User_Address 
    ADD CONSTRAINT User_Address_PK PRIMARY KEY ( uad_usr_id ) ;

ALTER TABLE Addresses 
    ADD CONSTRAINT add_rty_FK FOREIGN KEY 
    ( 
     add_rty_id
    ) 
    REFERENCES Residence_Types 
    ( 
     rty_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Addresses 
    ADD CONSTRAINT add_sty_FK FOREIGN KEY 
    ( 
     add_sty_id
    ) 
    REFERENCES Street_Types 
    ( 
     sty_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Books 
    ADD CONSTRAINT bok_cat_FK FOREIGN KEY 
    ( 
     bok_cat_id
    ) 
    REFERENCES Categories 
    ( 
     cat_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Cards 
    ADD CONSTRAINT car_cba_FK FOREIGN KEY 
    ( 
     car_cba_id
    ) 
    REFERENCES Card_Banners 
    ( 
     cba_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Cards 
    ADD CONSTRAINT car_pty_FK FOREIGN KEY 
    ( 
     car_pty_id
    ) 
    REFERENCES Payment_Types 
    ( 
     pty_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Carts 
    ADD CONSTRAINT cat_bok_FK FOREIGN KEY 
    ( 
     cat_bok_id
    ) 
    REFERENCES Books 
    ( 
     bok_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Carts 
    ADD CONSTRAINT cat_usr_FK FOREIGN KEY 
    ( 
     cat_cli_id
    ) 
    REFERENCES "User" 
    ( 
     usr_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Cards 
    ADD CONSTRAINT cli_usr_FK FOREIGN KEY 
    ( 
     car_usr_id
    ) 
    REFERENCES "User" 
    ( 
     usr_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Trade_Coupons 
    ADD CONSTRAINT cou_sal_FK FOREIGN KEY 
    ( 
     tco_sal_id
    ) 
    REFERENCES Sales 
    ( 
     sal_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales 
    ADD CONSTRAINT sal_add_FK FOREIGN KEY 
    ( 
     sal_add_id
    ) 
    REFERENCES Addresses 
    ( 
     add_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales 
    ADD CONSTRAINT sal_cou_FK FOREIGN KEY 
    ( 
     sal_cou_id
    ) 
    REFERENCES Trade_Coupons 
    ( 
     tco_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales 
    ADD CONSTRAINT sal_pco_FK FOREIGN KEY 
    ( 
     sal_pco_id
    ) 
    REFERENCES Promotional_Coupons 
    ( 
     pco_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales 
    ADD CONSTRAINT sal_ssa_FK FOREIGN KEY 
    ( 
     sal_ssa_id
    ) 
    REFERENCES Status_Sale 
    ( 
     ssa_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales 
    ADD CONSTRAINT sal_usr_FK FOREIGN KEY 
    ( 
     sal_cli_id
    ) 
    REFERENCES "User" 
    ( 
     usr_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales_Books 
    ADD CONSTRAINT sbo_bok_FK FOREIGN KEY 
    ( 
     sbo_bok_id
    ) 
    REFERENCES Books 
    ( 
     bok_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales_Books 
    ADD CONSTRAINT sbo_sal_FK FOREIGN KEY 
    ( 
     sbo_sal_id
    ) 
    REFERENCES Sales 
    ( 
     sal_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales_Cards 
    ADD CONSTRAINT sca_car_FK FOREIGN KEY 
    ( 
     sca_car_id
    ) 
    REFERENCES Cards 
    ( 
     car_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE Sales_Cards 
    ADD CONSTRAINT sca_sal_FK FOREIGN KEY 
    ( 
     sca_sal_id
    ) 
    REFERENCES Sales 
    ( 
     sal_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE User_Address 
    ADD CONSTRAINT uad_add_FK FOREIGN KEY 
    ( 
     uad_add_id
    ) 
    REFERENCES Addresses 
    ( 
     add_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE User_Address 
    ADD CONSTRAINT udd_usr_FK FOREIGN KEY 
    ( 
     uad_usr_id
    ) 
    REFERENCES "User" 
    ( 
     usr_id
    ) 
    NOT DEFERRABLE 
;

ALTER TABLE "User" 
    ADD CONSTRAINT usr_gen_FK FOREIGN KEY 
    ( 
     usr_gen_id
    ) 
    REFERENCES Genders 
    ( 
     gen_id
    ) 
    NOT DEFERRABLE 
;



-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            19
-- CREATE INDEX                             0
-- ALTER TABLE                             39
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
-- ERRORS                                 132
-- WARNINGS                                 0
