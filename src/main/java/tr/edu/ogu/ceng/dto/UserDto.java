package tr.edu.ogu.ceng.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.enums.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	private Long id;
	@NotBlank(message = "{password.not.blank}")
	private String password;
	private String email;
	private UserType userType;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}
