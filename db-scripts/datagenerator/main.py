import psycopg2

from caller import caller

# BENİ DÜZENLE
conn = psycopg2.connect(
    host="localhost",
    database="internship-management-system",
    user="postgres",
    password="123456"
)

# SAYILARI DEĞİŞTİREBİLİRSİNİZ
caller(conn, {
    'student_count': 200,
    'internship_count': 200,
    'company_companySupervisor_count': 23,  # max 23 or companies will be duplicated
    'facultySupervisor_count': 50
})

conn.close()
