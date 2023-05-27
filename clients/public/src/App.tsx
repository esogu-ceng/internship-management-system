import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from './components/Header';
import Footer from './components/Footer';
import Login from './components/Login';
import ResetPassword from './components/ResetPassword';
import Companies from './components/Companies';

function App() {
  const public_url: string = process.env.PUBLIC_URL;
  return (
    <React.StrictMode>
        <Router>
            <Header />
            <div className="app-container">
                <Routes>
                    <Route path={`${public_url}`} element={<Login/>}/>
                    <Route path={`${public_url}/reset-password`} element={<ResetPassword/>}/>
                    <Route path={`${public_url}/companies`} element={<Companies/>}/>
                </Routes>
            </div>
            <Footer />
        </Router>
    </React.StrictMode>
);
}

export default App;
