/** @format */

import React from "react";

interface PaginationProps {
  totalItems: number;
  itemsPerPage: number;
  currentPage: number;
  onPageChange: (pageNumber: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({
  totalItems,
  itemsPerPage,
  currentPage,
  onPageChange,
}) => {
  const totalPages = Math.ceil(totalItems / itemsPerPage);

  const handleClick = (pageNumber: number) => {
    if (pageNumber < 0 || pageNumber >= totalPages) {
      return;
    }
    onPageChange(pageNumber);
  };

  return (
    <div className="pagination-container">
      <button
        className={`pagination-button ${currentPage === 0 ? "disabled" : ""}`}
        onClick={() => handleClick(currentPage - 1)}
        disabled={currentPage === 0}>
        Önceki
      </button>
      {Array.from({ length: totalPages }, (_, index) => index).map(
        (pageNumber) => (
          <button
            key={pageNumber}
            className={`pagination-button ${
              currentPage === pageNumber ? "active" : ""
            }`}
            onClick={() => handleClick(pageNumber)}>
            {pageNumber + 1}
          </button>
        )
      )}
      <button
        className={`pagination-button ${
          currentPage === totalPages - 1 ? "disabled" : ""
        }`}
        onClick={() => handleClick(currentPage + 1)}
        disabled={currentPage === totalPages - 1}>
        Sonraki
      </button>
    </div>
  );
};

export default Pagination;
