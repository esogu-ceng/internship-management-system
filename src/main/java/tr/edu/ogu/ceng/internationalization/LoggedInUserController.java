package tr.edu.ogu.ceng.internationalization;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.CompanySupervisorService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/get-logged-in-user")
public class LoggedInUserController {
	private final MessageResource resource;
	
	@GetMapping
    public User getLoggedInUser(){
        return resource.getLoggedInUser();
    }
}
