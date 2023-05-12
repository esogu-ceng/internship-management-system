package tr.edu.ogu.ceng.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import tr.edu.ogu.ceng.enums.UserType;

public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		UserType userType = ((UserPrincipal) authentication.getPrincipal()).getUser().getUserType();
		String targetUrl = "/" + userType.name().toLowerCase();
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

}
