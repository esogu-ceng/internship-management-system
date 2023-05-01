package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAsCompanySupervisorRequestDto {
	private long userId;
	private long companyId;
	private String name;
	private String surname;
	private String phoneNumber;
	private String password;
	private String confirmPassword;
}
