package tr.edu.ogu.ceng.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "ims_internship_evaluate_form")
@Data
public class InternshipEvaluateForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "question1", nullable = true)
	private Integer question1;

	@Column(name = "question2", nullable = true)
	private Integer question2;

	@Column(name = "question3", nullable = true)
	private Integer question3;

	@Column(name = "question4", nullable = true)
	private Integer question4;

	@Column(name = "question5", nullable = true)
	private Integer question5;

	@Column(name = "question6", nullable = true)
	private Integer question6;

	@Column(name = "question7", nullable = true)
	private Integer question7;

	@Column(name = "question8", nullable = true)
	private Integer question8;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internship_id", nullable = false)
	private Internship internship;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

}
