package tr.edu.ogu.ceng.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyDto {

	private Long id;
	private String name;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

}
