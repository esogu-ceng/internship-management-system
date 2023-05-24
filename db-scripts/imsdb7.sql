CREATE OR REPLACE FUNCTION public.ims_general_set_dates()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
    IF TG_OP = 'INSERT' THEN
        NEW.create_date := NOW();
    END IF;
    NEW.update_date := NOW();
    RETURN NEW;
END;
$BODY$;

ALTER FUNCTION public.ims_general_set_dates()
    OWNER TO postgres;
	

CREATE OR REPLACE TRIGGER ims_companies_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_companies
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_company_supervisors_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_company_supervisors
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_faculties_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_faculties
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_faculty_supervisors_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_faculty_supervisors
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_internship_documents_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_internship_documents
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_internship_registries_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_internship_registries
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_internships_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_internships
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_settings_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_settings
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_students_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_students
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
CREATE OR REPLACE TRIGGER ims_users_set_dates_trigger
	BEFORE INSERT OR UPDATE
	ON public.ims_users
	FOR EACH ROW
	EXECUTE FUNCTION public.ims_general_set_dates();
	
