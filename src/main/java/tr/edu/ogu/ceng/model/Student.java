package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ims_students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@NotBlank(message = "tckn is mandatory")
	@Column(name = "tckn", nullable = false, unique = true)
	private String tckn;

	@NotBlank(message = "student no is mandatory")
	@Column(name = "student_no", nullable = false, unique = true)
	private String studentNo;

	@Column(name = "grade", nullable = false)
	private String grade;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "birth_place", nullable = false)
	private String birthPlace;

	@Column(name = "birth_date", nullable = false)
	private Timestamp birthDate;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", unique = true, referencedColumnName = "id")
	private User user;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "faculty_id", nullable = false)
	private Faculty faculty;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "cv_path", nullable = true)
	private String cvPath;

}
