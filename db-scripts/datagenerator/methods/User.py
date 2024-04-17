
def addADMIN(conn):
    cur = conn.cursor()
    insert_query = f"""
                INSERT INTO public.ims_users (
                    password, email, user_type, language, activity
                )
                VALUES (
                    '$2a$10$0JcB6.kytS7h215ptvjVxOitKMeth3IQriCXmLUUFvStp.6wKqftm', 'ykartal@ogu.edu.tr', 'ADMIN', '1', true
                ) RETURNING id
            """
    cur.execute(insert_query)
    conn.commit()
    cur.close()
    print(f"ADMIN user added. (ykartal@ogu.edu.tr:$2a$10$0JcB6.kytS7h215ptvjVxOitKMeth3IQriCXmLUUFvStp.6wKqftm)")


def clear_ims_users(conn):
    cur = conn.cursor()
    Q = "DELETE FROM public.ims_users"
    cur.execute(Q)
    cur.close()
    conn.commit()
    print("ims_users cleared.")