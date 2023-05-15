import data_pool as data
import random


def generateCompanies(conn, count):
    cur = conn.cursor()
    if count > data.company_data.__len__(): count = data.company_data.__len__()
    for i in range(count):
        name, description, scope = data.company_data[i]
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
            )
        """
        cur.execute(insert_query)
        conn.commit()
    cur.close()
    print(f"{count} companies added.")


def clear(conn):
    cur = conn.cursor()
    sIdSelectQ = "DELETE FROM public.ims_companies"
    cur.execute(sIdSelectQ)
    cur.close()
    print("ims_companies cleared.")
