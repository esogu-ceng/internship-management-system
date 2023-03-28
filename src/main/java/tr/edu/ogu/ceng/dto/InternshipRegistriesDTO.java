package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipRegistriesDTO {

	private Long id;
	private String file_path;
	private String name;
	private String type;
	private Timestamp date;
	private Timestamp create_date;
	private Timestamp update_date;
	private Long internship_id;
	
}
