-- Table: public.ims_language

-- DROP TABLE IF EXISTS public.ims_language;

CREATE TABLE IF NOT EXISTS public.ims_language
(
    id smallint NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    flag character varying COLLATE pg_catalog."default",
    language_abbr character varying COLLATE pg_catalog."default" NOT NULL,
    country_abbr character varying COLLATE pg_catalog."default" NOT NULL,
    country character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT ims_language_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.ims_language
    OWNER to postgres;
    
ALTER TABLE IF EXISTS public.ims_users
    ADD COLUMN language smallint;
ALTER TABLE IF EXISTS public.ims_users
    ADD FOREIGN KEY (language)
    REFERENCES public.ims_language (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;