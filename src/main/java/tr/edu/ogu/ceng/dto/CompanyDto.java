package tr.edu.ogu.ceng.dto;

public class CompanyDto {
	
	public CompanyDto() { }
	
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getFax_number() {
		return fax_number;
	}
	public void setFax_number(String fax_number) {
		this.fax_number = fax_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
