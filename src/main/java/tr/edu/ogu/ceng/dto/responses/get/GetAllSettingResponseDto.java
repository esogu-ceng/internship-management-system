package tr.edu.ogu.ceng.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSettingResponseDto {
	private String key;
	private String value;
}
