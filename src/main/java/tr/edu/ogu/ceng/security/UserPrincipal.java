package tr.edu.ogu.ceng.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.AccessDeniedException;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
	private User user;
	private List<GrantedAuthority> authorities;

	@Override
	public List<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = true;

		if (user.isActivity() == false && user.getUserType() == UserType.STUDENT){
			enabled = false;
			throw new AccessDeniedException("Öğrenci " + user.getEmail() + " pasif durumda");
		}
		return enabled;
	}
}