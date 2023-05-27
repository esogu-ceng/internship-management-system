import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import InternshipDashboard from "./components/InternshipDashboard";
import AllInternships from "./components/AllInternships";
import CompanyInfo from "./components/CompanyInfo";
import StudentInfo from "./components/StudentInfo";

import "./index.css";

function App() {
	return (
		<React.StrictMode>
			<Router>
				<Header />
				<div className="app-container">
					<Routes>
						<Route path="/student" element={<InternshipDashboard />} />
						<Route path="/student/AllInternships" element={<AllInternships />} />
						<Route path="/student/StudentInfo" element={<StudentInfo />} />
					</Routes>
				</div>
				<Footer />
			</Router>
		</React.StrictMode>
	);
}

export default App;
