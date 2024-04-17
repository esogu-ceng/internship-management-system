ALTER TABLE ims_students ADD COLUMN cv_path TEXT;
INSERT INTO public.ims_settings ("key","value") VALUES('cv_directory', 'files');
