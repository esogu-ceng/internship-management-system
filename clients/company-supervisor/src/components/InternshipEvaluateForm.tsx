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
  const [internshipId, setInternshipId] = useState<number>(_internshipId);
  const [loading, setLoading] = useState<boolean>(true);
  const [internshipEvalFrom, setInternshipEvalFrom] =
    useState<InternshipEvalFrom>();
  const [onEdit, setOnEdit] = useState(false);
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const [popUpScreen, setPopUpScreen] = useState<ReactNode>(null);
  const [isExists, setIsExists] = useState<boolean>(false);
  const [isNew, setIsNew] = useState<boolean>(!isExists);
  const [currentVal, setCurrentVal] = useState<string | undefined>('');

  useEffect(() => {
    fetch(`/api/internship-evaluate-forms/getByInternshipId/${_internshipId}`, {
      method: 'GET',
    })
      .then((response) => response.json())
      .then((data) => {
        setInternshipEvalFrom(data);
        setIsExists(true);
        setIsNew(false);
        setCurrentVal(data.filePath);
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
      filePath: textareaRef.current?.value,
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
        filePath: textareaRef.current?.value,
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

  const handleClick = () => {
    setOnEdit(!onEdit);
    if (onEdit) {
      if (isNew) {
        saveNew();
        setIsNew(false);
      } else {
        saveEdit();
      }
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setCurrentVal(e.target.value);
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
    <div className="w-full rounded-md bg-white p-5 pt-0">
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
          <div style={{ display: 'flex', justifyContent: 'right' }}>
            <button
              style={{ width: '50px' }}
              className={`mt-4 rounded px-4 py-2 ${
                onEdit
                  ? 'border border-green-500 px-4 py-2 text-base text-green-500 transition-colors hover:bg-green-50'
                  : 'border border-yellow-500 px-4 py-2 text-base text-yellow-500 transition-colors hover:bg-yellow-50'
              }`}
              onClick={handleClick}
            >
              {onEdit ? '\u2713' : '\u270E'}
            </button>
          </div>
          <div style={{ overflowY: 'auto' }}>
            <div style={{ height: '5px' }} />
            <div style={{ width: '100%', overflowX: 'auto' }}>
              <div style={{ border: '2px solid #3A4F7A', padding: '10px' }}>
                <textarea
                  style={{
                    height: '400px',
                    width: '400px',
                    border: 'none',
                    outline: 'none',
                    resize: 'none',
                    padding: '0',
                    margin: '0',
                  }}
                  ref={textareaRef}
                  readOnly={!onEdit}
                  value={currentVal}
                  onChange={handleChange}
                ></textarea>
              </div>
            </div>
          </div>
        </div>
      )}

      <div>{popUpScreen}</div>
    </div>
  );
};

export default InternshipEvaluateForm;
