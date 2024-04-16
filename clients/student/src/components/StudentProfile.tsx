import { useEffect, useState } from 'react';
import { Student } from '../types/StudentType';
import CvDropzone from './CvDropzone';
import { FolderViewOutlined } from '@ant-design/icons';
import { toast } from 'react-toastify';


function StudentProfile() {
    const [studentDatas, setStudentDatas] = useState<Student[] | null>(null);

    useEffect(() => {
        fetch('/api/user/student/auth', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
               
                const studentData = {
                    ...data,
                    email:data.email,
                    createDate: new Date(data.createDate),
                };
                setStudentDatas([studentData]);

                fetch('/api/student/byUserId/' + studentData.id, {
                    method: 'GET'
                  })
                    .then(response => response.json())
                    .then(data => {
                   
                      const updatedStudentData = {
                        ...studentData,
                        ...data,
                        userId: data.user.id,
                        email: data.user.email,
                        facultyId: data.faculty.id,
                        createDate: new Date(data.createDate),
                      };
                      setStudentDatas([updatedStudentData]);

                      fetch('/api/faculty/' + data.faculty.id, {
                        method: 'GET'
                      })
                        .then(response => response.json())
                        .then(facultyData => {
                          
                          const finalStudentData = {
                            ...updatedStudentData,
                            facultyName: facultyData.name,
                          };
                          setStudentDatas([finalStudentData]);
                        })
                        .catch(error => {
                          console.log(error);
                        });
                    })
                    .catch(error => {
                      console.log(error);
                    });

            })
            .catch(error => {
                console.log(error);
            });
    }, []);




    const handleViewCv = async() => {
        if(!studentDatas) return;

        try {
            const res = await fetch('/api/student/' + studentDatas[0].studentNo+ "/cv");

            if(!res.ok){
              const data = await res.json();
            toast.error(data.message);
            }

            const blob = await res.blob();
            const url = URL.createObjectURL(blob);
            window.open(url);


        } catch (error : any) {
            toast.error(error.message);
        }
    }




    return (
        <div className="flex flex-col justify-content-flex-start pt-10 items-center h-screen w-screen bg-gray-100">
            <div className="relative flex flex-col justify-content-flex-start rounded-[20px] w-[700px] max-w-[95%] mx-auto bg-white bg-clip-border shadow-3xl shadow-shadow-500 p-3">
                <div className="mt-2 mb-8 w-full">
                    <h4 className="px-2 text-xl text-center font-bold text-navy-700">Profil Bilgileri</h4>
                </div>

                {studentDatas &&
                    studentDatas.map(studentData => (
                        <div key={studentData.email} className="grid grid-cols-2 gap-4 px-2 w-full">
                            <div
                                className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">İsim </p>
                                <p className="text-base font-medium text-navy-700">
                                    {studentData.name}
                                </p>
                            </div>

                            <div
                                className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Soyisim</p>
                                <p className="text-base font-medium text-navy-700">
                                    {studentData.surname}
                                </p>
                            </div>

                            <div
                                className="flex flex-col items-start justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Telefon Numarası</p>
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
                                <p className="text-sm text-gray-600">Kullanıcı Oluşturulma Tarihi</p>
                                <p className="text-base font-medium text-navy-700">
                                    {studentData.createDate.toLocaleDateString()}</p>
                            </div>

                            <div className="flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500">
                                <p className="text-sm text-gray-600">Özgeçmiş</p>
                                <div className="text-base font-medium text-navy-700">
                                    {studentData.cvPath ? 
                                    <div className='flex gap-2 cursor-pointer hover:text-blue-500 transition duration-300' onClick={handleViewCv}>
                                        <p>Görüntülemek için tıkayınız.</p>
                                        <FolderViewOutlined className='text-2xl text-blue-400' />
                                    </div> 
                                    : "Özgeçmiş bulunamadı."}
                                </div>
                            </div>

                            <div className='col-span-2 flex flex-col justify-center rounded-2xl bg-white bg-clip-border px-3 py-4 shadow-3xl shadow-shadow-500'>
                               <CvDropzone  studentDatas={studentDatas} setStudentDatas={setStudentDatas}/>
                            </div>
                        </div>

                    ))}
            </div>
        </div>
    );
}

export default StudentProfile;