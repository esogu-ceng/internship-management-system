
def addADMIN(conn):
    cur = conn.cursor()
    insert_query = f"""
                INSERT INTO public.ims_users (
                    username, password, email, user_type, language
                )
                VALUES (
                    'ykartal@ogu.edu.tr', 'sdfasdfadfasdfasdfasdf', 'ykartal@ogu.edu.tr', 'ADMIN', '1'
                ) RETURNING id
            """

    cur.execute(insert_query)
    conn.commit()
    cur.close()


def clear_ims_users(conn):
    cur = conn.cursor()
    Q = "DELETE FROM public.ims_users"
    cur.execute(Q)
    cur.close()
    print("ims_users cleared.")