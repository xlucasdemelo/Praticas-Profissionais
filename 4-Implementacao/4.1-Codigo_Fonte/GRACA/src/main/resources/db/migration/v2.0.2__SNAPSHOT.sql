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

  
  /*  DATA */
  
  INSERT INTO public.pais values (1, now(), now(), 'Brasil');
  
  INSERT INTO public.estado values (1, now(), now(), 'Paraná');
  
  INSERT INTO public.cidade values (1, now(), now(), 'Foz do iguaçu');
  
  INSERT INTO public.endereco values (2, now(), now(), 'Bairro qualquer', 'complemento', 23, 'rua', 1);
  
  INSERT INTO public.familia values (1, now(), now(), true, 'fera', 'Silva', 'Maria', 10, 5, 'boa', '9999-9999', 0, 0, 2)