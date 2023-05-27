package tr.edu.ogu.ceng.dto.responses;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipResponseCompanyDto {
	private Long id;
	private InternshipStatus status;
	private Timestamp startDate;
	private Timestamp endDate;
	private int days;
	private StudentResponseDto student;
	private CompanyDto company;
	private Long facultySupervisorId;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}
