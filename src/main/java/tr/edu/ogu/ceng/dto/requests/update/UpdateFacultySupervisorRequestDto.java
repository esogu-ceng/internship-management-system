package tr.edu.ogu.ceng.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFacultySupervisorRequestDto {
	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private String supervisorNo;
	private long userId;
	private long facultyId;
}
