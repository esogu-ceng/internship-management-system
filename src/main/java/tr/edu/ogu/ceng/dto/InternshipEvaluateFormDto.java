package tr.edu.ogu.ceng.dto;

import lombok.Data;

@Data
public class InternshipEvaluateFormDto {
	private Long id;
	private String name;
	private String surname;
	private String filePath;
	private Long internshipId;
	private Long companyId;
}
