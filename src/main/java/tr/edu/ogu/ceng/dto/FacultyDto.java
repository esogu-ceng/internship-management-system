package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;

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
	private Timestamp createDate;
	private Timestamp updateDate;

}
