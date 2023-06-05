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
            ) RETURNING id
        """
        cur.execute(insert_query)
        id_of_new_row = cur.fetchone()[0]
        conn.commit()
        
        # Internship Documents
        for i in range(random.randint(1, 7)):
            file_path = 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Sit voluptas expedita enim earum corrupti impedit fugit illo, itaque libero repellendus saepe eligendi ratione, harum voluptatem dolore at distinctio quod error!'
            internship_id = id_of_new_row
            name = f'Internship Document {i+1}'
            type = f'Document Type {i+1}'
            insert_query = f"""
                            INSERT INTO public.ims_internship_documents (
                                file_path, name, type, internship_id
                            )
                            VALUES (
                                '{file_path}', '{name}', '{type}', '{internship_id}'
                            ) 
                        """
            cur.execute(insert_query)
        # Evaluation Documents
        for i in range(1):
            name = f'Name{1}'
            surname = f'Surname {1}'
            file_path = 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Sit voluptas expedita enim earum corrupti impedit fugit illo, itaque libero repellendus saepe eligendi ratione, harum voluptatem dolore at distinctio quod error!'
            internship_id = id_of_new_row
            ev_form_company_id = company_id
            insert_query = f"""
                            INSERT INTO public.ims_internship_evaluate_form (
                                name, surname, file_path, internship_id, company_id
                            )
                            VALUES (
                                '{name}', '{surname}', '{file_path}', '{internship_id}', '{ev_form_company_id}'
                            ) 
                        """
            cur.execute(insert_query)
    cur.close()
    print(f"{count} internships and documents added.")


def clear_ims_internships(conn):
    cur = conn.cursor()
    sIdSelectQ = "DELETE FROM public.ims_internship_documents"
    cur.execute(sIdSelectQ)
    sIdSelectQ = "DELETE FROM public.ims_internship_evaluate_form"
    cur.execute(sIdSelectQ)
    sIdSelectQ = "DELETE FROM public.ims_internship_registries"
    cur.execute(sIdSelectQ)
    sIdSelectQ = "DELETE FROM public.ims_internships"
    cur.execute(sIdSelectQ)
    conn.commit()
    cur.close()
    print("ims_internships cleared.")
