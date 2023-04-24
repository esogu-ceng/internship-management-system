package tr.edu.ogu.ceng.dto.requests;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {
	private Long id;
	private String name;
	private String surname;
	private String tckn;
	private String grade;
	private String studentNo;
	private String phoneNumber;
	private String homePhoneNumber;
	private String boulevard;
	private String mainStreet;
	private String street;
	private String neighborhood;
	private String outerDoorNo;
	private String innerDoorNo;
	private String province;
	private String subprovince;
	private String village;
	private String zipCode;
	private String motherName;
	private String fatherName;
	private String birthPlace;
	private String birthDate;
	private String idCardSerialNo;
	private String idRegisterProvince;
	private String idRegisterSubprovince;
	private String idRegisterStreetVillage;
	private String idRegisterVolumeNo;
	private String idRegisterFamilySerialNo;
	private String idRegisterSerialNo;
	private String idRegistryOffice;
	private String idRegistryReason;
	private Boolean sgkFamily;
	private Boolean sgkSelf;
	private Timestamp createDate;
	private Timestamp updateDate;
	private long userId;
	private long facultyId;
	private String password;
	private String confirmPassword;
	private String username;
	private String email;
}
