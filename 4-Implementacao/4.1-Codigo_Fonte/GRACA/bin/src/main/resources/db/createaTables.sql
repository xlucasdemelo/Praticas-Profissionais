SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

CREATE SCHEMA IF NOT EXISTS "public";
CREATE SCHEMA IF NOT EXISTS "auditing";
CREATE EXTENSION IF NOT EXISTS UNACCENT SCHEMA PG_CATALOG;

----------------------- 
-- AUDITING STRUCTURE
-----------------------
--
-- TOC entry 177 (class 1259 OID 278689)
-- Name: revision; Type: TABLE; Schema: auditing; Owner: -
--
CREATE TABLE auditing.revision
(
  id bigserial NOT NULL,
  "timestamp" bigint NOT NULL,
  user_id bigint,
  CONSTRAINT revision_pkey PRIMARY KEY (id)
);

--
-- TOC entry 178 (class 1259 OID 278695)
-- Name: user_audited; Type: TABLE; Schema: auditing; Owner: -
--
CREATE TABLE auditing.user_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  email character varying(144),
  enabled boolean,
  last_login timestamp without time zone,
  name character varying(50),
  password character varying(100),
  role integer,
  CONSTRAINT user_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_user_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

----------------------- 
-- PUBLIC STRUCTURE
-----------------------

--
-- TOC entry 175 (class 1259 OID 278679)
-- Name: user; Type: TABLE; Schema: public; Owner: -
--
CREATE TABLE "public"."user"
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  email character varying(144) NOT NULL,
  enabled boolean NOT NULL,
  name character varying(50) NOT NULL,
  password character varying(100) NOT NULL,
  role integer NOT NULL,
  last_login timestamp without time zone,
  CONSTRAINT user_pkey PRIMARY KEY (id),
  CONSTRAINT uk_user_email UNIQUE (email)
);

----------------------- 
-- DEFAULT DATA
-----------------------
    
    
    
    
    -- Table: pais

-- DROP TABLE public.pais;

CREATE TABLE public.pais
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  nome character varying(255) NOT NULL,
  CONSTRAINT pais_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.pais
  OWNER TO postgres;

  -- Table: estado

-- DROP TABLE public.estado;

CREATE TABLE public.estado
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  nome character varying(255) NOT NULL,
  pais_id bigint,
  CONSTRAINT estado_pkey PRIMARY KEY (id),
  CONSTRAINT fk_estado_pais_id FOREIGN KEY (pais_id)
      REFERENCES pais (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.estado
  OWNER TO postgres;

  -- Table: cidade

-- DROP TABLE public.cidade;

CREATE TABLE public.cidade
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  nome character varying(255) NOT NULL,
  estado_id bigint,
  CONSTRAINT cidade_pkey PRIMARY KEY (id),
  CONSTRAINT fk_cidade_estado_id FOREIGN KEY (estado_id)
      REFERENCES estado (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.cidade
  OWNER TO postgres;

-- Table: endereco

-- DROP TABLE public.endereco;

CREATE TABLE public.endereco
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  bairro character varying(255),
  complemento character varying(255),
  numero integer,
  rua character varying(255),
  cidade_id bigint,
  CONSTRAINT endereco_pkey PRIMARY KEY (id),
  CONSTRAINT fk_endereco_cidade_id FOREIGN KEY (cidade_id)
      REFERENCES cidade (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.endereco
  OWNER TO postgres;
 
 -- Table: familia

-- DROP TABLE public.familia;

CREATE TABLE public.familia
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  ativo boolean NOT NULL,
  infraestrutura character varying(255) NOT NULL,
  nome character varying(255) NOT NULL,
  nome_mae character varying(255) NOT NULL,
  numero_comodos integer NOT NULL,
  numero_dormitorios integer NOT NULL,
  situacao_imovel character varying(255) NOT NULL,
  telefone character varying(255) NOT NULL,
  tipo_imovel integer NOT NULL,
  tipo_moradia integer NOT NULL,
  endereco_id bigint,
  CONSTRAINT familia_pkey PRIMARY KEY (id),
  CONSTRAINT fk_familia_endereco_id FOREIGN KEY (endereco_id)
      REFERENCES endereco (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_familia_nome_nome_mae UNIQUE (nome, nome_mae)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.familia
  OWNER TO postgres;


  
 /* AUDITORIA */
 -- Table: auditing.pais_audited

-- DROP TABLE auditing.pais_audited;

CREATE TABLE auditing.pais_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  nome character varying(255),
  CONSTRAINT pais_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_pais_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.pais_audited
  OWNER TO postgres;

  -- Table: auditing.estado_audited

-- DROP TABLE auditing.estado_audited;

CREATE TABLE auditing.estado_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  nome character varying(255),
  pais_id bigint,
  CONSTRAINT estado_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_estado_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.estado_audited
  OWNER TO postgres;

 -- Table: auditing.cidade_audited

-- DROP TABLE auditing.cidade_audited;

CREATE TABLE auditing.cidade_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  nome character varying(255),
  estado_id bigint,
  CONSTRAINT cidade_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_cidade_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.cidade_audited
  OWNER TO postgres;

  -- Table: auditing.endereco_audited

-- DROP TABLE auditing.endereco_audited;

CREATE TABLE auditing.endereco_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  bairro character varying(255),
  complemento character varying(255),
  numero integer,
  rua character varying(255),
  cidade_id bigint,
  CONSTRAINT endereco_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_endereco_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.endereco_audited
  OWNER TO postgres;

  -- Table: auditing.familia_audited

-- DROP TABLE auditing.familia_audited;

CREATE TABLE auditing.familia_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  ativo boolean,
  infraestrutura character varying(255),
  nome character varying(255),
  nome_mae character varying(255),
  numero_comodos integer,
  situacao_imovel character varying(255),
  telefone character varying(255),
  tipo_imovel integer,
  endereco_id bigint,
  numero_dormitorios integer,
  tipo_moradia integer,
  CONSTRAINT familia_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_familia_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.familia_audited
  OWNER TO postgres;

  
  /*  DATA
  
  INSERT INTO public.pais values (1, now(), now(), 'Brasil');
  
  INSERT INTO public.estado values (1, now(), now(), 'Paraná', 1);
  
  INSERT INTO public.cidade values (1, now(), now(), 'Foz do iguaçu', 1);
  
  INSERT INTO public.endereco values (2, now(), now(), 'Bairro qualquer', 'complemento', 23, 'rua', 1);
  
  INSERT INTO public.familia values (1, now(), now(), true, 'fera', 'Silva', 'Maria', 10, 5, 'boa', '9999-9999', 0, 0, 2); */
 
  
  
  
-- Table: integrante_familiar

-- DROP TABLE integrante_familiar;

CREATE TABLE integrante_familiar
(
  id bigint NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  ativo boolean NOT NULL,
  data_nascimento timestamp without time zone NOT NULL,
  filiacao character varying(255) NOT NULL,
  grau_escolaridade integer NOT NULL,
  nome character varying(255) NOT NULL,
  ocupacao character varying(255) NOT NULL,
  renda_mensal numeric(19,2) NOT NULL,
  sexo integer NOT NULL,
  telefone character varying(255) NOT NULL,
  endereco_id bigint,
  familia_id bigint,
  CONSTRAINT integrante_familiar_pkey PRIMARY KEY (id),
  CONSTRAINT fk_integrante_familiar_endereco_id FOREIGN KEY (endereco_id)
      REFERENCES endereco (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_integrante_familiar_familia_id FOREIGN KEY (familia_id)
      REFERENCES familia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE integrante_familiar
  OWNER TO postgres;

-- Table: auditing.integrante_familiar_audited

-- DROP TABLE auditing.integrante_familiar_audited;

CREATE TABLE auditing.integrante_familiar_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  ativo boolean,
  data_nascimento timestamp without time zone,
  filiacao character varying(255),
  grau_escolaridade integer,
  nome character varying(255),
  ocupacao character varying(255),
  renda_mensal numeric(19,2),
  sexo integer,
  telefone character varying(255),
  endereco_id bigint,
  familia_id bigint,
  CONSTRAINT integrante_familiar_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_integrante_familiar_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.integrante_familiar_audited
  OWNER TO postgres;

-- Table: documento_integrante_familiar

-- DROP TABLE documento_integrante_familiar;

CREATE TABLE documento_integrante_familiar
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  numero_documento character varying(255) NOT NULL,
  tipo_documento integer NOT NULL,
  integrante_familiar_id bigint NOT NULL,
  CONSTRAINT documento_integrante_familiar_pkey PRIMARY KEY (id),
  CONSTRAINT uk_documento_integrante_familiar_integrante_familiar_id_tipo_d UNIQUE (tipo_documento, integrante_familiar_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE documento_integrante_familiar
  OWNER TO postgres;

  -- Table: auditing.documento_integrante_familiar_audited

-- DROP TABLE auditing.documento_integrante_familiar_audited;

CREATE TABLE auditing.documento_integrante_familiar_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  numero_documento character varying(255),
  tipo_documento integer,
  integrante_familiar_id bigint,
  CONSTRAINT documento_integrante_familiar_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_documento_integrante_familiar_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.documento_integrante_familiar_audited
  OWNER TO postgres;
	
  
  
CREATE TABLE crianca
(
  id bigint NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  ativo boolean NOT NULL,
  data_nascimento timestamp without time zone NOT NULL,
  filiacao character varying(255) NOT NULL,
  grau_escolaridade integer NOT NULL,
  nome character varying(255) NOT NULL,
  ocupacao character varying(255) NOT NULL,
  renda_mensal numeric(19,2) NOT NULL,
  sexo integer NOT NULL,
  telefone character varying(255) NOT NULL,
  endereco_id bigint,
  familia_id bigint,
  altura character varying(255),
  data_acolhimento timestamp without time zone,
  data_elaboracaopia timestamp without time zone,
  data_limite timestamp without time zone,
  entidade_acolhimento character varying(255),
  etnia integer NOT NULL,
  motivo_acolhimento character varying(255),
  numero_processo character varying(255),
  peso character varying(255),
  CONSTRAINT crianca_pkey PRIMARY KEY (id),
  CONSTRAINT fk_crianca_endereco_id FOREIGN KEY (endereco_id)
      REFERENCES endereco (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_crianca_familia_id FOREIGN KEY (familia_id)
      REFERENCES familia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE crianca
  OWNER TO postgres;

  
  CREATE TABLE auditing.crianca_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  ativo boolean,
  data_nascimento timestamp without time zone,
  filiacao character varying(255),
  grau_escolaridade integer,
  nome character varying(255),
  ocupacao character varying(255),
  renda_mensal numeric(19,2),
  sexo integer,
  telefone character varying(255),
  endereco_id bigint,
  familia_id bigint,
  altura character varying(255),
  data_acolhimento timestamp without time zone,
  data_elaboracaopia timestamp without time zone,
  data_limite timestamp without time zone,
  entidade_acolhimento character varying(255),
  etnia integer,
  motivo_acolhimento character varying(255),
  numero_processo character varying(255),
  peso character varying(255),
  CONSTRAINT crianca_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_crianca_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.crianca_audited
  OWNER TO postgres;
  
  -- Table: documento_crianca

-- DROP TABLE documento_crianca;

CREATE TABLE documento_crianca
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  numero_documento character varying(255) NOT NULL,
  tipo_documento integer NOT NULL,
  crianca_id bigint NOT NULL,
  CONSTRAINT documento_crianca_pkey PRIMARY KEY (id),
  CONSTRAINT fk_documento_crianca_crianca_id FOREIGN KEY (crianca_id)
      REFERENCES crianca (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_documento_crianca_crianca_id_tipo_documento UNIQUE (tipo_documento, crianca_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE documento_crianca
  OWNER TO postgres;

  -- Table: auditing.documento_crianca_audited

-- DROP TABLE auditing.documento_crianca_audited;

CREATE TABLE auditing.documento_crianca_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  numero_documento character varying(255),
  tipo_documento integer,
  crianca_id bigint,
  CONSTRAINT documento_crianca_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_documento_crianca_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.documento_crianca_audited
  OWNER TO postgres;
  
  
  
  -- Table: parente

-- DROP TABLE parente;

CREATE TABLE parente
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  grau_parentesco integer,
  crianca_id bigint NOT NULL,
  integrante_familiar_id bigint NOT NULL,
  CONSTRAINT parente_pkey PRIMARY KEY (id),
  CONSTRAINT fk_parente_crianca_id FOREIGN KEY (crianca_id)
      REFERENCES crianca (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE parente
  OWNER TO postgres;

  
  -- Table: auditing.parente_audited

-- DROP TABLE auditing.parente_audited;

CREATE TABLE auditing.parente_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  grau_parentesco integer,
  crianca_id bigint,
  integrante_familiar_id bigint,
  CONSTRAINT parente_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk_parente_audited_revision FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.parente_audited
  OWNER TO postgres;

/*-------------------------------------------------------------------
	 *				 		     SEQUENCES
	 *-------------------------------------------------------------------*/
  

CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE hibernate_sequence
  OWNER TO postgres;

  
  
  INSERT INTO public.pais values (1, now(), now(), 'Brasil');
  
  INSERT INTO public.estado values (1, now(), now(), 'Paraná', 1);
  
  INSERT INTO public.cidade values (1, now(), now(), 'Foz do iguaçu', 1);
  
  INSERT INTO public.endereco values (2, now(), now(), 'Bairro qualquer', 'complemento', 23, 'rua', 1);
  
  INSERT INTO public.familia values (1, now(), now(), true, 'fera', 'Silva', 'Maria', 10, 5, 'boa', '9999-9999', 0, 0, 2)
 
  INSERT INTO public.integrante_familiar values (1, now(), now(), true, now(), 'Pessoa', 0, 'Joao', 'Nada', 1000, 0, '123', 1, 1)
  
  INSERT INTO "public"."user"(
            id, created, updated, email, enabled, name, password, role)
    VALUES (1, NOW(), null, 'admin@admin.com', TRUE, 'Administrador de Sistemas', 'd1bd2f08fead38a982aed9d4ca060152400b1b8f', 0);