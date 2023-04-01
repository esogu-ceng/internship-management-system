package tr.edu.ogu.ceng.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="ims_internships")
public class Internship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	@Column(name="status", nullable=false)
	private String status;
	@Column(name="start_date", nullable=false)
	private Date start_date;
	@Column(name="end_date", nullable=false)
	private Date end_date;
	@Column(name="days", nullable=false)
	private int days;
	@Column(name="create_date", nullable=false)
	private Date create_date;
	@Column(name="update_date", nullable=false)
	private Date update_date;
	@Column(name="student_id", nullable=false)
	private Long student_id;
	@Column(name="company_id", nullable=false)
	private Long company_id;
	@Column(name="faculty_supervisor_id", nullable=false)
	private Long faculty_supervisor_id;
}
