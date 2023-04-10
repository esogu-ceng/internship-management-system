package tr.edu.ogu.ceng.dto;

import lombok.Data;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;

@Data
public class InternshipEvaluateFormDto {
	private Long id;
	private String name;
	private String surname;
	private byte[] fileContent;
	private Internship internship;
	private Company company;
}
