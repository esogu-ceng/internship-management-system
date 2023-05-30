import React from "react";
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from "react-router-dom";
import Header from './components/Header';
import Footer from './components/Footer';
import Login from './components/Login';
import ResetPassword from './components/ResetPassword';
import Companies from './components/Companies';
import { ToastContainer } from "react-toastify";

function App() {
  const public_url: string = process.env.PUBLIC_URL;

    return (
        <div className="container">
            <Header/>
            <ToastContainer/>
            <BrowserRouter>
                <Routes>
                    <Route path={`${public_url}`} element={<Login/>}/>
                    <Route path={`${public_url}/reset-password`} element={<ResetPassword/>}/>
                    <Route path={`${public_url}/public/companies`} element={<Companies/>}/>
                </Routes>
            </BrowserRouter>
            <Footer/>
        </div>
    );
}

export default App;
