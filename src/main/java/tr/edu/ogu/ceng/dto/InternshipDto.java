package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipDto {

	private Long id;
	private String status;
	private Timestamp startDate;
	private Timestamp endDate;
	private int days;
	private Long studentId;
	private Long companyId;
	private Long facultySupervisorId;

}
