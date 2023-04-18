package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ims_faculty_supervisors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultySupervisor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "supervisor_no")
	private String supervisorNo;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "faculty_id", nullable = false)
	private Faculty faculty;
}