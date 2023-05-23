package tr.edu.ogu.ceng.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
	 
	 @GetMapping("/update-password")
	 public RedirectView redirectToReactApp(@RequestParam(name = "hash") String hash) {
		 	String redirectUrl = "http://localhost:3000/public/update-password?hash=" + hash;
	        return new RedirectView(redirectUrl);
	 }
	 
}
