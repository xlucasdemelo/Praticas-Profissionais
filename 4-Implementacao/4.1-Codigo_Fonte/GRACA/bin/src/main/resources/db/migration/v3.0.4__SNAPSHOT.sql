
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.14
-- Dumped by pg_dump version 9.3.13
-- Started on 2016-09-14 06:00:44 BRT

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
-- TOC entry 178 (class 1259 OID 384401)
-- Name: avaliacao_individual_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE avaliacao_individual_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    enabled boolean,
    status integer,
    avaliador_id bigint,
    crianca_id bigint,
    questionario_id bigint
);


ALTER TABLE auditing.avaliacao_individual_audited OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 384416)
-- Name: configuracao_avaliacao_individual_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracao_avaliacao_individual_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    questionario_id bigint
);


ALTER TABLE auditing.configuracao_avaliacao_individual_audited OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 384496)
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
-- TOC entry 195 (class 1259 OID 384501)
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
-- TOC entry 198 (class 1259 OID 384525)
-- Name: resposta_audited; Type: TABLE; Schema: auditing; Owner: postgres; Tablespace: 
--

CREATE TABLE resposta_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    resposta character varying(255),
    avaliacao_id bigint,
    questao_id bigint
);


ALTER TABLE auditing.resposta_audited OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 384543)
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
-- TOC entry 204 (class 1259 OID 384550)
-- Name: avaliacao_individual; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avaliacao_individual (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    enabled boolean NOT NULL,
    status integer NOT NULL,
    avaliador_id bigint NOT NULL,
    crianca_id bigint NOT NULL,
    questionario_id bigint NOT NULL
);


ALTER TABLE public.avaliacao_individual OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 384548)
-- Name: avaliacao_individual_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE avaliacao_individual_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.avaliacao_individual_id_seq OWNER TO postgres;

--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 203
-- Name: avaliacao_individual_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE avaliacao_individual_id_seq OWNED BY avaliacao_individual.id;


--
-- TOC entry 210 (class 1259 OID 384574)
-- Name: configuracao_avaliacao_individual; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracao_avaliacao_individual (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    questionario_id bigint NOT NULL
);


ALTER TABLE public.configuracao_avaliacao_individual OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 384572)
-- Name: configuracao_avaliacao_individual_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configuracao_avaliacao_individual_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.configuracao_avaliacao_individual_id_seq OWNER TO postgres;

--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 209
-- Name: configuracao_avaliacao_individual_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE configuracao_avaliacao_individual_id_seq OWNED BY configuracao_avaliacao_individual.id;


--
-- TOC entry 234 (class 1259 OID 384689)
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
-- TOC entry 233 (class 1259 OID 384687)
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
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 233
-- Name: questao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE questao_id_seq OWNED BY questao.id;


--
-- TOC entry 236 (class 1259 OID 384697)
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
-- TOC entry 235 (class 1259 OID 384695)
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
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 235
-- Name: questionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE questionario_id_seq OWNED BY questionario.id;


--
-- TOC entry 242 (class 1259 OID 384730)
-- Name: resposta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resposta (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    resposta character varying(255),
    avaliacao_id bigint NOT NULL,
    questao_id bigint NOT NULL
);


ALTER TABLE public.resposta OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 384728)
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
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 241
-- Name: resposta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE resposta_id_seq OWNED BY resposta.id;


--
-- TOC entry 244 (class 1259 OID 384738)
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
-- TOC entry 243 (class 1259 OID 384736)
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
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 243
-- Name: versao_questionario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE versao_questionario_id_seq OWNED BY versao_questionario.id;


--
-- TOC entry 2070 (class 2604 OID 384553)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacao_individual ALTER COLUMN id SET DEFAULT nextval('avaliacao_individual_id_seq'::regclass);


--
-- TOC entry 2071 (class 2604 OID 384577)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuracao_avaliacao_individual ALTER COLUMN id SET DEFAULT nextval('configuracao_avaliacao_individual_id_seq'::regclass);


--
-- TOC entry 2072 (class 2604 OID 384692)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questao ALTER COLUMN id SET DEFAULT nextval('questao_id_seq'::regclass);


--
-- TOC entry 2073 (class 2604 OID 384700)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario ALTER COLUMN id SET DEFAULT nextval('questionario_id_seq'::regclass);


--
-- TOC entry 2074 (class 2604 OID 384733)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resposta ALTER COLUMN id SET DEFAULT nextval('resposta_id_seq'::regclass);


--
-- TOC entry 2075 (class 2604 OID 384741)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY versao_questionario ALTER COLUMN id SET DEFAULT nextval('versao_questionario_id_seq'::regclass);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2077 (class 2606 OID 384405)
-- Name: avaliacao_individual_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avaliacao_individual_audited
    ADD CONSTRAINT avaliacao_individual_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2079 (class 2606 OID 384420)
-- Name: configuracao_avaliacao_individual_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracao_avaliacao_individual_audited
    ADD CONSTRAINT configuracao_avaliacao_individual_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2081 (class 2606 OID 384500)
-- Name: questao_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questao_audited
    ADD CONSTRAINT questao_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2083 (class 2606 OID 384508)
-- Name: questionario_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionario_audited
    ADD CONSTRAINT questionario_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2085 (class 2606 OID 384529)
-- Name: resposta_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resposta_audited
    ADD CONSTRAINT resposta_audited_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2087 (class 2606 OID 384547)
-- Name: versao_questionario_audited_pkey; Type: CONSTRAINT; Schema: auditing; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY versao_questionario_audited
    ADD CONSTRAINT versao_questionario_audited_pkey PRIMARY KEY (id, revision);


SET search_path = public, pg_catalog;

--
-- TOC entry 2089 (class 2606 OID 384555)
-- Name: avaliacao_individual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avaliacao_individual
    ADD CONSTRAINT avaliacao_individual_pkey PRIMARY KEY (id);


--
-- TOC entry 2091 (class 2606 OID 384579)
-- Name: configuracao_avaliacao_individual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracao_avaliacao_individual
    ADD CONSTRAINT configuracao_avaliacao_individual_pkey PRIMARY KEY (id);


--
-- TOC entry 2093 (class 2606 OID 384694)
-- Name: questao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questao
    ADD CONSTRAINT questao_pkey PRIMARY KEY (id);


--
-- TOC entry 2095 (class 2606 OID 384705)
-- Name: questionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionario
    ADD CONSTRAINT questionario_pkey PRIMARY KEY (id);


--
-- TOC entry 2097 (class 2606 OID 384735)
-- Name: resposta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT resposta_pkey PRIMARY KEY (id);


--
-- TOC entry 2099 (class 2606 OID 384743)
-- Name: versao_questionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY versao_questionario
    ADD CONSTRAINT versao_questionario_pkey PRIMARY KEY (id);


SET search_path = auditing, pg_catalog;

--
-- TOC entry 2100 (class 2606 OID 384769)
-- Name: fk_avaliacao_individual_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY avaliacao_individual_audited
    ADD CONSTRAINT fk_avaliacao_individual_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2101 (class 2606 OID 384784)
-- Name: fk_configuracao_avaliacao_individual_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY configuracao_avaliacao_individual_audited
    ADD CONSTRAINT fk_configuracao_avaliacao_individual_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2102 (class 2606 OID 384849)
-- Name: fk_questao_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY questao_audited
    ADD CONSTRAINT fk_questao_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2103 (class 2606 OID 384854)
-- Name: fk_questionario_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY questionario_audited
    ADD CONSTRAINT fk_questionario_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2104 (class 2606 OID 384869)
-- Name: fk_resposta_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY resposta_audited
    ADD CONSTRAINT fk_resposta_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


--
-- TOC entry 2105 (class 2606 OID 384879)
-- Name: fk_versao_questionario_audited_revision; Type: FK CONSTRAINT; Schema: auditing; Owner: postgres
--

ALTER TABLE ONLY versao_questionario_audited
    ADD CONSTRAINT fk_versao_questionario_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


SET search_path = public, pg_catalog;

--
-- TOC entry 2106 (class 2606 OID 384884)
-- Name: fk_avaliacao_individual_avaliador_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacao_individual
    ADD CONSTRAINT fk_avaliacao_individual_avaliador_id FOREIGN KEY (avaliador_id) REFERENCES "user"(id);


--
-- TOC entry 2107 (class 2606 OID 384889)
-- Name: fk_avaliacao_individual_crianca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacao_individual
    ADD CONSTRAINT fk_avaliacao_individual_crianca_id FOREIGN KEY (crianca_id) REFERENCES crianca(id);


--
-- TOC entry 2108 (class 2606 OID 384894)
-- Name: fk_avaliacao_individual_questionario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacao_individual
    ADD CONSTRAINT fk_avaliacao_individual_questionario_id FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- TOC entry 2109 (class 2606 OID 384914)
-- Name: fk_configuracao_avaliacao_individual_questionario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuracao_avaliacao_individual
    ADD CONSTRAINT fk_configuracao_avaliacao_individual_questionario_id FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- TOC entry 2110 (class 2606 OID 385009)
-- Name: fk_questao_versao_questionario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questao
    ADD CONSTRAINT fk_questao_versao_questionario_id FOREIGN KEY (versao_questionario_id) REFERENCES versao_questionario(id);


--
-- TOC entry 2111 (class 2606 OID 385014)
-- Name: fk_questionario_usuario_criador_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario
    ADD CONSTRAINT fk_questionario_usuario_criador_id FOREIGN KEY (usuario_criador_id) REFERENCES "user"(id);


--
-- TOC entry 2112 (class 2606 OID 385029)
-- Name: fk_resposta_avaliacao_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT fk_resposta_avaliacao_id FOREIGN KEY (avaliacao_id) REFERENCES avaliacao_individual(id);


--
-- TOC entry 2113 (class 2606 OID 385034)
-- Name: fk_resposta_questao_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT fk_resposta_questao_id FOREIGN KEY (questao_id) REFERENCES questao(id);


--
-- TOC entry 2114 (class 2606 OID 385039)
-- Name: fk_versao_questionario_questionario_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY versao_questionario
    ADD CONSTRAINT fk_versao_questionario_questionario_id FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- TOC entry 2115 (class 2606 OID 385044)
-- Name: fk_versao_questionario_usuario_avaliador_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY versao_questionario
    ADD CONSTRAINT fk_versao_questionario_usuario_avaliador_id FOREIGN KEY (usuario_avaliador_id) REFERENCES "user"(id);


-- Completed on 2016-09-14 06:00:44 BRT

--
-- PostgreSQL database dump complete
--

