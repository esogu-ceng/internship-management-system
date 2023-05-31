package tr.edu.ogu.ceng.dto;

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
public class StudentDto {
	private Long id;
	private String name;
	private String surname;
	private String tckn;
	private String grade;
	private String studentNo;
	private String phoneNumber;
	private String birthPlace;
	private Timestamp birthDate;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private UserDto user;
	private FacultyDto faculty;
	private String password;
	private String confirmPassword;
	private String username;
	private String email;
	private String address;
}
