/** @format */

export interface CompanySupervisorCreate {
  name: string;
  surname: string;
  phoneNumber: string;
  user: {
    username: string;
    password: string;
    email: string;
  };
  companyId: number;
}

export interface CompanySuperviserUpdate {
  id: number;
  name: string;
  surname: string;
  phoneNumber: string;
  company: {
    id: number;
  };
  user: {
    id: number;
  };
}

export interface CompanySupervisor {
  id: number;
  name: string;
  surname: string;
  phoneNumber: string;
  user: User;
  company: Company;
}

interface PagedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

interface Pageable {
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export interface CompanySupervisorPagedResponse
  extends PagedResponse<CompanySupervisor> {}

export interface Company {
  id: number;
  name: string;
  address: string;
  phoneNumber: string;
  faxNumber: string;
  email: string;
  scope: string;
  description: string;
}

export interface User {
  id: number;
  username: string;
  password: string;
  email: string;
  userType: string;
}

export interface CompanyOption {
  id: number;
  name: string;
}

export interface CompanyPagedResponse extends PagedResponse<CompanyOption> {}
