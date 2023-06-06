/** @format */

import React, { useEffect } from "react";
import { useLocation, Link } from "react-router-dom";
import useSupervisorManagement from "../hooks/useSupervisorManagement";

const CompanySupervisorDetail = () => {
  const location = useLocation();

  const id = location.pathname.split("/")[3];

  const { selectedCompanySupervisor, getCompanySupervisor } =
    useSupervisorManagement();

  useEffect(() => {
    getCompanySupervisor(+id);
  }, [id]);

  return (
    <section className="company-supervisor-detail">
      <h3 className="detail-title">Şirket Yetkilisi Detay Sayfası</h3>
      <Link to="/admin/companySupervisors" className="back-link">
        Geri
      </Link>
      {selectedCompanySupervisor ? (
        <div className="detail-container">
          <p className="detail-info">
            <span className="detail-info-title">Ad:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.name}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Soyad:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.surname}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Telefon Numarası:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.phoneNumber}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Kullanıcı Adı:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.user.username}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Email:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.user.email}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Şirket Adı:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.company.name}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Adres:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.company.address}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Telefon Numarası:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.company.phoneNumber}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Fax Numarası:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.company.faxNumber}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Email:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.company.email}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Kapsam:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.company.scope}
            </span>
          </p>
          <p className="detail-info">
            <span className="detail-info-title">Açıklama:</span>{" "}
            <span className="detail-info-value">
              {selectedCompanySupervisor.company.description}
            </span>
          </p>
        </div>
      ) : (
        <p className="detail-error">Şirket yetkilisi bulunamadı.</p>
      )}
    </section>
  );
};

export default CompanySupervisorDetail;
