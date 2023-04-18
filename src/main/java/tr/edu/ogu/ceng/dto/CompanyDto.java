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
public class CompanyDto {

	private Long id;
	private String name;
	private String address;
	private String phone_number;
	private String fax_number;
	private String email;
	private String scope;
	private String description;
	private Timestamp createDate;
	private Timestamp updateDate;

}
