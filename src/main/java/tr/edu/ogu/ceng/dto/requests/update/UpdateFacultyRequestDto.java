package tr.edu.ogu.ceng.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFacultyRequestDto {
	private Long id;
	private String name;
}
