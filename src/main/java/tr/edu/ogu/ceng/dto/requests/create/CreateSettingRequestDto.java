package tr.edu.ogu.ceng.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSettingRequestDto {
	private String key;
	private String value;
}
