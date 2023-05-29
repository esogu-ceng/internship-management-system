package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySupervisorRequestDto {

	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private UserRequestDto user;
	private Long companyId;
}
