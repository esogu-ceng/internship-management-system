import generate_rows_company as companyOp
import generate_rows_student as studentOp
import generate_rows_internship as internshipOp

import psycopg2

# BENİ DÜZENLE
conn = psycopg2.connect(
    host="localhost",
    database="internship-management-system",
    user="postgres",
    password="123456"
)

internshipOp.clear(conn)
studentOp.clear(conn)
companyOp.clear(conn)

studentOp.generateStudents(conn, 2000)
companyOp.generateCompanies(conn, 20)  # max 23
internshipOp.generateInternShips(conn, 1000)

conn.close()
