import psycopg2

from caller import caller

# dolu bi veritabanına clear yapmadan insert yapmak hatalara yol açabilir
CLEAR = True
INSERT = True

# BENİ DÜZENLE
conn = psycopg2.connect(
    host="localhost",
    database="internship-management-system",
    user="postgres",
    password="123456"
)

# SAYILARI DEĞİŞTİREBİLİRSİNİZ
caller(conn=conn, clear=CLEAR, insert=INSERT, counts={
    'student_count': 200,
    'internship_count': 200,
    'company_companySupervisor_count': 23,  # max 23 or company content will be repetitive
    'facultySupervisor_count': 50
})

conn.close()
