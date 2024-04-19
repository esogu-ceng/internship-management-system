import React, { useEffect, useState } from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import InternshipDashboard from "./components/InternshipDashboard";
import CompanyListContainer from "./containers/CompanyListContainer/CompanyListContainer";
import { User } from "./types/UserType";
// Import other screen components

import Footer from "./components/Footer";
import Header from "./components/Header";

import AllInternships from "./components/AllInternships";
import FacultySupervisorProfile from "./components/FacultySupervisorProfile";
import StudentList from "./components/StudentList";

const App: React.FC = () => {
	const [user, setUser] = useState<User>();
	const [currentFacultySupervisorId, setcurrentFacultySupervisorId] =
		useState<number>(0);
	const root_path: string | undefined = process.env.PUBLIC_URL;

	function getAuthUser() {
		fetch("/api/user/faculty-supervisor/auth", {
			method: "GET",
		})
			.then((response) => response.json())
			.then((data) => {
				console.log(data);
				setUser(data);
			})
			.catch((error) => {
				console.log(error);
			});
	}

	function getFacultySupervisorId(userId: number) {
		console.log(userId);
		fetch(`/api/facultySupervisor/byUserId`, {
			method: "GET",
		})
			.then((response) => response.json())
			.then((data) => {
				setcurrentFacultySupervisorId(data.id);
			})
			.catch((error) => {
				console.log(error);
			});
	}

	useEffect(() => {
		getAuthUser();
	}, []);

	useEffect(() => {
		if (user) {
			getFacultySupervisorId(user.id);
		}
	}, [user]);

	return (
		<Router>
			<div>
				<Header />
				<div className="content-container">
					<Routes>
						${currentFacultySupervisorId}
						<Route
							path={`${root_path}`}
							element={<InternshipDashboard />}
						/>
						<Route
							path={`${root_path}/company-list`}
							element={<CompanyListContainer />}
						/>
						<Route
							path={`${root_path}/AllInternships`}
							element={<AllInternships />}
						/>
						<Route
							path={`${root_path}/profile`}
							element={<FacultySupervisorProfile />}
						/>
						<Route
							path={`${root_path}/student-list`}
							element={<StudentList />}
						/>
						{/* Add other routes for different screens */}
						<Route
							path={`${root_path}/default`}
							element={<div>Default Screen</div>}
						/>
					</Routes>
				</div>
				<Footer />
			</div>
		</Router>
	);
};

export default App;
