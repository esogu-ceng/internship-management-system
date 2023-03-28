package tr.edu.ogu.ceng.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Entity
@Table(name = "ims_students")
@Data
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

    @Column(name = "email", nullable = false, unique = true)
    private String email;

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
    private LocalDateTime birthDate;

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
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTckn() {
		return tckn;
	}

	public void setTckn(String tckn) {
		this.tckn = tckn;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
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

	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

	public String getIdCardSerialNo() {
		return idCardSerialNo;
	}

	public void setIdCardSerialNo(String idCardSerialNo) {
		this.idCardSerialNo = idCardSerialNo;
	}

	public String getIdRegisterProvince() {
		return idRegisterProvince;
	}

	public void setIdRegisterProvince(String idRegisterProvince) {
		this.idRegisterProvince = idRegisterProvince;
	}

	public String getIdRegisterSubprovince() {
		return idRegisterSubprovince;
	}

	public void setIdRegisterSubprovince(String idRegisterSubprovince) {
		this.idRegisterSubprovince = idRegisterSubprovince;
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

	public String getIdRegistryOffice() {
		return idRegistryOffice;
	}

	public void setIdRegistryOffice(String idRegistryOffice) {
		this.idRegistryOffice = idRegistryOffice;
	}

	public String getIdRegistryReason() {
		return idRegistryReason;
	}

	public void setIdRegistryReason(String idRegistryReason) {
		this.idRegistryReason = idRegistryReason;
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
}