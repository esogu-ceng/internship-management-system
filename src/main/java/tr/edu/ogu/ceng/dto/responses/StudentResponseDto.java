package tr.edu.ogu.ceng.dto.responses;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
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
	private Timestamp birthDate;
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
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private long userId;
	private long facultyId;

}
