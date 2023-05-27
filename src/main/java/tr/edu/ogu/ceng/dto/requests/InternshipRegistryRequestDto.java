package tr.edu.ogu.ceng.dto.requests;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipRegistryRequestDto {

	private Long id;
	private String filePath;
	private String name;
	private String type;
	private Timestamp date;
	private Long internshipId;
}
