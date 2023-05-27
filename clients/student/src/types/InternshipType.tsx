import { Student } from './StudentType';

export type Internship = {
    companyId: number;
    facultySupervisorId: number;
    id: number;
    status: string;
    days: number;
    student: Student;
    startDate: Date;
    endDate: Date;
};
