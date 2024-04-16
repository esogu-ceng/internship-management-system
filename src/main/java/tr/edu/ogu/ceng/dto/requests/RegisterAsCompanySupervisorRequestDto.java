package tr.edu.ogu.ceng.dto.requests;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanyDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAsCompanySupervisorRequestDto {
	private String name;
	private String surname;
	private String phoneNumber;
	private String email;
    private String password;
    private String confirmPassword;
	@JsonIncludeProperties(value="id")
	private CompanyDto company;
}
