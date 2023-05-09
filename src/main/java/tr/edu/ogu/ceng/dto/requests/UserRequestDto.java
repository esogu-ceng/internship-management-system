package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dto.UserTypeDto;
import tr.edu.ogu.ceng.model.UserType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
	private Long id;
	private String username;
	private String password;
	private String email;
	private long userTypeId;

}
