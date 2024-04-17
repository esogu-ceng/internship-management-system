import React, { useEffect, useState } from "react";
import "../style/dashboardstyle.css";
import { ToastContainer } from "react-toastify";
import 'bootstrap/dist/css/bootstrap.min.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Chart } from "react-google-charts";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Dashboard() {
    const [internshipDataYear, setInternshipDataYear] = useState([]);
    const [chartDataYear, setChartDataYear] = useState<string[][]>([]);
    const [internshipDataMonth, setInternshipDataMonth] = useState([]);
    const [chartDataMonth, setChartDataMonth] = useState<string[][]>([]);
    const [companyCount, setCompanyCount] = useState(null);
    const [internshipCount, setInternshipCount] = useState(null);
    const [studentCount, setStudentCount] = useState(null);
    const [facultyCount, setFacultyCount] = useState(null);
    const [internshipCountRejected, setInternshipCountRejected] = useState(null);
    const [internshipCountPending, setInternshipCountPending] = useState(null);
    const [internshipCountApproved, setInternshipCountApproved] = useState(null);
    const [info, setInfo] = useState("");
    const navigate = useNavigate();
    const URL = process.env.REACT_APP_API_BASE_URI+'public/api/';
    const axiosInstance = axios.create({
        baseURL: URL, //API kök URL'si
      });
    //Yıllık staj verileri için API'den veri çekme
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosInstance.get('count-by-year'); //doğrusu bu
                setInternshipDataYear(response.data); // API'den dönen veriyi state'e kaydet
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);
    //Aylık staj verileri için API'den veri çekme
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosInstance.get('count-by-month');
                setInternshipDataMonth(response.data); // API'den dönen veriyi state'e kaydet
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);
    useEffect(() => {
        infoGetMethod();
       
      }, []); 
    
      const infoGetMethod = () => {
        fetch(`/api/setting/${"info"}`, {
          method: "GET",
        })
          .then((response) => response.json())
          .then((data) => {
            setInfo(data); // Gelen veriyi state'e kaydediyoruz
          })
          .catch((error) => {
            console.error("Bir hata oluştu:", error);
          });
      };
    //Yıllık staj verileri için grafik çizme ve filtreleme
    const drawChartYear = () => {
        const currentYear = new Date().getFullYear();
        const filteredData = internshipDataYear.filter(item => {
            const year = parseInt(item[0]); // Veriden yılı al
            return year >= currentYear - 10 && year <= currentYear; // 10 sene geriye kadar olanları filtrele
        });

        const chartDataYear = [['Year', 'Number Of Internships'], ...filteredData.map(item => [String(item[0]), item[1]])];
        setChartDataYear(chartDataYear);
    };
    useEffect(() => {
        drawChartYear();
    }, [internshipDataYear]);
    //Aylık staj verileri için grafik çizme ve filtreleme
    const drawChartMonth = () => {
        const chartDataMonth = [['Month', 'Number Of Internships'], ...internshipDataMonth.map(item => [String(item[0]), item[1]])];
        setChartDataMonth(chartDataMonth);
    };
    useEffect(() => {
        drawChartMonth();
    }, [internshipDataMonth]);

    //Toplam staj, firma, öğrenci ve fakülte sayılarını API'den çekme
    useEffect(() => {
        fetch(URL+'count/company')
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
        fetch(URL+'count/student')
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
        fetch(URL+'count/faculty')
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
       
        fetch(URL+'count/all')
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
        fetch(URL+'count/approved')
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
        fetch(URL+'count/pending')
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
        fetch(URL+'count/rejected')
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
    //Grafiklerin ve tablonun oluşturulması
    const data = [
        ["Intern", "Percentage"],
        ["Kabul Edilen", internshipCountApproved],
        ["Bekleyen", internshipCountPending],
        ["Reddedilen", internshipCountRejected],
    ];
    const options = {
        is3D: true,
        title: "Staj Durumu",
        colors: ["#5dfa02", "#ebc634", "#b32614"]
    };
    //const infoText = JSON.stringify(info);
    const infoText = (info as any)?.value || '';


    return (
        <div className="container">
             <div>
             <div className="dataBox" style={{ marginTop: "100px" }}>
    <textarea
        value={infoText}
        readOnly
        className="textBox"
    />
</div>


             </div>
             
            <div className="dataTable">
                <table className="table ">
                    <thead>
                        <tr>
                            <th>Toplam Staj Sayısı</th>
                            <th>Toplam Firma Sayısı</th>
                            <th>Toplam Öğrenci Sayısı</th>
                            <th>Toplam Fakülte Sayısı</th>
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
                <div className="year-internship">
                    <Chart
                        chartType="BarChart"
                        data={chartDataYear}
                        options={{
                            title: "Son 10 Yıldaki Staj Başvuru Sayısı",
                            chartArea: { width: '85%' },
                            hAxis: { title: 'Yıl', type: 'number', format: '####', step: 0, minValue: new Date().getFullYear() - 10, maxValue: new Date().getFullYear() },
                            vAxis: { title: 'Staj Başvuru Sayısı', type: 'string' },
                            orientation: 'horizontal' // Grafiği yatay olarak döndürmek için
                        }}
                        width={"100%"}
                        height={"400px"}
                    />
                </div>
                <div className="internship">
                    <Chart
                        chartType="PieChart"
                        data={data}
                        options={options}
                        width={"100%"}
                        height={"100%"}
                    />
                </div>
                <div className="month-internship">
                    <Chart
                        chartType="BarChart"
                        data={chartDataMonth}
                        options={{
                            title: "Aylara Göre Staj Başvuru Sayısı",
                            chartArea: { width: '85%' },
                            hAxis: { title: 'Ay', type: 'number', format: '####', step: 0 },
                            vAxis: { title: 'Staj Başvuru Sayısı', type: 'string', format: '####', step: '0', minValue: 1, maxValue: 12},
                            orientation: 'horizontal' // Grafiği yatay olarak döndürmek için
                        }}
                        width={"100%"}
                        height={"400px"}
                    />
                </div>
            </div>
        </div>
    );
}

export default Dashboard;
function setInfo(data: any) {
    throw new Error("Function not implemented.");
}

