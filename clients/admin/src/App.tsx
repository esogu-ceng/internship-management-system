import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import AdminDashboard from "./components/AdminDashboard";
import GetSettings from "./components/GetSettings";
import "./index.css";

const App: React.FC = () => {
	return (
		<React.StrictMode>
			<Router>
				<Header />
				<div className="app-container">
					<Routes>
						<Route path="/admin" element={<AdminDashboard />} />
						<Route path="/admin/setting" element={<GetSettings />} />
					</Routes>
				</div>
				<Footer />
			</Router>
		</React.StrictMode>
	);
};

export default App;
