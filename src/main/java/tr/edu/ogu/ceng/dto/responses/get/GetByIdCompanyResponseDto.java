package tr.edu.ogu.ceng.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.model.Company;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetByIdCompanyResponseDto {

	public GetByIdCompanyResponseDto(Company company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
		this.address = company.getAddress();
		this.phone_number = company.getPhoneNumber();
		this.fax_number = company.getFaxNumber();
		this.email = company.getEmail();
		this.scope = company.getScope();
		this.description = company.getDescription();
	}

	private Long id;
	private String name;
	private String address;
	private String phone_number;
	private String fax_number;
	private String email;
	private String scope;
	private String description;

}
