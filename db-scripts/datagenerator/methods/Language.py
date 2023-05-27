
def generate_language(conn):
    cur = conn.cursor()
    insert_query = f"""
                        INSERT INTO public.ims_language (
                            id, name, flag, language_abbr, country_abbr, country
                        )
                        VALUES 
                        (1, 'turkish', 'f', 'tr', 'TR', 'turkey'),
                        (2, 'english', 'f', 'en', 'EN', 'england')
                        RETURNING id
                    """

    cur.execute(insert_query)
    conn.commit()
    cur.close()


def clear_ims_language(conn):
    cur = conn.cursor()
    Q = "DELETE FROM public.ims_language"
    cur.execute(Q)
    cur.close()
    print("ims_language cleared.")