package tr.edu.ogu.ceng.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSettingRequestDto {
	private Long id;
	private String key;
	private String value;
}
