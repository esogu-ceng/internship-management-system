package tr.edu.ogu.ceng.model;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ims_faculties")
@Data
public class Faculty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name = "name")
    private String name;

	@Column(name = "create_date")
	private Timestamp createDate;
	
	@Column(name = "update_date")
	private Timestamp updateDate;



	
}
