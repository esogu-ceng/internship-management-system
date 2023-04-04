package tr.edu.ogu.ceng.dto;

import java.sql.Date;

import lombok.Data;
@Data
public class InternshipDto {
	public InternshipDto() {}
	private Long id;
	private String status;
	private Date start_date;
	private Date end_date;
	private int days;
	private Date create_date;
	private Date update_date;
	private Long student_id;
	private Long company_id;
	private Long faculty_supervisor_id;
	
}
