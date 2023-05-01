package tr.edu.ogu.ceng.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	private Long id;
	private String username;
	private String password;
	private String email;
	private UserTypeDto userType;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}
