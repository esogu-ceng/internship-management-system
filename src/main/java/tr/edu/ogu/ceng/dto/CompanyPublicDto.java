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
public class CompanyPublicDto {

	private Long id; 
	private String name;
	private String address;	
	private String scope;
	private String description;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

}
