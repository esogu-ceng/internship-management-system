package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipEvaluateFormRequestDto {
	private String name;
	private String surname;
	private String filePath;
	private Internship internship;
	private Company company;
}
