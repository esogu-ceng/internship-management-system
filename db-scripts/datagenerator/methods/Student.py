import random
from datetime import date

from data import data_pool as data


def generate_student(conn, facultyIds, count):

    cur = conn.cursor()
    for i in range(count):
        name = str(random.choice(data.person_names))
        surname = str(random.choice(data.surnames))
        tckn = str(data.f_random_n_digit(11))
        student_no = i
        grade = str(data.f_students_grade())
        phone_number = str(data.f_random_n_digit(9))
        home_phone_number = str(data.f_random_n_digit(10))
        boulevard = str(random.choice(data.boulevard_street_names))
        main_street = str(random.choice(data.boulevard_street_names))
        street = str(random.choice(data.boulevard_street_names))
        neighborhood = str(random.choice(data.mahalleler))
        outer_door_no = data.f_random_n_digit(2)
        inner_door_no = data.f_random_n_digit(1)
        province = str(random.choice(data.sehir))
        subprovince = random.choice(data.subprovince)
        village = str(random.choice(data.village_names))
        zip_code = data.f_random_n_digit(5)
        mother_name = str(random.choice(data.person_names))
        father_name = str(random.choice(data.person_names))
        birth_place = str(random.choice(data.sehir))
        birth_date = f'199{data.f_random_n_digit(1)}-0{data.f_random_n_digit(1)}-1{data.f_random_n_digit(1)}'
        id_card_serial_no = data.f_random_n_digit(5)
        id_register_province = str(random.choice(data.sehir))
        id_register_subprovince = str(random.choice(data.subprovince))
        id_register_street_village = str(random.choice(data.village_names))
        id_register_volume_no = data.f_random_n_digit(5)
        id_register_family_serial_no = data.f_random_n_digit(5)
        id_register_serial_no = data.f_random_n_digit(5)
        id_registry_office = f'{random.choice(data.subprovince)} Nüfus Müdürlüğü'
        id_registry_reason = str(random.choice(data.id_registry_reasons))
        sgk_family = False
        sgk_self = True
        create_date = date.today().strftime("%Y-%m-%d")
        update_date = date.today().strftime("%Y-%m-%d")
        faculty_id = random.choice(facultyIds)
        insert_query = f"""
                    INSERT INTO public.ims_users (
                        username, password, email, user_type, language
                    )
                    VALUES (
                        '{(name[0] + surname).lower() + i.__str__()}', '123', '{(name[0] + surname).lower() + i.__str__()}@ogu.edu.tr', 'STUDENT', '1'
                    ) RETURNING id
                """

        cur.execute(insert_query)
        id_of_new_row = cur.fetchone()[0]
        conn.commit()
        insert_query = f"""
            INSERT INTO public.ims_students (
                name, surname, tckn, student_no, grade, phone_number, home_phone_number, boulevard, main_street, 
                street, neighborhood, outer_door_no, inner_door_no, province, subprovince, village, zip_code, 
                mother_name, father_name, birth_place, birth_date, id_card_serial_no, id_register_province, 
                id_register_subprovince, id_register_street_village, id_register_volume_no, id_register_family_serial_no, 
                id_register_serial_no, id_registry_office, id_registry_reason, sgk_family, sgk_self, create_date, update_date, 
                user_id, faculty_id
            )
            VALUES (
                '{name}', '{surname}', '{tckn}', '{'1521%04d' % student_no}', '{grade}', '{phone_number}', '{home_phone_number}', 
                '{boulevard}', '{main_street}', '{street}', '{neighborhood}', '{outer_door_no}', '{inner_door_no}', 
                '{province}', '{subprovince}', '{village}', '{zip_code}', '{mother_name}', '{father_name}', '{birth_place}', 
                '{birth_date}', '{id_card_serial_no}', '{id_register_province}', '{id_register_subprovince}', 
                '{id_register_street_village}', '{id_register_volume_no}', '{id_register_family_serial_no}', 
                '{id_register_serial_no}', '{id_registry_office}', '{id_registry_reason}', '{sgk_family}', '{sgk_self}', 
                '{create_date}', '{update_date}', '{id_of_new_row}', '{faculty_id}'
            )
        """
        cur.execute(insert_query)
        conn.commit()
    cur.close()
    print(f"{count} students added.")


def clear_ims_students(conn):
    cur = conn.cursor()
    sIdSelectQ = "DELETE FROM public.ims_students"
    cur.execute(sIdSelectQ)
    cur.close()
    print("ims_students cleared.")
