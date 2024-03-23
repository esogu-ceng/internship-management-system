import React, { useEffect, useState } from "react";
import "../style/dashboardstyle.css";
import { ToastContainer } from "react-toastify";
import 'bootstrap/dist/css/bootstrap.min.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { Chart } from "react-google-charts";
import { useNavigate } from "react-router-dom";



function Dashboard() {

    const [companyCount, setCompanyCount] = useState(null);
    const [internshipCount, setInternshipCount] = useState(null);
    const [studentCount, setStudentCount] = useState(null);
    const [facultyCount, setFacultyCount] = useState(null);
    const [internshipCountRejected, setInternshipCountRejected] = useState(null);
    const [internshipCountPending, setInternshipCountPending] = useState(null);
    const [internshipCountApproved, setInternshipCountApproved] = useState(null);
    const navigate = useNavigate();
    useEffect(() => {
        fetch('/api/company/count')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setCompanyCount(data);
                console.log('Company count:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);
    useEffect(() => {
        fetch('/api/student/count')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setStudentCount(data);
                console.log('Company count:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);
    useEffect(() => {
        fetch('/api/faculty/count')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setFacultyCount(data);
                console.log('Company count:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);
    useEffect(() => {
        fetch('/api/internship/count/all')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setInternshipCount(data);
                console.log('Company count:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);
    useEffect(() => {
        fetch('/api/internship/count/approved')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setInternshipCountApproved(data);
                console.log('Company count:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);
    useEffect(() => {
        fetch('/api/internship/count/pending')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setInternshipCountPending(data);
                console.log('Company count:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);
    useEffect(() => {
        fetch('/api/internship/count/rejected')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setInternshipCountRejected(data);
                console.log('Company count:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);
    const data = [
        ["Intern", "Percentage"],
        ["Kabul Edilen", internshipCountApproved],
        ["Bekleyen", internshipCountPending],
        ["Reddedilen", internshipCountRejected],

    ];
    const options = {
        is3D: true,
        title: "Staj Durumu",
        colors:["#5dfa02", "#ebc634", "#b32614"]
    };
    return (

        <div className="container">
            <div className="dataTable">
                <table className="table ">
                    <thead>
                        <tr>
                            <th >Toplam Staj Sayısı</th>
                            <th >Toplam Firma Sayısı</th>
                            <th >Toplam Öğrenci Sayısı</th>
                            <th >Toplam Fakülte Sayısı</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{internshipCount}</td>
                            <td>{companyCount}</td>
                            <td>{studentCount}</td>
                            <td>{facultyCount}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div className="grid-container">
                <div className="internship">
                    <Chart
                        chartType="PieChart"
                        data={data}
                        options={options}
                        width={"100%"}
                        height={"100%"}
                       
                    />
                </div>
                <div className="month-internship"><Chart
                    chartType="PieChart"
                    data={data}
                    options={options}
                    width={"100%"}
                    height={"100%"}


                /></div>
                <div className="year-internship"><Chart
                    chartType="PieChart"
                    data={data}
                    options={options}
                    width={"100%"}
                    height={"100%"}


                /></div>

            </div>

        </div>


    );
}

export default Dashboard;