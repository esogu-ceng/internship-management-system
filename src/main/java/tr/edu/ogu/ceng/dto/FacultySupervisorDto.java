package tr.edu.ogu.ceng.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

	@NotBlank(message = "{name.not.blank}")
	private String name;
	private String surname;
	private String phoneNumber;
	private String supervisorNo;

	@Valid
	private UserDto user;
	private FacultyDto faculty;

}
