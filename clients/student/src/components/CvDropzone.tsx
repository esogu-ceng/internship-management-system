// ** React Imports
import React, { FC, useState } from 'react'

// ** Third Party Components
import { DeleteFilled, FilePdfOutlined } from '@ant-design/icons'
import Dropzone from 'react-dropzone'
import { toast } from 'react-toastify'
import { Student } from '../types/StudentType';


interface CvDropzoneProps {
  studentDatas: Student[] | null;
  setStudentDatas: React.Dispatch<React.SetStateAction<Student[] | null>>;
}

const CvDropzone: FC<CvDropzoneProps> = (props) => {
  const { studentDatas , setStudentDatas } = props;
  const studentNo = studentDatas && studentDatas[0].studentNo;

  const [file, setFile] = useState<File | null>(null)

  const handleOnDrop = (files: File[]) => {
    if (files.length !== 1) {
      toast.error('Lütfen sadece bir dosya yükleyin')
      return
    }
    // check is pdf file
    if (files[0].type !== 'application/pdf') {
      toast.error('Lütfen sadece pdf dosyası yükleyin')
      return
    }
    // max file size 5mb = 5 * 1024 * 1024 = 5242880
    if (files[0].size > 5242880) {
      toast.error('Dosya boyutu 5mb dan büyük olamaz')
      return
    }

    setFile(files[0]);
    
  
  }

  const handleSave = async () => {
    try {
      let formData = new FormData()
      formData.append('cv', file as Blob)
      
      const response = await fetch(`/api/student/${studentNo}/cv`, {
        method: 'POST',
        body: formData,
      })
      if (response.ok) {
        toast.success('Özgeçmiş başarıyla yüklendi!');
        setFile(null);
        setStudentDatas((prev) => {
          if (prev) {
            return [{ ...prev[0], cvPath: studentNo}]
          }
          return prev
        })
      } else {
        const data = await response.json();
        toast.error(data.message);
      }

    } catch (error : any) {
      toast.error(error.message)
    }
    
  }

  return (
    <Dropzone onDrop={handleOnDrop} maxFiles={1}>
      {({ getRootProps, getInputProps }) => (
        <section className="text-center pb-2">
          <div
            {...getRootProps()}
            className="py-3 border border-gray-700 border-dashed rounded-2xl"
          >
            <h4 className="px-2 text-lg text-center font-medium text-navy-700 ">
              Özgeçmiş Yükle
            </h4>
            <input {...getInputProps()} />
            <p className="text-gray-700 mt-2">
              Buraya dosyayı sürükleyip bırakın, ya da dosya seçmek için
              tıklayın
            </p>
            <p className="text-gray-400">1 adet .pdf dosyası yüklemelisiniz</p>
          </div>
          {/* Selected File Operations */}
          <div>
            {file ? (
              <div className="mt-2 border border-gray-200 rounded-md flex items-center text-left">
                <div className="bg-gray-300 px-4 py-4 text-xl">
                  <FilePdfOutlined />
                </div>
                <div className="pr-2 flex-grow flex items-center justify-between">
                  {/* File Content */}
                  <div className="ml-6 ">
                    <p>{file.name}</p>
                    <p className="text-sm text-gray-400">
                      {(file.size / 1024).toFixed(2)} KB
                    </p>
                  </div>
                  {/* ACTIONS */}
                  <div className="space-x-3">
                    <button
                      className="py-2 px-4 bg-blue-500 rounded-lg text-white hover:bg-blue-600 transition duration-300"
                      onClick={handleSave}
                    >
                      Kaydet
                    </button>
                    <button
                      className="px-3 py-2  text-red-500 text-xl hover:bg-gray-200 rounded-lg transition duration-300"
                      onClick={() => setFile(null)}
                    >
                      <DeleteFilled />
                    </button>
                  </div>
                </div>
              </div>
            ) : null}
          </div>
        </section>
      )}
    </Dropzone>
  )
}

export default CvDropzone
