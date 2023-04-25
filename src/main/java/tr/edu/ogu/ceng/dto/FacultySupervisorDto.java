package tr.edu.ogu.ceng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultySupervisorDto {
	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private String supervisorNo;
}
