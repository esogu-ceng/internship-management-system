package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;

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
	private long userTypeId;
	private Timestamp createDate;
	private Timestamp updateDate;
}
