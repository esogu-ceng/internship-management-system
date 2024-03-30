import React from "react";

const InternshipApprove = ({ id }: { id: number }) =>  {
    const handleConfirm = async () => {
        try {
            console.log("handle confirm");
            const response = await fetch(`/api/internship/approve/${id}`, {
                method: 'PUT', // PUT isteği yapılacak
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            // Cevaptan gelen veriyi kullanarak istediğiniz işlemleri gerçekleştirebilirsiniz
            console.log(data); // Örneğin, gelen veriyi konsola yazdırabiliriz
        } catch (error) {
            console.error('Hata:', error);
        }
    };

    return (
        <div >
            <div className="z-50 flex flex-col items-center rounded-lg bg-white p-8">
                <h2>Onaylamak İstediğinize Emin Misiniz?</h2>
                <button
                    className="mt-4 rounded bg-indigo-500 px-4 py-2 font-bold text-white hover:bg-indigo-600"
                    style={{backgroundColor: '#3A4F7A'}}
                    onClick={handleConfirm}
                >
                    Onayla
                </button>
            </div>
        </div>
    );
}

export default InternshipApprove;
