package tr.edu.ogu.ceng.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {
	private String username;
	private String password;
	private String email;
	private long userTypeId;
}
