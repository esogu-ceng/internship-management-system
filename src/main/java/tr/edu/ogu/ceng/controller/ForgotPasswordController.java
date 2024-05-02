package tr.edu.ogu.ceng.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.EmailReceiverDto;
import tr.edu.ogu.ceng.dto.ResetPasswordDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.service.ForgotPasswordService;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class ForgotPasswordController {
	private ForgotPasswordService forgotPasswordService;
	private MessageResource messageResource;

	@PostMapping("/forgot-password")
	public ResponseEntity<String> sendPasswordResetEmail(@RequestBody EmailReceiverDto email) throws Exception {
		forgotPasswordService.sendResetPasswordEmail(email);
		return ResponseEntity.ok(messageResource.getMessage("reset.password.email.success"));
	}

	@PostMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestBody ResetPasswordDto resetPasswordDto) throws Exception {
		forgotPasswordService.updatePassword(resetPasswordDto);
		return ResponseEntity.ok(messageResource.getMessage("update.password.success"));
	}

}
