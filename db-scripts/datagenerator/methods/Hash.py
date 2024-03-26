import bcrypt

def hash_password(password):
    salt = bcrypt.gensalt(log_rounds=10)
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), salt)
    return hashed_password