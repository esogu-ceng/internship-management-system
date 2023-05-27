

def setting_fill_blanks(conn):
    cur = conn.cursor()
    sIdSelectQ = "SELECT key FROM public.ims_settings"
    cur.execute(sIdSelectQ)
    keys = cur.fetchall()

    for key in keys:
        insert_query = f"""
                            UPDATE public.ims_settings
                            SET value = '{str(key[0])}_sample'
                            WHERE key = '{str(key[0])}';
                        """

        cur.execute(insert_query)
        conn.commit()

    cur.close()
    print(f"{keys.__len__()} settings updated.")
