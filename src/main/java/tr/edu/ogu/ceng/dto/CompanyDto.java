package tr.edu.ogu.ceng.dto;

import tr.edu.ogu.ceng.model.Company;

import lombok.Data;

@Data
public class CompanyDto {
	
	public CompanyDto() { }
	
	public CompanyDto(Company company) {
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
	
	public CompanyDto(Long id, String name, String address, String phone_number, String fax_number, String email,
			String scope, String description) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone_number = phone_number;
		this.fax_number = fax_number;
		this.email = email;
		this.scope = scope;
		this.description = description;
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
