package tr.edu.ogu.ceng.model;
import javax.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name= "Settings")

public class Settings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "key", nullable = false)
	private String key;
	
	@Column(name = "value", nullable = false)
	private String value;
	
}