import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import InternshipDashboard from "./components/InternshipDashboard";
import AllInternships from "./components/AllInternships";
import StudentProfile from './components/StudentProfile';

import "./index.css";

function App() {
	const root_path : string | undefined = process.env.PUBLIC_URL
	return (
		<React.StrictMode>
			<Router>
				<Header />
				<div className="app-container">
					<Routes>
						<Route path={`${root_path}`} element={<InternshipDashboard />} />
						<Route path={`${root_path}/AllInternships`} element={<AllInternships />} />
						<Route path={`${root_path}/profile`} element={<StudentProfile />} />
					</Routes>
				</div>
				<Footer />
			</Router>
		</React.StrictMode>
	);
}

export default App;
