package tr.edu.ogu.ceng.dto.requests;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.enums.InternshipStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipRequestDto {

	private Long id;
	private InternshipStatus status;
	private Timestamp startDate;
	private Timestamp endDate;
	private int days;
	private Long studentId;
	private Long companyId;
	private Long facultySupervisorId;

}