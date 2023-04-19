package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultySupervisorRequestDto {
	private String name;
	private String surname;
	private String phoneNumber;
	private String supervisorNo;
}
