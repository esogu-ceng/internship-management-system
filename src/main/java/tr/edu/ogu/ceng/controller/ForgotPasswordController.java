package tr.edu.ogu.ceng.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.EmailReceiverDto;
import tr.edu.ogu.ceng.service.ForgotPasswordService;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
	@Autowired
	ForgotPasswordService forgotPasswordService; 
	
	 @PostMapping()
	 public ResponseEntity<String> sendPasswordResetEmail(@RequestBody EmailReceiverDto email) {
		 try {
			 forgotPasswordService.sendResetPasswordEmail(email);
			 return ResponseEntity.ok("Reset password e-mail sent successfully!");
		 }
		 catch (MessagingException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while sending e-mail!");
	     }
		 catch(Exception ex) {
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		 }
	 } 
}
