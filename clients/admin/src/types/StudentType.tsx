export interface Student {
  id: number;
  name: string;
  surname: string;
  tckn: string;
  studentNo: string;
  grade: string;
  phoneNumber: string;
  birthPlace: string;
  birthDate: Date;
  user: User;
  faculty: Faculty;
  address: string;
}

export interface StudentCreate {
  name: string;
  surname: string;
  tckn: string;
  studentNo: string;
  grade: string;
  phoneNumber: string;
  birthPlace: string;
  birthDate: Date;
  user: UserCreate;
  facultyId: number;
  address: string;
}

export interface StudentUpdate {
  id: number;
  name: string;
  surname: string;
  tckn: string;
  studentNo: string;
  grade: string;
  phoneNumber: string;
  birthPlace: string;
  birthDate: Date;
  user: UserUpdate;
  facultyId: number;
  address: string;
}

interface PagedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export interface Pageable {
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export interface StudentPagedResponse
  extends PagedResponse<Student> { }

export interface User {
  id: number;
  email: string;
  activity: boolean;
}

export interface UserCreate {
  password: string;
  email: string;
}

export interface UserUpdate {
  id: number;
  email: string;
  activity: boolean;
}

export type Faculty = {
  id: number;
  name: string;
}

export interface FacultyPagedResponse extends PagedResponse<Faculty> {}