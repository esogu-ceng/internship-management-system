package tr.edu.ogu.ceng.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSettingResponseDto {
	private String key;
	private String value;
}
