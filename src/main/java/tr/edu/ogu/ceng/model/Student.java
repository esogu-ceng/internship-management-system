package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ims_students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "tckn", nullable = false)
	private String tckn;

	@Column(name = "student_no", nullable = false)
	private String student_no;

	@Column(name = "grade", nullable = false)
	private String grade;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "phone_number", nullable = false)
	private String phone_number;

	@Column(name = "home_phone_number", nullable = false)
	private String home_phone_number;

	@Column(name = "boulevard", nullable = false)
	private String boulevard;

	@Column(name = "main_stret", nullable = false)
	private String main_street;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "neighborhood", nullable = false)
	private String neighborhood;

	@Column(name = "outer_door_no", nullable = false)
	private String outer_door_no;

	@Column(name = "inner_door_no", nullable = false)
	private String inner_door_no;

	@Column(name = "province", nullable = false)
	private String province;

	@Column(name = "subprovince", nullable = false)
	private String subprovince;

	@Column(name = "village", nullable = false)
	private String village;

	@Column(name = "zip_code", nullable = false)
	private String zip_code;

	@Column(name = "mother_name", nullable = false)
	private String mother_name;

	@Column(name = "father_name", nullable = false)
	private String father_name;

	@Column(name = "birth_place", nullable = false)
	private String birth_place;

	@Column(name = "birth_date", nullable = false)
	private Timestamp birth_date;

	@Column(name = "id_card_serial_no", nullable = false)
	private String id_card_serial_no;

	@Column(name = "id_register_province", nullable = false)
	private String id_register_province;

	@Column(name = "id_register_subprovince", nullable = false)
	private String id_register_subprovince;

	@Column(name = "id_register_street_village", nullable = false)
	private String id_register_street_village;

	@Column(name = "id_register_volume_no", nullable = false)
	private String id_register_volume_no;

	@Column(name = "id_register_family_serial_no", nullable = false)
	private String id_register_family_serial_no;

	@Column(name = "id_register_serial_no", nullable = false)
	private String id_register_serial_no;

	@Column(name = "id_register_office", nullable = false)
	private String id_register_office;

	@Column(name = "id_register_reason", nullable = false)
	private String id_register_reason;

	@Column(name = "sgk_family", nullable = false)
	private Boolean sgk_family;

	@Column(name = "sgk_self", nullable = false)
	private Boolean sgk_self;

	@Column(name = "create_date", nullable = false)
	private Timestamp create_date;

	@Column(name = "update_date", nullable = false)
	private Timestamp update_date;

	@Column(name = "faculty_id", nullable = false)
	private int faculty_id;

	@Column(name = "user_id", nullable = false)
	private int user_id;

}
