package tr.edu.ogu.ceng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.model.Faculty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto {

	public FacultyDto(Faculty faculty) {
		super();
		this.id = faculty.getId();
		this.name = faculty.getName();

	}

	private Long id;
	private String name;

}
