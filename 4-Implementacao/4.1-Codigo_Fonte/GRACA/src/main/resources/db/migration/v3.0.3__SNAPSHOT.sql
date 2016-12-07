/*SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

CREATE SCHEMA IF NOT EXISTS "public";
CREATE SCHEMA IF NOT EXISTS "auditing";
CREATE EXTENSION IF NOT EXISTS UNACCENT SCHEMA PG_CATALOG;

SET search_path = auditing, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.14
-- Dumped by pg_dump version 9.3.13
-- Started on 2016-09-14 05:58:25 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = auditing, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 177 (class 1259 OID 384396)
-- Name: apoio_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE apoio_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    plano_atendimento_id bigint,
    rede_apoio_id bigint
);


ALTER TABLE auditing.apoio_audited OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 384406)
-- Name: casa_lar_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE casa_lar_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    enabled boolean,
    numero integer,
    cuidadora_apoiadora_id bigint,
    cuidadora_residente_id bigint
);


ALTER TABLE auditing.casa_lar_audited OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 384411)
-- Name: cidade_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE cidade_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    nome character varying(255),
    estado_id bigint
);


ALTER TABLE auditing.cidade_audited OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 384421)
-- Name: crianca_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE crianca_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    altura character varying(255),
    data_acolhimento timestamp without time zone,
    data_elaboracaopia timestamp without time zone,
    data_limite timestamp without time zone,
    entidade_acolhimento character varying(255),
    etnia integer,
    motivo_acolhimento character varying(255),
    numero_processo character varying(255),
    peso character varying(255),
    casa_lar_id bigint
);


ALTER TABLE auditing.crianca_audited OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 384429)
-- Name: documento_crianca_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE documento_crianca_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    numero_documento character varying(255),
    tipo_documento integer,
    crianca_id bigint
);


ALTER TABLE auditing.documento_crianca_audited OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 384434)
-- Name: documento_integrante_familiar_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE documento_integrante_familiar_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    numero_documento character varying(255),
    tipo_documento integer,
    integrante_familiar_id bigint
);


ALTER TABLE auditing.documento_integrante_familiar_audited OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 384439)
-- Name: encaminhamento_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE encaminhamento_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    data_final timestamp without time zone,
    descricao character varying(255),
    observacao character varying(255),
    status integer,
    tipo integer,
    integrante_familiar_id bigint,
    plano_atendimento_id bigint,
    responsavel_id bigint,
    user_id bigint
);


ALTER TABLE auditing.encaminhamento_audited OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 384447)
-- Name: endereco_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE endereco_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    bairro character varying(255),
    complemento character varying(255),
    numero integer,
    rua character varying(255),
    cidade_id bigint
);


ALTER TABLE auditing.endereco_audited OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 384455)
-- Name: estado_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE estado_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    nome character varying(255),
    pais_id bigint
);


ALTER TABLE auditing.estado_audited OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 384460)
-- Name: familia_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE familia_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    ativo boolean,
    infraestrutura character varying(255),
    nome character varying(255),
    nome_mae character varying(255),
    numero_comodos integer,
    numero_dormitorios integer,
    situacao_imovel character varying(255),
    telefone character varying(255),
    tipo_imovel integer,
    tipo_moradia integer,
    endereco_id bigint
);


ALTER TABLE auditing.familia_audited OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 384468)
-- Name: integrante_familiar_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE integrante_familiar_audited (
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
    familia_id bigint
);


ALTER TABLE auditing.integrante_familiar_audited OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 384476)
-- Name: pais_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE pais_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    nome character varying(255)
);


ALTER TABLE auditing.pais_audited OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 384481)
-- Name: parecer_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE parecer_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    descricao character varying(255),
    tipo integer,
    plano_atendimento_id bigint,
    usuario_id bigint
);


ALTER TABLE auditing.parecer_audited OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 384486)
-- Name: parente_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE parente_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    grau_parentesco integer,
    crianca_id bigint,
    integrante_familiar_id bigint
);


ALTER TABLE auditing.parente_audited OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 384491)
-- Name: plano_atendimento_familiar_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE plano_atendimento_familiar_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    ativo boolean,
    status integer,
    motivo_finalizacao character varying(255),
    familia_id bigint
);


ALTER TABLE auditing.plano_atendimento_familiar_audited OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 384509)
-- Name: rede_apoio_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE rede_apoio_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    enabled boolean,
    nome character varying(255),
    telefone character varying(255),
    endereco_id bigint,
    responsavel_id bigint
);


ALTER TABLE auditing.rede_apoio_audited OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 384517)
-- Name: responsavel_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE responsavel_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    cpf character varying(255),
    email character varying(255),
    nome character varying(255),
    telefone character varying(255)
);


ALTER TABLE auditing.responsavel_audited OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 384532)
-- Name: revision; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE revision (
    id bigint NOT NULL,
    "timestamp" bigint NOT NULL,
    user_id bigint
);


ALTER TABLE auditing.revision OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 384530)
-- Name: revision_id_seq; Type: SEQUENCE; Schema: auditing; Owner: postgres
--

CREATE SEQUENCE revision_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE auditing.revision_id_seq OWNER TO postgres;

--
-- TOC entry 2328 (class 0 OID 0)
-- Dependencies: 199
-- Name: revision_id_seq; Type: SEQUENCE OWNED BY; Schema: auditing; Owner: postgres
--

ALTER SEQUENCE revision_id_seq OWNED BY revision.id;


--
-- TOC entry 201 (class 1259 OID 384538)
-- Name: user_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE user_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    email character varying(144),
    enabled boolean,
    last_login timestamp without time zone,
    name character varying(50),
    password character varying(100),
    role integer,
    rede_apoio_id bigint
);


ALTER TABLE auditing.user_audited OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- TOC entry 176 (class 1259 OID 384390)
-- Name: apoio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE apoio (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    plano_atendimento_id bigint,
    rede_apoio_id bigint
);


ALTER TABLE public.apoio OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 384388)
-- Name: apoio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE apoio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.apoio_id_seq OWNER TO postgres;

--
-- TOC entry 2329 (class 0 OID 0)
-- Dependencies: 175
-- Name: apoio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE apoio_id_seq OWNED BY apoio.id;


--
-- TOC entry 206 (class 1259 OID 384558)
-- Name: casa_lar; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE casa_lar (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    enabled boolean NOT NULL,
    numero integer NOT NULL,
    cuidadora_apoiadora_id bigint NOT NULL,
    cuidadora_residente_id bigint NOT NULL
);


ALTER TABLE public.casa_lar OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 384556)
-- Name: casa_lar_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE casa_lar_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.casa_lar_id_seq OWNER TO postgres;

--
-- TOC entry 2330 (class 0 OID 0)
-- Dependencies: 205
-- Name: casa_lar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE casa_lar_id_seq OWNED BY casa_lar.id;


--
-- TOC entry 208 (class 1259 OID 384566)
-- Name: cidade; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cidade (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    nome character varying(255) NOT NULL,
    estado_id bigint
);


ALTER TABLE public.cidade OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 384564)
-- Name: cidade_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cidade_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cidade_id_seq OWNER TO postgres;

--
-- TOC entry 2331 (class 0 OID 0)
-- Dependencies: 207
-- Name: cidade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cidade_id_seq OWNED BY cidade.id;


--
-- TOC entry 211 (class 1259 OID 384580)
-- Name: crianca; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crianca (
    altura character varying(255),
    data_acolhimento timestamp without time zone NOT NULL,
    data_elaboracaopia timestamp without time zone,
    data_limite timestamp without time zone,
    entidade_acolhimento character varying(255),
    etnia integer,
    motivo_acolhimento character varying(255) NOT NULL,
    numero_processo character varying(255) NOT NULL,
    peso character varying(255),
    id bigint NOT NULL,
    casa_lar_id bigint
);


ALTER TABLE public.crianca OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 384590)
-- Name: documento_crianca; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE documento_crianca (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    numero_documento character varying(255) NOT NULL,
    tipo_documento integer NOT NULL,
    crianca_id bigint NOT NULL
);


ALTER TABLE public.documento_crianca OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 384588)
-- Name: documento_crianca_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE documento_crianca_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.documento_crianca_id_seq OWNER TO postgres;

--
-- TOC entry 2332 (class 0 OID 0)
-- Dependencies: 212
-- Name: documento_crianca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE documento_crianca_id_seq OWNED BY documento_crianca.id;


--
-- TOC entry 215 (class 1259 OID 384598)
-- Name: documento_integrante_familiar; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE documento_integrante_familiar (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    numero_documento character varying(255) NOT NULL,
    tipo_documento integer NOT NULL,
    integrante_familiar_id bigint NOT NULL
);


ALTER TABLE public.documento_integrante_familiar OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 384596)
-- Name: documento_integrante_familiar_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE documento_integrante_familiar_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.documento_integrante_familiar_id_seq OWNER TO postgres;

--
-- TOC entry 2333 (class 0 OID 0)
-- Dependencies: 214
-- Name: documento_integrante_familiar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE documento_integrante_familiar_id_seq OWNED BY documento_integrante_familiar.id;


--
-- TOC entry 217 (class 1259 OID 384606)
-- Name: encaminhamento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE encaminhamento (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    data_final timestamp without time zone NOT NULL,
    descricao character varying(255) NOT NULL,
    observacao character varying(255),
    status integer NOT NULL,
    tipo integer NOT NULL,
    integrante_familiar_id bigint,
    plano_atendimento_id bigint,
    responsavel_id bigint,
    user_id bigint,
    CONSTRAINT encaminhamento_check CHECK (((status <> 2) OR (observacao IS NOT NULL)))
);


ALTER TABLE public.encaminhamento OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 384604)
-- Name: encaminhamento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE encaminhamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.encaminhamento_id_seq OWNER TO postgres;

--
-- TOC entry 2334 (class 0 OID 0)
-- Dependencies: 216
-- Name: encaminhamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE encaminhamento_id_seq OWNED BY encaminhamento.id;


--
-- TOC entry 219 (class 1259 OID 384618)
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE endereco (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    bairro character varying(255),
    complemento character varying(255),
    numero integer,
    rua character varying(255),
    cidade_id bigint
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 384616)
-- Name: endereco_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE endereco_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.endereco_id_seq OWNER TO postgres;

--
-- TOC entry 2335 (class 0 OID 0)
-- Dependencies: 218
-- Name: endereco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE endereco_id_seq OWNED BY endereco.id;


--
-- TOC entry 221 (class 1259 OID 384629)
-- Name: estado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estado (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    nome character varying(255) NOT NULL,
    pais_id bigint
);


ALTER TABLE public.estado OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 384627)
-- Name: estado_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE estado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.estado_id_seq OWNER TO postgres;

--
-- TOC entry 2336 (class 0 OID 0)
-- Dependencies: 220
-- Name: estado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE estado_id_seq OWNED BY estado.id;


--
-- TOC entry 223 (class 1259 OID 384637)
-- Name: familia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE familia (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    ativo boolean,
    infraestrutura character varying(255),
    nome character varying(255) NOT NULL,
    nome_mae character varying(255) NOT NULL,
    numero_comodos integer,
    numero_dormitorios integer,
    situacao_imovel character varying(255),
    telefone character varying(255),
    tipo_imovel integer,
    tipo_moradia integer,
    endereco_id bigint
);


ALTER TABLE public.familia OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 384635)
-- Name: familia_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE familia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.familia_id_seq OWNER TO postgres;

--
-- TOC entry 2337 (class 0 OID 0)
-- Dependencies: 222
-- Name: familia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE familia_id_seq OWNED BY familia.id;


--
-- TOC entry 225 (class 1259 OID 384648)
-- Name: integrante_familiar; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE integrante_familiar (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    ativo boolean NOT NULL,
    data_nascimento timestamp without time zone NOT NULL,
    filiacao character varying(255),
    grau_escolaridade integer,
    nome character varying(255) NOT NULL,
    ocupacao character varying(255),
    renda_mensal numeric(19,2),
    sexo integer NOT NULL,
    telefone character varying(255),
    endereco_id bigint,
    familia_id bigint
);


ALTER TABLE public.integrante_familiar OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 384646)
-- Name: integrante_familiar_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE integrante_familiar_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.integrante_familiar_id_seq OWNER TO postgres;

--
-- TOC entry 2338 (class 0 OID 0)
-- Dependencies: 224
-- Name: integrante_familiar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE integrante_familiar_id_seq OWNED BY integrante_familiar.id;


--
-- TOC entry 227 (class 1259 OID 384659)
-- Name: pais; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pais (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.pais OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 384657)
-- Name: pais_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pais_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pais_id_seq OWNER TO postgres;

--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 226
-- Name: pais_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pais_id_seq OWNED BY pais.id;


--
-- TOC entry 229 (class 1259 OID 384667)
-- Name: parecer; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE parecer (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    descricao character varying(255) NOT NULL,
    tipo integer NOT NULL,
    plano_atendimento_id bigint,
    usuario_id bigint
);


ALTER TABLE public.parecer OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 384665)
-- Name: parecer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE parecer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.parecer_id_seq OWNER TO postgres;

--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 228
-- Name: parecer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE parecer_id_seq OWNED BY parecer.id;


--
-- TOC entry 231 (class 1259 OID 384675)
-- Name: parente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE parente (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    grau_parentesco integer,
    crianca_id bigint NOT NULL,
    integrante_familiar_id bigint NOT NULL
);


ALTER TABLE public.parente OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 384673)
-- Name: parente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE parente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.parente_id_seq OWNER TO postgres;

--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 230
-- Name: parente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE parente_id_seq OWNED BY parente.id;


--
-- TOC entry 232 (class 1259 OID 384681)
-- Name: plano_atendimento_familiar; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE plano_atendimento_familiar (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    ativo boolean NOT NULL,
    status integer NOT NULL,
    motivo_finalizacao character varying(255),
    familia_id bigint NOT NULL,
    CONSTRAINT plano_atendimento_familiar_check CHECK (((status <> 5) OR (motivo_finalizacao IS NOT NULL)))
);


ALTER TABLE public.plano_atendimento_familiar OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 384708)
-- Name: rede_apoio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rede_apoio (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    enabled boolean NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255),
    endereco_id bigint,
    responsavel_id bigint NOT NULL
);


ALTER TABLE public.rede_apoio OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 384706)
-- Name: rede_apoio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rede_apoio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rede_apoio_id_seq OWNER TO postgres;

--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 237
-- Name: rede_apoio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rede_apoio_id_seq OWNED BY rede_apoio.id;


--
-- TOC entry 240 (class 1259 OID 384719)
-- Name: responsavel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE responsavel (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    cpf character varying(255),
    email character varying(255),
    nome character varying(255) NOT NULL,
    telefone character varying(255)
);


ALTER TABLE public.responsavel OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 384717)
-- Name: responsavel_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE responsavel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.responsavel_id_seq OWNER TO postgres;

--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 239
-- Name: responsavel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE responsavel_id_seq OWNED BY responsavel.id;


--
-- TOC entry 174 (class 1259 OID 384381)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "user" (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    email character varying(144) NOT NULL,
    enabled boolean NOT NULL,
    last_login timestamp without time zone,
    name character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    role integer NOT NULL,
    rede_apoio_id bigint,
    CONSTRAINT user_check CHECK (((role <> 2) OR (rede_apoio_id IS NOT NULL)))
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 384379)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 173
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2073 (class 2604 OID 384535)
-- Name: id; Type: DEFAULT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY revision ALTER COLUMN id SET DEFAULT nextval('revision_id_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- TOC entry 2072 (class 2604 OID 384393)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apoio ALTER COLUMN id SET DEFAULT nextval('apoio_id_seq'::regclass);


--
-- TOC entry 2074 (class 2604 OID 384561)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar ALTER COLUMN id SET DEFAULT nextval('casa_lar_id_seq'::regclass);


--
-- TOC entry 2075 (class 2604 OID 384569)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade ALTER COLUMN id SET DEFAULT nextval('cidade_id_seq'::regclass);


--
-- TOC entry 2076 (class 2604 OID 384593)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_crianca ALTER COLUMN id SET DEFAULT nextval('documento_crianca_id_seq'::regclass);


--
-- TOC entry 2077 (class 2604 OID 384601)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar ALTER COLUMN id SET DEFAULT nextval('documento_integrante_familiar_id_seq'::regclass);


--
-- TOC entry 2078 (class 2604 OID 384609)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento ALTER COLUMN id SET DEFAULT nextval('encaminhamento_id_seq'::regclass);


--
-- TOC entry 2080 (class 2604 OID 384621)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco ALTER COLUMN id SET DEFAULT nextval('endereco_id_seq'::regclass);


--
-- TOC entry 2081 (class 2604 OID 384632)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado ALTER COLUMN id SET DEFAULT nextval('estado_id_seq'::regclass);


--
-- TOC entry 2082 (class 2604 OID 384640)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familia ALTER COLUMN id SET DEFAULT nextval('familia_id_seq'::regclass);


--
-- TOC entry 2083 (class 2604 OID 384651)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar ALTER COLUMN id SET DEFAULT nextval('integrante_familiar_id_seq'::regclass);


--
-- TOC entry 2084 (class 2604 OID 384662)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pais ALTER COLUMN id SET DEFAULT nextval('pais_id_seq'::regclass);


--
-- TOC entry 2085 (class 2604 OID 384670)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer ALTER COLUMN id SET DEFAULT nextval('parecer_id_seq'::regclass);


--
-- TOC entry 2086 (class 2604 OID 384678)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente ALTER COLUMN id SET DEFAULT nextval('parente_id_seq'::regclass);


--
-- TOC entry 2088 (class 2604 OID 384711)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio ALTER COLUMN id SET DEFAULT nextval('rede_apoio_id_seq'::regclass);


--
-- TOC entry 2089 (class 2604 OID 384722)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY responsavel ALTER COLUMN id SET DEFAULT nextval('responsavel_id_seq'::regclass);


--
-- TOC entry 2070 (class 2604 OID 384384)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2097 (class 2606 OID 384400)
-- Name: apoio_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apoio_audited
    ADD CONSTRAINT apoio_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2099 (class 2606 OID 384410)
-- Name: casa_lar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY casa_lar_audited
    ADD CONSTRAINT casa_lar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2101 (class 2606 OID 384415)
-- Name: cidade_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade_audited
    ADD CONSTRAINT cidade_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2103 (class 2606 OID 384428)
-- Name: crianca_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca_audited
    ADD CONSTRAINT crianca_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2105 (class 2606 OID 384433)
-- Name: documento_crianca_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca_audited
    ADD CONSTRAINT documento_crianca_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2107 (class 2606 OID 384438)
-- Name: documento_integrante_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar_audited
    ADD CONSTRAINT documento_integrante_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2109 (class 2606 OID 384446)
-- Name: encaminhamento_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY encaminhamento_audited
    ADD CONSTRAINT encaminhamento_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2111 (class 2606 OID 384454)
-- Name: endereco_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco_audited
    ADD CONSTRAINT endereco_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2113 (class 2606 OID 384459)
-- Name: estado_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado_audited
    ADD CONSTRAINT estado_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2115 (class 2606 OID 384467)
-- Name: familia_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia_audited
    ADD CONSTRAINT familia_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2117 (class 2606 OID 384475)
-- Name: integrante_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY integrante_familiar_audited
    ADD CONSTRAINT integrante_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2119 (class 2606 OID 384480)
-- Name: pais_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais_audited
    ADD CONSTRAINT pais_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2121 (class 2606 OID 384485)
-- Name: parecer_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parecer_audited
    ADD CONSTRAINT parecer_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2123 (class 2606 OID 384490)
-- Name: parente_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parente_audited
    ADD CONSTRAINT parente_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2125 (class 2606 OID 384495)
-- Name: plano_atendimento_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plano_atendimento_familiar_audited
    ADD CONSTRAINT plano_atendimento_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2127 (class 2606 OID 384516)
-- Name: rede_apoio_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rede_apoio_audited
    ADD CONSTRAINT rede_apoio_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2129 (class 2606 OID 384524)
-- Name: responsavel_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY responsavel_audited
    ADD CONSTRAINT responsavel_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2131 (class 2606 OID 384537)
-- Name: revision_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY revision
    ADD CONSTRAINT revision_pkey PRIMARY KEY (id);


--
-- TOC entry 2133 (class 2606 OID 384542)
-- Name: user_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_audited
    ADD CONSTRAINT user_audited_pkey PRIMARY KEY (id, revision);


SET search_path = public, pg_catalog;

--
-- TOC entry 2095 (class 2606 OID 384395)
-- Name: apoio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apoio
    ADD CONSTRAINT apoio_pkey PRIMARY KEY (id);


--
-- TOC entry 2135 (class 2606 OID 384563)
-- Name: casa_lar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT casa_lar_pkey PRIMARY KEY (id);


--
-- TOC entry 2137 (class 2606 OID 384571)
-- Name: cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);


--
-- TOC entry 2139 (class 2606 OID 384587)
-- Name: crianca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT crianca_pkey PRIMARY KEY (id);


--
-- TOC entry 2143 (class 2606 OID 384595)
-- Name: documento_crianca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT documento_crianca_pkey PRIMARY KEY (id);


--
-- TOC entry 2147 (class 2606 OID 384603)
-- Name: documento_integrante_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT documento_integrante_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2151 (class 2606 OID 384615)
-- Name: encaminhamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT encaminhamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2153 (class 2606 OID 384626)
-- Name: endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 2155 (class 2606 OID 384634)
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id);


--
-- TOC entry 2157 (class 2606 OID 384645)
-- Name: familia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT familia_pkey PRIMARY KEY (id);


--
-- TOC entry 2161 (class 2606 OID 384656)
-- Name: integrante_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT integrante_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2163 (class 2606 OID 384664)
-- Name: pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT pais_pkey PRIMARY KEY (id);


--
-- TOC entry 2165 (class 2606 OID 384672)
-- Name: parecer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT parecer_pkey PRIMARY KEY (id);


--
-- TOC entry 2167 (class 2606 OID 384680)
-- Name: parente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT parente_pkey PRIMARY KEY (id);


--
-- TOC entry 2169 (class 2606 OID 384686)
-- Name: plano_atendimento_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plano_atendimento_familiar
    ADD CONSTRAINT plano_atendimento_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2171 (class 2606 OID 384716)
-- Name: rede_apoio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT rede_apoio_pkey PRIMARY KEY (id);


--
-- TOC entry 2173 (class 2606 OID 384727)
-- Name: responsavel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY responsavel
    ADD CONSTRAINT responsavel_pkey PRIMARY KEY (id);


--
-- TOC entry 2141 (class 2606 OID 384747)
-- Name: uk_crianca_casa_lar_id_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT uk_crianca_casa_lar_id_id UNIQUE (id, casa_lar_id);


--
-- TOC entry 2145 (class 2606 OID 384749)
-- Name: uk_documento_crianca_crianca_id_tipo_documento; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT uk_documento_crianca_crianca_id_tipo_documento UNIQUE (tipo_documento, crianca_id);


--
-- TOC entry 2149 (class 2606 OID 384751)
-- Name: uk_documento_integrante_familiar_integrante_familiar_id_tipo_d; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT uk_documento_integrante_familiar_integrante_familiar_id_tipo_d UNIQUE (tipo_documento, integrante_familiar_id);


--
-- TOC entry 2159 (class 2606 OID 384753)
-- Name: uk_familia_nome_nome_mae; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT uk_familia_nome_nome_mae UNIQUE (nome, nome_mae);


--
-- TOC entry 2091 (class 2606 OID 384745)
-- Name: uk_user_email; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT uk_user_email UNIQUE (email);


--
-- TOC entry 2093 (class 2606 OID 384387)
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2176 (class 2606 OID 384764)
-- Name: fk_apoio_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY apoio_audited
    ADD CONSTRAINT fk_apoio_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2177 (class 2606 OID 384774)
-- Name: fk_casa_lar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY casa_lar_audited
    ADD CONSTRAINT fk_casa_lar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2178 (class 2606 OID 384779)
-- Name: fk_cidade_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY cidade_audited
    ADD CONSTRAINT fk_cidade_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2179 (class 2606 OID 384789)
-- Name: fk_crianca_audited_id_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY crianca_audited
    ADD CONSTRAINT fk_crianca_audited_id_revision FOREIGN KEY (id, revision) REFERENCES integrante_familiar_audited(id, revision);


--
-- TOC entry 2180 (class 2606 OID 384794)
-- Name: fk_documento_crianca_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY documento_crianca_audited
    ADD CONSTRAINT fk_documento_crianca_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2181 (class 2606 OID 384799)
-- Name: fk_documento_integrante_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar_audited
    ADD CONSTRAINT fk_documento_integrante_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2182 (class 2606 OID 384804)
-- Name: fk_encaminhamento_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY encaminhamento_audited
    ADD CONSTRAINT fk_encaminhamento_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2183 (class 2606 OID 384809)
-- Name: fk_endereco_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY endereco_audited
    ADD CONSTRAINT fk_endereco_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2184 (class 2606 OID 384814)
-- Name: fk_estado_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY estado_audited
    ADD CONSTRAINT fk_estado_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2185 (class 2606 OID 384819)
-- Name: fk_familia_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY familia_audited
    ADD CONSTRAINT fk_familia_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2186 (class 2606 OID 384824)
-- Name: fk_integrante_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar_audited
    ADD CONSTRAINT fk_integrante_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2187 (class 2606 OID 384829)
-- Name: fk_pais_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY pais_audited
    ADD CONSTRAINT fk_pais_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2188 (class 2606 OID 384834)
-- Name: fk_parecer_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY parecer_audited
    ADD CONSTRAINT fk_parecer_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2189 (class 2606 OID 384839)
-- Name: fk_parente_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY parente_audited
    ADD CONSTRAINT fk_parente_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2190 (class 2606 OID 384844)
-- Name: fk_plano_atendimento_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY plano_atendimento_familiar_audited
    ADD CONSTRAINT fk_plano_atendimento_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2191 (class 2606 OID 384859)
-- Name: fk_rede_apoio_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY rede_apoio_audited
    ADD CONSTRAINT fk_rede_apoio_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2192 (class 2606 OID 384864)
-- Name: fk_responsavel_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY responsavel_audited
    ADD CONSTRAINT fk_responsavel_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2193 (class 2606 OID 384874)
-- Name: fk_user_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY user_audited
    ADD CONSTRAINT fk_user_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


SET search_path = public, pg_catalog;

--
-- TOC entry 2175 (class 2606 OID 384759)
-- Name: fk_apoio_rede_apoio_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apoio
    ADD CONSTRAINT fk_apoio_rede_apoio_id FOREIGN KEY (rede_apoio_id) REFERENCES rede_apoio(id);


--
-- TOC entry 2194 (class 2606 OID 384899)
-- Name: fk_casa_lar_cuidadora_apoiadora_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT fk_casa_lar_cuidadora_apoiadora_id FOREIGN KEY (cuidadora_apoiadora_id) REFERENCES responsavel(id);


--
-- TOC entry 2195 (class 2606 OID 384904)
-- Name: fk_casa_lar_cuidadora_residente_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT fk_casa_lar_cuidadora_residente_id FOREIGN KEY (cuidadora_residente_id) REFERENCES responsavel(id);


--
-- TOC entry 2196 (class 2606 OID 384909)
-- Name: fk_cidade_estado_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT fk_cidade_estado_id FOREIGN KEY (estado_id) REFERENCES estado(id);


--
-- TOC entry 2197 (class 2606 OID 384919)
-- Name: fk_crianca_casa_lar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT fk_crianca_casa_lar_id FOREIGN KEY (casa_lar_id) REFERENCES casa_lar(id);


--
-- TOC entry 2198 (class 2606 OID 384924)
-- Name: fk_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT fk_crianca_id FOREIGN KEY (id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2199 (class 2606 OID 384929)
-- Name: fk_documento_crianca_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT fk_documento_crianca_crianca_id FOREIGN KEY (crianca_id) REFERENCES crianca(id);


--
-- TOC entry 2200 (class 2606 OID 384934)
-- Name: fk_documento_integrante_familiar_integrante_familiar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT fk_documento_integrante_familiar_integrante_familiar_id FOREIGN KEY (integrante_familiar_id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2201 (class 2606 OID 384939)
-- Name: fk_encaminhamento_integrante_familiar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_integrante_familiar_id FOREIGN KEY (integrante_familiar_id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2202 (class 2606 OID 384944)
-- Name: fk_encaminhamento_plano_atendimento_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_plano_atendimento_id FOREIGN KEY (plano_atendimento_id) REFERENCES plano_atendimento_familiar(id);


--
-- TOC entry 2203 (class 2606 OID 384949)
-- Name: fk_encaminhamento_responsavel_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);


--
-- TOC entry 2204 (class 2606 OID 384954)
-- Name: fk_encaminhamento_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_user_id FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- TOC entry 2205 (class 2606 OID 384959)
-- Name: fk_endereco_cidade_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT fk_endereco_cidade_id FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- TOC entry 2206 (class 2606 OID 384964)
-- Name: fk_estado_pais_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT fk_estado_pais_id FOREIGN KEY (pais_id) REFERENCES pais(id);


--
-- TOC entry 2207 (class 2606 OID 384969)
-- Name: fk_familia_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT fk_familia_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2208 (class 2606 OID 384974)
-- Name: fk_integrante_familiar_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT fk_integrante_familiar_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2209 (class 2606 OID 384979)
-- Name: fk_integrante_familiar_familia_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT fk_integrante_familiar_familia_id FOREIGN KEY (familia_id) REFERENCES familia(id);


--
-- TOC entry 2210 (class 2606 OID 384984)
-- Name: fk_parecer_plano_atendimento_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT fk_parecer_plano_atendimento_id FOREIGN KEY (plano_atendimento_id) REFERENCES plano_atendimento_familiar(id);


--
-- TOC entry 2211 (class 2606 OID 384989)
-- Name: fk_parecer_usuario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT fk_parecer_usuario_id FOREIGN KEY (usuario_id) REFERENCES "user"(id);


--
-- TOC entry 2212 (class 2606 OID 384994)
-- Name: fk_parente_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT fk_parente_crianca_id FOREIGN KEY (crianca_id) REFERENCES crianca(id);


--
-- TOC entry 2213 (class 2606 OID 384999)
-- Name: fk_parente_integrante_familiar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT fk_parente_integrante_familiar_id FOREIGN KEY (integrante_familiar_id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2214 (class 2606 OID 385004)
-- Name: fk_plano_atendimento_familiar_familia_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY plano_atendimento_familiar
    ADD CONSTRAINT fk_plano_atendimento_familiar_familia_id FOREIGN KEY (familia_id) REFERENCES familia(id);


--
-- TOC entry 2215 (class 2606 OID 385019)
-- Name: fk_rede_apoio_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT fk_rede_apoio_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2216 (class 2606 OID 385024)
-- Name: fk_rede_apoio_responsavel_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT fk_rede_apoio_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);


--
-- TOC entry 2174 (class 2606 OID 384754)
-- Name: fk_user_rede_apoio_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT fk_user_rede_apoio_id FOREIGN KEY (rede_apoio_id) REFERENCES rede_apoio(id);


-- Completed on 2016-09-14 05:58:25 BRT

--
-- PostgreSQL database dump complete
--


REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-09-04 20:54:17 BRT

--
-- PostgreSQL database dump complete
--
  
  

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
  
  INSERT INTO public.endereco values (1, now(), now(), 'Bairro qualquer', 'complemento', 23, 'rua', 1);
  
  INSERT INTO public.familia values (1, now(), now(), true, 'fera', 'Silva', 'Maria', 10, 5, 'boa', '9999-9999', 0, 0, 1);
 
  INSERT INTO public.integrante_familiar values (1, now(), now(), true, now(), 'Pessoa', 0, 'Joao', 'Nada', 1000, 0, '123', 1, 1);
  
  INSERT INTO "public"."user"(
            id, created, updated, email, enabled, name, password, role)
    VALUES (1, NOW(), null, 'admin@admin.com', TRUE, 'Administrador de Sistemas', 'd1bd2f08fead38a982aed9d4ca060152400b1b8f', 0);
    
    insert into public.responsavel values(1, now(), now(), '1234565789', 'responsavel@email.com', 'nome', '9999-9999');
*/