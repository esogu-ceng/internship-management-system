CREATE TABLE public.ims_internship_evaluate_form (
    id bigserial NOT NULL,
    "name" varchar NOT NULL,
    surname varchar NOT NULL,
    file_path varchar NOT NULL,
    internship_id int8 NOT NULL,
    company_id int8 NOT NULL,
    CONSTRAINT ims_internship_evaluate_form_internships_fk FOREIGN KEY (internship_id) REFERENCES public.ims_internships(id),
    CONSTRAINT ims_internship_evaluate_form_companies_fk FOREIGN KEY (company_id) REFERENCES public.ims_companies(id)
);