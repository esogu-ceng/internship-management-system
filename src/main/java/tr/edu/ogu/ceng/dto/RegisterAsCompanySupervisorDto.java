package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAsCompanySupervisorDto {
	private Long id;
	private Long userId;
	private Long companyId;
	private String name;
	private String surname;
	private String phoneNumber;
	private Timestamp createDate;
	private Timestamp updateDate;
    private String password;
    private String confirmPassword;
}
