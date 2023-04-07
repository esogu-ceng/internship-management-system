package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ims_students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "tckn", nullable = false, unique = true)
	private String tckn;

	@Column(name = "student_no", nullable = false, unique = true)
	private String studentNo;

	@Column(name = "grade", nullable = false)
	private String grade;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "home_phone_number")
	private String homePhoneNumber;

	@Column(name = "boulevard")
	private String boulevard;

	@Column(name = "main_street")
	private String mainStreet;

	@Column(name = "street")
	private String street;

	@Column(name = "neighborhood")
	private String neighborhood;

	@Column(name = "outer_door_no")
	private String outerDoorNo;

	@Column(name = "inner_door_no")
	private String innerDoorNo;

	@Column(name = "province", nullable = false)
	private String province;

	@Column(name = "subprovince", nullable = false)
	private String subprovince;

	@Column(name = "village")
	private String village;

	@Column(name = "zip_code", nullable = false)
	private String zipCode;

	@Column(name = "mother_name", nullable = false)
	private String motherName;

	@Column(name = "father_name", nullable = false)
	private String fatherName;

	@Column(name = "birth_place", nullable = false)
	private String birthPlace;

	@Column(name = "birth_date", nullable = false)
	private Timestamp birthDate;

	@Column(name = "id_card_serial_no", nullable = false)
	private String idCardSerialNo;

	@Column(name = "id_register_province", nullable = false)
	private String idRegisterProvince;

	@Column(name = "id_register_subprovince", nullable = false)
	private String idRegisterSubprovince;

	@Column(name = "id_register_street_village", nullable = false)
	private String idRegisterStreetVillage;

	@Column(name = "id_register_volume_no", nullable = false)
	private String idRegisterVolumeNo;

	@Column(name = "id_register_family_serial_no", nullable = false)
	private String idRegisterFamilySerialNo;

	@Column(name = "id_register_serial_no", nullable = false)
	private String idRegisterSerialNo;

	@Column(name = "id_registry_office", nullable = false)
	private String idRegistryOffice;

	@Column(name = "id_registry_reason", nullable = false)
	private String idRegistryReason;

	@Column(name = "sgk_family")
	private Boolean sgkFamily;

	@Column(name = "sgk_self")
	private Boolean sgkSelf;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true, referencedColumnName = "id")
	private User user;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "faculty_id", nullable = false)
	private Faculty faculty;

}
