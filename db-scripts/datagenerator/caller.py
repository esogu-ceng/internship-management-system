from methods import Setting as settingOp, \
    User as userOp, \
    Language as languageOp, \
    Student as studentOp, \
    Company as companyOp, \
    Internship as internshipOp, \
    Faculty as facultyOp


def caller(conn, only_clear, counts):

    print("\nCLEAR:")
    internshipOp.clear_ims_internships(conn)
    studentOp.clear_ims_students(conn)
    facultyOp.clear_ims_faculty_supervisors(conn)
    facultyOp.clear_ims_faculties(conn)
    companyOp.clear_ims_company_supervisors(conn)
    companyOp.clear_ims_companies(conn)
    userOp.clear_ims_users(conn)
    languageOp.clear_ims_language(conn)

    if not only_clear:
        print("\nINSERT:")
        languageOp.generate_language(conn)
        userOp.addADMIN(conn)
        facultyIds, facultySupervisorIds = facultyOp.generate_faculty_facultySupervisor(conn, counts['facultySupervisor_count'])
        studentOp.generate_student(conn, facultyIds, counts['student_count'])
        companyOp.generate_company_companySupervisor(conn, counts['company_companySupervisor_count'])
        internshipOp.generate_internship(conn, facultySupervisorIds, counts['internship_count'])
        settingOp.setting_fill_blanks(conn)
