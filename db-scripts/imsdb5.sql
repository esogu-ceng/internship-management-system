ALTER TABLE ims_internship_evaluate_form
    ADD COLUMN question1 INT,
ADD COLUMN question2 INT,
ADD COLUMN question3 INT,
ADD COLUMN question4 INT,
ADD COLUMN question5 INT,
ADD COLUMN question6 INT,
ADD COLUMN question7 INT,
ADD COLUMN question8 INT;

ALTER TABLE ims_internship_evaluate_form DROP COLUMN name, DROP COLUMN surname, DROP COLUMN file_path;