import { useEffect, useState } from "react";
import { Student } from "../types/StudentType";
function StudentProfile() {
	const [studentDatas, setStudentDatas] = useState<Student[] | null>(null);

	useEffect(() => {
		fetch("/api/user/student/auth", {
			method: "GET",
		})
			.then((response) => response.json())
			.then((data) => {
				console.log("data: ", data);
				const studentData = {
					...data,
					email: data.email,
					createDate: new Date(data.createDate),
				};
				setStudentDatas([studentData]);

				fetch("/api/student/byUserId/" + studentData.id, {
					method: "GET",
				})
					.then((response) => response.json())
					.then((data) => {
						console.log("data: ", data);
						const updatedStudentData = {
							...studentData,
							...data,
							userId: data.user.id,
							email: data.user.email,
							facultyId: data.faculty.id,
							createDate: new Date(data.createDate),
						};
						setStudentDatas([updatedStudentData]);

						fetch("/api/faculty/", {
							method: "GET",
						})
							.then((response) => response.json())
							.then((facultyData) => {
								console.log("facultyData: ", facultyData);
								const finalStudentData = {
									...updatedStudentData,
									facultyName: facultyData.name,
								};
								setStudentDatas([finalStudentData]);
							})
							.catch((error) => {
								console.log(error);
							});
					})
					.catch((error) => {
						console.log(error);
					});
			})
			.catch((error) => {
				console.log(error);
			});
	}, []);

	return (
		<div className="flex flex-col justify-content-flex-start pt-10 items-center h-screen w-screen bg-gray-100">
			<div className="relative flex flex-col justify-content-flex-start rounded-[20px] w-[700px] max-w-[95%] mx-auto bg-white bg-clip-border shadow-3xl shadow-shadow-500 p-3">
				<div className="mt-2 mb-8 w-full">
					<h4 className="px-2 text-xl text-center font-bold text-navy-700">
						Profil Bilgileri
					</h4>
				</div>

				{studentDatas &&
					studentDatas.map((studentData) => (
						<div
							key={studentData.email}
							className="grid grid-cols-2 gap-4 px-2 w-full"
						>
							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">İsim </p>
								<p className="text-base font-medium text-navy-700">
									{studentData.name}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Soyisim</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.surname}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">
									Telefon Numarası
								</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.phoneNumber}
								</p>
							</div>

							<div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Email</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.email}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Fakülte</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.facultyName}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Kullanıcı Tipi</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.userType}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Sınıf</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.grade}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Adres</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.address}
								</p>
							</div>

							<div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">
									Kullanıcı Oluşturulma Tarihi
								</p>
								<p className="text-base font-medium text-navy-700">
									{studentData.createDate.toLocaleDateString()}
								</p>
							</div>
						</div>
					))}
			</div>
		</div>
	);
}

export default StudentProfile;
