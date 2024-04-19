import { useEffect, useState } from "react";
import { FacultySupervisor } from "../types/FacultySupervisorType";
function FacultySupervisorProfile() {
	const [facultySupervisorDatas, setFacultySupervisorDatas] = useState<
		FacultySupervisor[] | null
	>(null);

	useEffect(() => {
		fetch("/api/user/faculty-supervisor/auth", {
			method: "GET",
		})
			.then((response) => response.json())
			.then((data) => {
				console.log("data: ", data);
				const facultySupervisorData = {
					...data,
					createDate: new Date(data.createDate),
				};
				setFacultySupervisorDatas([facultySupervisorData]);

				fetch("/api/facultySupervisor/byUserId", {
					method: "GET",
				})
					.then((response) => response.json())
					.then((data) => {
						console.log("data: ", data);
						const updatedFacultySupervisorData = {
							...facultySupervisorData,
							...data,
							createDate: new Date(data.createDate),
						};
						setFacultySupervisorDatas([updatedFacultySupervisorData]);

						fetch("/api/faculty/", {
							method: "GET",
						})
							.then((response) => response.json())
							.then((facultyData) => {
								console.log("facultyData: ", facultyData);
								const finalFacultySupervisorData = {
									...updatedFacultySupervisorData,
									facultyName: facultyData.name,
								};
								setFacultySupervisorDatas([finalFacultySupervisorData]);
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

				{facultySupervisorDatas &&
					facultySupervisorDatas.map((facultySupervisorData) => (
						<div
							key={facultySupervisorData.email}
							className="grid grid-cols-2 gap-4 px-2 w-full"
						>
							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">İsim </p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.name}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Soyisim</p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.surname}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">
									Telefon Numarası
								</p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.phoneNumber}
								</p>
							</div>

							<div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Email</p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.email}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Danışman No</p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.supervisorNo}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Fakülte</p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.facultyName}
								</p>
							</div>

							<div className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">Kullanıcı Tipi</p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.userType}
								</p>
							</div>

							<div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
								<p className="text-sm text-gray-600">
									Kullanıcı Oluşturulma Tarihi
								</p>
								<p className="text-base font-medium text-navy-700">
									{facultySupervisorData.createDate.toLocaleDateString()}
								</p>
							</div>
						</div>
					))}
			</div>
		</div>
	);
}

export default FacultySupervisorProfile;
