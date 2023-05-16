import React, {useState} from 'react';
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

function App() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const handleSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        var formData = new FormData();
        formData.append("username", username);
        formData.append("password", password);

        fetch('/login', {
            method: 'POST',
            body: formData
        })
            .then((response) => {
                    console.log(response)
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
            <form onSubmit={handleSubmit}>
                <div className="form-group required">
                    <label htmlFor="exampleInputEmail1">Kullanıcı adı</label>
                    <input
                        type="text"
                        className="form-control"
                        id="exampleInputEmail1"
                        aria-describedby="emailHelp"
                        value={username}
                        onChange={(event) => setUsername(event.target.value)}
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
                    />
                </div>

                <button type="submit" className="btn btn-primary">
                    Giriş
                </button>
            </form>
        </div>
    );
}

export default App;