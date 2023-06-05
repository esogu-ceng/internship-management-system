import { FacultySupervisor } from './FacultySupervisorType';
import { Student } from './StudentType';
import { Company } from './CompanyType';

export type Internship = {
    createDate: Date;
    days: number;
    endDate: Date;
    facultySupervisor: FacultySupervisor;
    id: number;
    startDate: Date;
    status: string;
    student: Student;
    company: Company;
    updateDate: Date;
};