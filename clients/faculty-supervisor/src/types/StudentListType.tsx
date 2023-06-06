export type Student = {
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
  };
  export type User = {
    id: number;
    email : string;
  };
  export type Faculty ={
    id: number;
    name : string;
  };