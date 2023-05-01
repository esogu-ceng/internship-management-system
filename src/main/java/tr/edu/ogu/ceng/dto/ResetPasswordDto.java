package tr.edu.ogu.ceng.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
	private String hash;
	private String password;
	private String confirmPassword;
}