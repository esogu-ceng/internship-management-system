--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-03-30 00:07:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4986 (class 1262 OID 16398)
-- Name: internship-management-system; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE "internship-management-system" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


\connect -reuse-previous=on "dbname='internship-management-system'"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 4987 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 238 (class 1255 OID 16606)
-- Name: ims_general_set_dates(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.ims_general_set_dates() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        NEW.create_date := NOW();
    END IF;
    NEW.update_date := NOW();
    RETURN NEW;
END;
$$;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16438)
-- Name: ims_companies; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_companies (
    id bigint NOT NULL,
    name character varying NOT NULL,
    address character varying NOT NULL,
    phone_number character varying NOT NULL,
    fax_number character varying NOT NULL,
    email character varying NOT NULL,
    scope character varying NOT NULL,
    description character varying,
    create_date timestamp with time zone,
    update_date timestamp with time zone
);


--
-- TOC entry 219 (class 1259 OID 16437)
-- Name: ims_companies_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_companies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4988 (class 0 OID 0)
-- Dependencies: 219
-- Name: ims_companies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_companies_id_seq OWNED BY public.ims_companies.id;


--
-- TOC entry 224 (class 1259 OID 16460)
-- Name: ims_company_supervisors; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_company_supervisors (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    phone_number character varying NOT NULL,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    user_id bigint NOT NULL,
    company_id bigint NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 16459)
-- Name: ims_company_supervisors_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_company_supervisors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4989 (class 0 OID 0)
-- Dependencies: 223
-- Name: ims_company_supervisors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_company_supervisors_id_seq OWNED BY public.ims_company_supervisors.id;


--
-- TOC entry 222 (class 1259 OID 16449)
-- Name: ims_faculties; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_faculties (
    id bigint NOT NULL,
    name character varying NOT NULL,
    create_date timestamp with time zone,
    update_date timestamp with time zone
);


--
-- TOC entry 221 (class 1259 OID 16448)
-- Name: ims_faculties_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_faculties_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4990 (class 0 OID 0)
-- Dependencies: 221
-- Name: ims_faculties_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_faculties_id_seq OWNED BY public.ims_faculties.id;


--
-- TOC entry 226 (class 1259 OID 16479)
-- Name: ims_faculty_supervisors; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_faculty_supervisors (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    phone_number character varying NOT NULL,
    supervisor_no character varying,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    user_id bigint NOT NULL,
    faculty_id bigint NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 16478)
-- Name: ims_faculty_supervisors_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_faculty_supervisors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4991 (class 0 OID 0)
-- Dependencies: 225
-- Name: ims_faculty_supervisors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_faculty_supervisors_id_seq OWNED BY public.ims_faculty_supervisors.id;


--
-- TOC entry 234 (class 1259 OID 16561)
-- Name: ims_internship_documents; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_internship_documents (
    id bigint NOT NULL,
    file_path character varying NOT NULL,
    name character varying NOT NULL,
    type character varying NOT NULL,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    internship_id bigint NOT NULL
);


--
-- TOC entry 233 (class 1259 OID 16560)
-- Name: ims_internship_documents_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_internship_documents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4992 (class 0 OID 0)
-- Dependencies: 233
-- Name: ims_internship_documents_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_internship_documents_id_seq OWNED BY public.ims_internship_documents.id;


--
-- TOC entry 236 (class 1259 OID 16577)
-- Name: ims_internship_evaluate_form; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_internship_evaluate_form (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    file_path character varying NOT NULL,
    internship_id bigint NOT NULL,
    company_id bigint NOT NULL
);


--
-- TOC entry 235 (class 1259 OID 16576)
-- Name: ims_internship_evaluate_form_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_internship_evaluate_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4993 (class 0 OID 0)
-- Dependencies: 235
-- Name: ims_internship_evaluate_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_internship_evaluate_form_id_seq OWNED BY public.ims_internship_evaluate_form.id;


--
-- TOC entry 232 (class 1259 OID 16547)
-- Name: ims_internship_registries; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_internship_registries (
    id bigint NOT NULL,
    file_path character varying NOT NULL,
    name character varying NOT NULL,
    type character varying NOT NULL,
    date timestamp with time zone NOT NULL,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    internship_id bigint NOT NULL
);


--
-- TOC entry 231 (class 1259 OID 16546)
-- Name: ims_internship_registries_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_internship_registries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4994 (class 0 OID 0)
-- Dependencies: 231
-- Name: ims_internship_registries_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_internship_registries_id_seq OWNED BY public.ims_internship_registries.id;


--
-- TOC entry 230 (class 1259 OID 16523)
-- Name: ims_internships; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_internships (
    id bigint NOT NULL,
    status character varying NOT NULL,
    start_date timestamp with time zone NOT NULL,
    end_date timestamp with time zone NOT NULL,
    days integer NOT NULL,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    student_id bigint NOT NULL,
    company_id bigint NOT NULL,
    faculty_supervisor_id bigint NOT NULL
);


--
-- TOC entry 229 (class 1259 OID 16522)
-- Name: ims_internships_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_internships_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4995 (class 0 OID 0)
-- Dependencies: 229
-- Name: ims_internships_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_internships_id_seq OWNED BY public.ims_internships.id;


--
-- TOC entry 237 (class 1259 OID 16594)
-- Name: ims_language; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_language (
    id smallint NOT NULL,
    name character varying NOT NULL,
    flag character varying,
    language_abbr character varying NOT NULL,
    country_abbr character varying NOT NULL,
    country character varying NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 16400)
-- Name: ims_settings; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_settings (
    id bigint NOT NULL,
    key character varying NOT NULL,
    value character varying,
    create_date timestamp with time zone,
    update_date timestamp with time zone
);


--
-- TOC entry 215 (class 1259 OID 16399)
-- Name: ims_settings_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4996 (class 0 OID 0)
-- Dependencies: 215
-- Name: ims_settings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_settings_id_seq OWNED BY public.ims_settings.id;


--
-- TOC entry 228 (class 1259 OID 16498)
-- Name: ims_students; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_students (
    id bigint NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL,
    tckn character varying NOT NULL,
    student_no character varying NOT NULL,
    grade character varying NOT NULL,
    phone_number character varying NOT NULL,
    birth_place character varying NOT NULL,
    birth_date timestamp with time zone NOT NULL,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    user_id bigint NOT NULL,
    faculty_id bigint NOT NULL,
    address character varying NOT NULL
);


--
-- TOC entry 227 (class 1259 OID 16497)
-- Name: ims_students_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4997 (class 0 OID 0)
-- Dependencies: 227
-- Name: ims_students_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_students_id_seq OWNED BY public.ims_students.id;


--
-- TOC entry 218 (class 1259 OID 16422)
-- Name: ims_users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ims_users (
    id bigint NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    create_date timestamp with time zone,
    update_date timestamp with time zone,
    email character varying NOT NULL,
    user_type character varying NOT NULL,
    language smallint,
    activity boolean DEFAULT true NOT NULL
);


--
-- TOC entry 217 (class 1259 OID 16421)
-- Name: ims_users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ims_users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4998 (class 0 OID 0)
-- Dependencies: 217
-- Name: ims_users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ims_users_id_seq OWNED BY public.ims_users.id;


--
-- TOC entry 4746 (class 2604 OID 16441)
-- Name: ims_companies id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_companies ALTER COLUMN id SET DEFAULT nextval('public.ims_companies_id_seq'::regclass);


--
-- TOC entry 4748 (class 2604 OID 16463)
-- Name: ims_company_supervisors id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_company_supervisors ALTER COLUMN id SET DEFAULT nextval('public.ims_company_supervisors_id_seq'::regclass);


--
-- TOC entry 4747 (class 2604 OID 16452)
-- Name: ims_faculties id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_faculties ALTER COLUMN id SET DEFAULT nextval('public.ims_faculties_id_seq'::regclass);


--
-- TOC entry 4749 (class 2604 OID 16482)
-- Name: ims_faculty_supervisors id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_faculty_supervisors ALTER COLUMN id SET DEFAULT nextval('public.ims_faculty_supervisors_id_seq'::regclass);


--
-- TOC entry 4753 (class 2604 OID 16564)
-- Name: ims_internship_documents id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_documents ALTER COLUMN id SET DEFAULT nextval('public.ims_internship_documents_id_seq'::regclass);


--
-- TOC entry 4754 (class 2604 OID 16580)
-- Name: ims_internship_evaluate_form id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_evaluate_form ALTER COLUMN id SET DEFAULT nextval('public.ims_internship_evaluate_form_id_seq'::regclass);


--
-- TOC entry 4752 (class 2604 OID 16550)
-- Name: ims_internship_registries id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_registries ALTER COLUMN id SET DEFAULT nextval('public.ims_internship_registries_id_seq'::regclass);


--
-- TOC entry 4751 (class 2604 OID 16526)
-- Name: ims_internships id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internships ALTER COLUMN id SET DEFAULT nextval('public.ims_internships_id_seq'::regclass);


--
-- TOC entry 4743 (class 2604 OID 16403)
-- Name: ims_settings id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_settings ALTER COLUMN id SET DEFAULT nextval('public.ims_settings_id_seq'::regclass);


--
-- TOC entry 4750 (class 2604 OID 16501)
-- Name: ims_students id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_students ALTER COLUMN id SET DEFAULT nextval('public.ims_students_id_seq'::regclass);


--
-- TOC entry 4744 (class 2604 OID 16425)
-- Name: ims_users id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_users ALTER COLUMN id SET DEFAULT nextval('public.ims_users_id_seq'::regclass);


--
-- TOC entry 4963 (class 0 OID 16438)
-- Dependencies: 220
-- Data for Name: ims_companies; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4967 (class 0 OID 16460)
-- Dependencies: 224
-- Data for Name: ims_company_supervisors; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4965 (class 0 OID 16449)
-- Dependencies: 222
-- Data for Name: ims_faculties; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4969 (class 0 OID 16479)
-- Dependencies: 226
-- Data for Name: ims_faculty_supervisors; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4977 (class 0 OID 16561)
-- Dependencies: 234
-- Data for Name: ims_internship_documents; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4979 (class 0 OID 16577)
-- Dependencies: 236
-- Data for Name: ims_internship_evaluate_form; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4975 (class 0 OID 16547)
-- Dependencies: 232
-- Data for Name: ims_internship_registries; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4973 (class 0 OID 16523)
-- Dependencies: 230
-- Data for Name: ims_internships; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4980 (class 0 OID 16594)
-- Dependencies: 237
-- Data for Name: ims_language; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4959 (class 0 OID 16400)
-- Dependencies: 216
-- Data for Name: ims_settings; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ims_settings VALUES (1, 'mail_host', NULL, NULL, NULL);
INSERT INTO public.ims_settings VALUES (2, 'mail_port', NULL, NULL, NULL);
INSERT INTO public.ims_settings VALUES (3, 'mail_username', NULL, NULL, NULL);
INSERT INTO public.ims_settings VALUES (4, 'mail_password', NULL, NULL, NULL);
INSERT INTO public.ims_settings VALUES (5, 'upload_directory', NULL, NULL, NULL);
INSERT INTO public.ims_settings VALUES (6, 'app_host', NULL, NULL, NULL);
INSERT INTO public.ims_settings VALUES (7, 'app_port', NULL, NULL, NULL);


--
-- TOC entry 4971 (class 0 OID 16498)
-- Dependencies: 228
-- Data for Name: ims_students; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4961 (class 0 OID 16422)
-- Dependencies: 218
-- Data for Name: ims_users; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 4999 (class 0 OID 0)
-- Dependencies: 219
-- Name: ims_companies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_companies_id_seq', 1, false);


--
-- TOC entry 5000 (class 0 OID 0)
-- Dependencies: 223
-- Name: ims_company_supervisors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_company_supervisors_id_seq', 1, false);


--
-- TOC entry 5001 (class 0 OID 0)
-- Dependencies: 221
-- Name: ims_faculties_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_faculties_id_seq', 1, false);


--
-- TOC entry 5002 (class 0 OID 0)
-- Dependencies: 225
-- Name: ims_faculty_supervisors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_faculty_supervisors_id_seq', 1, false);


--
-- TOC entry 5003 (class 0 OID 0)
-- Dependencies: 233
-- Name: ims_internship_documents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_internship_documents_id_seq', 1, false);


--
-- TOC entry 5004 (class 0 OID 0)
-- Dependencies: 235
-- Name: ims_internship_evaluate_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_internship_evaluate_form_id_seq', 1, false);


--
-- TOC entry 5005 (class 0 OID 0)
-- Dependencies: 231
-- Name: ims_internship_registries_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_internship_registries_id_seq', 1, false);


--
-- TOC entry 5006 (class 0 OID 0)
-- Dependencies: 229
-- Name: ims_internships_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_internships_id_seq', 1, false);


--
-- TOC entry 5007 (class 0 OID 0)
-- Dependencies: 215
-- Name: ims_settings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_settings_id_seq', 7, true);


--
-- TOC entry 5008 (class 0 OID 0)
-- Dependencies: 227
-- Name: ims_students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_students_id_seq', 1, false);


--
-- TOC entry 5009 (class 0 OID 0)
-- Dependencies: 217
-- Name: ims_users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ims_users_id_seq', 1, false);


--
-- TOC entry 4766 (class 2606 OID 16447)
-- Name: ims_companies ims_companies_name_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_companies
    ADD CONSTRAINT ims_companies_name_un UNIQUE (name);


--
-- TOC entry 4768 (class 2606 OID 16445)
-- Name: ims_companies ims_companies_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_companies
    ADD CONSTRAINT ims_companies_pk PRIMARY KEY (id);


--
-- TOC entry 4774 (class 2606 OID 16467)
-- Name: ims_company_supervisors ims_company_supervisors_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_company_supervisors
    ADD CONSTRAINT ims_company_supervisors_pk PRIMARY KEY (id);


--
-- TOC entry 4770 (class 2606 OID 16458)
-- Name: ims_faculties ims_facultie_name_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_faculties
    ADD CONSTRAINT ims_facultie_name_un UNIQUE (name);


--
-- TOC entry 4772 (class 2606 OID 16456)
-- Name: ims_faculties ims_faculties_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_faculties
    ADD CONSTRAINT ims_faculties_pk PRIMARY KEY (id);


--
-- TOC entry 4776 (class 2606 OID 16486)
-- Name: ims_faculty_supervisors ims_faculty_supervisors_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_faculty_supervisors
    ADD CONSTRAINT ims_faculty_supervisors_pk PRIMARY KEY (id);


--
-- TOC entry 4788 (class 2606 OID 16568)
-- Name: ims_internship_documents ims_internship_documents_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_documents
    ADD CONSTRAINT ims_internship_documents_pk PRIMARY KEY (id);


--
-- TOC entry 4786 (class 2606 OID 16554)
-- Name: ims_internship_registries ims_internship_registries_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_registries
    ADD CONSTRAINT ims_internship_registries_pk PRIMARY KEY (id);


--
-- TOC entry 4784 (class 2606 OID 16530)
-- Name: ims_internships ims_internships_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internships
    ADD CONSTRAINT ims_internships_pk PRIMARY KEY (id);


--
-- TOC entry 4790 (class 2606 OID 16600)
-- Name: ims_language ims_language_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_language
    ADD CONSTRAINT ims_language_pkey PRIMARY KEY (id);


--
-- TOC entry 4756 (class 2606 OID 16409)
-- Name: ims_settings ims_settings_key_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_settings
    ADD CONSTRAINT ims_settings_key_un UNIQUE (key);


--
-- TOC entry 4758 (class 2606 OID 16407)
-- Name: ims_settings ims_settings_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_settings
    ADD CONSTRAINT ims_settings_pk PRIMARY KEY (id);


--
-- TOC entry 4778 (class 2606 OID 16505)
-- Name: ims_students ims_students_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_students
    ADD CONSTRAINT ims_students_pk PRIMARY KEY (id);


--
-- TOC entry 4780 (class 2606 OID 16509)
-- Name: ims_students ims_students_student_no_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_students
    ADD CONSTRAINT ims_students_student_no_un UNIQUE (student_no);


--
-- TOC entry 4782 (class 2606 OID 16511)
-- Name: ims_students ims_students_tckn_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_students
    ADD CONSTRAINT ims_students_tckn_un UNIQUE (tckn);


--
-- TOC entry 4760 (class 2606 OID 16575)
-- Name: ims_users ims_users_email_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_users
    ADD CONSTRAINT ims_users_email_un UNIQUE (email);


--
-- TOC entry 4762 (class 2606 OID 16429)
-- Name: ims_users ims_users_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_users
    ADD CONSTRAINT ims_users_pk PRIMARY KEY (id);


--
-- TOC entry 4764 (class 2606 OID 16431)
-- Name: ims_users ims_users_username_un; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_users
    ADD CONSTRAINT ims_users_username_un UNIQUE (username);


--
-- TOC entry 4807 (class 2620 OID 16607)
-- Name: ims_companies ims_companies_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_companies_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_companies FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4809 (class 2620 OID 16608)
-- Name: ims_company_supervisors ims_company_supervisors_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_company_supervisors_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_company_supervisors FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4808 (class 2620 OID 16609)
-- Name: ims_faculties ims_faculties_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_faculties_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_faculties FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4810 (class 2620 OID 16610)
-- Name: ims_faculty_supervisors ims_faculty_supervisors_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_faculty_supervisors_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_faculty_supervisors FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4814 (class 2620 OID 16611)
-- Name: ims_internship_documents ims_internship_documents_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_internship_documents_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_internship_documents FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4813 (class 2620 OID 16612)
-- Name: ims_internship_registries ims_internship_registries_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_internship_registries_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_internship_registries FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4812 (class 2620 OID 16613)
-- Name: ims_internships ims_internships_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_internships_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_internships FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4805 (class 2620 OID 16614)
-- Name: ims_settings ims_settings_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_settings_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_settings FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4811 (class 2620 OID 16615)
-- Name: ims_students ims_students_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_students_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_students FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4806 (class 2620 OID 16616)
-- Name: ims_users ims_users_set_dates_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER ims_users_set_dates_trigger BEFORE INSERT OR UPDATE ON public.ims_users FOR EACH ROW EXECUTE FUNCTION public.ims_general_set_dates();


--
-- TOC entry 4792 (class 2606 OID 16468)
-- Name: ims_company_supervisors ims_company_supervisors_companies_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_company_supervisors
    ADD CONSTRAINT ims_company_supervisors_companies_fk FOREIGN KEY (company_id) REFERENCES public.ims_companies(id);


--
-- TOC entry 4793 (class 2606 OID 16473)
-- Name: ims_company_supervisors ims_company_supervisors_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_company_supervisors
    ADD CONSTRAINT ims_company_supervisors_users_fk FOREIGN KEY (user_id) REFERENCES public.ims_users(id);


--
-- TOC entry 4794 (class 2606 OID 16487)
-- Name: ims_faculty_supervisors ims_faculty_supervisors_faculties_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_faculty_supervisors
    ADD CONSTRAINT ims_faculty_supervisors_faculties_fk FOREIGN KEY (faculty_id) REFERENCES public.ims_faculties(id);


--
-- TOC entry 4795 (class 2606 OID 16492)
-- Name: ims_faculty_supervisors ims_faculty_supervisors_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_faculty_supervisors
    ADD CONSTRAINT ims_faculty_supervisors_users_fk FOREIGN KEY (user_id) REFERENCES public.ims_users(id);


--
-- TOC entry 4802 (class 2606 OID 16569)
-- Name: ims_internship_documents ims_internship_documents_internships_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_documents
    ADD CONSTRAINT ims_internship_documents_internships_fk FOREIGN KEY (internship_id) REFERENCES public.ims_internships(id);


--
-- TOC entry 4803 (class 2606 OID 16588)
-- Name: ims_internship_evaluate_form ims_internship_evaluate_form_companies_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_evaluate_form
    ADD CONSTRAINT ims_internship_evaluate_form_companies_fk FOREIGN KEY (company_id) REFERENCES public.ims_companies(id);


--
-- TOC entry 4804 (class 2606 OID 16583)
-- Name: ims_internship_evaluate_form ims_internship_evaluate_form_internships_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_evaluate_form
    ADD CONSTRAINT ims_internship_evaluate_form_internships_fk FOREIGN KEY (internship_id) REFERENCES public.ims_internships(id);


--
-- TOC entry 4801 (class 2606 OID 16555)
-- Name: ims_internship_registries ims_internship_registries_internships_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internship_registries
    ADD CONSTRAINT ims_internship_registries_internships_fk FOREIGN KEY (internship_id) REFERENCES public.ims_internships(id);


--
-- TOC entry 4798 (class 2606 OID 16531)
-- Name: ims_internships ims_internships_companies_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internships
    ADD CONSTRAINT ims_internships_companies_fk FOREIGN KEY (company_id) REFERENCES public.ims_companies(id);


--
-- TOC entry 4799 (class 2606 OID 16536)
-- Name: ims_internships ims_internships_faculty_supervisors_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internships
    ADD CONSTRAINT ims_internships_faculty_supervisors_fk FOREIGN KEY (faculty_supervisor_id) REFERENCES public.ims_faculty_supervisors(id);


--
-- TOC entry 4800 (class 2606 OID 16541)
-- Name: ims_internships ims_internships_students_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_internships
    ADD CONSTRAINT ims_internships_students_fk FOREIGN KEY (student_id) REFERENCES public.ims_students(id);


--
-- TOC entry 4796 (class 2606 OID 16512)
-- Name: ims_students ims_students_faculties_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_students
    ADD CONSTRAINT ims_students_faculties_fk FOREIGN KEY (faculty_id) REFERENCES public.ims_faculties(id);


--
-- TOC entry 4797 (class 2606 OID 16517)
-- Name: ims_students ims_students_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_students
    ADD CONSTRAINT ims_students_users_fk FOREIGN KEY (user_id) REFERENCES public.ims_users(id);


--
-- TOC entry 4791 (class 2606 OID 16601)
-- Name: ims_users ims_users_language_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ims_users
    ADD CONSTRAINT ims_users_language_fkey FOREIGN KEY (language) REFERENCES public.ims_language(id) NOT VALID;


-- Completed on 2024-03-30 00:07:39

--
-- PostgreSQL database dump complete
--

