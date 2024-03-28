import React, {useState} from "react";
import {toast, ToastContainer} from "react-toastify";
import {Button, Form, Modal, Spinner} from "react-bootstrap";
import 'react-toastify/dist/ReactToastify.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faKey} from '@fortawesome/free-solid-svg-icons';

const initialError = {
    title: "",
    message: "",
};

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [email, setEmail] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(initialError);

    const handleForgotPassword = () => {
        setShowModal(true);
    };

    const handleModalClose = () => {
        setError(initialError);
        setEmail("");
        setShowModal(false);
    };

    const handleResetPassword = () => {
        fetch('/public/forgot-password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }, body: JSON.stringify({email}),
        }).then((response) => {
            setIsLoading(false);
            if (response.ok === true) {
                setError(initialError)
                toast.success("Link başarıyla gönderildi")
                setShowModal(false);
            } else{
                setError({
                    title: "E-postanız bulunamadı!",
                    message: "Lütfen sistem yöneticisi ile iletişime geçin!",
                  });            }

        }).catch((error) => {
            setIsLoading(false);
            toast.error("Bir hata oluştu!", {autoClose: 2000})

        });
        setIsLoading(true);
    };

    const handleSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        var formData = new FormData();
        formData.append("username", username);
        formData.append("password", password);

        const domain = process.env.REACT_APP_API_BASE_URI;
        const login = `${domain}login`;
        // Bu ksımı daha düzgün yazarsınız artık
        fetch(login, {
            method: 'POST',
            body: formData
        })
            .then((response) => {
                    if (response.redirected === true) {
                        window.location.href = response.url
                    } else
                        toast.error('Kullanıcı adı ya da parola yanlış!', {autoClose: 2000});
                }
            ).catch((error) => {
            toast.error("Bir hata oluştu!", {autoClose: 2000})
            console.log(error);
        });

        
    };
    return (
        <div className="container">
            <ToastContainer/>
            <div className="login-page">
                <Form className="form-login" onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="exampleInputEmail1">Kullanıcı adı</label>
                        <input
                            type="text"
                            className="form-control"
                            id="exampleInputEmail1"
                            aria-describedby="emailHelp"
                            value={username}
                            onChange={(event) => setUsername(event.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="exampleInputPassword1">Şifre</label>
                        <input
                            type="password"
                            className="form-control"
                            id="exampleInputPassword1"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            required

                        />
                    </div>
                    <div className="form-group-forgot-password-link">
                        <a onClick={handleForgotPassword}>
                            Şifrenizi mi unuttunuz?
                        </a>
                    </div>
                    <Button type="submit" className="submit-btn">
                        Giriş
                    </Button>
                    <Modal show={showModal} onHide={handleModalClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>Şifrenizi mi unuttunuz?</Modal.Title>

                        </Modal.Header>
                        <Modal.Body>
                            <div className="d-flex justify-content-center mb-3">
                                <FontAwesomeIcon icon={faKey} size="5x"/>
                            </div>
                            <div>
                                E-posta adresinizi girin. E-posta adresinize şifrenizi sıfırlamanız için bir bağlantı
                                göndereceğiz.
                            </div>
                            <div className="form-group-send-link">
                                <input
                                    placeholder="E-posta"
                                    type="email"
                                    className="form-control"
                                    id="emailInput"
                                    value={email}
                                    onChange={(event) => setEmail(event.target.value)}
                                    required
                                />
                            </div>
                            {error.message && (
                                <div className="error-container">
                                    <h2>{error.title}</h2>
                                    <p>{error.message}</p>
                                </div>
                            )}
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" className="cancel-btn" onClick={handleModalClose}>
                                İptal
                            </Button>
                            <Button variant="primary" className="submit-btn" onClick={handleResetPassword} disabled={isLoading}>
                                {isLoading ? (
                                    <>
                                        <Spinner animation="border" role="status"/>
                                        <span className="ml-2">Mail gönderiliyor...</span>
                                    </>
                                ) : (
                                    "Şifreyi sıfırla"
                                )}
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </Form>
            </div>
        </div>
    );
}
export default Login;