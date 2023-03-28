package tr.edu.ogu.ceng.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "ims_students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, name = "student_no", nullable = false)
	private String studentNo;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surname", nullable = false)
	private String surname;
	
	@Column(name = "tckn", nullable = false)
	private String tcNo;
	
	@Column(name = "grade", nullable = false)
	private String grade;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "home_phone_number", nullable = false)
	private String homePhoneNumber;
	
	@Column(name = "boulevard", nullable = false)
	private String boulevard;
	
	@Column(name = "main_street", nullable = false)
	private String mainStreet;
	
	@Column(name = "street", nullable = false)
	private String street;
	
	@Column(name = "neighborhood", nullable = false)
	private String neighborhood;
	
	@Column(name = "outer_door_no", nullable = false)
	private String outerDoorNo;
	
	@Column(name = "inner_door_no", nullable = false)
	private String innerDoorNo;
	
	@Column(name = "province", nullable = false)
	private String province;
	
	@Column(name = "subprovince", nullable = false)
	private String subprovince;
	
	@Column(name = "village", nullable = false)
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
	private String birtDate;
	
	@Column(name = "id_card_serial_no", nullable = false)
	private String idCardSerialNo;
	
	@Column(name = "id_register_province", nullable = false)
	private String idRegisterProvience;
	
	@Column(name = "id_register_subprovince", nullable = false)
	private String idRegisterSubprovience;
	
	@Column(name = "id_register_street_village", nullable = false)
	private String idRegisterStreetVillage;
	
	@Column(name = "id_register_volume_no", nullable = false)
	private String idRegisterVolumeNo;
	
	@Column(name = "id_register_family_serial_no", nullable = false)
	private String idRegisterFamilySerialNo;
	
	@Column(name = "id_register_serial_no", nullable = false)
	private String idRegisterSerialNo;
	
	@Column(name = "id_registry_office", nullable = false)
	private String idRegisterOffice;
	
	@Column(name = "id_registry_reason", nullable = false)
	private String idRegisterReason;
	
	@Column(name = "sgk_family", nullable = true)
	private Boolean sgkFamily;
	
	@Column(name = "sgk_self", nullable = true)
	private Boolean sgkSelf;
	
	@Column(name = "create_date", nullable = true)
	private Timestamp createDate;
	
	@Column(name = "update_date", nullable = true)
	private Timestamp updateDate;
	
	@Column(name = "user_id", nullable = false)
	private Integer UserId;
	
	@Column(name = "faculty_id", nullable = false)
	private Integer facultyId;
	
	//getter & setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTcNo() {
		return tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getBoulevard() {
		return boulevard;
	}

	public void setBoulevard(String boulevard) {
		this.boulevard = boulevard;
	}

	public String getMainStreet() {
		return mainStreet;
	}

	public void setMainStreet(String mainStreet) {
		this.mainStreet = mainStreet;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getOuterDoorNo() {
		return outerDoorNo;
	}

	public void setOuterDoorNo(String outerDoorNo) {
		this.outerDoorNo = outerDoorNo;
	}

	public String getInnerDoorNo() {
		return innerDoorNo;
	}

	public void setInnerDoorNo(String innerDoorNo) {
		this.innerDoorNo = innerDoorNo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSubprovince() {
		return subprovince;
	}

	public void setSubprovince(String subprovince) {
		this.subprovince = subprovince;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getBirtDate() {
		return birtDate;
	}

	public void setBirtDate(String birtDate) {
		this.birtDate = birtDate;
	}

	public String getIdCardSerialNo() {
		return idCardSerialNo;
	}

	public void setIdCardSerialNo(String idCardSerialNo) {
		this.idCardSerialNo = idCardSerialNo;
	}

	public String getIdRegisterProvience() {
		return idRegisterProvience;
	}

	public void setIdRegisterProvience(String idRegisterProvience) {
		this.idRegisterProvience = idRegisterProvience;
	}

	public String getIdRegisterSubprovience() {
		return idRegisterSubprovience;
	}

	public void setIdRegisterSubprovience(String idRegisterSubprovience) {
		this.idRegisterSubprovience = idRegisterSubprovience;
	}

	public String getIdRegisterStreetVillage() {
		return idRegisterStreetVillage;
	}

	public void setIdRegisterStreetVillage(String idRegisterStreetVillage) {
		this.idRegisterStreetVillage = idRegisterStreetVillage;
	}

	public String getIdRegisterVolumeNo() {
		return idRegisterVolumeNo;
	}

	public void setIdRegisterVolumeNo(String idRegisterVolumeNo) {
		this.idRegisterVolumeNo = idRegisterVolumeNo;
	}

	public String getIdRegisterFamilySerialNo() {
		return idRegisterFamilySerialNo;
	}

	public void setIdRegisterFamilySerialNo(String idRegisterFamilySerialNo) {
		this.idRegisterFamilySerialNo = idRegisterFamilySerialNo;
	}

	public String getIdRegisterSerialNo() {
		return idRegisterSerialNo;
	}

	public void setIdRegisterSerialNo(String idRegisterSerialNo) {
		this.idRegisterSerialNo = idRegisterSerialNo;
	}

	public String getIdRegisterOffice() {
		return idRegisterOffice;
	}

	public void setIdRegisterOffice(String idRegisterOffice) {
		this.idRegisterOffice = idRegisterOffice;
	}

	public String getIdRegisterReason() {
		return idRegisterReason;
	}

	public void setIdRegisterReason(String idRegisterReason) {
		this.idRegisterReason = idRegisterReason;
	}

	public Boolean getSgkFamily() {
		return sgkFamily;
	}

	public void setSgkFamily(Boolean sgkFamily) {
		this.sgkFamily = sgkFamily;
	}

	public Boolean getSgkSelf() {
		return sgkSelf;
	}

	public void setSgkSelf(Boolean sgkSelf) {
		this.sgkSelf = sgkSelf;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUserId() {
		return UserId;
	}

	public void setUserId(Integer userId) {
		UserId = userId;
	}

	public Integer getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Integer facultyId) {
		this.facultyId = facultyId;
	}
}