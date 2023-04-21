package tr.edu.ogu.ceng.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {
	private Long id;
	private String username;
	private String password;
	private String email;
	private long userTypeId;
}
