import random

from data.data_pool import facultyNames
from data.data_pool import person_names, surnames, f_random_n_digit


def generate_faculty_facultySupervisor(conn, count):
    cur = conn.cursor()

    facultyIds = []
    for i in range(facultyNames.__len__()):
        insert_query = f"""
                            INSERT INTO public.ims_faculties (
                                name
                            )
                            VALUES ('{facultyNames[i]}') RETURNING id
                        """

        cur.execute(insert_query)
        facultyIds.append(cur.fetchone()[0])
        conn.commit()

    facultySupervisorIds = []
    for i in range(count):
        name = str(random.choice(person_names))
        surname = str(random.choice(surnames))
        phone_number = str(f_random_n_digit(9))
        supervisor_no = (i)
        faculty_id = str(random.choice(facultyIds))

        insert_query = f"""
                    INSERT INTO public.ims_users (
                        username, password, email, user_type, language
                    )
                    VALUES (
                        '{(name[0] + surname).lower() + i.__str__()}fs', '123', '{(name[0] + surname).lower() + i.__str__()}fs@ogu.edu.tr', 'FACULTYSUPERVISOR', '1'
                    ) RETURNING id
                """
        cur.execute(insert_query)
        user_id = cur.fetchone()[0]
        conn.commit()
        insert_query = f"""
                    INSERT INTO public.ims_faculty_supervisors (
                        name, surname, phone_number, supervisor_no, user_id, faculty_id
                    )
                    VALUES (
                        '{name}', '{surname}', '{phone_number}', '{supervisor_no}', '{user_id}', '{faculty_id}'
                    ) RETURNING id
                """
        cur.execute(insert_query)
        facultySupervisorIds.append(cur.fetchone()[0])
        conn.commit()

    cur.close()
    return facultyIds, facultySupervisorIds


def clear_ims_faculty_supervisors(conn):
    cur = conn.cursor()
    Q = "DELETE FROM public.ims_faculty_supervisors"
    cur.execute(Q)
    cur.close()
    print("ims_faculty_supervisors cleared.")


def clear_ims_faculties(conn):
    cur = conn.cursor()
    Q = "DELETE FROM public.ims_faculties"
    cur.execute(Q)
    cur.close()
    print("ims_faculties cleared.")