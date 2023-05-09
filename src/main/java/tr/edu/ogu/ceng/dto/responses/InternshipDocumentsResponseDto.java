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
public class InternshipDocumentsResponseDto {

	private Long id;
	private String filePath;
	private String name;
	private String type;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private Long internshipId;
}
