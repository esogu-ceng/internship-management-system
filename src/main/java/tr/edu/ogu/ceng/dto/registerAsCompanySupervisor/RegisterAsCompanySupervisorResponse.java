package tr.edu.ogu.ceng.dto.registerAsCompanySupervisor;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAsCompanySupervisorResponse {
	private long id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String phoneNumber;
	private Timestamp createDate;
	private Timestamp updateDate;

}
