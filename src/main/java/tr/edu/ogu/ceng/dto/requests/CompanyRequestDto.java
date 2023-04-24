package tr.edu.ogu.ceng.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.model.Company;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {

	public CompanyRequestDto(Company company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
		this.address = company.getAddress();
		this.phoneNumber = company.getPhoneNumber();
		this.faxNumber = company.getFaxNumber();
		this.email = company.getEmail();
		this.scope = company.getScope();
		this.description = company.getDescription();
	}

	private Long id;
	private String name;
	private String address;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	private String scope;
	private String description;

}
