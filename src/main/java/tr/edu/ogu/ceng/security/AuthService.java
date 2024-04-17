package tr.edu.ogu.ceng.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.model.User;

@AllArgsConstructor
@Service
public class AuthService {

	public User getAuthUser() {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userPrincipal.getUser();
	}

}
