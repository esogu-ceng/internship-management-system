import React, { useEffect, useState, useRef, ReactNode } from 'react';
import { InternshipEvalFrom } from '../types/InternshipEvalFromType';

type ModalProps = {
  _internshipId: number;
  isOpen: boolean;
  onClose: () => void;
  children?: React.ReactNode;
};

const InternshipEvaluateForm: React.FC<ModalProps> = ({
  _internshipId,
  isOpen,
  onClose,
  children,
}) => {
  const [loading, setLoading] = useState<boolean>(true);
  const [internshipEvalFrom, setInternshipEvalFrom] =
    useState<InternshipEvalFrom>();
  const [popUpScreen, setPopUpScreen] = useState<ReactNode>(null);
  const [isExists, setIsExists] = useState<boolean>(false);
  const [isNew, setIsNew] = useState<boolean>(!isExists);
  const [selectedOptions, setSelectedOptions] = useState<number[]>(new Array(8).fill(0));
  
  
  const internshipEvalQuestions = [
      "Öğrencinin kendine amaçlar ve hedefler belirleyip uygulama yeteneği.",
      "Öğrencinin zamanı etkin kullanabilme yeteneği.",
      "Öğrencinin verilen isi vaktinde bitirebilme yeteneği.",
      "Öğrencinin görev ve sorumluluk alma konusundaki hevesi.",
      "Öğrencinin öğrenme hırs ve isteği.",
      "Öğrencinin bilgilerini ve görüşlerini anlatabilme yeteneği.",
      "Öğrencinin kurum çalışanları ile yapıcı ve olumlu iletişim kurabilme yeteneği.",
      "Öğrencinin stajdaki genel başarısı.",
  ];
  const checkboxLabels = [
      "Çok Yüksek",
      "Yüksek",
      "Orta",
      "Düşük",
      "Fikrim Yok"
  ];
    
  useEffect(() => {
    fetch(`/api/internship-evaluate-forms/getByInternshipId/${_internshipId}`, {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setInternshipEvalFrom(data);
        setSelectedOptions(
            Object.keys(data).filter(key => key.startsWith('question'))
                .map(key => data[key])
        );
        setIsExists(true);
        setIsNew(false);
      })
      .catch((error) => {
        setIsNew(true);
        setIsExists(false);
      });
    setLoading(false);
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  const saveEdit = () => {
    const dataToSave: Partial<InternshipEvalFrom> = {
      id: internshipEvalFrom?.id,
      name: internshipEvalFrom?.name,
      surname: internshipEvalFrom?.surname,
      question1: selectedOptions[0],
      question2: selectedOptions[1],
      question3: selectedOptions[2],
      question4: selectedOptions[3],
      question5: selectedOptions[4],
      question6: selectedOptions[5],
      question7: selectedOptions[6],
      question8: selectedOptions[7],
      internshipId: _internshipId,
    };
    fetch(`/api/internship-evaluate-forms/`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(dataToSave),
    })
      .then((response) => response.json())
      .catch((error) => {
        console.log(error);
      });
    setPopUpConfirm();
  };

  const saveNew = () => {
    const dataToSave: Partial<InternshipEvalFrom> = {
        name: "sampleName",
        surname: "sampleSurname",
        question1: selectedOptions[0],
        question2: selectedOptions[1],
        question3: selectedOptions[2],
        question4: selectedOptions[3],
        question5: selectedOptions[4],
        question6: selectedOptions[5],
        question7: selectedOptions[6],
        question8: selectedOptions[7],
        internshipId: _internshipId,
      };
      fetch(`/api/internship-evaluate-forms/`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(dataToSave),
      })
        .then((response) => response.json())
        .catch((error) => {
          console.log(error);
        });
    setPopUpConfirm();
  };

  const handleSubmit = () => {   
    if (isNew) {
      saveNew();
      setIsNew(false);
    } else {
      saveEdit();
    }
  };

  const handleEvalChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { checked, value} = event.target;
    const numberValue = parseInt(value);
    const questionIndex = parseInt(event.target.name.split('-')[1]);
    
    if (checked) {
        setSelectedOptions((selectedOptions) => {
            const newSelectedOptions = [...selectedOptions];
            newSelectedOptions[questionIndex] = numberValue;
            return newSelectedOptions;
            }
        );
    } else {
      setSelectedOptions(selectedOptions.filter((option) => option !== numberValue));
    }
  };   
    
  const onClosePopUp = () => {
    setPopUpScreen(null);
  };

  const setPopUpConfirm = () => {
    setPopUpScreen(
      <div className="fixed inset-0 z-50 flex items-center justify-center">
        <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
        <div className="z-50 flex flex-col items-center rounded-lg bg-white p-8">
          Başarı ile kaydedildi.
          <br />
          <button
            className="mt-4 rounded bg-indigo-500 px-4 py-2 font-bold text-white hover:bg-indigo-600"
            style={{ backgroundColor: '#3A4F7A' }}
            onClick={onClosePopUp}
          >
            Tamam
          </button>
        </div>
      </div>
    );
  };

  const createNew = () => {
    setIsExists(true);
  };

  return (
    <div className="w-full rounded-md bg-white">
      <h2 style={{ textAlign: 'center', fontWeight: 'bold', color: '#3A4F7A' }}>
        SİRKET DEĞERLENDİRMESİ
      </h2>
      {!isExists ? (
        <div>
          <div className="z-50 flex flex-col items-center rounded-lg bg-white p-8">
            Değerlendirme formu bulunamadı.
            <br />
            <button
              className="mt-4 rounded border border-green-500 px-4 py-2 text-base text-green-500 transition-colors hover:bg-green-50"
              onClick={createNew}
            >
              Oluştur
            </button>
          </div>
        </div>
      ) : (
        <div>
          {internshipEvalQuestions.map((question, questionIndex) => (
            <div key={question} style={{ marginTop: 10 }}> 
              <label htmlFor={`question-${questionIndex}`} style={{ fontWeight: 'bold', textOverflow:"ellipsis"}}>
                {question}
              </label>
                <div style={{ display: 'flex', alignItems: 'center', marginTop: 5 }}> 
                {checkboxLabels.map((label, index) => (
                  <div key={label} style={{ marginRight: 10 }}> 
                    <input
                      type="radio"
                      id={`option-${questionIndex}-${label}`}
                      value={index}
                      name={`question-${questionIndex}`}
                      onChange={handleEvalChange}
                      style={{ marginRight: 5, cursor: 'pointer'}}
                      checked={selectedOptions[questionIndex] === index} 
                    />
                    <label
                      htmlFor={`option-${questionIndex}-${label}`}
                      style={{ cursor: 'pointer' }} 
                    >
                      {label}
                    </label>
                  </div>
                ))}
                </div>
            </div>
          ))}
          <div className="update-eval-buttons" style={{marginTop:20, marginRight:0}}>
            <button type="submit" className='submit-button' onClick={handleSubmit}>Kaydet</button>
            <button type="button" className="cancel-button" onClick={onClose}>
              İptal
            </button>
          </div>            
        </div>
      )}
      <div>{popUpScreen}</div>
    </div>
  );
};

export default InternshipEvaluateForm;
