export interface FacultySupervisorCreate {
    name: string;
    surname: string;
    phoneNumber: string;
    supervisorNo: string;
    user: {
      username: string;
      password: string;
      email: string;
    };
    faculty: {
        id: number;
    };
}

export interface FacultySuperviserUpdate {
    id: number;
    name: string;
    surname: string;
    phoneNumber: string;
    supervisorNo: string;
    faculty  : {
        id : number;
    };
    user :{
        id : number;
        activity : boolean;
    };
}

export interface FacultySupervisor {
    id: number;
    name: string;
    surname: string;
    phoneNumber: string;
    supervisorNo: string;
    user : User;
    facultyId : number;
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

export interface FacultySupervisorPagedResponse
    extends PagedResponse<FacultySupervisor> {}


export interface FacultyPagedResponse
extends PagedResponse<Faculty> {}

export interface Faculty {
    id: number;
    name: string;
}

export interface User {
    id: number;
    username: string;
    password: string;
    email: string;
    activity: boolean;
    userType: string;
  }