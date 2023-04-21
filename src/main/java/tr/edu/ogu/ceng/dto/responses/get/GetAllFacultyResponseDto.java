package tr.edu.ogu.ceng.dto.responses.get;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllFacultyResponseDto {
	private Long id;
	private String name;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}
