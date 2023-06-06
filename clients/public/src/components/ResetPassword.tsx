import React, { useState, ChangeEvent, FormEvent } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { Button, Form } from 'react-bootstrap';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faKey} from '@fortawesome/free-solid-svg-icons';

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

    const isPasswordValid = () => {
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()\-_=+\\|[\]{};:'",.<>/?]{8,}$/;
        return passwordRegex.test(password) && checkPasswordMatch();
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
            <Form className='reset-password-page' onSubmit={handleSubmit}>
                <div className='justify-content-start mb-3'>
                     <FontAwesomeIcon icon={faKey} size="3x"/>
                     <span className='ml-4 fs-5 fw-bold'>Şifrenizi belirleyin</span>
                </div>
                <div>
                    <label className='mb-2' htmlFor="password">Yeni Şifre</label>
                    <input
                        type="password"
                        className="form-control mb-2"
                        id="password"
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                </div>
                <div>
                    <label className='mb-2' htmlFor="confirmPassword">Yeni Şifre Tekrar</label>
                    <input
                        type="password"
                        className="form-control mb-2"
                        id="confirmPassword"
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        required
                    />
                </div>
                {!checkPasswordMatch() ? <small className='text-danger mt-0 mb-0 not-matched'>Girilen şifreler eşleşmiyor!</small> : null}
                <Button className='submit-btn' type="submit" disabled={!isPasswordValid()}> Kaydet</Button>
                <div>
                    <ul className='mt-2 pl-4 password-check'>
                        <li className={password.length >= 8 ? 'valid' : 'invalid'}>
                            En az 8 karakter
                        </li>
                        <li className={/\d/.test(password) ? 'valid' : 'invalid'}>
                            En az bir rakam (0-9)
                        </li>
                        <li className={/[A-Za-z]/.test(password) ? 'valid' : 'invalid'}>
                            En az bir harf (A-Z)
                        </li>
                    </ul>
                </div>
            </Form>
        </div>
    );
};

export default ResetPassword;
