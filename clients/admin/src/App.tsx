import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import AdminDashboard from "./components/AdminDashboard";
import GetSettings from "./components/GetSettings";
import "./index.css";

function App() {
	const root_path : string | undefined = process.env.PUBLIC_URL
	return (
		<React.StrictMode>
			<Router>
				<Header />
				<div className="app-container">
					<Routes>
						<Route path={`${root_path}`} element={<AdminDashboard />} />
						<Route path={`${root_path}/setting`} element={<GetSettings />} />
					</Routes>
				</div>
				<Footer />
			</Router>
		</React.StrictMode>
	);
};

export default App;
