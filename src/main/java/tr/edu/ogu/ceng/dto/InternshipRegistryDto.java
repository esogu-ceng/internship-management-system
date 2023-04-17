package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.model.Internship;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipRegistryDto {

	private Long id;
	private String filePath;
	private String name;
	private String type;
	private Timestamp date;
	private Internship internship;

}
