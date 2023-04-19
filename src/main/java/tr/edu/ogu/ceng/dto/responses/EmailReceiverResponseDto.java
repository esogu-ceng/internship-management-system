package tr.edu.ogu.ceng.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailReceiverResponseDto {
	private String email;

	public String getEmail() {
		return email;
	}
}
