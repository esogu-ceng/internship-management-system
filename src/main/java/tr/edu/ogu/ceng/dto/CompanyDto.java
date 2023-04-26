package tr.edu.ogu.ceng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
	private Long id;
	private String name;
	private String address;
	private String phone_number;
	private String fax_number;
	private String email;
	private String scope;
	private String description;
}
