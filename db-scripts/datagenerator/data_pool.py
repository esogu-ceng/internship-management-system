import random

person_names = [
    "Ali", "Ahmet", "Ayşe", "Fatma", "Mehmet", "Mustafa", "Hüseyin", "İbrahim", "Emin", "Emre", "Merve",
    "Derya", "Zeynep", "Deniz", "Tugba", "Burcu", "Can", "Cem", "Ceylan", "Gökhan", "Gül", "Gülşah",
    "Halil", "Hatice", "Hakan", "Kemal", "Kerem", "Kubilay", "Leyla", "Meltem", "Mert", "Nihal", "Ozan",
    "Özge", "Özkan", "Pınar", "Recep", "Seda", "Serap", "Sibel", "Şahin", "Şenol", "Tahir", "Talha",
    "Tayfun", "Tuğrul", "Uğur", "Yağmur", "Yakup", "Yılmaz", "Yusuf", "Zeki", "Zerrin", "Ahsen", "Alper",
    "Aysel", "Barış", "Bilal", "Celal", "Cengiz", "Ercan", "Erdal", "Erdem", "Esra", "Ferdi", "Ferhat",
    "Gizem", "Gökçe", "Gönül", "Gülcan", "Gülay", "Gülten", "Hakan", "Hamza", "Hilal", "İlker", "İpek",
    "İsmail", "Kamuran", "Keriman", "Kübra", "Lale", "Levent", "Mehpare", "Merve", "Mevlüt", "Nalan", "Nazlı",
    "Necati", "Nur", "Oktay", "Onur", "Orhan", "Osman", "Özcan", "Özlem", "Pınar", "Rahmi", "Ramazan", "Rasim",
    "Remzi", "Sait", "Sami", "Savas", "Sedat", "Selin", "Sevim", "Sıla", "Şadi", "Şakir", "Şeyma", "Şule", "Taylan",
    "Tolga", "Umut", "Ümit", "Volkan", "Yavuz", "Yiğit", "Yücel", "Zafer",
]

surnames = [
    'Acar', 'Aksoy', 'Altay', 'Arslan', 'Aydın', 'Aygün', 'Başar', 'Bayraktar', 'Bozkurt', 'Cengiz',
    'Çakır', 'Çetin', 'Demir', 'Doğan', 'Durmuş', 'Erdem', 'Erdoğan', 'Erkılıç', 'Ersoy', 'Gökçe',
    'Göksu', 'Gül', 'Gültekin', 'Güner', 'Güney', 'Güngör', 'Gürsoy', 'Hacıoğlu', 'İnan', 'İpek',
    'İşler', 'Kahraman', 'Kalaycı', 'Kaplan', 'Karabulut', 'Karadağ', 'Karaer', 'Karatay', 'Kaya', 'Kılıç',
    'Koç', 'Köse', 'Köylü', 'Kurt', 'Kurtuluş', 'Mavi', 'Mercan', 'Mert', 'Mete', 'Nalbantoğlu',
    'Öncel', 'Öner', 'Özdemir', 'Özkan', 'Öztürk', 'Özyılmaz', 'Pala', 'Polat', 'Sancak', 'Saygı',
    'Saygılı', 'Selçuk', 'Sert', 'Sezer', 'Sönmez', 'Söylemez', 'Süleymanoğlu', 'Şahin', 'Şanlı', 'Şeker',
    'Taşkın', 'Taşlı', 'Tekin', 'Tok', 'Topçu', 'Tuncer', 'Turan', 'Türk', 'Uçar', 'Ulu',
    'Uysal', 'Ünal', 'Üner', 'Yalçın', 'Yanık', 'Yapıcı', 'Yavuz', 'Yıldırım', 'Yılmaz', 'Yücel'
]


def f_tckn():
    """
    Generate a random 11-digit number.

    Returns:
        A string containing a random 11-digit number.
    """
    return str(random.randint(10 ** 10, 10 ** 11 - 1))


def f_random_n_digit(n):
    """
    Generate a random n-digit number.

    Args:
        n: The number of digits for the random number.

    Returns:
        A string containing a random n-digit number.
    """
    return str(random.randint(10 ** (n - 1), 10 ** n - 1))


def f_students_grade():
    """
    Generate a random float number between 1.5 and 4.0.

    Returns:
        A random float number between 1.5 and 4.0.
    """
    return round(random.uniform(1.5, 4.0), 2)


boulevard_street_names = [
    "İstiklal",
    "Bağdat",
    "Abdi İpekçi",
    "Nişantaşı Abdi İpekçi",
    "Ankara",
    "Mevlana",
    "İnönü",
    "Atatürk",
    "Büyükşehir",
    "Adnan Menderes",
    "Gazi Mustafa Kemal",
    "Kızılay",
    "Anafartalar",
    "Cinnah",
    "Tunalı Hilmi",
    "Konur",
    "Fevzi Çakmak",
    "Eskiyeni",
    "Eskişehir",
    "Dumlupınar",
    "Ege",
    "Çiğdem",
    "Karşıyaka",
    "Hacı Bayram Veli",
    "Başkent",
    "Barbaros",
    "İbrahim Gökçen",
    "Fahrettin Altay",
    "Ostim",
    "Ufuk Üniversitesi",
    "Ümitköy",
    "Köroğlu",
    "Eskişehir Yolu 9.",
    "Egekent",
    "Osmangazi",
    "Ataşehir",
    "Başkent Bulvarı 75.",
    "Ankara Çevre",
    "İzmir Çevre",
    "Adana Çevre",
    "Gaziantep Çevre",
    "Antalya Çevre",
    "Mersin Çevre",
    "Bursa Çevre",
    "Konya Çevre",
    "Gebze İzmir",
    "Kahramanmaraş",
    "Samsun",
    "Sivas"
]

mahalleler = [
    "Yavuz Selim Mahallesi",
    "Kocatepe Mahallesi",
    "Kızılay Mahallesi",
    "Dikmen Mahallesi",
    "Sıhhiye Mahallesi",
    "Bahçelievler Mahallesi",
    "Çankaya Mahallesi",
    "Eryaman Mahallesi",
    "Gölbaşı Mahallesi",
    "Keçiören Mahallesi",
    "Mamak Mahallesi",
    "Altındağ Mahallesi",
    "Yenidoğan Mahallesi",
    "Ümitköy Mahallesi",
    "Ankara Mahallesi",
    "Akyurt Mahallesi",
    "Batıkent Mahallesi",
    "Cebeci Mahallesi",
    "Demetevler Mahallesi",
    "Elvankent Mahallesi",
    "Gölhisar Mahallesi",
    "Hacıbayram Mahallesi",
    "İncek Mahallesi",
    "Karaağaç Mahallesi",
    "Kavaklıdere Mahallesi",
    "Lokmanhekim Mahallesi",
    "Mithatpaşa Mahallesi",
    "Nasuh Akar Mahallesi",
    "Ostim Mahallesi",
    "Polatlı Mahallesi",
    "Rüzgarlıbahçe Mahallesi",
    "Sincan Mahallesi",
    "Tandoğan Mahallesi",
    "Ulubey Mahallesi",
    "Varlık Mahallesi",
    "Yahyalar Mahallesi",
    "Zafer Mahallesi",
    "Adliye Mahallesi",
    "Balgat Mahallesi",
    "Çayyolu Mahallesi",
    "Demirtepe Mahallesi",
    "Etimesgut Mahallesi",
    "Gazi Mahallesi",
    "Hamamönü Mahallesi",
    "İvedik Mahallesi",
    "Kurtuluş Mahallesi",
    "Maltepe Mahallesi",
    "Nasreddin Hoca Mahallesi"
]
sehir = [
    "Adana",
    "Adıyaman",
    "Afyonkarahisar",
    "Ağrı",
    "Amasya",
    "Ankara",
    "Antalya",
    "Artvin",
    "Aydın",
    "Balıkesir",
    "Bilecik",
    "Bingöl",
    "Bitlis",
    "Bolu",
    "Burdur",
    "Bursa",
    "Çanakkale",
    "Çankırı",
    "Çorum",
    "Denizli",
    "Diyarbakır",
    "Edirne",
    "Elazığ",
    "Erzincan",
    "Erzurum",
    "Eskişehir",
    "Gaziantep",
    "Giresun",
    "Gümüşhane",
    "Hakkari",
    "Hatay",
    "Isparta",
    "Mersin",
    "İstanbul",
    "İzmir",
    "Kars",
    "Kastamonu",
    "Kayseri",
    "Kırklareli",
    "Kırşehir",
    "Kocaeli",
    "Konya",
    "Kütahya",
    "Malatya",
    "Manisa",
    "Kahramanmaraş",
    "Mardin",
    "Muğla",
    "Muş",
    "Nevşehir",
    "Niğde",
    "Ordu",
    "Rize",
    "Sakarya",
    "Samsun",
    "Siirt",
    "Sinop",
    "Sivas",
    "Tekirdağ",
    "Tokat",
    "Trabzon",
    "Tunceli",
    "Şanlıurfa",
    "Uşak",
    "Van",
    "Yozgat",
    "Zonguldak",
    "Aksaray",
    "Bayburt",
    "Karaman",
    "Kırıkkale",
    "Batman",
    "Şırnak",
    "Bartın",
    "Ardahan",
    "Iğdır",
    "Yalova",
    "Karabük",
    "Kilis",
    "Osmaniye",
    "Düzce"
]

subprovince = ['Adalar', 'Arnavutköy', 'Ataşehir', 'Avcılar', 'Bağcılar', 'Bahçelievler', 'Bakırköy', 'Başakşehir',
               'Bayrampaşa', 'Beşiktaş', 'Beykoz', 'Beylikdüzü', 'Beyoğlu', 'Büyükçekmece', 'Çatalca', 'Çekmeköy',
               'Esenler',
               'Esenyurt', 'Eyüp', 'Fatih', 'Gaziosmanpaşa', 'Güngören', 'Kadıköy', 'Kağıthane', 'Kartal',
               'Küçükçekmece',
               'Maltepe', 'Pendik', 'Sancaktepe', 'Sarıyer', 'Silivri', 'Sultanbeyli', 'Sultangazi', 'Şile', 'Şişli',
               'Tuzla', 'Ümraniye', 'Üsküdar', 'Zeytinburnu', 'Aliağa', 'Bayındır', 'Bergama', 'Bornova', 'Buca',
               'Çeşme',
               'Çiğli', 'Foça', 'Gaziemir', 'Güzelbahçe', 'Karabağlar', 'Karaburun']

village_names = ['Akçakent', 'Bağlıca', 'Çayköy', 'Dereköy', 'Esenkent', 'Fındıklı', 'Gölyaka', 'Hacılar', 'İncirliova',
                 'Karaköy', 'Kızılcaköy', 'Köseler', 'Narlıca', 'Ortakent', 'Pınarcık', 'Sarıyer', 'Taşköy', 'Uğurlu',
                 'Yalı',
                 'Zeytinburnu', 'Akyurt', 'Beyler', 'Çukuroren', 'Demirkent', 'Eceabat', 'Göçbeyli', 'Hacılar',
                 'İncesu',
                 'Karaköy', 'Köşk', 'Narlıdere', 'Ortaköy', 'Pınarbaşı', 'Sarıköy', 'Taşlıca', 'Uğurkent', 'Yalıncak',
                 'Zeytinköy',
                 'Akşemsettin', 'Beyli', 'Çukurca', 'Derinkuyu', 'Eflani', 'Göçeri', 'Hacıveli', 'İncirli', 'Karaağaç',
                 'Köşklüçiftlik',
                 'Narman', 'Ortaköprü']

id_registry_reasons = ['Doğum', 'Kayıp', 'Değiştirm']

company_data = [
    ("Kuzey Ticaret", "Bir ticaret şirketi.", "Ticaret"),
    ("Akdeniz İnşaat", "İnşaat sektöründe faaliyet gösteren bir firma.", "İnşaat"),
    ("Doğu Bankası", "Bankacılık hizmetleri sunan bir finans kuruluşu.", "Bankacılık"),
    ("Batı Enerji", "Enerji sektöründe faaliyet gösteren bir şirket.", "Enerji"),
    ("Güney Gıda", "Gıda sektöründe hizmet veren bir firma.", "Gıda"),
    ("Merkez Otomotiv", "Otomotiv sektöründe faaliyet gösteren bir şirket.", "Otomotiv"),
    ("Anadolu Teknoloji", "Teknoloji alanında faaliyet gösteren bir şirket.", "Teknoloji"),
    ("Ege Turizm", "Turizm sektöründe hizmet veren bir firma.", "Turizm"),
    ("Marmara Medya", "Medya ve iletişim alanında faaliyet gösteren bir şirket.", "Medya ve İletişim"),
    ("Çukurova Tarım", "Tarım sektöründe faaliyet gösteren bir şirket.", "Tarım"),
    ("Karadeniz Havayolları", "Havayolu şirketi.", "Havayolu"),
    ("İç Anadolu Mobilya", "Mobilya sektöründe hizmet veren bir firma.", "Mobilya"),
    ("Trakya Kimya", "Kimya sektöründe faaliyet gösteren bir şirket.", "Kimya"),
    ("Yıldız Elektronik", "Elektronik ürünler satan bir şirket.", "Elektronik"),
    ("Aegean Sağlık", "Sağlık sektöründe hizmet veren bir firma.", "Sağlık"),
    ("Black Sea İletişim", "İletişim sektöründe faaliyet gösteren bir şirket.", "İletişim"),
    ("Central İnşaat", "İnşaat sektöründe faaliyet gösteren bir şirket.", "İnşaat"),
    ("Mediterranean Gemi", "Deniz taşımacılığı yapan bir firma.", "Deniz Taşımacılığı"),
    ("Eastbank", "Bankacılık hizmetleri sunan bir finans kuruluşu.", "Bankacılık"),
    ("West Energy", "Enerji sektöründe faaliyet gösteren bir şirket.", "Enerji"),
    ("South Food", "Gıda sektöründe hizmet veren bir firma.", "Gıda"),
    ("Central Motors", "Otomotiv sektöründe faaliyet gösteren bir şirket.", "Otomotiv"),
    ("Anatolian Technology", "Teknoloji alanında faaliyet gösteren bir şirket.", "Teknoloji")]

internship_status = [
    "PENDING",
    "APPROVED",
    "REJECTED"
]
