ALTER TABLE ims_students DROP CONSTRAINT ims_students_email_un;
ALTER TABLE ims_students DROP COLUMN email;
ALTER TABLE ims_company_supervisors DROP COLUMN email;
ALTER TABLE ims_faculty_supervisors DROP COLUMN email;

ALTER TABLE ims_users ADD COLUMN email varchar NOT NULL;
ALTER TABLE ims_users ADD CONSTRAINT ims_users_email_un UNIQUE (email);

ALTER TABLE ims_settings ADD COLUMN mail_host varchar NOT NULL;
ALTER TABLE ims_settings ADD COLUMN mail_port integer NOT NULL;
ALTER TABLE ims_settings ADD COLUMN mail_username varchar NOT NULL;
ALTER TABLE ims_settings ADD COLUMN mail_password varchar NOT NULL;