import { Student } from './StudentType';

export type Internship = {
  id: number;
  status: string;
  days: number;
  student: Student;
  startDate: Date;
  endDate: Date;
};
