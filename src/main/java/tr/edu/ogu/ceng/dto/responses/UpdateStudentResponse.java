package tr.edu.ogu.ceng.dto.responses;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdateStudentResponse {

	private long id;
	
    private String name;
	
	private String surname;
	
	private String tckn;
	
	private String student_no;
	
	private String grade;
	
	private String email;
	
	private String phone_number;
	
	private String home_phone_number;
	
	private String boulevard;
	
	private String main_street;
	
	private String street;
	
	private String neighborhood;
	
	private String outer_door_no;
	
	private String inner_door_no;
	
	private String province;
	
	private String subprovince;
	
	private String village;
	
	private String zip_code;
	
	private String mother_name;
	
	private String father_name;
	
	private String birth_place;
	
	private Timestamp birth_date;
	
	private String id_card_serial_no;
	
	private String id_register_province;
	
	private String id_register_subprovince;
	
	private String id_register_street_village;
	
	private String id_register_volume_no;
	
	private String id_register_family_serial_no;
	
	private String id_register_serial_no;
	
	private String id_registry_office;
	
	private String id_registry_reason;

	private Boolean sgk_family;

	private Boolean sgk_self;
	
	private Timestamp update_date;
	
	private long userId;
	
	private long facultyId;
}
