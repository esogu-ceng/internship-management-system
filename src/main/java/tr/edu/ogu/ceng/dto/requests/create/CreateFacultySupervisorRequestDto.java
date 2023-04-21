package tr.edu.ogu.ceng.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFacultySupervisorRequestDto {
	private String name;
	private String surname;
	private String phoneNumber;
	private String supervisorNo;
	private long userId;
	private long facultyId;
}
