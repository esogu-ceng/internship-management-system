import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import InternshipDashboard from "./components/InternshipDashboard";
import AllInternships from "./components/AllInternships";
import StudentProfile from './components/StudentProfile';
import InternshipCalender from "./components/InternshipCalendar"
import InternshipApplication from "./components/InternshipApplications";
import "./index.css";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';


function App() {
	const root_path : string | undefined = process.env.PUBLIC_URL
	return (
		<React.StrictMode>
			<Router>
				<ToastContainer pauseOnFocusLoss={false} />
				<Header />
				<div className="app-container">
					<Routes>
						<Route path={`${root_path}`} element={<InternshipDashboard />} />
						<Route path={`${root_path}/AllInternships`} element={<AllInternships />} />
						<Route path={`${root_path}/profile`} element={<StudentProfile />} />
						<Route path={`${root_path}/calendar`} element={<InternshipCalender />} />
						<Route path={`${root_path}/internship-application`} element={< InternshipApplication/>} />
					</Routes>
				</div>
				<Footer />
			</Router>
		</React.StrictMode>
	);
}

export default App;
