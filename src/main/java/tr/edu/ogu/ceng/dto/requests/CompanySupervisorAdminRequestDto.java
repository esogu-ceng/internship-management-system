package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanyDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CompanySupervisorAdminRequestDto {

	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private UserRequestDto user;
	private CompanyDto company;

}
