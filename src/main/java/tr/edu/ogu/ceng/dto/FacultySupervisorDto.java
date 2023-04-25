package tr.edu.ogu.ceng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultySupervisorDto {

	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private String supervisorNo;
	private UserDto user;
	private FacultyDto faculty;

}
