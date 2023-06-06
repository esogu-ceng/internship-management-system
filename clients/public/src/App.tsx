import React, { useState } from 'react';
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import Header from './components/Header';
import Footer from './components/Footer';
import { BrowserRouter, Route, Routes, } from 'react-router-dom';
import Login from "./components/Login";
import ResetPassword from "./components/ResetPassword";
import Companies from './components/Companies';
import { log } from 'console';

function App() {
    const root_path: string | undefined = process.env.PUBLIC_URL
    return (
        <div className="container">
            <BrowserRouter>

            <Header />
            <ToastContainer />
                <Routes>
                    <Route path={`${root_path}`} element={<Login />} />
                    <Route path={`${root_path}/reset-password`} element={<ResetPassword />} />
                    <Route path={`${root_path}/companies`} element={<Companies />} />
                </Routes>
            <Footer />
            </BrowserRouter>

        </div>
    );
}
export default App;