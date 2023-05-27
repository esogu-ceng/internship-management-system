import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import AdminDashbord from "./components/AdminDashboard"
import "./index.css";
import FacultySupervisor from "./components/FacultySupervisor";

function App() {
	return (
		<React.StrictMode>
			<Router>
				<Header />
				<div className="app-container">
					<Routes>
						<Route path="/admin" element={<AdminDashbord />} />
					</Routes>
					<Routes>
						<Route path="/admin/facultysupervisor" element={<FacultySupervisor />} />
					</Routes>
				</div>
				<Footer />
			</Router>
		</React.StrictMode>
	);
}

export default App;