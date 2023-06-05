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
        address = ""
        address += str(random.choice(data.boulevard_street_names)) + " "
        address += str(random.choice(data.boulevard_street_names)) + " "
        address += str(random.choice(data.boulevard_street_names)) + " "
        address += str(random.choice(data.mahalleler)) + " "
        address += data.f_random_n_digit(2) + " "
        address += data.f_random_n_digit(1) + " "
        address += str(random.choice(data.sehir)) + " "
        address += random.choice(data.subprovince) + " "
        address += str(random.choice(data.village_names)) + " "
        address += data.f_random_n_digit(5)

        birth_place = str(random.choice(data.sehir))
        birth_date = f'199{data.f_random_n_digit(1)}-0{data.f_random_n_digit(1)}-1{data.f_random_n_digit(1)}'

        faculty_id = random.choice(facultyIds)
        activity = data.activityRandomizer()
        insert_query = f"""
                    INSERT INTO public.ims_users (
                        username, password, email, user_type, language, activity
                    )
                    VALUES (
                        '{(name[0] + surname).lower() + i.__str__()}', '123', '{(name[0] + surname).lower() + i.__str__()}@ogu.edu.tr', 'STUDENT', '1', {activity}
                    ) RETURNING id
                """

        cur.execute(insert_query)
        id_of_new_row = cur.fetchone()[0]
        conn.commit()
        insert_query = f"""
            INSERT INTO public.ims_students (
                name, surname, tckn, student_no, grade, phone_number,
                birth_place, birth_date,
                user_id, faculty_id, address
            )
            VALUES (
                '{name}', '{surname}', '{tckn}', '{'1521%04d' % student_no}', '{grade}', '{phone_number}', 
                '{birth_place}', '{birth_date}',
                '{id_of_new_row}', '{faculty_id}', '{address}'
            )
        """
        cur.execute(insert_query)
        conn.commit()
    cur.close()
    print(f"{count} students and users added.")


def clear_ims_students(conn):
    cur = conn.cursor()
    sIdSelectQ = "DELETE FROM public.ims_students"
    cur.execute(sIdSelectQ)
    conn.commit()
    cur.close()
    print("ims_students cleared.")
