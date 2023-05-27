import random
from datetime import date

from data import data_pool as data


def generate_internship(conn, facultySupervisorIds, count):
    cur = conn.cursor()
    sIdSelectQ = "SELECT id FROM public.ims_students"
    cur.execute(sIdSelectQ)
    studentIds = cur.fetchall()
    cur.close()

    cur = conn.cursor()
    sIdSelectQ = "SELECT id FROM public.ims_companies"
    cur.execute(sIdSelectQ)
    companyIds = cur.fetchall()
    cur.close()

    cur = conn.cursor()
    for i in range(count):
        student_id = int(random.choice(studentIds)[0])
        company_id = int(random.choice(companyIds)[0])
        faculty_supervisor_id = random.choice(facultySupervisorIds)
        status = random.choice(data.internship_status)
        rndYear = data.f_random_n_digit(2)
        rndMonth = data.f_random_n_digit(1)
        start_date = f'20{rndYear}-0{rndMonth}-0{data.f_random_n_digit(1)}'
        end_date = f'20{rndYear}-0{rndMonth}-1{data.f_random_n_digit(1)}'
        create_date = date.today().strftime("%Y-%m-%d")
        days = 10

        insert_query = f"""
            INSERT INTO public.ims_internships (
                student_id, company_id, faculty_supervisor_id, status, create_date,
                update_date, start_date, end_date, days
            )
            VALUES (
                '{student_id}', '{company_id}', '{faculty_supervisor_id}', '{status}', '{create_date}',
                '{create_date}', '{start_date}', '{end_date}', '{days}'
            )
        """
        cur.execute(insert_query)
        conn.commit()
    cur.close()
    print(f"{count} internships added.")


def clear_ims_internships(conn):
    cur = conn.cursor()
    sIdSelectQ = "DELETE FROM public.ims_internships"
    cur.execute(sIdSelectQ)
    conn.commit()
    cur.close()
    print("ims_internships cleared.")
