package tr.edu.ogu.ceng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipEvaluateFormDto {
	private Long id;
	private Internship internship;
	private Company company;
	private int question1;
	private int question2;
	private int question3;
	private int question4;
	private int question5;
	private int question6;
	private int question7;
	private int question8;
}
