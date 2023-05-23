import React from "react";
import Header from "./components/Header";
import Footer from "./components/Footer";
import InternshipDashboard from "./components/InternshipDashboard";

import "./index.css";

function App() {
	return (
	  <React.StrictMode>
		<Header />
		<div className="app-container">
        	<InternshipDashboard />
      	</div>
		<Footer />
	  </React.StrictMode>
	);
  }
  
  export default App;
