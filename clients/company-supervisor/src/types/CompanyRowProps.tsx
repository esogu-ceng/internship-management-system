import { Company } from './CompanyType';

export interface CompanyRowProps {
    companyId?: number;
    field: string;
    value: string | number | undefined;
    dataField: keyof Company;
    onEditClick: () => void;
    onSaveClick: (dataField: keyof Company, editedValue: string | number | undefined) => void;
  }