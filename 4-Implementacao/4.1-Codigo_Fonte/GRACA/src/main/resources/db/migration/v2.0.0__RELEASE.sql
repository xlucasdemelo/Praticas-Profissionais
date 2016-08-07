
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
