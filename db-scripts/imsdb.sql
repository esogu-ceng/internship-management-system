CREATE TABLE public.ims_settings (
	id bigserial NOT NULL,
	"key" varchar NOT NULL,
	value varchar NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	CONSTRAINT ims_settings_key_un UNIQUE (key),
	CONSTRAINT ims_settings_pk PRIMARY KEY (id)
);

CREATE TABLE public.ims_user_types (
	id bigserial NOT NULL,
	"type" varchar NOT NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	CONSTRAINT ims_user_types_pk PRIMARY KEY (id),
	CONSTRAINT ims_user_types_type_un UNIQUE (type)
);

CREATE TABLE public.ims_users (
	id bigserial NOT NULL,
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	user_type_id int8 NOT NULL,
	CONSTRAINT ims_users_pk PRIMARY KEY (id),
	CONSTRAINT ims_users_username_un UNIQUE (username),
	CONSTRAINT ims_users_user_types_fk FOREIGN KEY (user_type_id) REFERENCES public.ims_user_types(id)
);

CREATE TABLE public.ims_companies (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	address varchar NOT NULL,
	phone_number varchar NOT NULL,
	fax_number varchar NOT NULL,
	email varchar NOT NULL,
	"scope" varchar NOT NULL,
	description varchar NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	CONSTRAINT ims_companies_name_un UNIQUE (name),
	CONSTRAINT ims_companies_pk PRIMARY KEY (id)
);

CREATE TABLE public.ims_faculties (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	CONSTRAINT ims_facultie_name_un UNIQUE (name),
	CONSTRAINT ims_faculties_pk PRIMARY KEY (id)
);

CREATE TABLE public.ims_company_supervisors (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	surname varchar NOT NULL,
	phone_number varchar NOT NULL,
	email varchar NOT NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	user_id int8 NOT NULL,
	company_id int8 NOT NULL,
	CONSTRAINT ims_company_supervisors_pk PRIMARY KEY (id),
	CONSTRAINT ims_company_supervisors_companies_fk FOREIGN KEY (company_id) REFERENCES public.ims_companies(id),
	CONSTRAINT ims_company_supervisors_users_fk FOREIGN KEY (user_id) REFERENCES public.ims_users(id)
);

CREATE TABLE public.ims_faculty_supervisors (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	surname varchar NOT NULL,
	phone_number varchar NOT NULL,
	email varchar NOT NULL,
	supervisor_no varchar NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	user_id int8 NOT NULL,
	faculty_id int8 NOT NULL,
	CONSTRAINT ims_faculty_supervisors_pk PRIMARY KEY (id),
	CONSTRAINT ims_faculty_supervisors_faculties_fk FOREIGN KEY (faculty_id) REFERENCES public.ims_faculties(id),
	CONSTRAINT ims_faculty_supervisors_users_fk FOREIGN KEY (user_id) REFERENCES public.ims_users(id)
);

CREATE TABLE public.ims_students (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	surname varchar NOT NULL,
	tckn varchar NOT NULL,
	student_no varchar NOT NULL,
	grade varchar NOT NULL,
	email varchar NOT NULL,
	phone_number varchar NOT NULL,
	home_phone_number varchar NULL,
	boulevard varchar NULL,
	main_street varchar NULL, -- Cadde
	street varchar NULL, -- Sokak
	neighborhood varchar NULL, -- Mahalle/Semt
	outer_door_no varchar NULL,
	inner_door_no varchar NULL,
	province varchar NOT NULL, -- Ýl
	subprovince varchar NOT NULL, -- Ýlçe
	village varchar NULL,
	zip_code varchar NOT NULL,
	mother_name varchar NOT NULL,
	father_name varchar NOT NULL,
	birth_place varchar NOT NULL,
	birth_date timestamptz NOT NULL,
	id_card_serial_no varchar NOT NULL,
	id_register_province varchar NOT NULL,
	id_register_subprovince varchar NOT NULL,
	id_register_street_village varchar NOT NULL,
	id_register_volume_no varchar NOT NULL, -- Nufüs kayýt cilt no
	id_register_family_serial_no varchar NOT NULL, -- Nüfus kayýt aile sýra no
	id_register_serial_no varchar NOT NULL, -- Nüfus kayýt sýra no
	id_registry_office varchar NOT NULL,
	id_registry_reason varchar NOT NULL,
	sgk_family bool NULL,
	sgk_self bool NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	user_id int8 NOT NULL,
	faculty_id int8 NOT NULL,
	CONSTRAINT ims_students_email_un UNIQUE (email),
	CONSTRAINT ims_students_pk PRIMARY KEY (id),
	CONSTRAINT ims_students_student_no_un UNIQUE (student_no),
	CONSTRAINT ims_students_tckn_un UNIQUE (tckn),
	CONSTRAINT ims_students_faculties_fk FOREIGN KEY (faculty_id) REFERENCES public.ims_faculties(id),
	CONSTRAINT ims_students_users_fk FOREIGN KEY (user_id) REFERENCES public.ims_users(id)
);

COMMENT ON COLUMN public.ims_students.main_street IS 'Cadde';
COMMENT ON COLUMN public.ims_students.street IS 'Sokak';
COMMENT ON COLUMN public.ims_students.neighborhood IS 'Mahalle/Semt';
COMMENT ON COLUMN public.ims_students.province IS 'Ýl';
COMMENT ON COLUMN public.ims_students.subprovince IS 'Ýlçe';
COMMENT ON COLUMN public.ims_students.id_register_volume_no IS 'Nufüs kayýt cilt no';
COMMENT ON COLUMN public.ims_students.id_register_family_serial_no IS 'Nüfus kayýt aile sýra no';
COMMENT ON COLUMN public.ims_students.id_register_serial_no IS 'Nüfus kayýt sýra no';

CREATE TABLE public.ims_internships (
	id bigserial NOT NULL,
	status varchar NOT NULL,
	start_date timestamptz NOT NULL,
	end_date timestamptz NOT NULL,
	days int4 NOT NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	student_id int8 NOT NULL,
	company_id int8 NOT NULL,
	faculty_supervisor_id int8 NOT NULL,
	CONSTRAINT ims_internships_pk PRIMARY KEY (id),
	CONSTRAINT ims_internships_companies_fk FOREIGN KEY (company_id) REFERENCES public.ims_companies(id),
	CONSTRAINT ims_internships_faculty_supervisors_fk FOREIGN KEY (faculty_supervisor_id) REFERENCES public.ims_faculty_supervisors(id),
	CONSTRAINT ims_internships_students_fk FOREIGN KEY (student_id) REFERENCES public.ims_students(id)
);

CREATE TABLE public.ims_internship_registries (
	id bigserial NOT NULL,
	file_path varchar NOT NULL,
	"name" varchar NOT NULL,
	"type" varchar NOT NULL,
	"date" timestamptz NOT NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	internship_id int8 NOT NULL,
	CONSTRAINT ims_internship_registries_pk PRIMARY KEY (id),
	CONSTRAINT ims_internship_registries_internships_fk FOREIGN KEY (internship_id) REFERENCES public.ims_internships(id)
);

CREATE TABLE public.ims_internship_documents (
	id bigserial NOT NULL,
	file_path varchar NOT NULL,
	"name" varchar NOT NULL,
	"type" varchar NOT NULL,
	create_date timestamptz NULL,
	update_date timestamptz NULL,
	internship_id int8 NOT NULL,
	CONSTRAINT ims_internship_documents_pk PRIMARY KEY (id),
	CONSTRAINT ims_internship_documents_internships_fk FOREIGN KEY (internship_id) REFERENCES public.ims_internships(id)
);