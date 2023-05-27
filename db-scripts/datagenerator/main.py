import psycopg2

from caller import caller

# sadece temizleme yapmak için True yapabilirsiniz
ONLY_CLEAR = False

# BENİ DÜZENLE
conn = psycopg2.connect(
    host="localhost",
    database="internship-management-system",
    user="postgres",
    password="123456"
)

# SAYILARI DEĞİŞTİREBİLİRSİNİZ
caller(conn=conn, only_clear=ONLY_CLEAR, counts={
    'student_count': 200,
    'internship_count': 200,
    'company_companySupervisor_count': 23,  # max 23 or company content will be repetitive
    'facultySupervisor_count': 50
})

conn.close()
