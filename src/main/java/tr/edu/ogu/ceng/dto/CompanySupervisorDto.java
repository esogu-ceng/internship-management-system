package tr.edu.ogu.ceng.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySupervisorDto {
	private Long id;
	private String name;
	private String surname;
	private String phoneNumber;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	@JsonIncludeProperties(value= {"id","name"})
	private UserDto user;
	@JsonIncludeProperties(value= {"id","name"})
	private CompanyDto company;
}
