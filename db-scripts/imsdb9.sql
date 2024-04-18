ALTER TABLE IF EXISTS public.ims_students DROP COLUMN IF EXISTS home_phone_number, DROP COLUMN IF EXISTS boulevard, 
DROP COLUMN IF EXISTS main_street, DROP COLUMN IF EXISTS street, DROP COLUMN IF EXISTS neighborhood, DROP COLUMN IF EXISTS outer_door_no,
DROP COLUMN IF EXISTS inner_door_no,DROP COLUMN IF EXISTS province, DROP COLUMN IF EXISTS subprovince, DROP COLUMN IF EXISTS village, 
DROP COLUMN IF EXISTS zip_code, DROP COLUMN IF EXISTS mother_name, DROP COLUMN IF EXISTS father_name, DROP COLUMN IF EXISTS id_card_serial_no, 
DROP COLUMN IF EXISTS id_register_province, DROP COLUMN IF EXISTS id_register_subprovince,DROP COLUMN IF EXISTS id_register_street_village, 
DROP COLUMN IF EXISTS id_register_volume_no, DROP COLUMN IF EXISTS id_register_family_serial_no, DROP COLUMN IF EXISTS id_register_serial_no, 
DROP COLUMN IF EXISTS id_registry_office, DROP COLUMN IF EXISTS id_registry_reason, DROP COLUMN IF EXISTS sgk_family, DROP COLUMN IF EXISTS sgk_self;

--CLEAR TABLE BEFORE EXECUTE NEXT LINE
ALTER TABLE IF EXISTS public.ims_students ADD COLUMN IF NOT EXISTS address character varying NOT NULL;

--DELETE app_port and app_host lines from settings
DELETE FROM ims_settings WHERE key = 'app_host';
DELETE FROM ims_settings WHERE key = 'app_port';

-- Add show/disable and UI name to settings for now later will be implemented in Setting.py
ALTER TABLE ims_settings ADD show_disable boolean NOT NULL DEFAULT TRUE;
ALTER TABLE ims_settings ADD uiname varchar;

-- Add attributes to existed rows in settigns
UPDATE ims_settings SET uiname = 'Mail Host' WHERE key = 'mail_host';
UPDATE ims_settings SET uiname = 'Mail Port' WHERE key = 'mail_port';
UPDATE ims_settings SET uiname = 'Mail Username' WHERE key = 'mail_username';
UPDATE ims_settings SET uiname = 'Mail Password' WHERE key = 'mail_password';
UPDATE ims_settings SET uiname = 'Upload Directory' WHERE key = 'upload_directory';
