package tr.edu.ogu.ceng.enums;

public enum InternshipStatus {
	APPLIED, 			// staj başvurusu yapıldı ve sonuç bekleniyor.
	COMPANY_APPROVED, 	// firma staj başlangıcını onayladı.
	FACULTY_APPROVED,	// fakülte staj başlangıcını onayladı.
	ONGOING, 			// staj yapılmakta.
	COMPANY_EVALUATION_STAGE,	// staj firma değerlendirme formunun eklenmesi bekleniyor.
	FACULTY_EVALUATION_STAGE, // staj firma tarafından onaylandı, fakültenin onayını bekliyor.
	SUCCESS, 			// fakülte stajın bitişini onayladı ve resmi işlemler tamamlandı.
	
	FACULTY_REJECTED,	// fakülte stajı reddetti.
	COMPANY_REJECTED, 	// firma stajı reddetti.
	FACULTY_INVALID,	// fakülte stajı geçersiz buldu.
	CANCELED,			// staj fakülte tarafından iptal edildi.
}
