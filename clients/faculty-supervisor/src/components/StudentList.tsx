import axios from "axios";
import React, { useEffect, useState } from "react";
import { PaginationButton } from "../components/PaginationButton";
import { StudentAdd } from "../modals/StudentAddModal/StudentAddModal";
import { StudentUpdate } from "../modals/StudentUpdateModal/StudentUpdateModal";

export interface Student {
	id: number;
	name: string;
	surname: string;
	tckn: string;
	studentNo: string;
	faculty: {
		id: number;
		name: string;
	};
	phoneNumber: string;
	birthPlace: string;
	birthDate: Date;
	address: string;
	grade: number;
}

type PaginationButtonProps = {
	number: number;
	isActive: boolean;
	handleClick: () => void;
};

const StudentList: React.FC = () => {
	const [students, setStudents] = useState<Student[]>([]);
	const [showUpdateModal, setUpdateModal] = useState<boolean>(false);
	const [searchTerm, setSearchTerm] = useState("");
	const [studentId, setStudentId] = useState<number | null>(null);
	const [pageSize, setPageSize] = useState<number>(7);
	const [sortBy, setSortBy] = useState<string>("name");
	const [open, setOpen] = useState(false);
	const [selectedStudentId, setSelectedStudentId] = useState<number | null>(
		null
	);
	const [hoveredButton, setHoveredButton] = useState<string | null>(null);
	const [totalPages, setTotalPages] = useState<number>(0);
	const [currentPage, setCurrentPage] = useState(1);
	const pageNumbers = [];
	const [addModal, setAddModal] = useState<boolean>(false);

	const closeUpdateModal = () => {
		setUpdateModal(false);
	};

	const closeAddModal = () => {
		setAddModal(false);
	};

	const showAddModal = () => {
		setAddModal(true);
	};

	const fetchFacultyById = async (facultyId: number) => {
		try {
			const response = await axios.get(`/api/faculty`);
			return response.data; // API'den tam bir fakülte nesnesi döner
		} catch (error) {
			console.error(error);
			throw error;
		}
	};

	const fetchStudentsByFacultySupervisorId = async (
		pageNo = 0,
		limit = 10,
		sortBy = "id"
	) => {
		try {
			const response = await axios.get(`/api/student/supervisor`, {
				params: {
					pageNo,
					limit,
					sortBy,
				},
			});
			const { data } = response;
			const { content, totalPages } = data;
			const updatedStudents = await Promise.all<Student>(
				content.map(async (student: Student) => {
					const facultyData = await fetchFacultyById(student.faculty.id);
					return {
						...student,
						faculty: {
							id: facultyData.id,
							name: facultyData.name,
						},
					};
				})
			);
			setStudents(content);
			setTotalPages(totalPages);
		} catch (error) {
			console.error(error);
			throw error;
		}
	};

	useEffect(() => {
		fetchStudentsByFacultySupervisorId(currentPage, pageSize, sortBy);
	}, [currentPage, pageSize, sortBy]);

	for (let i = 1; i <= Math.ceil(totalPages); i++) {
		pageNumbers.push(i);
	}

	const handleClick = (pageNumber: number) => {
		setCurrentPage(pageNumber);
	};

	const handleStudentDelete = async (studentId: number) => {
		const confirmed = window.confirm(
			"Öğrenciyi silmek istediğinize emin misiniz?"
		);
		if (confirmed) {
			try {
				await axios.delete(`/api/student/${studentId}`);
				// Silme işlemi başarılı oldu, listeden silinmeli
				const updatedStudents = students.filter(
					(student) => student.id !== studentId
				);
				setStudents(updatedStudents);
			} catch (error) {
				console.error("Öğrenci silinirken bir hata oluştu:", error);
			}
		}
	};

	const handleStudentUpdate = async (studentId: number) => {
		try {
			setSelectedStudentId(studentId);
			setUpdateModal(true);
			fetchStudentsByFacultySupervisorId(currentPage, pageSize, sortBy);
		} catch (error) {
			console.error("Öğrenci güncellenirken bir hata oluştu:", error);
		}
	};

	const decreasePageNumber = () => {
		if (currentPage > 0) {
			setCurrentPage(currentPage - 1);
		}
	};

	const increasePageNumber = () => {
		if (currentPage < totalPages - 1) {
			setCurrentPage(currentPage + 1);
		}
	};

	const handleButtonHover = (buttonName: string | null) => {
		setHoveredButton(buttonName);
	};

	return (
		<div>
			<div className="flex flex-col items-center justify-between">
				<div className="max-w-screen container mx-auto px-4 sm:px-8">
					<div className="py-8">
						<div className="mb-1 flex w-full flex-row justify-between sm:mb-0">
							<h2 className="text-2xl leading-tight">Öğrenci Listesi</h2>
							<div className="text-end">
								<form className="flex w-3/4 max-w-sm flex-col justify-center space-y-3 md:w-full md:flex-row md:space-x-3 md:space-y-0">
									<div className=" relative ">
										<input
											type="text"
											placeholder="Öğrenci Ara"
											className="border border-gray-300 rounded-md p-2 mr-2"
											value={searchTerm}
											onChange={(e) => setSearchTerm(e.target.value)}
										/>
										<button
											className="add-student"
											type="button"
											onClick={showAddModal}
											style={{
												backgroundColor: "#4f46e5",
												color: "white",
												borderRadius: "20px",
												padding: "10px 20px",
												boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.2)",
												fontWeight: "bold",
											}}
										>
											Öğrenci Ekle
										</button>
									</div>
								</form>
							</div>
						</div>
						<div className="-mx-4 overflow-x-auto px-4 py-4 sm:-mx-10 sm:px-10">
							<div className="inline-block min-w-full overflow-hidden rounded-lg shadow">
								<table className="min-w-full leading-normal">
									<thead>
										<tr>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "1px solid #e5e7eb",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Öğrenci Adı
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Öğrenci Soyadı
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												TCKN
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Öğrenci Numarası
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Ortalama
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Telefon Numarası
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Doğum Yeri
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Doğum Tarihi
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Fakülte Adı
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-1.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "none",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												Adres
											</th>
											<th
												scope="col"
												className="border-b border-gray-200 bg-white px-5 py-31.5 text-left text-sm font-bold uppercase text-gray-800"
												style={{
													borderLeft: "none",
													borderRight: "1px solid #e5e7eb",
													borderTop:
														"1px solid rgba(169, 169, 169, 0.05)",
												}}
											>
												İşlemler
											</th>
										</tr>
									</thead>
									<tbody>
										{students
											.filter((student) => {
												const fullName = `${student.name} ${student.surname}`;
												return fullName
													.toLowerCase()
													.includes(searchTerm.toLowerCase());
											})
											.map((student, index) => (
												<tr key={student.id}>
													<td
														className={`px-5 py-2 border-b border-gray-200 bg-white text-sm ${
															index !== 0 && "border-l"
														}`}
														style={{ borderRight: "none" }}
													>
														{student.name}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.surname}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.tckn}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.studentNo}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.grade}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.phoneNumber}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.birthPlace}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{new Date(student.birthDate)
															.toLocaleDateString("tr-TR")
															.replace(/\./g, "/")}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.faculty.name}
													</td>
													<td
														className="px-5 py-2 border-b border-gray-200 bg-white text-sm"
														style={{
															borderLeft: "none",
															borderRight: "none",
														}}
													>
														{student.address}
													</td>
													<td
														className={`px-5 py-2 border-b border-gray-200 bg-white text-sm ${
															index !== 0 && "border-r"
														}`}
														style={{ borderLeft: "none" }}
													>
														<div className="flex items-center">
															<button
																className="relative text-indigo-600 hover:text-indigo-900"
																onMouseEnter={() =>
																	handleButtonHover(
																		"studentDelete"
																	)
																}
																onMouseLeave={() =>
																	handleButtonHover(null)
																}
																onClick={() =>
																	handleStudentDelete(
																		student.id
																	)
																}
															>
																<svg
																	className="h-6 w-6"
																	viewBox="0 0 24 24"
																	fill="none"
																	stroke="currentColor"
																	strokeWidth="2"
																	strokeLinecap="round"
																	strokeLinejoin="round"
																>
																	<path d="M3 6L5 6 21 6"></path>
																	<path d="M6 6V3C6 2.46957 6.21071 1.96086 6.58579 1.58579C6.96086 1.21071 7.46957 1 8 1H16C16.5304 1 17.0391 1.21071 17.4142 1.58579C17.7893 1.96086 18 2.46957 18 3V6M9 11V17M15 11V17"></path>
																	<path
																		strokeLinecap="round"
																		d="M10 11L10 17"
																	></path>
																	<path
																		strokeLinecap="round"
																		d="M14 11L14 17"
																	></path>
																</svg>
																{hoveredButton ===
																	"studentDelete" && (
																	<span
																		className="absolute top-0 left-0 mt-2 -ml-24 w-24 p-1 bg-indigo-500 text-white text-xs rounded"
																		style={{
																			display:
																				hoveredButton ===
																				"studentDelete"
																					? "block"
																					: "none",
																		}}
																	>
																		Öğrenciyi Sil
																	</span>
																)}
															</button>

															<button
																className="relative text-indigo-600 hover:text-indigo-900"
																onMouseEnter={() =>
																	handleButtonHover(
																		"studentUpdate"
																	)
																}
																onMouseLeave={() =>
																	handleButtonHover(null)
																}
																onClick={() =>
																	handleStudentUpdate(
																		student.id
																	)
																}
																style={{
																	padding: "0px",
																	margin: "0px",
																	border: "none",
																	background: "none",
																}}
															>
																<svg
																	className="h-6 w-6"
																	viewBox="0 0 24 24"
																	fill="none"
																	stroke="currentColor"
																	strokeWidth="2"
																	strokeLinecap="round"
																	strokeLinejoin="round"
																>
																	<circle
																		cx="12"
																		cy="7"
																		r="4"
																	></circle>
																	<path d="M2 20C2 14.4772 6.47715 10 12 10C17.5228 10 22 14.4772 22 20"></path>
																</svg>
																{hoveredButton ===
																	"studentUpdate" && (
																	<span
																		className="absolute top-0 left-0 mt-2 -ml-28 w-28 p-1 bg-indigo-500 text-white text-xs rounded"
																		style={{
																			display:
																				hoveredButton ===
																				"studentUpdate"
																					? "block"
																					: "none",
																		}}
																	>
																		Öğrenciyi Güncelle
																	</span>
																)}
															</button>
														</div>
													</td>
												</tr>
											))}
									</tbody>
								</table>
								<div className="xs:flex-row xs:justify-between flex flex-col items-center bg-white px-5 py-5">
									<div className="flex items-center">
										<button
											type="button"
											className="rounded-l-xl border bg-white p-4 text-base text-gray-600 hover:bg-gray-100"
											onClick={() => decreasePageNumber()}
										>
											<svg
												width="9"
												fill="currentColor"
												height="8"
												viewBox="0 0 1792 1792"
												xmlns="http://www.w3.org/2000/svg"
											>
												<path d="M1427 301l-531 531 531 531q19 19 19 45t-19 45l-166 166q-19 19-45 19t-45-19l-742-742q-19-19-19-45t19-45l742-742q19-19 45-19t45 19l166 166q19 19 19 45t-19 45z"></path>
											</svg>
										</button>
										{pageNumbers.map(
											(number) =>
												number === currentPage && (
													<PaginationButton
														key={number}
														number={number}
														isActive={true}
														handleClick={() =>
															handleClick(number)
														}
													/>
												)
										)}
										<button
											type="button"
											className="rounded-r-xl border bg-white p-4 text-base text-gray-600 hover:bg-gray-100"
											onClick={() => increasePageNumber()}
										>
											<svg
												width="9"
												fill="currentColor"
												height="8"
												viewBox="0 0 1792 1792"
												xmlns="http://www.w3.org/2000/svg"
											>
												<path d="M1363 877l-742 742q-19 19-45 19t-45-19l-166-166q-19-19-19-45t19-45l531-531-531-531q-19-19-19-45t19-45l166-166q19-19 45-19t45 19l742 742q19 19 19 45t-19 45z"></path>
											</svg>
										</button>
										{showUpdateModal && (
											<div className="fixed inset-0 flex items-center justify-center z-50">
												<div className="absolute inset-0 bg-gray-900 opacity-50"></div>
												<div className="bg-white rounded-lg p-8 z-50">
													<StudentUpdate
														_studentId={selectedStudentId}
														isOpen={showUpdateModal}
														onClose={closeUpdateModal}
													/>
													<button
														className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
														onClick={closeUpdateModal}
													>
														Kapat
													</button>
												</div>
											</div>
										)}
										{addModal && (
											<div className="fixed inset-0 flex items-center justify-center z-50">
												<div className="absolute inset-0 bg-gray-900 opacity-50"></div>
												<div className="bg-white rounded-lg p-8 z-50">
													<StudentAdd
														isOpen={addModal}
														onClose={closeAddModal}
													/>
													<button
														className="mt-4 bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded"
														onClick={closeAddModal}
													>
														Kapat
													</button>
												</div>
											</div>
										)}
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default StudentList;
