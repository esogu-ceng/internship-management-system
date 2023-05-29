import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import AdminDashbord from "./components/AdminDashboard"
import "./index.css";

function App() {
	const root_path : string | undefined = process.env.PUBLIC_URL
	return (
		<React.StrictMode>
			<Router>
				<Header />
				<div className="app-container">
					<Routes>
						<Route path={`${root_path}`} element={<AdminDashbord />} />
					</Routes>
				</div>
				<Footer />
			</Router>
		</React.StrictMode>
	);
}

export default App;