package tr.edu.ogu.ceng.dto.responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipDocumentResponseDto {

	private Long id;
	private String file_path;
	private String name;
	private String type;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private Long internshipId;
}
