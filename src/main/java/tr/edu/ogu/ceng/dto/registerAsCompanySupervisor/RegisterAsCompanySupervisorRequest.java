package tr.edu.ogu.ceng.dto.registerAsCompanySupervisor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAsCompanySupervisorRequest {

	private long companyId;
	private long userId;
	private String username;
	private String password;
	private String confirmPassword;	
	private String name;
	private String surname;
	private String phoneNumber;
	
}
