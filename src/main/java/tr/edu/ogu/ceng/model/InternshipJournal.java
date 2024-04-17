package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ims_internship_journals")
public class InternshipJournal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "unit_name")
	private String unitName;

	@Column(name = "journal", nullable = false)
	private String journal;

	@Column(name = "operation_time", nullable = false)
	private Long operationTime;

    @Column(name = "starting_date", nullable = false)
	private Timestamp startingDate;

	@Column(name = "end_date", nullable = false)
	private Timestamp endDate;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internship_id", nullable = false)
	private Internship internship;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_supervisor_id")
	private CompanySupervisor supervisor;

	@Column(name = "confirmation", nullable = false)
	private boolean confirmation;
	



}