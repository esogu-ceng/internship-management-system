package tr.edu.ogu.ceng.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipEvaluateFormResponseDto {
	private Long id;
	private String name;
	private String surname;
	private String filePath;
	private Internship internship;
	private Company company;
}
