import React, { useState, ChangeEvent, FormEvent } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { Button, Form } from 'react-bootstrap';

const ResetPassword = () => {
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const navigate = useNavigate();

    const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    };

    const handleConfirmPasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
        setConfirmPassword(e.target.value);
    };

    const checkPasswordMatch = () => {
        if (password !== '' && confirmPassword !== '')
            return password === confirmPassword;
        return true;
    };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        const hash = new URLSearchParams(window.location.search).get('hash');
        await axios.post('/public/update-password', {
            password: password,
            confirmPassword: confirmPassword,
            hash: hash
        })
            .then(response => {
                if (response.status === 200) {
                    toast.success('Şifre başarılı bir şekilde güncellendi. Anasayfaya yönlendiriliyorsunuz...', { autoClose: 2000 })
                    navigate('/public/');
                }
            })
            .catch((error) => {
                toast.error('Bir hata oluştu!', { autoClose: 2000 });
            })
    };

    return (
        <div className='container'>
            <ToastContainer />
            <Form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="password">Yeni Şifre</label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="confirmPassword">Yeni Şifre Tekrar</label>
                    <input
                        type="password"
                        className="form-control"
                        id="confirmPassword"
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        required
                    />
                </div>
                {!checkPasswordMatch() ? <p className='text-danger'>Girilen şifreler eşleşmiyor!</p> : null}
                <Button className='mt-3' type="submit" disabled={!checkPasswordMatch()}> Kaydet</Button>
            </Form>
        </div>
    );
};

export default ResetPassword;