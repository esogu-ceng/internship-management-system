CREATE TABLE public.ims_internship_journals (
    id bigserial NOT NULL,
    unit_name varchar NULL,
    journal varchar NOT NULL,
    operation_time int8 NULL,
    starting_date timestamptz NOT NULL,
    end_date timestamptz NOT NULL,
    update_date timestamptz NOT NULL,
    create_date timestamptz NULL,
    internship_id int8 NOT NULL,
    company_supervisor_id int8,
    confirmation boolean NOT NULL,
    CONSTRAINT ims_internship_journal_pk PRIMARY KEY (id),
    CONSTRAINT ims_internship_journal_internship_fk FOREIGN KEY (internship_id) REFERENCES public.ims_internships(id),
	CONSTRAINT ims_internship_journal_company_supervisor_fk FOREIGN KEY (company_supervisor_id) REFERENCES public.ims_company_supervisors(id)
);