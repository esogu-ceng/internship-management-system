import random

from data import data_pool as data


def generate_company_companySupervisor(conn, count):
    cur = conn.cursor()

    # COMPANY

    lap = 0
    uniqueizer = ""
    for i in range(count):
        if i - lap == data.company_data.__len__():
            lap += data.company_data.__len__()
            uniqueizer += str(int(lap / data.company_data.__len__()))

        name, description, scope = data.company_data[i - lap]
        name += uniqueizer
        address = f"""{str(random.choice(data.boulevard_street_names))} {str(random.choice(data.boulevard_street_names))}\
 {str(random.choice(data.boulevard_street_names))} {str(random.choice(data.mahalleler))}\
 {data.f_random_n_digit(2)} {data.f_random_n_digit(2)} {data.f_random_n_digit(1)}\
 {str(random.choice(data.sehir))} {random.choice(data.subprovince)} {str(random.choice(data.village_names))}"""

        phone_number = f"5{str(data.f_random_n_digit(8))}"
        fax_number = f"5{str(data.f_random_n_digit(8))}"
        email = f"{name}@mail.com".lower().replace(" ", "")

        insert_query = f"""
            INSERT INTO public.ims_companies (
                name, address, phone_number, fax_number, email, description, scope
            )
            VALUES (
                '{name}', '{address}', '{phone_number}', '{fax_number}', '{email}', '{description}', '{scope}'
            ) RETURNING id
        """
        cur.execute(insert_query)
        id_of_new_row = cur.fetchone()[0]
        conn.commit()

        # USER AND COMPANYSUPERVISOR

        name = str(random.choice(data.person_names))
        surname = str(random.choice(data.surnames))
        phone_number = str(data.f_random_n_digit(9))
        company_id = str(id_of_new_row)

        insert_query = f"""
                           INSERT INTO public.ims_users (
                               username, password, email, user_type, language
                           )
                           VALUES (
                               '{(name[0] + surname).lower() + i.__str__()}cs', '123', '{(name[0] + surname).lower() + i.__str__()}cs@ogu.edu.tr', 'COMPANYSUPERVISOR', '1'
                           ) RETURNING id
                       """
        cur.execute(insert_query)
        user_id = cur.fetchone()[0]
        conn.commit()
        insert_query = f"""
                           INSERT INTO public.ims_company_supervisors (
                               name, surname, phone_number, user_id, company_id
                           )
                           VALUES (
                               '{name}', '{surname}', '{phone_number}', '{user_id}', '{company_id}'
                           ) RETURNING id
                       """
        cur.execute(insert_query)
        conn.commit()

    cur.close()
    print(f"{count} companies added.")


def clear_ims_companies(conn):
    cur = conn.cursor()
    sIdSelectQ = "DELETE FROM public.ims_companies"
    cur.execute(sIdSelectQ)
    cur.close()
    print("ims_companies cleared.")


def clear_ims_company_supervisors(conn):
    cur = conn.cursor()
    sIdSelectQ = "DELETE FROM public.ims_company_supervisors"
    cur.execute(sIdSelectQ)
    cur.close()
    print("ims_company_supervisors cleared.")
