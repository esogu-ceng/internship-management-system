package tr.edu.ogu.ceng.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="ims_internship_registries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipRegistries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "file_path", nullable = false)
	private String filePath;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "date", nullable = false)
	private Timestamp date;
	
	@Column(name = "create_date", nullable = false)
	private Timestamp create_date;
	
	@Column(name = "update_date", nullable = false)
	private Timestamp update_date;
	
	@Column(name = "internship_id", nullable = false)
	private Long internshipId;
	
}

