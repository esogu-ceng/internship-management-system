ALTER TABLE IF EXISTS public.ims_students DROP COLUMN IF EXISTS home_phone_number, DROP COLUMN IF EXISTS boulevard, 
DROP COLUMN IF EXISTS main_street, DROP COLUMN IF EXISTS street, DROP COLUMN IF EXISTS neighborhood, DROP COLUMN IF EXISTS outer_door_no,
DROP COLUMN IF EXISTS inner_door_no,DROP COLUMN IF EXISTS province, DROP COLUMN IF EXISTS subprovince, DROP COLUMN IF EXISTS village, 
DROP COLUMN IF EXISTS zip_code, DROP COLUMN IF EXISTS mother_name, DROP COLUMN IF EXISTS father_name, DROP COLUMN IF EXISTS id_card_serial_no, 
DROP COLUMN IF EXISTS id_register_province, DROP COLUMN IF EXISTS id_register_subprovince,DROP COLUMN IF EXISTS id_register_street_village, 
DROP COLUMN IF EXISTS id_register_volume_no, DROP COLUMN IF EXISTS id_register_family_serial_no, DROP COLUMN IF EXISTS id_register_serial_no, 
DROP COLUMN IF EXISTS id_registry_office, DROP COLUMN IF EXISTS id_registry_reason, DROP COLUMN IF EXISTS sgk_family, DROP COLUMN IF EXISTS sgk_self;

--CLEAR TABLE BEFORE EXECUTE NEXT LINE
ALTER TABLE IF EXISTS public.ims_students ADD COLUMN IF NOT EXISTS address character varying NOT NULL;