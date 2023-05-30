import { User } from './UserType';

export type Student = {
  id: number;
  name: string;
  surname: string;
  tckn: string;
  studentNo: string;
  grade: string;
  phoneNumber: string;
  homePhoneNumber: string;
  boulevard: string;
  mainStreet: string;
  street: string;
  neighborhood: string;
  outerDoorNo: string;
  innerDoorNo: string;
  province: string;
  subprovince: string;
  village: string;
  zipCode: string;
  motherName: string;
  fatherName: string;
  birthPlace: string;
  birthDate: Date;
  idCardSerialNo: string;
  idRegisterProvince: string;
  idRegisterSubprovince: string;
  idRegisterStreetVillage: string;
  idRegisterVolumeNo: string;
  idRegisterFamilySerialNo: string;
  idRegisterSerialNo: string;
  idRegistryOffice: string;
  idRegistryReason: string;
  sgkFamily?: boolean;
  sgkSelf?: boolean;
  user: User;
  faculty: Faculty;
};

export type Faculty = {
  id: number;
  // Diğer özellikleri buraya ekleyin
};
