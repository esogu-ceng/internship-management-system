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
}

export type Faculty ={
  id: number;
}
