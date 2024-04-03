package tr.edu.ogu.ceng.dto.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.enums.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAsCompanySupervisorResponseDto {
	private String name;
	private String surname;
	private String phoneNumber;
	private String email;
	private UserType userType;
	private LocalDateTime createDate;
	@JsonIncludeProperties(value="id")
	private CompanyDto company;
	@JsonIncludeProperties(value="id")
	private UserDto user;
	@JsonIncludeProperties(value="id")
	private CompanySupervisorDto supervisor;
}
