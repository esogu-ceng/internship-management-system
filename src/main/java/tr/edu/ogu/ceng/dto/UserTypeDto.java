package tr.edu.ogu.ceng.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeDto {
	private Long id;
	private String type;
	private Timestamp createDate;
	private Timestamp updateDate;
}
