package tr.edu.ogu.ceng.dto.requests;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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
	private String birthPlace;
	private Timestamp birthDate;
	private UserRequestDto user;
	private long facultyId;
	private String password;
	private String confirmPassword;
	private String email;
	private String address;
}
