package tr.edu.ogu.ceng.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingResponseDto {
	private String key;
	private String value;
}
