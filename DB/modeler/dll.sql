-- Gerado por Oracle SQL Developer Data Modeler 24.3.1.351.0831
--   em:        2025-09-06 15:59:04 BRT
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE Address_Name 
    ( 
     ana_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ana_name        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ana_add_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ana_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ana_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     ana_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE Address_Name 
    ADD CONSTRAINT Address_Name_PK PRIMARY KEY ( ana_id ) ;

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
     add_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_rty_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_cit_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     add_str_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
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
;

ALTER TABLE Books 
    ADD CONSTRAINT Books_PK PRIMARY KEY ( bok_id ) ;

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
                     NOT NULL 
    ) 
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
;

ALTER TABLE Carts 
    ADD CONSTRAINT Carts_PK PRIMARY KEY ( cat_cli_id, cat_bok_id ) ;

CREATE TABLE Cities 
    ( 
     cit_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cit_city        UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cit_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cit_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cit_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cit_sta_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE Cities 
    ADD CONSTRAINT Cities_PK PRIMARY KEY ( cit_id ) ;

CREATE TABLE Countries 
    ( 
     cou_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cou_country     UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     cou_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     cou_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                    , 
     cou_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE Countries 
    ADD CONSTRAINT Countries_PK PRIMARY KEY ( cou_id ) ;

CREATE TABLE Genders 
    ( 
     gen_id   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     gen_name UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
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
;

ALTER TABLE Payment_Types 
    ADD CONSTRAINT Payment_Types_PK PRIMARY KEY ( pty_id ) ;

CREATE TABLE Residence_Types 
    ( 
     rty_id             UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     rty_residence_type UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
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
;

ALTER TABLE Residence_Types 
    ADD CONSTRAINT residence_types_PK PRIMARY KEY ( rty_id ) ;

CREATE TABLE Sales 
    ( 
     sal_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_cli_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_ana_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_car_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sal_ssa_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE Sales 
    ADD CONSTRAINT Sales_PK PRIMARY KEY ( sal_id ) ;

CREATE TABLE Sales_Books 
    ( 
     sbo_sal_id   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_bok_id   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sbo_quantity UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE Sales_Books 
    ADD CONSTRAINT Sales_Books_PK PRIMARY KEY ( sbo_bok_id, sbo_sal_id ) ;

CREATE TABLE States 
    ( 
     sta_id          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sta_state       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sta_uf          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sta_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sta_updateAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sta_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     sta_cou_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE States 
    ADD CONSTRAINT States_PK PRIMARY KEY ( sta_id ) ;

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
;

ALTER TABLE Street_Types 
    ADD CONSTRAINT Street_Types_PK PRIMARY KEY ( sty_id ) ;

CREATE TABLE Streets 
    ( 
     str_id           UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     str_street       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     str_neighborhood UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     str_zip          UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     str_createdAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     str_updatedAt    UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     str_publishedAt  UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     str_sty_id       UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE Streets 
    ADD CONSTRAINT Streets_PK PRIMARY KEY ( str_id ) ;

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
;

ALTER TABLE "User" 
    ADD CONSTRAINT Clients_PK PRIMARY KEY ( usr_id ) ;

CREATE TABLE User_Address 
    ( 
     uad_usr_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     uad_ana_id      UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     udd_publishedAt UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     udd_createdAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL , 
     udd_updatedAt   UNKNOWN 
--  ERROR: Datatype UNKNOWN is not allowed 
                     NOT NULL 
    ) 
;

ALTER TABLE User_Address 
    ADD CONSTRAINT User_Address_PK PRIMARY KEY ( uad_ana_id, uad_usr_id ) ;

ALTER TABLE Addresses 
    ADD CONSTRAINT add_cit_FK FOREIGN KEY 
    ( 
     add_cit_id
    ) 
    REFERENCES Cities 
    ( 
     cit_id
    ) 
;

ALTER TABLE Addresses 
    ADD CONSTRAINT add_rty_FK FOREIGN KEY 
    ( 
     add_rty_id
    ) 
    REFERENCES Residence_Types 
    ( 
     rty_id
    ) 
;

ALTER TABLE Addresses 
    ADD CONSTRAINT add_str_FK FOREIGN KEY 
    ( 
     add_str_id
    ) 
    REFERENCES Streets 
    ( 
     str_id
    ) 
;

ALTER TABLE Address_Name 
    ADD CONSTRAINT ana_add_FK FOREIGN KEY 
    ( 
     ana_add_id
    ) 
    REFERENCES Addresses 
    ( 
     add_id
    ) 
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
;

ALTER TABLE Cities 
    ADD CONSTRAINT cit_sta_FK FOREIGN KEY 
    ( 
     cit_sta_id
    ) 
    REFERENCES States 
    ( 
     sta_id
    ) 
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
;

ALTER TABLE Sales 
    ADD CONSTRAINT sal_ana_FK FOREIGN KEY 
    ( 
     sal_ana_id
    ) 
    REFERENCES Address_Name 
    ( 
     ana_id
    ) 
;

ALTER TABLE Sales 
    ADD CONSTRAINT sal_car_FK FOREIGN KEY 
    ( 
     sal_car_id
    ) 
    REFERENCES Cards 
    ( 
     car_id
    ) 
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
;

ALTER TABLE States 
    ADD CONSTRAINT sta_cou_FK FOREIGN KEY 
    ( 
     sta_cou_id
    ) 
    REFERENCES Countries 
    ( 
     cou_id
    ) 
;

ALTER TABLE Streets 
    ADD CONSTRAINT str_sty_FK FOREIGN KEY 
    ( 
     str_sty_id
    ) 
    REFERENCES Street_Types 
    ( 
     sty_id
    ) 
;

ALTER TABLE User_Address 
    ADD CONSTRAINT udd_ana_FK FOREIGN KEY 
    ( 
     uad_ana_id
    ) 
    REFERENCES Address_Name 
    ( 
     ana_id
    ) 
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
-- ERRORS                                 119
-- WARNINGS                                 0
