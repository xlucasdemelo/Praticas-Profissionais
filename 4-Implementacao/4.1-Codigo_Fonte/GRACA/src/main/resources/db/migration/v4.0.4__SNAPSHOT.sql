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


ALTER SCHEMA auditing OWNER TO postgres;

--
-- TOC entry 2 (class 3079 OID 11789)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2663 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 1 (class 3079 OID 231714)
-- Name: unaccent; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS unaccent WITH SCHEMA pg_catalog;


--
-- TOC entry 2664 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION unaccent; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION unaccent IS 'text search dictionary that removes accents';


SET search_path = auditing, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 179 (class 1259 OID 644952)
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
-- TOC entry 180 (class 1259 OID 644957)
-- Name: aquisicao_produto_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE aquisicao_produto_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    condicao_pagamento integer,
    dia_vencimento integer,
    forma_pagamento integer,
    porcentagem_diferenca real,
    status integer,
    vezes_pagamento integer,
    fornecedor_id bigint
);


ALTER TABLE auditing.aquisicao_produto_audited OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 644962)
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
-- TOC entry 182 (class 1259 OID 644967)
-- Name: categoria_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE categoria_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    nome character varying(255)
);


ALTER TABLE auditing.categoria_audited OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 644972)
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
-- TOC entry 184 (class 1259 OID 644977)
-- Name: conta_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE conta_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    descricao character varying(255),
    enabled boolean,
    nome character varying(255),
    saldo numeric(19,2)
);


ALTER TABLE auditing.conta_audited OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 644985)
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
-- TOC entry 186 (class 1259 OID 644993)
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
-- TOC entry 187 (class 1259 OID 644998)
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
-- TOC entry 188 (class 1259 OID 645003)
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
-- TOC entry 189 (class 1259 OID 645011)
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
-- TOC entry 190 (class 1259 OID 645019)
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
-- TOC entry 191 (class 1259 OID 645024)
-- Name: evento_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE evento_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    data timestamp without time zone,
    descricao character varying(255),
    enabled boolean,
    hora_fim integer,
    hora_inicio integer,
    local character varying(255),
    nome character varying(255)
);


ALTER TABLE auditing.evento_audited OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 645032)
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
-- TOC entry 193 (class 1259 OID 645040)
-- Name: fornecedor_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE fornecedor_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    ativo boolean,
    cnpj character varying(255),
    razao_social character varying(255),
    telefone character varying(255),
    responsavel_id bigint
);


ALTER TABLE auditing.fornecedor_audited OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 645048)
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
-- TOC entry 195 (class 1259 OID 645056)
-- Name: marca_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE marca_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    nome character varying(255)
);


ALTER TABLE auditing.marca_audited OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 645061)
-- Name: modelo_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE modelo_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    nome character varying(255),
    marca_id bigint
);


ALTER TABLE auditing.modelo_audited OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 645066)
-- Name: movimentacao_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE movimentacao_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    data_efetivada timestamp without time zone,
    data_emissao timestamp without time zone,
    data_pagamento timestamp without time zone,
    descricao character varying(255),
    porcentagem_diferenca real,
    status integer,
    tipo_movimentacao integer,
    valor_efetivado numeric(19,2),
    valor_emissao numeric(19,2),
    aquisicao_produto_id bigint,
    conta_destino_id bigint,
    conta_origem_id bigint,
    natureza_gastos_id bigint
);


ALTER TABLE auditing.movimentacao_audited OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 645071)
-- Name: natureza_gastos_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE natureza_gastos_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    descricao character varying(255),
    enabled boolean,
    nome character varying(255)
);


ALTER TABLE auditing.natureza_gastos_audited OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 645079)
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
-- TOC entry 200 (class 1259 OID 645084)
-- Name: papel_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE papel_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    descricao character varying(255),
    evento_id bigint,
    usuario_id bigint
);


ALTER TABLE auditing.papel_audited OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 645089)
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
-- TOC entry 202 (class 1259 OID 645094)
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
-- TOC entry 203 (class 1259 OID 645099)
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
-- TOC entry 204 (class 1259 OID 645104)
-- Name: produto_adquirido_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE produto_adquirido_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    quantidade integer,
    valor numeric(19,2),
    aquisicao_produto_id bigint,
    produto_id bigint
);


ALTER TABLE auditing.produto_adquirido_audited OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 645109)
-- Name: produto_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE produto_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    ativo boolean,
    nome character varying(255),
    quantidade integer,
    categoria_id bigint,
    marca_id bigint,
    modelo_id bigint
);


ALTER TABLE auditing.produto_audited OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 645114)
-- Name: produto_repassado_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE produto_repassado_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    quantidade integer,
    produto_id bigint,
    repasse_id bigint
);


ALTER TABLE auditing.produto_repassado_audited OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 645119)
-- Name: questao_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE questao_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    descricao character varying(255),
    tipo_questao integer,
    versao_questionario_id bigint
);


ALTER TABLE auditing.questao_audited OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 645124)
-- Name: questionario_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE questionario_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    descricao character varying(255),
    enabled boolean,
    nome character varying(255),
    usuario_criador_id bigint
);


ALTER TABLE auditing.questionario_audited OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 645132)
-- Name: questionario_resposta_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE questionario_resposta_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    data_finalizacao timestamp without time zone,
    status integer,
    usuario_id bigint,
    versao_id bigint
);


ALTER TABLE auditing.questionario_resposta_audited OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 645137)
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
-- TOC entry 211 (class 1259 OID 645145)
-- Name: repasse_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE repasse_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    status integer,
    casa_lar_id bigint
);


ALTER TABLE auditing.repasse_audited OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 645150)
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
-- TOC entry 213 (class 1259 OID 645158)
-- Name: resposta_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE resposta_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    resposta_boolean boolean,
    resposta_texto character varying(255),
    questao_id bigint,
    questionario_resposta_id bigint
);


ALTER TABLE auditing.resposta_audited OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 645165)
-- Name: revision; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE revision (
    id bigint NOT NULL,
    "timestamp" bigint NOT NULL,
    user_id bigint
);


ALTER TABLE auditing.revision OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 645163)
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
-- TOC entry 2665 (class 0 OID 0)
-- Dependencies: 214
-- Name: revision_id_seq; Type: SEQUENCE OWNED BY; Schema: auditing; Owner: postgres
--

ALTER SEQUENCE revision_id_seq OWNED BY revision.id;


--
-- TOC entry 216 (class 1259 OID 645171)
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

--
-- TOC entry 217 (class 1259 OID 645176)
-- Name: versao_questionario_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE versao_questionario_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    data_avaliacao timestamp without time zone,
    numero_versao integer,
    status integer,
    questionario_id bigint,
    usuario_avaliador_id bigint
);


ALTER TABLE auditing.versao_questionario_audited OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- TOC entry 176 (class 1259 OID 644938)
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
-- TOC entry 175 (class 1259 OID 644936)
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
-- TOC entry 2666 (class 0 OID 0)
-- Dependencies: 175
-- Name: apoio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE apoio_id_seq OWNED BY apoio.id;


--
-- TOC entry 178 (class 1259 OID 644946)
-- Name: aquisicao_produto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE aquisicao_produto (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    condicao_pagamento integer NOT NULL,
    dia_vencimento integer,
    forma_pagamento integer NOT NULL,
    porcentagem_diferenca real,
    status integer NOT NULL,
    vezes_pagamento integer,
    fornecedor_id bigint
);


ALTER TABLE public.aquisicao_produto OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 644944)
-- Name: aquisicao_produto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aquisicao_produto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.aquisicao_produto_id_seq OWNER TO postgres;

--
-- TOC entry 2667 (class 0 OID 0)
-- Dependencies: 177
-- Name: aquisicao_produto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE aquisicao_produto_id_seq OWNED BY aquisicao_produto.id;


--
-- TOC entry 219 (class 1259 OID 645183)
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
-- TOC entry 218 (class 1259 OID 645181)
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
-- TOC entry 2668 (class 0 OID 0)
-- Dependencies: 218
-- Name: casa_lar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE casa_lar_id_seq OWNED BY casa_lar.id;


--
-- TOC entry 221 (class 1259 OID 645191)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE categoria (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 645189)
-- Name: categoria_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE categoria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_id_seq OWNER TO postgres;

--
-- TOC entry 2669 (class 0 OID 0)
-- Dependencies: 220
-- Name: categoria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE categoria_id_seq OWNED BY categoria.id;


--
-- TOC entry 223 (class 1259 OID 645199)
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
-- TOC entry 222 (class 1259 OID 645197)
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
-- TOC entry 2670 (class 0 OID 0)
-- Dependencies: 222
-- Name: cidade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cidade_id_seq OWNED BY cidade.id;


--
-- TOC entry 225 (class 1259 OID 645207)
-- Name: conta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conta (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    descricao character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    nome character varying(255) NOT NULL,
    saldo numeric(19,2) NOT NULL
);


ALTER TABLE public.conta OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 645205)
-- Name: conta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE conta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.conta_id_seq OWNER TO postgres;

--
-- TOC entry 2671 (class 0 OID 0)
-- Dependencies: 224
-- Name: conta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE conta_id_seq OWNED BY conta.id;


--
-- TOC entry 226 (class 1259 OID 645216)
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
-- TOC entry 228 (class 1259 OID 645226)
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
-- TOC entry 227 (class 1259 OID 645224)
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
-- TOC entry 2672 (class 0 OID 0)
-- Dependencies: 227
-- Name: documento_crianca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE documento_crianca_id_seq OWNED BY documento_crianca.id;


--
-- TOC entry 230 (class 1259 OID 645234)
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
-- TOC entry 229 (class 1259 OID 645232)
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
-- TOC entry 2673 (class 0 OID 0)
-- Dependencies: 229
-- Name: documento_integrante_familiar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE documento_integrante_familiar_id_seq OWNED BY documento_integrante_familiar.id;


--
-- TOC entry 232 (class 1259 OID 645242)
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
-- TOC entry 231 (class 1259 OID 645240)
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
-- TOC entry 2674 (class 0 OID 0)
-- Dependencies: 231
-- Name: encaminhamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE encaminhamento_id_seq OWNED BY encaminhamento.id;


--
-- TOC entry 234 (class 1259 OID 645254)
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
-- TOC entry 233 (class 1259 OID 645252)
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
-- TOC entry 2675 (class 0 OID 0)
-- Dependencies: 233
-- Name: endereco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE endereco_id_seq OWNED BY endereco.id;


--
-- TOC entry 236 (class 1259 OID 645265)
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
-- TOC entry 235 (class 1259 OID 645263)
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
-- TOC entry 2676 (class 0 OID 0)
-- Dependencies: 235
-- Name: estado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE estado_id_seq OWNED BY estado.id;


--
-- TOC entry 238 (class 1259 OID 645273)
-- Name: evento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE evento (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    data timestamp without time zone NOT NULL,
    descricao character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    hora_fim integer NOT NULL,
    hora_inicio integer NOT NULL,
    local character varying(255) NOT NULL,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.evento OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 645271)
-- Name: evento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE evento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.evento_id_seq OWNER TO postgres;

--
-- TOC entry 2677 (class 0 OID 0)
-- Dependencies: 237
-- Name: evento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE evento_id_seq OWNED BY evento.id;


--
-- TOC entry 240 (class 1259 OID 645284)
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
-- TOC entry 239 (class 1259 OID 645282)
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
-- TOC entry 2678 (class 0 OID 0)
-- Dependencies: 239
-- Name: familia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE familia_id_seq OWNED BY familia.id;


--
-- TOC entry 242 (class 1259 OID 645295)
-- Name: fornecedor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fornecedor (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    ativo boolean NOT NULL,
    cnpj character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL,
    telefone character varying(255),
    responsavel_id bigint NOT NULL
);


ALTER TABLE public.fornecedor OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 645293)
-- Name: fornecedor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fornecedor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fornecedor_id_seq OWNER TO postgres;

--
-- TOC entry 2679 (class 0 OID 0)
-- Dependencies: 241
-- Name: fornecedor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fornecedor_id_seq OWNED BY fornecedor.id;


--
-- TOC entry 284 (class 1259 OID 645938)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 645306)
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
-- TOC entry 243 (class 1259 OID 645304)
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
-- TOC entry 2680 (class 0 OID 0)
-- Dependencies: 243
-- Name: integrante_familiar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE integrante_familiar_id_seq OWNED BY integrante_familiar.id;


--
-- TOC entry 246 (class 1259 OID 645317)
-- Name: marca; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE marca (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.marca OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 645315)
-- Name: marca_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE marca_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.marca_id_seq OWNER TO postgres;

--
-- TOC entry 2681 (class 0 OID 0)
-- Dependencies: 245
-- Name: marca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE marca_id_seq OWNED BY marca.id;


--
-- TOC entry 248 (class 1259 OID 645325)
-- Name: modelo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE modelo (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    nome character varying(255) NOT NULL,
    marca_id bigint
);


ALTER TABLE public.modelo OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 645323)
-- Name: modelo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE modelo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.modelo_id_seq OWNER TO postgres;

--
-- TOC entry 2682 (class 0 OID 0)
-- Dependencies: 247
-- Name: modelo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE modelo_id_seq OWNED BY modelo.id;


--
-- TOC entry 250 (class 1259 OID 645333)
-- Name: movimentacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE movimentacao (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    data_efetivada timestamp without time zone,
    data_emissao timestamp without time zone NOT NULL,
    data_pagamento timestamp without time zone,
    descricao character varying(255),
    porcentagem_diferenca real,
    status integer NOT NULL,
    tipo_movimentacao integer NOT NULL,
    valor_efetivado numeric(19,2),
    valor_emissao numeric(19,2) NOT NULL,
    aquisicao_produto_id bigint,
    conta_destino_id bigint,
    conta_origem_id bigint,
    natureza_gastos_id bigint NOT NULL
);


ALTER TABLE public.movimentacao OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 645331)
-- Name: movimentacao_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE movimentacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movimentacao_id_seq OWNER TO postgres;

--
-- TOC entry 2683 (class 0 OID 0)
-- Dependencies: 249
-- Name: movimentacao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE movimentacao_id_seq OWNED BY movimentacao.id;


--
-- TOC entry 252 (class 1259 OID 645341)
-- Name: natureza_gastos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE natureza_gastos (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    descricao character varying(255),
    enabled boolean NOT NULL,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.natureza_gastos OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 645339)
-- Name: natureza_gastos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE natureza_gastos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.natureza_gastos_id_seq OWNER TO postgres;

--
-- TOC entry 2684 (class 0 OID 0)
-- Dependencies: 251
-- Name: natureza_gastos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE natureza_gastos_id_seq OWNED BY natureza_gastos.id;


--
-- TOC entry 254 (class 1259 OID 645352)
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
-- TOC entry 253 (class 1259 OID 645350)
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
-- TOC entry 2685 (class 0 OID 0)
-- Dependencies: 253
-- Name: pais_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pais_id_seq OWNED BY pais.id;


--
-- TOC entry 256 (class 1259 OID 645360)
-- Name: papel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE papel (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    descricao character varying(255),
    evento_id bigint,
    usuario_id bigint
);


ALTER TABLE public.papel OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 645358)
-- Name: papel_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE papel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.papel_id_seq OWNER TO postgres;

--
-- TOC entry 2686 (class 0 OID 0)
-- Dependencies: 255
-- Name: papel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE papel_id_seq OWNED BY papel.id;


--
-- TOC entry 258 (class 1259 OID 645368)
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
-- TOC entry 257 (class 1259 OID 645366)
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
-- TOC entry 2687 (class 0 OID 0)
-- Dependencies: 257
-- Name: parecer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE parecer_id_seq OWNED BY parecer.id;


--
-- TOC entry 260 (class 1259 OID 645376)
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
-- TOC entry 259 (class 1259 OID 645374)
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
-- TOC entry 2688 (class 0 OID 0)
-- Dependencies: 259
-- Name: parente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE parente_id_seq OWNED BY parente.id;


--
-- TOC entry 261 (class 1259 OID 645382)
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
-- TOC entry 263 (class 1259 OID 645390)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE produto (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    ativo boolean NOT NULL,
    nome character varying(255) NOT NULL,
    quantidade integer NOT NULL,
    categoria_id bigint NOT NULL,
    marca_id bigint NOT NULL,
    modelo_id bigint NOT NULL
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 645398)
-- Name: produto_adquirido; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE produto_adquirido (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    quantidade integer NOT NULL,
    valor numeric(19,2) NOT NULL,
    aquisicao_produto_id bigint,
    produto_id bigint
);


ALTER TABLE public.produto_adquirido OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 645396)
-- Name: produto_adquirido_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE produto_adquirido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_adquirido_id_seq OWNER TO postgres;

--
-- TOC entry 2689 (class 0 OID 0)
-- Dependencies: 264
-- Name: produto_adquirido_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE produto_adquirido_id_seq OWNED BY produto_adquirido.id;


--
-- TOC entry 262 (class 1259 OID 645388)
-- Name: produto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE produto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_id_seq OWNER TO postgres;

--
-- TOC entry 2690 (class 0 OID 0)
-- Dependencies: 262
-- Name: produto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE produto_id_seq OWNED BY produto.id;


--
-- TOC entry 267 (class 1259 OID 645406)
-- Name: produto_repassado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE produto_repassado (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    quantidade integer NOT NULL,
    produto_id bigint,
    repasse_id bigint
);


ALTER TABLE public.produto_repassado OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 645404)
-- Name: produto_repassado_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE produto_repassado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_repassado_id_seq OWNER TO postgres;

--
-- TOC entry 2691 (class 0 OID 0)
-- Dependencies: 266
-- Name: produto_repassado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE produto_repassado_id_seq OWNED BY produto_repassado.id;


--
-- TOC entry 269 (class 1259 OID 645414)
-- Name: questao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE questao (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    descricao character varying(255) NOT NULL,
    tipo_questao integer NOT NULL,
    versao_questionario_id bigint
);


ALTER TABLE public.questao OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 645412)
-- Name: questao_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE questao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.questao_id_seq OWNER TO postgres;

--
-- TOC entry 2692 (class 0 OID 0)
-- Dependencies: 268
-- Name: questao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE questao_id_seq OWNED BY questao.id;


--
-- TOC entry 271 (class 1259 OID 645422)
-- Name: questionario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE questionario (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    descricao character varying(255),
    enabled boolean NOT NULL,
    nome character varying(255) NOT NULL,
    usuario_criador_id bigint
);


ALTER TABLE public.questionario OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 645420)
-- Name: questionario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE questionario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.questionario_id_seq OWNER TO postgres;

--
-- TOC entry 2693 (class 0 OID 0)
-- Dependencies: 270
-- Name: questionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE questionario_id_seq OWNED BY questionario.id;


--
-- TOC entry 273 (class 1259 OID 645433)
-- Name: questionario_resposta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE questionario_resposta (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    data_finalizacao timestamp without time zone,
    status integer NOT NULL,
    usuario_id bigint,
    versao_id bigint
);


ALTER TABLE public.questionario_resposta OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 645431)
-- Name: questionario_resposta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE questionario_resposta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.questionario_resposta_id_seq OWNER TO postgres;

--
-- TOC entry 2694 (class 0 OID 0)
-- Dependencies: 272
-- Name: questionario_resposta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE questionario_resposta_id_seq OWNED BY questionario_resposta.id;


--
-- TOC entry 275 (class 1259 OID 645441)
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
-- TOC entry 274 (class 1259 OID 645439)
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
-- TOC entry 2695 (class 0 OID 0)
-- Dependencies: 274
-- Name: rede_apoio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rede_apoio_id_seq OWNED BY rede_apoio.id;


--
-- TOC entry 277 (class 1259 OID 645452)
-- Name: repasse; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE repasse (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    status integer NOT NULL,
    casa_lar_id bigint
);


ALTER TABLE public.repasse OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 645450)
-- Name: repasse_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE repasse_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repasse_id_seq OWNER TO postgres;

--
-- TOC entry 2696 (class 0 OID 0)
-- Dependencies: 276
-- Name: repasse_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE repasse_id_seq OWNED BY repasse.id;


--
-- TOC entry 279 (class 1259 OID 645460)
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
-- TOC entry 278 (class 1259 OID 645458)
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
-- TOC entry 2697 (class 0 OID 0)
-- Dependencies: 278
-- Name: responsavel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE responsavel_id_seq OWNED BY responsavel.id;


--
-- TOC entry 281 (class 1259 OID 645471)
-- Name: resposta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resposta (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    resposta_boolean boolean,
    resposta_texto character varying(255),
    questao_id bigint NOT NULL,
    questionario_resposta_id bigint NOT NULL
);


ALTER TABLE public.resposta OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 645469)
-- Name: resposta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE resposta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.resposta_id_seq OWNER TO postgres;

--
-- TOC entry 2698 (class 0 OID 0)
-- Dependencies: 280
-- Name: resposta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE resposta_id_seq OWNED BY resposta.id;


--
-- TOC entry 174 (class 1259 OID 644929)
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
-- TOC entry 173 (class 1259 OID 644927)
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
-- TOC entry 2699 (class 0 OID 0)
-- Dependencies: 173
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- TOC entry 283 (class 1259 OID 645479)
-- Name: versao_questionario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE versao_questionario (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    data_avaliacao timestamp without time zone,
    numero_versao integer NOT NULL,
    status integer NOT NULL,
    questionario_id bigint NOT NULL,
    usuario_avaliador_id bigint
);


ALTER TABLE public.versao_questionario OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 645477)
-- Name: versao_questionario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE versao_questionario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.versao_questionario_id_seq OWNER TO postgres;

--
-- TOC entry 2700 (class 0 OID 0)
-- Dependencies: 282
-- Name: versao_questionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE versao_questionario_id_seq OWNED BY versao_questionario.id;


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2261 (class 2604 OID 645168)
-- Name: id; Type: DEFAULT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY revision ALTER COLUMN id SET DEFAULT nextval('revision_id_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- TOC entry 2259 (class 2604 OID 644941)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apoio ALTER COLUMN id SET DEFAULT nextval('apoio_id_seq'::regclass);


--
-- TOC entry 2260 (class 2604 OID 644949)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aquisicao_produto ALTER COLUMN id SET DEFAULT nextval('aquisicao_produto_id_seq'::regclass);


--
-- TOC entry 2262 (class 2604 OID 645186)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar ALTER COLUMN id SET DEFAULT nextval('casa_lar_id_seq'::regclass);


--
-- TOC entry 2263 (class 2604 OID 645194)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categoria ALTER COLUMN id SET DEFAULT nextval('categoria_id_seq'::regclass);


--
-- TOC entry 2264 (class 2604 OID 645202)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade ALTER COLUMN id SET DEFAULT nextval('cidade_id_seq'::regclass);


--
-- TOC entry 2265 (class 2604 OID 645210)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conta ALTER COLUMN id SET DEFAULT nextval('conta_id_seq'::regclass);


--
-- TOC entry 2266 (class 2604 OID 645229)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_crianca ALTER COLUMN id SET DEFAULT nextval('documento_crianca_id_seq'::regclass);


--
-- TOC entry 2267 (class 2604 OID 645237)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar ALTER COLUMN id SET DEFAULT nextval('documento_integrante_familiar_id_seq'::regclass);


--
-- TOC entry 2268 (class 2604 OID 645245)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento ALTER COLUMN id SET DEFAULT nextval('encaminhamento_id_seq'::regclass);


--
-- TOC entry 2270 (class 2604 OID 645257)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco ALTER COLUMN id SET DEFAULT nextval('endereco_id_seq'::regclass);


--
-- TOC entry 2271 (class 2604 OID 645268)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado ALTER COLUMN id SET DEFAULT nextval('estado_id_seq'::regclass);


--
-- TOC entry 2272 (class 2604 OID 645276)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY evento ALTER COLUMN id SET DEFAULT nextval('evento_id_seq'::regclass);


--
-- TOC entry 2273 (class 2604 OID 645287)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familia ALTER COLUMN id SET DEFAULT nextval('familia_id_seq'::regclass);


--
-- TOC entry 2274 (class 2604 OID 645298)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fornecedor ALTER COLUMN id SET DEFAULT nextval('fornecedor_id_seq'::regclass);


--
-- TOC entry 2275 (class 2604 OID 645309)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar ALTER COLUMN id SET DEFAULT nextval('integrante_familiar_id_seq'::regclass);


--
-- TOC entry 2276 (class 2604 OID 645320)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY marca ALTER COLUMN id SET DEFAULT nextval('marca_id_seq'::regclass);


--
-- TOC entry 2277 (class 2604 OID 645328)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modelo ALTER COLUMN id SET DEFAULT nextval('modelo_id_seq'::regclass);


--
-- TOC entry 2278 (class 2604 OID 645336)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY movimentacao ALTER COLUMN id SET DEFAULT nextval('movimentacao_id_seq'::regclass);


--
-- TOC entry 2279 (class 2604 OID 645344)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY natureza_gastos ALTER COLUMN id SET DEFAULT nextval('natureza_gastos_id_seq'::regclass);


--
-- TOC entry 2280 (class 2604 OID 645355)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pais ALTER COLUMN id SET DEFAULT nextval('pais_id_seq'::regclass);


--
-- TOC entry 2281 (class 2604 OID 645363)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY papel ALTER COLUMN id SET DEFAULT nextval('papel_id_seq'::regclass);


--
-- TOC entry 2282 (class 2604 OID 645371)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer ALTER COLUMN id SET DEFAULT nextval('parecer_id_seq'::regclass);


--
-- TOC entry 2283 (class 2604 OID 645379)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente ALTER COLUMN id SET DEFAULT nextval('parente_id_seq'::regclass);


--
-- TOC entry 2285 (class 2604 OID 645393)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto ALTER COLUMN id SET DEFAULT nextval('produto_id_seq'::regclass);


--
-- TOC entry 2286 (class 2604 OID 645401)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto_adquirido ALTER COLUMN id SET DEFAULT nextval('produto_adquirido_id_seq'::regclass);


--
-- TOC entry 2287 (class 2604 OID 645409)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto_repassado ALTER COLUMN id SET DEFAULT nextval('produto_repassado_id_seq'::regclass);


--
-- TOC entry 2288 (class 2604 OID 645417)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questao ALTER COLUMN id SET DEFAULT nextval('questao_id_seq'::regclass);


--
-- TOC entry 2289 (class 2604 OID 645425)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario ALTER COLUMN id SET DEFAULT nextval('questionario_id_seq'::regclass);


--
-- TOC entry 2290 (class 2604 OID 645436)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario_resposta ALTER COLUMN id SET DEFAULT nextval('questionario_resposta_id_seq'::regclass);


--
-- TOC entry 2291 (class 2604 OID 645444)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio ALTER COLUMN id SET DEFAULT nextval('rede_apoio_id_seq'::regclass);


--
-- TOC entry 2292 (class 2604 OID 645455)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY repasse ALTER COLUMN id SET DEFAULT nextval('repasse_id_seq'::regclass);


--
-- TOC entry 2293 (class 2604 OID 645463)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY responsavel ALTER COLUMN id SET DEFAULT nextval('responsavel_id_seq'::regclass);


--
-- TOC entry 2294 (class 2604 OID 645474)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resposta ALTER COLUMN id SET DEFAULT nextval('resposta_id_seq'::regclass);


--
-- TOC entry 2257 (class 2604 OID 644932)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- TOC entry 2295 (class 2604 OID 645482)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY versao_questionario ALTER COLUMN id SET DEFAULT nextval('versao_questionario_id_seq'::regclass);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2305 (class 2606 OID 644956)
-- Name: apoio_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apoio_audited
    ADD CONSTRAINT apoio_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2307 (class 2606 OID 644961)
-- Name: aquisicao_produto_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY aquisicao_produto_audited
    ADD CONSTRAINT aquisicao_produto_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2309 (class 2606 OID 644966)
-- Name: casa_lar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY casa_lar_audited
    ADD CONSTRAINT casa_lar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2311 (class 2606 OID 644971)
-- Name: categoria_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categoria_audited
    ADD CONSTRAINT categoria_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2313 (class 2606 OID 644976)
-- Name: cidade_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade_audited
    ADD CONSTRAINT cidade_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2315 (class 2606 OID 644984)
-- Name: conta_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conta_audited
    ADD CONSTRAINT conta_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2317 (class 2606 OID 644992)
-- Name: crianca_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca_audited
    ADD CONSTRAINT crianca_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2319 (class 2606 OID 644997)
-- Name: documento_crianca_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca_audited
    ADD CONSTRAINT documento_crianca_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2321 (class 2606 OID 645002)
-- Name: documento_integrante_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar_audited
    ADD CONSTRAINT documento_integrante_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2323 (class 2606 OID 645010)
-- Name: encaminhamento_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY encaminhamento_audited
    ADD CONSTRAINT encaminhamento_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2325 (class 2606 OID 645018)
-- Name: endereco_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco_audited
    ADD CONSTRAINT endereco_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2327 (class 2606 OID 645023)
-- Name: estado_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado_audited
    ADD CONSTRAINT estado_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2329 (class 2606 OID 645031)
-- Name: evento_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY evento_audited
    ADD CONSTRAINT evento_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2331 (class 2606 OID 645039)
-- Name: familia_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia_audited
    ADD CONSTRAINT familia_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2333 (class 2606 OID 645047)
-- Name: fornecedor_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fornecedor_audited
    ADD CONSTRAINT fornecedor_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2335 (class 2606 OID 645055)
-- Name: integrante_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY integrante_familiar_audited
    ADD CONSTRAINT integrante_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2337 (class 2606 OID 645060)
-- Name: marca_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca_audited
    ADD CONSTRAINT marca_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2339 (class 2606 OID 645065)
-- Name: modelo_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY modelo_audited
    ADD CONSTRAINT modelo_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2341 (class 2606 OID 645070)
-- Name: movimentacao_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY movimentacao_audited
    ADD CONSTRAINT movimentacao_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2343 (class 2606 OID 645078)
-- Name: natureza_gastos_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY natureza_gastos_audited
    ADD CONSTRAINT natureza_gastos_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2345 (class 2606 OID 645083)
-- Name: pais_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais_audited
    ADD CONSTRAINT pais_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2347 (class 2606 OID 645088)
-- Name: papel_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY papel_audited
    ADD CONSTRAINT papel_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2349 (class 2606 OID 645093)
-- Name: parecer_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parecer_audited
    ADD CONSTRAINT parecer_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2351 (class 2606 OID 645098)
-- Name: parente_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parente_audited
    ADD CONSTRAINT parente_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2353 (class 2606 OID 645103)
-- Name: plano_atendimento_familiar_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plano_atendimento_familiar_audited
    ADD CONSTRAINT plano_atendimento_familiar_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2355 (class 2606 OID 645108)
-- Name: produto_adquirido_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY produto_adquirido_audited
    ADD CONSTRAINT produto_adquirido_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2357 (class 2606 OID 645113)
-- Name: produto_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY produto_audited
    ADD CONSTRAINT produto_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2359 (class 2606 OID 645118)
-- Name: produto_repassado_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY produto_repassado_audited
    ADD CONSTRAINT produto_repassado_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2361 (class 2606 OID 645123)
-- Name: questao_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questao_audited
    ADD CONSTRAINT questao_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2363 (class 2606 OID 645131)
-- Name: questionario_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionario_audited
    ADD CONSTRAINT questionario_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2365 (class 2606 OID 645136)
-- Name: questionario_resposta_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionario_resposta_audited
    ADD CONSTRAINT questionario_resposta_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2367 (class 2606 OID 645144)
-- Name: rede_apoio_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rede_apoio_audited
    ADD CONSTRAINT rede_apoio_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2369 (class 2606 OID 645149)
-- Name: repasse_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY repasse_audited
    ADD CONSTRAINT repasse_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2371 (class 2606 OID 645157)
-- Name: responsavel_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY responsavel_audited
    ADD CONSTRAINT responsavel_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2373 (class 2606 OID 645162)
-- Name: resposta_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resposta_audited
    ADD CONSTRAINT resposta_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2375 (class 2606 OID 645170)
-- Name: revision_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY revision
    ADD CONSTRAINT revision_pkey PRIMARY KEY (id);


--
-- TOC entry 2377 (class 2606 OID 645175)
-- Name: user_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_audited
    ADD CONSTRAINT user_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2379 (class 2606 OID 645180)
-- Name: versao_questionario_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY versao_questionario_audited
    ADD CONSTRAINT versao_questionario_audited_pkey PRIMARY KEY (id, revision);


SET search_path = public, pg_catalog;

--
-- TOC entry 2301 (class 2606 OID 644943)
-- Name: apoio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apoio
    ADD CONSTRAINT apoio_pkey PRIMARY KEY (id);


--
-- TOC entry 2303 (class 2606 OID 644951)
-- Name: aquisicao_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY aquisicao_produto
    ADD CONSTRAINT aquisicao_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2381 (class 2606 OID 645188)
-- Name: casa_lar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT casa_lar_pkey PRIMARY KEY (id);


--
-- TOC entry 2383 (class 2606 OID 645196)
-- Name: categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 2387 (class 2606 OID 645204)
-- Name: cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);


--
-- TOC entry 2389 (class 2606 OID 645215)
-- Name: conta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conta
    ADD CONSTRAINT conta_pkey PRIMARY KEY (id);


--
-- TOC entry 2391 (class 2606 OID 645223)
-- Name: crianca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT crianca_pkey PRIMARY KEY (id);


--
-- TOC entry 2395 (class 2606 OID 645231)
-- Name: documento_crianca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT documento_crianca_pkey PRIMARY KEY (id);


--
-- TOC entry 2399 (class 2606 OID 645239)
-- Name: documento_integrante_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT documento_integrante_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2403 (class 2606 OID 645251)
-- Name: encaminhamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT encaminhamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2405 (class 2606 OID 645262)
-- Name: endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 2407 (class 2606 OID 645270)
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id);


--
-- TOC entry 2409 (class 2606 OID 645281)
-- Name: evento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id);


--
-- TOC entry 2411 (class 2606 OID 645292)
-- Name: familia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT familia_pkey PRIMARY KEY (id);


--
-- TOC entry 2415 (class 2606 OID 645303)
-- Name: fornecedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fornecedor
    ADD CONSTRAINT fornecedor_pkey PRIMARY KEY (id);


--
-- TOC entry 2421 (class 2606 OID 645314)
-- Name: integrante_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT integrante_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2423 (class 2606 OID 645322)
-- Name: marca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY marca
    ADD CONSTRAINT marca_pkey PRIMARY KEY (id);


--
-- TOC entry 2425 (class 2606 OID 645330)
-- Name: modelo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY modelo
    ADD CONSTRAINT modelo_pkey PRIMARY KEY (id);


--
-- TOC entry 2429 (class 2606 OID 645338)
-- Name: movimentacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY movimentacao
    ADD CONSTRAINT movimentacao_pkey PRIMARY KEY (id);


--
-- TOC entry 2431 (class 2606 OID 645349)
-- Name: natureza_gastos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY natureza_gastos
    ADD CONSTRAINT natureza_gastos_pkey PRIMARY KEY (id);


--
-- TOC entry 2433 (class 2606 OID 645357)
-- Name: pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT pais_pkey PRIMARY KEY (id);


--
-- TOC entry 2435 (class 2606 OID 645365)
-- Name: papel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY papel
    ADD CONSTRAINT papel_pkey PRIMARY KEY (id);


--
-- TOC entry 2437 (class 2606 OID 645373)
-- Name: parecer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT parecer_pkey PRIMARY KEY (id);


--
-- TOC entry 2439 (class 2606 OID 645381)
-- Name: parente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT parente_pkey PRIMARY KEY (id);


--
-- TOC entry 2441 (class 2606 OID 645387)
-- Name: plano_atendimento_familiar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plano_atendimento_familiar
    ADD CONSTRAINT plano_atendimento_familiar_pkey PRIMARY KEY (id);


--
-- TOC entry 2445 (class 2606 OID 645403)
-- Name: produto_adquirido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY produto_adquirido
    ADD CONSTRAINT produto_adquirido_pkey PRIMARY KEY (id);


--
-- TOC entry 2443 (class 2606 OID 645395)
-- Name: produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- TOC entry 2447 (class 2606 OID 645411)
-- Name: produto_repassado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY produto_repassado
    ADD CONSTRAINT produto_repassado_pkey PRIMARY KEY (id);


--
-- TOC entry 2449 (class 2606 OID 645419)
-- Name: questao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questao
    ADD CONSTRAINT questao_pkey PRIMARY KEY (id);


--
-- TOC entry 2451 (class 2606 OID 645430)
-- Name: questionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionario
    ADD CONSTRAINT questionario_pkey PRIMARY KEY (id);


--
-- TOC entry 2453 (class 2606 OID 645438)
-- Name: questionario_resposta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionario_resposta
    ADD CONSTRAINT questionario_resposta_pkey PRIMARY KEY (id);


--
-- TOC entry 2455 (class 2606 OID 645449)
-- Name: rede_apoio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT rede_apoio_pkey PRIMARY KEY (id);


--
-- TOC entry 2457 (class 2606 OID 645457)
-- Name: repasse_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY repasse
    ADD CONSTRAINT repasse_pkey PRIMARY KEY (id);


--
-- TOC entry 2459 (class 2606 OID 645468)
-- Name: responsavel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY responsavel
    ADD CONSTRAINT responsavel_pkey PRIMARY KEY (id);


--
-- TOC entry 2461 (class 2606 OID 645476)
-- Name: resposta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT resposta_pkey PRIMARY KEY (id);


--
-- TOC entry 2385 (class 2606 OID 645488)
-- Name: uk_categoria_nome; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categoria
    ADD CONSTRAINT uk_categoria_nome UNIQUE (nome);


--
-- TOC entry 2393 (class 2606 OID 645490)
-- Name: uk_crianca_casa_lar_id_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT uk_crianca_casa_lar_id_id UNIQUE (id, casa_lar_id);


--
-- TOC entry 2397 (class 2606 OID 645492)
-- Name: uk_documento_crianca_crianca_id_tipo_documento; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT uk_documento_crianca_crianca_id_tipo_documento UNIQUE (tipo_documento, crianca_id);


--
-- TOC entry 2401 (class 2606 OID 645494)
-- Name: uk_documento_integrante_familiar_integrante_familiar_id_tipo_d; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT uk_documento_integrante_familiar_integrante_familiar_id_tipo_d UNIQUE (tipo_documento, integrante_familiar_id);


--
-- TOC entry 2413 (class 2606 OID 645496)
-- Name: uk_familia_nome_nome_mae; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT uk_familia_nome_nome_mae UNIQUE (nome, nome_mae);


--
-- TOC entry 2417 (class 2606 OID 645498)
-- Name: uk_fornecedor_cnpj; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fornecedor
    ADD CONSTRAINT uk_fornecedor_cnpj UNIQUE (cnpj);


--
-- TOC entry 2419 (class 2606 OID 645500)
-- Name: uk_fornecedor_razao_social; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fornecedor
    ADD CONSTRAINT uk_fornecedor_razao_social UNIQUE (razao_social);


--
-- TOC entry 2427 (class 2606 OID 645502)
-- Name: uk_modelo_nome; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY modelo
    ADD CONSTRAINT uk_modelo_nome UNIQUE (nome);


--
-- TOC entry 2297 (class 2606 OID 645486)
-- Name: uk_user_email; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT uk_user_email UNIQUE (email);


--
-- TOC entry 2299 (class 2606 OID 644935)
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2463 (class 2606 OID 645484)
-- Name: versao_questionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY versao_questionario
    ADD CONSTRAINT versao_questionario_pkey PRIMARY KEY (id);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2467 (class 2606 OID 645518)
-- Name: fk_apoio_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY apoio_audited
    ADD CONSTRAINT fk_apoio_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2468 (class 2606 OID 645523)
-- Name: fk_aquisicao_produto_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY aquisicao_produto_audited
    ADD CONSTRAINT fk_aquisicao_produto_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2469 (class 2606 OID 645528)
-- Name: fk_casa_lar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY casa_lar_audited
    ADD CONSTRAINT fk_casa_lar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2470 (class 2606 OID 645533)
-- Name: fk_categoria_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY categoria_audited
    ADD CONSTRAINT fk_categoria_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2471 (class 2606 OID 645538)
-- Name: fk_cidade_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY cidade_audited
    ADD CONSTRAINT fk_cidade_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2472 (class 2606 OID 645543)
-- Name: fk_conta_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY conta_audited
    ADD CONSTRAINT fk_conta_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2473 (class 2606 OID 645548)
-- Name: fk_crianca_audited_id_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY crianca_audited
    ADD CONSTRAINT fk_crianca_audited_id_revision FOREIGN KEY (id, revision) REFERENCES integrante_familiar_audited(id, revision);


--
-- TOC entry 2474 (class 2606 OID 645553)
-- Name: fk_documento_crianca_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY documento_crianca_audited
    ADD CONSTRAINT fk_documento_crianca_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2475 (class 2606 OID 645558)
-- Name: fk_documento_integrante_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar_audited
    ADD CONSTRAINT fk_documento_integrante_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2476 (class 2606 OID 645563)
-- Name: fk_encaminhamento_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY encaminhamento_audited
    ADD CONSTRAINT fk_encaminhamento_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2477 (class 2606 OID 645568)
-- Name: fk_endereco_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY endereco_audited
    ADD CONSTRAINT fk_endereco_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2478 (class 2606 OID 645573)
-- Name: fk_estado_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY estado_audited
    ADD CONSTRAINT fk_estado_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2479 (class 2606 OID 645578)
-- Name: fk_evento_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY evento_audited
    ADD CONSTRAINT fk_evento_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2480 (class 2606 OID 645583)
-- Name: fk_familia_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY familia_audited
    ADD CONSTRAINT fk_familia_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2481 (class 2606 OID 645588)
-- Name: fk_fornecedor_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY fornecedor_audited
    ADD CONSTRAINT fk_fornecedor_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2482 (class 2606 OID 645593)
-- Name: fk_integrante_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar_audited
    ADD CONSTRAINT fk_integrante_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2483 (class 2606 OID 645598)
-- Name: fk_marca_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY marca_audited
    ADD CONSTRAINT fk_marca_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2484 (class 2606 OID 645603)
-- Name: fk_modelo_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY modelo_audited
    ADD CONSTRAINT fk_modelo_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2485 (class 2606 OID 645608)
-- Name: fk_movimentacao_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY movimentacao_audited
    ADD CONSTRAINT fk_movimentacao_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2486 (class 2606 OID 645613)
-- Name: fk_natureza_gastos_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY natureza_gastos_audited
    ADD CONSTRAINT fk_natureza_gastos_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2487 (class 2606 OID 645618)
-- Name: fk_pais_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY pais_audited
    ADD CONSTRAINT fk_pais_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2488 (class 2606 OID 645623)
-- Name: fk_papel_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY papel_audited
    ADD CONSTRAINT fk_papel_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2489 (class 2606 OID 645628)
-- Name: fk_parecer_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY parecer_audited
    ADD CONSTRAINT fk_parecer_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2490 (class 2606 OID 645633)
-- Name: fk_parente_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY parente_audited
    ADD CONSTRAINT fk_parente_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2491 (class 2606 OID 645638)
-- Name: fk_plano_atendimento_familiar_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY plano_atendimento_familiar_audited
    ADD CONSTRAINT fk_plano_atendimento_familiar_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2492 (class 2606 OID 645643)
-- Name: fk_produto_adquirido_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY produto_adquirido_audited
    ADD CONSTRAINT fk_produto_adquirido_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2493 (class 2606 OID 645648)
-- Name: fk_produto_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY produto_audited
    ADD CONSTRAINT fk_produto_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2494 (class 2606 OID 645653)
-- Name: fk_produto_repassado_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY produto_repassado_audited
    ADD CONSTRAINT fk_produto_repassado_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2495 (class 2606 OID 645658)
-- Name: fk_questao_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY questao_audited
    ADD CONSTRAINT fk_questao_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2496 (class 2606 OID 645663)
-- Name: fk_questionario_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY questionario_audited
    ADD CONSTRAINT fk_questionario_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2497 (class 2606 OID 645668)
-- Name: fk_questionario_resposta_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY questionario_resposta_audited
    ADD CONSTRAINT fk_questionario_resposta_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2498 (class 2606 OID 645673)
-- Name: fk_rede_apoio_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY rede_apoio_audited
    ADD CONSTRAINT fk_rede_apoio_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2499 (class 2606 OID 645678)
-- Name: fk_repasse_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY repasse_audited
    ADD CONSTRAINT fk_repasse_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2500 (class 2606 OID 645683)
-- Name: fk_responsavel_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY responsavel_audited
    ADD CONSTRAINT fk_responsavel_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2501 (class 2606 OID 645688)
-- Name: fk_resposta_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY resposta_audited
    ADD CONSTRAINT fk_resposta_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2502 (class 2606 OID 645693)
-- Name: fk_user_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY user_audited
    ADD CONSTRAINT fk_user_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2503 (class 2606 OID 645698)
-- Name: fk_versao_questionario_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY versao_questionario_audited
    ADD CONSTRAINT fk_versao_questionario_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


SET search_path = public, pg_catalog;

--
-- TOC entry 2465 (class 2606 OID 645508)
-- Name: fk_apoio_rede_apoio_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apoio
    ADD CONSTRAINT fk_apoio_rede_apoio_id FOREIGN KEY (rede_apoio_id) REFERENCES rede_apoio(id);


--
-- TOC entry 2466 (class 2606 OID 645513)
-- Name: fk_aquisicao_produto_fornecedor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aquisicao_produto
    ADD CONSTRAINT fk_aquisicao_produto_fornecedor_id FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id);


--
-- TOC entry 2504 (class 2606 OID 645703)
-- Name: fk_casa_lar_cuidadora_apoiadora_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT fk_casa_lar_cuidadora_apoiadora_id FOREIGN KEY (cuidadora_apoiadora_id) REFERENCES responsavel(id);


--
-- TOC entry 2505 (class 2606 OID 645708)
-- Name: fk_casa_lar_cuidadora_residente_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY casa_lar
    ADD CONSTRAINT fk_casa_lar_cuidadora_residente_id FOREIGN KEY (cuidadora_residente_id) REFERENCES responsavel(id);


--
-- TOC entry 2506 (class 2606 OID 645713)
-- Name: fk_cidade_estado_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT fk_cidade_estado_id FOREIGN KEY (estado_id) REFERENCES estado(id);


--
-- TOC entry 2507 (class 2606 OID 645718)
-- Name: fk_crianca_casa_lar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT fk_crianca_casa_lar_id FOREIGN KEY (casa_lar_id) REFERENCES casa_lar(id);


--
-- TOC entry 2508 (class 2606 OID 645723)
-- Name: fk_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crianca
    ADD CONSTRAINT fk_crianca_id FOREIGN KEY (id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2509 (class 2606 OID 645728)
-- Name: fk_documento_crianca_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_crianca
    ADD CONSTRAINT fk_documento_crianca_crianca_id FOREIGN KEY (crianca_id) REFERENCES crianca(id);


--
-- TOC entry 2510 (class 2606 OID 645733)
-- Name: fk_documento_integrante_familiar_integrante_familiar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento_integrante_familiar
    ADD CONSTRAINT fk_documento_integrante_familiar_integrante_familiar_id FOREIGN KEY (integrante_familiar_id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2511 (class 2606 OID 645738)
-- Name: fk_encaminhamento_integrante_familiar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_integrante_familiar_id FOREIGN KEY (integrante_familiar_id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2512 (class 2606 OID 645743)
-- Name: fk_encaminhamento_plano_atendimento_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_plano_atendimento_id FOREIGN KEY (plano_atendimento_id) REFERENCES plano_atendimento_familiar(id);


--
-- TOC entry 2513 (class 2606 OID 645748)
-- Name: fk_encaminhamento_responsavel_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);


--
-- TOC entry 2514 (class 2606 OID 645753)
-- Name: fk_encaminhamento_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT fk_encaminhamento_user_id FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- TOC entry 2515 (class 2606 OID 645758)
-- Name: fk_endereco_cidade_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT fk_endereco_cidade_id FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- TOC entry 2516 (class 2606 OID 645763)
-- Name: fk_estado_pais_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT fk_estado_pais_id FOREIGN KEY (pais_id) REFERENCES pais(id);


--
-- TOC entry 2517 (class 2606 OID 645768)
-- Name: fk_familia_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familia
    ADD CONSTRAINT fk_familia_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2518 (class 2606 OID 645773)
-- Name: fk_fornecedor_responsavel_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fornecedor
    ADD CONSTRAINT fk_fornecedor_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);


--
-- TOC entry 2519 (class 2606 OID 645778)
-- Name: fk_integrante_familiar_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT fk_integrante_familiar_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2520 (class 2606 OID 645783)
-- Name: fk_integrante_familiar_familia_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY integrante_familiar
    ADD CONSTRAINT fk_integrante_familiar_familia_id FOREIGN KEY (familia_id) REFERENCES familia(id);


--
-- TOC entry 2521 (class 2606 OID 645788)
-- Name: fk_modelo_marca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY modelo
    ADD CONSTRAINT fk_modelo_marca_id FOREIGN KEY (marca_id) REFERENCES marca(id);


--
-- TOC entry 2522 (class 2606 OID 645793)
-- Name: fk_movimentacao_aquisicao_produto_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY movimentacao
    ADD CONSTRAINT fk_movimentacao_aquisicao_produto_id FOREIGN KEY (aquisicao_produto_id) REFERENCES aquisicao_produto(id);


--
-- TOC entry 2523 (class 2606 OID 645798)
-- Name: fk_movimentacao_conta_destino_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY movimentacao
    ADD CONSTRAINT fk_movimentacao_conta_destino_id FOREIGN KEY (conta_destino_id) REFERENCES conta(id);


--
-- TOC entry 2524 (class 2606 OID 645803)
-- Name: fk_movimentacao_conta_origem_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY movimentacao
    ADD CONSTRAINT fk_movimentacao_conta_origem_id FOREIGN KEY (conta_origem_id) REFERENCES conta(id);


--
-- TOC entry 2525 (class 2606 OID 645808)
-- Name: fk_movimentacao_natureza_gastos_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY movimentacao
    ADD CONSTRAINT fk_movimentacao_natureza_gastos_id FOREIGN KEY (natureza_gastos_id) REFERENCES natureza_gastos(id);


--
-- TOC entry 2526 (class 2606 OID 645813)
-- Name: fk_papel_evento_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY papel
    ADD CONSTRAINT fk_papel_evento_id FOREIGN KEY (evento_id) REFERENCES evento(id);


--
-- TOC entry 2527 (class 2606 OID 645818)
-- Name: fk_papel_usuario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY papel
    ADD CONSTRAINT fk_papel_usuario_id FOREIGN KEY (usuario_id) REFERENCES "user"(id);


--
-- TOC entry 2528 (class 2606 OID 645823)
-- Name: fk_parecer_plano_atendimento_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT fk_parecer_plano_atendimento_id FOREIGN KEY (plano_atendimento_id) REFERENCES plano_atendimento_familiar(id);


--
-- TOC entry 2529 (class 2606 OID 645828)
-- Name: fk_parecer_usuario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parecer
    ADD CONSTRAINT fk_parecer_usuario_id FOREIGN KEY (usuario_id) REFERENCES "user"(id);


--
-- TOC entry 2530 (class 2606 OID 645833)
-- Name: fk_parente_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT fk_parente_crianca_id FOREIGN KEY (crianca_id) REFERENCES crianca(id);


--
-- TOC entry 2531 (class 2606 OID 645838)
-- Name: fk_parente_integrante_familiar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parente
    ADD CONSTRAINT fk_parente_integrante_familiar_id FOREIGN KEY (integrante_familiar_id) REFERENCES integrante_familiar(id);


--
-- TOC entry 2532 (class 2606 OID 645843)
-- Name: fk_plano_atendimento_familiar_familia_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY plano_atendimento_familiar
    ADD CONSTRAINT fk_plano_atendimento_familiar_familia_id FOREIGN KEY (familia_id) REFERENCES familia(id);


--
-- TOC entry 2536 (class 2606 OID 645863)
-- Name: fk_produto_adquirido_aquisicao_produto_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto_adquirido
    ADD CONSTRAINT fk_produto_adquirido_aquisicao_produto_id FOREIGN KEY (aquisicao_produto_id) REFERENCES aquisicao_produto(id);


--
-- TOC entry 2537 (class 2606 OID 645868)
-- Name: fk_produto_adquirido_produto_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto_adquirido
    ADD CONSTRAINT fk_produto_adquirido_produto_id FOREIGN KEY (produto_id) REFERENCES produto(id);


--
-- TOC entry 2533 (class 2606 OID 645848)
-- Name: fk_produto_categoria_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk_produto_categoria_id FOREIGN KEY (categoria_id) REFERENCES categoria(id);


--
-- TOC entry 2534 (class 2606 OID 645853)
-- Name: fk_produto_marca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk_produto_marca_id FOREIGN KEY (marca_id) REFERENCES marca(id);


--
-- TOC entry 2535 (class 2606 OID 645858)
-- Name: fk_produto_modelo_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk_produto_modelo_id FOREIGN KEY (modelo_id) REFERENCES modelo(id);


--
-- TOC entry 2538 (class 2606 OID 645873)
-- Name: fk_produto_repassado_produto_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto_repassado
    ADD CONSTRAINT fk_produto_repassado_produto_id FOREIGN KEY (produto_id) REFERENCES produto(id);


--
-- TOC entry 2539 (class 2606 OID 645878)
-- Name: fk_produto_repassado_repasse_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto_repassado
    ADD CONSTRAINT fk_produto_repassado_repasse_id FOREIGN KEY (repasse_id) REFERENCES repasse(id);


--
-- TOC entry 2540 (class 2606 OID 645883)
-- Name: fk_questao_versao_questionario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questao
    ADD CONSTRAINT fk_questao_versao_questionario_id FOREIGN KEY (versao_questionario_id) REFERENCES versao_questionario(id);


--
-- TOC entry 2542 (class 2606 OID 645893)
-- Name: fk_questionario_resposta_usuario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario_resposta
    ADD CONSTRAINT fk_questionario_resposta_usuario_id FOREIGN KEY (usuario_id) REFERENCES "user"(id);


--
-- TOC entry 2543 (class 2606 OID 645898)
-- Name: fk_questionario_resposta_versao_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario_resposta
    ADD CONSTRAINT fk_questionario_resposta_versao_id FOREIGN KEY (versao_id) REFERENCES versao_questionario(id);


--
-- TOC entry 2541 (class 2606 OID 645888)
-- Name: fk_questionario_usuario_criador_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario
    ADD CONSTRAINT fk_questionario_usuario_criador_id FOREIGN KEY (usuario_criador_id) REFERENCES "user"(id);


--
-- TOC entry 2544 (class 2606 OID 645903)
-- Name: fk_rede_apoio_endereco_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT fk_rede_apoio_endereco_id FOREIGN KEY (endereco_id) REFERENCES endereco(id);


--
-- TOC entry 2545 (class 2606 OID 645908)
-- Name: fk_rede_apoio_responsavel_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rede_apoio
    ADD CONSTRAINT fk_rede_apoio_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);


--
-- TOC entry 2546 (class 2606 OID 645913)
-- Name: fk_repasse_casa_lar_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY repasse
    ADD CONSTRAINT fk_repasse_casa_lar_id FOREIGN KEY (casa_lar_id) REFERENCES casa_lar(id);


--
-- TOC entry 2547 (class 2606 OID 645918)
-- Name: fk_resposta_questao_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT fk_resposta_questao_id FOREIGN KEY (questao_id) REFERENCES questao(id);


--
-- TOC entry 2548 (class 2606 OID 645923)
-- Name: fk_resposta_questionario_resposta_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT fk_resposta_questionario_resposta_id FOREIGN KEY (questionario_resposta_id) REFERENCES questionario_resposta(id);


--
-- TOC entry 2464 (class 2606 OID 645503)
-- Name: fk_user_rede_apoio_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT fk_user_rede_apoio_id FOREIGN KEY (rede_apoio_id) REFERENCES rede_apoio(id);


--
-- TOC entry 2549 (class 2606 OID 645928)
-- Name: fk_versao_questionario_questionario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY versao_questionario
    ADD CONSTRAINT fk_versao_questionario_questionario_id FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- TOC entry 2550 (class 2606 OID 645933)
-- Name: fk_versao_questionario_usuario_avaliador_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY versao_questionario
    ADD CONSTRAINT fk_versao_questionario_usuario_avaliador_id FOREIGN KEY (usuario_avaliador_id) REFERENCES "user"(id);


-- Completed on 2016-11-30 13:08:31 BRST

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