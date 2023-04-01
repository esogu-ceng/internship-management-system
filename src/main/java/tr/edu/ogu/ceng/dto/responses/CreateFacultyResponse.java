package tr.edu.ogu.ceng.dto.responses;

import java.sql.Timestamp;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.ogu.ceng.dto.requests.CreateFacultyRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFacultyResponse {

	private Long id;
	
    private String name;

	private Timestamp createDate;
	
	private Timestamp updateDate;
}
