package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.enums.InternshipStatus;

@Entity
@Table(name = "ims_internships")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Internship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private InternshipStatus status;

	@Column(name = "start_date", nullable = false)
	private Timestamp startDate;

	@Column(name = "end_date", nullable = false)
	private Timestamp endDate;

	@Column(name = "days", nullable = false)
	private int days;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_supervisor_id", nullable = false)
	private FacultySupervisor facultySupervisor;

	// TODO Bu sınıfın internship registry sınıfıyla ilişkide olması sebebiyle
	// buraya internship registry listesi eklenebilir.
}
