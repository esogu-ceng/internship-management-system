package tr.edu.ogu.ceng.dto.responses;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
	private Long id;
	private String username;
	private String password;
	private String email;
	private long userTypeID;
	private Timestamp createDate;
	private Timestamp updateDate;
}
