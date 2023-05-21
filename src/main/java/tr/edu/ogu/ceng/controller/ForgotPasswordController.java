package tr.edu.ogu.ceng.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.EmailReceiverDto;
import tr.edu.ogu.ceng.dto.ResetPasswordDto;
import tr.edu.ogu.ceng.service.ForgotPasswordService;

@RestController
@RequestMapping("/public")
public class ForgotPasswordController {
	@Autowired
	ForgotPasswordService forgotPasswordService; 
	
	 @PostMapping("/forgot-password")
	 public ResponseEntity<String> sendPasswordResetEmail(@RequestBody EmailReceiverDto email) throws Exception{
			 forgotPasswordService.sendResetPasswordEmail(email);
			 return ResponseEntity.ok("Reset password e-mail sent successfully!");
	 } 
	 
	 @PostMapping("/update-password")
	 public ResponseEntity<String> updatePassword(@RequestBody ResetPasswordDto resetPasswordDto) throws Exception{
			 forgotPasswordService.updatePassword(resetPasswordDto);
			 return ResponseEntity.ok("Password updated successfully!");
	 } 
	 
	 // TODO: Show reset password form
	 // @GetMapping
	 
}
