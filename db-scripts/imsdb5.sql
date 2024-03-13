ALTER TABLE IF EXISTS public.ims_users DROP CONSTRAINT IF EXISTS ims_users_user_types_fk;

DROP TABLE IF EXISTS ims_user_types;

ALTER TABLE IF EXISTS public.ims_users DROP COLUMN IF EXISTS user_type_id;

ALTER TABLE IF EXISTS public.ims_users ADD COLUMN user_type character varying NOT NULL;

