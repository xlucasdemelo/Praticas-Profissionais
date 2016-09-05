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

SET search_path = auditing, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 176 (class 1259 OID 227942)
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
-- TOC entry 223 (class 1259 OID 232423)
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
-- TOC entry 177 (class 1259 OID 227947)
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
-- TOC entry 178 (class 1259 OID 227952)
-- Name: crianca_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE crianca_audited (
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
    casa_lar_id bigint
);


ALTER TABLE auditing.crianca_audited OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 227960)
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
-- TOC entry 180 (class 1259 OID 227965)
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
-- TOC entry 181 (class 1259 OID 227970)
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
-- TOC entry 182 (class 1259 OID 227978)
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
-- TOC entry 183 (class 1259 OID 227986)
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
-- TOC entry 184 (class 1259 OID 227991)
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
-- TOC entry 185 (class 1259 OID 227999)
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
-- TOC entry 186 (class 1259 OID 228007)
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
-- TOC entry 187 (class 1259 OID 228012)
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
-- TOC entry 188 (class 1259 OID 228017)
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
-- TOC entry 189 (class 1259 OID 228022)
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
-- TOC entry 190 (class 1259 OID 228027)
-- Name: rede_apoio_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE rede_apoio_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    nome character varying(255),
    telefone character varying(255),
    endereco_id bigint,
    responsavel_id bigint
);


ALTER TABLE auditing.rede_apoio_audited OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 228035)
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
-- TOC entry 193 (class 1259 OID 228045)
-- Name: revision; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE revision (
    id bigint NOT NULL,
    "timestamp" bigint NOT NULL,
    user_id bigint
);


ALTER TABLE auditing.revision OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 228043)
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
-- TOC entry 2313 (class 0 OID 0)
-- Dependencies: 192
-- Name: revision_id_seq; Type: SEQUENCE OWNED BY; Schema: auditing; Owner: postgres
--

ALTER SEQUENCE revision_id_seq OWNED BY revision.id;


--
-- TOC entry 194 (class 1259 OID 228051)
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
-- TOC entry 175 (class 1259 OID 227936)
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
-- TOC entry 174 (class 1259 OID 227934)
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
-- TOC entry 2314 (class 0 OID 0)
-- Dependencies: 174
-- Name: apoio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE apoio_id_seq OWNED BY apoio.id;


--
-- TOC entry 225 (class 1259 OID 232430)
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
-- TOC entry 224 (class 1259 OID 232428)
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
-- TOC entry 2315 (class 0 OID 0)
-- Dependencies: 224
-- Name: casa_lar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE casa_lar_id_seq OWNED BY casa_lar.id;


--
-- TOC entry 196 (class 1259 OID 228058)
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
-- TOC entry 195 (class 1259 OID 228056)
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
-- TOC entry 2316 (class 0 OID 0)
-- Dependencies: 195
-- Name: cidade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cidade_id_seq OWNED BY cidade.id;


--
-- TOC entry 197 (class 1259 OID 228064)
-- Name: crianca; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crianca (
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
    casa_lar_id bigint
);


ALTER TABLE public.crianca OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 228074)
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
-- TOC entry 198 (class 1259 OID 228072)
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
-- TOC entry 2317 (class 0 OID 0)
-- Dependencies: 198
-- Name: documento_crianca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE documento_crianca_id_seq OWNED BY documento_crianca.id;


--
-- TOC entry 201 (class 1259 OID 228082)
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
-- TOC entry 200 (class 1259 OID 228080)
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
-- TOC entry 2318 (class 0 OID 0)
-- Dependencies: 200
-- Name: documento_integrante_familiar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE documento_integrante_familiar_id_seq OWNED BY documento_integrante_familiar.id;


--
-- TOC entry 203 (class 1259 OID 228090)
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
-- TOC entry 202 (class 1259 OID 228088)
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
-- TOC entry 2319 (class 0 OID 0)
-- Dependencies: 202
-- Name: encaminhamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE encaminhamento_id_seq OWNED BY encaminhamento.id;


--
-- TOC entry 205 (class 1259 OID 228102)
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
-- TOC entry 204 (class 1259 OID 228100)
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
-- TOC entry 2320 (class 0 OID 0)
-- Dependencies: 204
-- Name: endereco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE endereco_id_seq OWNED BY endereco.id;


--
-- TOC entry 207 (class 1259 OID 228113)
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
-- TOC entry 206 (class 1259 OID 228111)
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
-- TOC entry 2321 (class 0 OID 0)
-- Dependencies: 206
-- Name: estado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE estado_id_seq OWNED BY estado.id;


--
-- TOC entry 209 (class 1259 OID 228121)
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
-- TOC entry 208 (class 1259 OID 228119)
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
-- TOC entry 2322 (class 0 OID 0)
-- Dependencies: 208
-- Name: familia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE familia_id_seq OWNED BY familia.id;


--
-- TOC entry 210 (class 1259 OID 228130)
-- Name: integrante_familiar; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE integrante_familiar (
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
    familia_id bigint
);


ALTER TABLE public.integrante_familiar OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 228140)
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
-- TOC entry 211 (class 1259 OID 228138)
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
-- TOC entry 2323 (class 0 OID 0)
-- Dependencies: 211
-- Name: pais_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pais_id_seq OWNED BY pais.id;


--
-- TOC entry 214 (class 1259 OID 228148)
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
-- TOC entry 213 (class 1259 OID 228146)
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
-- TOC entry 2324 (class 0 OID 0)
-- Dependencies: 213
-- Name: parecer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE parecer_id_seq OWNED BY parecer.id;


--
-- TOC entry 216 (class 1259 OID 228156)
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
-- TOC entry 215 (class 1259 OID 228154)
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
-- TOC entry 2325 (class 0 OID 0)
-- Dependencies: 215
-- Name: parente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE parente_id_seq OWNED BY parente.id;


--
-- TOC entry 217 (class 1259 OID 228162)
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
-- TOC entry 219 (class 1259 OID 228170)
-- Name: rede_apoio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rede_apoio (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    nome character varying(255) NOT NULL,
    telefone character varying(255),
    endereco_id bigint,
    responsavel_id bigint
);


ALTER TABLE public.rede_apoio OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 228168)
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
-- TOC entry 2326 (class 0 OID 0)
-- Dependencies: 218
-- Name: rede_apoio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rede_apoio_id_seq OWNED BY rede_apoio.id;


--
-- TOC entry 221 (class 1259 OID 228181)
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
-- TOC entry 220 (class 1259 OID 228179)
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
-- TOC entry 2327 (class 0 OID 0)
-- Dependencies: 220
-- Name: responsavel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE responsavel_id_seq OWNED BY responsavel.id;


--
-- TOC entry 173 (class 1259 OID 227927)
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
-- TOC entry 172 (class 1259 OID 227925)
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
-- TOC entry 2328 (class 0 OID 0)
-- Dependencies: 172
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2055 (class 2604 OID 228048)
-- Name: id; Type: DEFAULT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY revision ALTER COLUMN id SET DEFAULT nextval('revision_id_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- TOC entry 2054 (class 2604 OID 227939)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apoio ALTER COLUMN id SET DEFAULT nextval('apoio_id_seq'::regclass);


--
-- TOC entry 2070 (class 2604 OID 232433)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar ALTER COLUMN id SET DEFAULT nextval('casa_lar_id_seq'::regclass);


--
-- TOC entry 2056 (class 2604 OID 228061)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade ALTER COLUMN id SET DEFAULT nextval('cidade_id_seq'::regclass);


--
-- TOC entry 2057 (class 2604 OID 228077)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_crianca ALTER COLUMN id SET DEFAULT nextval('documento_crianca_id_seq'::regclass);


--
-- TOC entry 2058 (class 2604 OID 228085)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar ALTER COLUMN id SET DEFAULT nextval('documento_integrante_familiar_id_seq'::regclass);


--
-- TOC entry 2059 (class 2604 OID 228093)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento ALTER COLUMN id SET DEFAULT nextval('encaminhamento_id_seq'::regclass);


--
-- TOC entry 2061 (class 2604 OID 228105)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco ALTER COLUMN id SET DEFAULT nextval('endereco_id_seq'::regclass);


--
-- TOC entry 2062 (class 2604 OID 228116)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado ALTER COLUMN id SET DEFAULT nextval('estado_id_seq'::regclass);


--
-- TOC entry 2063 (class 2604 OID 228124)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familia ALTER COLUMN id SET DEFAULT nextval('familia_id_seq'::regclass);


--
-- TOC entry 2064 (class 2604 OID 228143)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pais ALTER COLUMN id SET DEFAULT nextval('pais_id_seq'::regclass);


--
-- TOC entry 2065 (class 2604 OID 228151)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer ALTER COLUMN id SET DEFAULT nextval('parecer_id_seq'::regclass);


--
-- TOC entry 2066 (class 2604 OID 228159)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente ALTER COLUMN id SET DEFAULT nextval('parente_id_seq'::regclass);


--
-- TOC entry 2068 (class 2604 OID 228173)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio ALTER COLUMN id SET DEFAULT nextval('rede_apoio_id_seq'::regclass);


--
-- TOC entry 2069 (class 2604 OID 228184)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY responsavel ALTER COLUMN id SET DEFAULT nextval('responsavel_id_seq'::regclass);


--
-- TOC entry 2052 (class 2604 OID 227930)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2078 (class 2606 OID 227946)
-- Name: apoio_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apoio_audited
    ADD CONSTRAINT apoio_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2152 (class 2606 OID 232427)
-- Name: casa_lar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY casa_lar_audited
    ADD CONSTRAINT casa_lar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2080 (class 2606 OID 227951)
-- Name: cidade_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade_audited
    ADD CONSTRAINT cidade_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2082 (class 2606 OID 227959)
-- Name: crianca_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca_audited
    ADD CONSTRAINT crianca_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2084 (class 2606 OID 227964)
-- Name: documento_crianca_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca_audited
    ADD CONSTRAINT documento_crianca_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2086 (class 2606 OID 227969)
-- Name: documento_integrante_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar_audited
    ADD CONSTRAINT documento_integrante_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2088 (class 2606 OID 227977)
-- Name: encaminhamento_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY encaminhamento_audited
    ADD CONSTRAINT encaminhamento_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2090 (class 2606 OID 227985)
-- Name: endereco_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco_audited
    ADD CONSTRAINT endereco_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2092 (class 2606 OID 227990)
-- Name: estado_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado_audited
    ADD CONSTRAINT estado_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2094 (class 2606 OID 227998)
-- Name: familia_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia_audited
    ADD CONSTRAINT familia_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2096 (class 2606 OID 228006)
-- Name: integrante_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY integrante_familiar_audited
    ADD CONSTRAINT integrante_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2098 (class 2606 OID 228011)
-- Name: pais_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais_audited
    ADD CONSTRAINT pais_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2100 (class 2606 OID 228016)
-- Name: parecer_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parecer_audited
    ADD CONSTRAINT parecer_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2102 (class 2606 OID 228021)
-- Name: parente_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parente_audited
    ADD CONSTRAINT parente_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2104 (class 2606 OID 228026)
-- Name: plano_atendimento_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plano_atendimento_familiar_audited
    ADD CONSTRAINT plano_atendimento_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2106 (class 2606 OID 228034)
-- Name: rede_apoio_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rede_apoio_audited
    ADD CONSTRAINT rede_apoio_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2108 (class 2606 OID 228042)
-- Name: responsavel_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY responsavel_audited
    ADD CONSTRAINT responsavel_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2110 (class 2606 OID 228050)
-- Name: revision_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY revision
    ADD CONSTRAINT revision_pkey PRIMARY KEY (id);


--
-- TOC entry 2112 (class 2606 OID 228055)
-- Name: user_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_audited
    ADD CONSTRAINT user_audited_pkey PRIMARY KEY (id, revision);


SET search_path = public, pg_catalog;

--
-- TOC entry 2076 (class 2606 OID 227941)
-- Name: apoio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apoio
    ADD CONSTRAINT apoio_pkey PRIMARY KEY (id);


--
-- TOC entry 2154 (class 2606 OID 232435)
-- Name: casa_lar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT casa_lar_pkey PRIMARY KEY (id);


--
-- TOC entry 2114 (class 2606 OID 228063)
-- Name: cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);


--
-- TOC entry 2116 (class 2606 OID 228071)
-- Name: crianca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT crianca_pkey PRIMARY KEY (id);


--
-- TOC entry 2120 (class 2606 OID 228079)
-- Name: documento_crianca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT documento_crianca_pkey PRIMARY KEY (id);


--
-- TOC entry 2124 (class 2606 OID 228087)
-- Name: documento_integrante_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT documento_integrante_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2128 (class 2606 OID 228099)
-- Name: encaminhamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT encaminhamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2130 (class 2606 OID 228110)
-- Name: endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 2132 (class 2606 OID 228118)
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id);


--
-- TOC entry 2134 (class 2606 OID 228129)
-- Name: familia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT familia_pkey PRIMARY KEY (id);


--
-- TOC entry 2138 (class 2606 OID 228137)
-- Name: integrante_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT integrante_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2140 (class 2606 OID 228145)
-- Name: pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT pais_pkey PRIMARY KEY (id);


--
-- TOC entry 2142 (class 2606 OID 228153)
-- Name: parecer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT parecer_pkey PRIMARY KEY (id);


--
-- TOC entry 2144 (class 2606 OID 228161)
-- Name: parente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT parente_pkey PRIMARY KEY (id);


--
-- TOC entry 2146 (class 2606 OID 228167)
-- Name: plano_atendimento_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plano_atendimento_familiar
    ADD CONSTRAINT plano_atendimento_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2148 (class 2606 OID 228178)
-- Name: rede_apoio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT rede_apoio_pkey PRIMARY KEY (id);


--
-- TOC entry 2150 (class 2606 OID 228189)
-- Name: responsavel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY responsavel
    ADD CONSTRAINT responsavel_pkey PRIMARY KEY (id);



--
-- TOC entry 2118 (class 2606 OID 232439)
-- Name: uk_crianca_casa_lar_id_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT uk_crianca_casa_lar_id_id UNIQUE (id, casa_lar_id);


--
-- TOC entry 2122 (class 2606 OID 228193)
-- Name: uk_documento_crianca_crianca_id_tipo_documento; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT uk_documento_crianca_crianca_id_tipo_documento UNIQUE (tipo_documento, crianca_id);


--
-- TOC entry 2126 (class 2606 OID 228195)
-- Name: uk_documento_integrante_familiar_integrante_familiar_id_tipo_d; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT uk_documento_integrante_familiar_integrante_familiar_id_tipo_d UNIQUE (tipo_documento, integrante_familiar_id);


--
-- TOC entry 2136 (class 2606 OID 228197)
-- Name: uk_familia_nome_nome_mae; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT uk_familia_nome_nome_mae UNIQUE (nome, nome_mae);


--
-- TOC entry 2072 (class 2606 OID 228191)
-- Name: uk_user_email; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT uk_user_email UNIQUE (email);


--
-- TOC entry 2074 (class 2606 OID 227933)
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2159 (class 2606 OID 228208)
-- Name: fk_apoio_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY apoio_audited
    ADD CONSTRAINT fk_apoio_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2195 (class 2606 OID 232440)
-- Name: fk_casa_lar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY casa_lar_audited
    ADD CONSTRAINT fk_casa_lar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2160 (class 2606 OID 228213)
-- Name: fk_cidade_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY cidade_audited
    ADD CONSTRAINT fk_cidade_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2161 (class 2606 OID 228218)
-- Name: fk_crianca_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY crianca_audited
    ADD CONSTRAINT fk_crianca_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2162 (class 2606 OID 228223)
-- Name: fk_documento_crianca_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY documento_crianca_audited
    ADD CONSTRAINT fk_documento_crianca_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2163 (class 2606 OID 228228)
-- Name: fk_documento_integrante_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar_audited
    ADD CONSTRAINT fk_documento_integrante_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2164 (class 2606 OID 228233)
-- Name: fk_encaminhamento_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY encaminhamento_audited
    ADD CONSTRAINT fk_encaminhamento_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2165 (class 2606 OID 228238)
-- Name: fk_endereco_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY endereco_audited
    ADD CONSTRAINT fk_endereco_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2166 (class 2606 OID 228243)
-- Name: fk_estado_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY estado_audited
    ADD CONSTRAINT fk_estado_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2167 (class 2606 OID 228248)
-- Name: fk_familia_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY familia_audited
    ADD CONSTRAINT fk_familia_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2168 (class 2606 OID 228253)
-- Name: fk_integrante_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar_audited
    ADD CONSTRAINT fk_integrante_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2169 (class 2606 OID 228258)
-- Name: fk_pais_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY pais_audited
    ADD CONSTRAINT fk_pais_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2170 (class 2606 OID 228263)
-- Name: fk_parecer_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY parecer_audited
    ADD CONSTRAINT fk_parecer_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2171 (class 2606 OID 228268)
-- Name: fk_parente_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY parente_audited
    ADD CONSTRAINT fk_parente_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2172 (class 2606 OID 228273)
-- Name: fk_plano_atendimento_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY plano_atendimento_familiar_audited
    ADD CONSTRAINT fk_plano_atendimento_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2173 (class 2606 OID 228278)
-- Name: fk_rede_apoio_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY rede_apoio_audited
    ADD CONSTRAINT fk_rede_apoio_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2174 (class 2606 OID 228283)
-- Name: fk_responsavel_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY responsavel_audited
    ADD CONSTRAINT fk_responsavel_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2175 (class 2606 OID 228288)
-- Name: fk_user_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY user_audited
    ADD CONSTRAINT fk_user_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


SET search_path = public, pg_catalog;

--
-- TOC entry 2158 (class 2606 OID 228203)
-- Name: fk_apoio_rede_apoio_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apoio
    ADD CONSTRAINT fk_apoio_rede_apoio_id FOREIGN KEY (rede_apoio_id) REFERENCES rede_apoio(id);


--
-- TOC entry 2196 (class 2606 OID 232445)
-- Name: fk_casa_lar_cuidadora_apoiadora_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT fk_casa_lar_cuidadora_apoiadora_id FOREIGN KEY (cuidadora_apoiadora_id) REFERENCES responsavel(id);


--
-- TOC entry 2197 (class 2606 OID 232450)
-- Name: fk_casa_lar_cuidadora_residente_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT fk_casa_lar_cuidadora_residente_id FOREIGN KEY (cuidadora_residente_id) REFERENCES responsavel(id);


--
-- TOC entry 2176 (class 2606 OID 228293)
-- Name: fk_cidade_estado_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT fk_cidade_estado_id FOREIGN KEY (estado_id) REFERENCES estado(id);


--
-- TOC entry 2179 (class 2606 OID 232455)
-- Name: fk_crianca_casa_lar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT fk_crianca_casa_lar_id FOREIGN KEY (casa_lar_id) REFERENCES casa_lar(id);


--
-- TOC entry 2177 (class 2606 OID 228298)
-- Name: fk_crianca_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT fk_crianca_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2178 (class 2606 OID 228303)
-- Name: fk_crianca_familia_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT fk_crianca_familia_id FOREIGN KEY (familia_id) REFERENCES familia(id);


--
-- TOC entry 2180 (class 2606 OID 228308)
-- Name: fk_documento_crianca_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT fk_documento_crianca_crianca_id FOREIGN KEY (crianca_id) REFERENCES crianca(id);


--
-- TOC entry 2181 (class 2606 OID 228313)
-- Name: fk_encaminhamento_plano_atendimento_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_plano_atendimento_id FOREIGN KEY (plano_atendimento_id) REFERENCES plano_atendimento_familiar(id);


--
-- TOC entry 2182 (class 2606 OID 228318)
-- Name: fk_encaminhamento_responsavel_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);


--
-- TOC entry 2183 (class 2606 OID 228323)
-- Name: fk_encaminhamento_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_user_id FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- TOC entry 2184 (class 2606 OID 228328)
-- Name: fk_endereco_cidade_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT fk_endereco_cidade_id FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- TOC entry 2185 (class 2606 OID 228333)
-- Name: fk_estado_pais_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT fk_estado_pais_id FOREIGN KEY (pais_id) REFERENCES pais(id);


--
-- TOC entry 2186 (class 2606 OID 228338)
-- Name: fk_familia_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT fk_familia_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2187 (class 2606 OID 228343)
-- Name: fk_integrante_familiar_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT fk_integrante_familiar_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2188 (class 2606 OID 228348)
-- Name: fk_integrante_familiar_familia_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT fk_integrante_familiar_familia_id FOREIGN KEY (familia_id) REFERENCES familia(id);


--
-- TOC entry 2189 (class 2606 OID 228353)
-- Name: fk_parecer_plano_atendimento_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT fk_parecer_plano_atendimento_id FOREIGN KEY (plano_atendimento_id) REFERENCES plano_atendimento_familiar(id);


--
-- TOC entry 2190 (class 2606 OID 228358)
-- Name: fk_parecer_usuario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT fk_parecer_usuario_id FOREIGN KEY (usuario_id) REFERENCES "user"(id);


--
-- TOC entry 2191 (class 2606 OID 228363)
-- Name: fk_parente_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT fk_parente_crianca_id FOREIGN KEY (crianca_id) REFERENCES crianca(id);


--
-- TOC entry 2192 (class 2606 OID 228368)
-- Name: fk_plano_atendimento_familiar_familia_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY plano_atendimento_familiar
    ADD CONSTRAINT fk_plano_atendimento_familiar_familia_id FOREIGN KEY (familia_id) REFERENCES familia(id);


--
-- TOC entry 2193 (class 2606 OID 228373)
-- Name: fk_rede_apoio_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT fk_rede_apoio_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2194 (class 2606 OID 228378)
-- Name: fk_rede_apoio_responsavel_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT fk_rede_apoio_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);


--
-- TOC entry 2157 (class 2606 OID 228198)
-- Name: fk_user_rede_apoio_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT fk_user_rede_apoio_id FOREIGN KEY (rede_apoio_id) REFERENCES rede_apoio(id);


--
-- TOC entry 2311 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-09-04 20:54:17 BRT

--
-- PostgreSQL database dump complete
--
  

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
  
  INSERT INTO public.endereco values (1, now(), now(), 'Bairro qualquer', 'complemento', 23, 'rua', 1);
  
  INSERT INTO public.familia values (1, now(), now(), true, 'fera', 'Silva', 'Maria', 10, 5, 'boa', '9999-9999', 0, 0, 1);
 
  INSERT INTO public.integrante_familiar values (1, now(), now(), true, now(), 'Pessoa', 0, 'Joao', 'Nada', 1000, 0, '123', 1, 1);
  
  INSERT INTO "public"."user"(
            id, created, updated, email, enabled, name, password, role)
    VALUES (1, NOW(), null, 'admin@admin.com', TRUE, 'Administrador de Sistemas', 'd1bd2f08fead38a982aed9d4ca060152400b1b8f', 0);
    
    insert into public.responsavel values(1, now(), now(), '1234565789', 'responsavel@email.com', 'nome', '9999-9999');
    
    insert into public.rede_apoio values(1, now(), now(), 'CREA', '123133', 1, 1)