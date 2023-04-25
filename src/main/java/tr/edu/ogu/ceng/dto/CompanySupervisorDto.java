package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySupervisorDto {
	private Long userId;
	private Long companyId;
	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private Timestamp createDate;
	private Timestamp updateDate;
}
