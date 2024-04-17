import psycopg2
from caller import caller
import configparser

# Read the configuration file
config = configparser.ConfigParser()

# Add a dummy section header to the configuration
dummy_section = 'dummy_section'
config[dummy_section] = {}

# Read the properties file
with open('src/main/resources/application-dev.properties', 'r') as file:
    config_text = file.read()

# Populate the dummy section with properties
for line in config_text.splitlines():
    if '=' in line:
        key, value = line.split('=', 1)
        config[dummy_section][key.strip()] = value.strip()

# Get the values
database_url = config.get(dummy_section, 'spring.datasource.url').split('/')[-1]
database_username = config.get(dummy_section, 'spring.datasource.username')
database_password = config.get(dummy_section, 'spring.datasource.password')

# sadece temizleme yapmak için True yapabilirsiniz
ONLY_CLEAR = False

# BENİ DÜZENLE
conn = psycopg2.connect(
    host="localhost",
    database=database_url,
    user=database_username,
    password=database_password
)
print("dshkj")
# SAYILARI DEĞİŞTİREBİLİRSİNİZ
caller(conn=conn, only_clear=ONLY_CLEAR, counts={
    'student_count': 200,
    'internship_count': 200,
    'company_companySupervisor_count': 23,  # max 23 or company content will be repetitive
    'facultySupervisor_count': 50
})

conn.close()
