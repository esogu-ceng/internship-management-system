package tr.edu.ogu.ceng.dto.responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanyDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySupervisorResponseDto {
	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private UserResponseDto user;
	private CompanyDto company;
}
