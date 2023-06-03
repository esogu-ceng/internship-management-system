Veritabanında verilerin kolay yoldan oluşturulması için bir python scripti.

imsdb9.sql veritabanı güncellemesinden sonra tasarlanmıştır. Üstüne güncelleme gelmiş ise düzenlenmesi gerekebilir.

Güncel olarak doldurduğu tablolar:
    ims_companies,
    ims_company_supervisors,
    ims_faculties,
    ims_faculty_supervisors,
    ims_internships,
    ims_language,
    ims_settings,
    ims_students,
    ims_users.

Çalıştırmak için main.py dosyasını koşturmanız yeterlidir.

Admin kullanıcı bilgileri -> ykartal@ogu.edu.tr sdfasdfadfasdfasdfasdf
Admin dışında kullanıcı şifreleri -> 123

Not: Çalıştımak için psycopg2 kütüphanesini "pip install psycopg2" komutu ile yükleyebilirsiniz.

Not: Var olan verilerinizi bozmamak için halihazırda bulunan veritabanınınzda çalıştırmak yerine yeni bir veritabanı açmanızı öneririz.