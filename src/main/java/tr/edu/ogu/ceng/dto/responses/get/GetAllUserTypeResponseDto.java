package tr.edu.ogu.ceng.dto.responses.get;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllUserTypeResponseDto {
	private Long id;
	private String type;
	private Timestamp createDate;
	private Timestamp updateDate;
}
