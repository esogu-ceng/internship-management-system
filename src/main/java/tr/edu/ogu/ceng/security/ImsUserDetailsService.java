package tr.edu.ogu.ceng.security;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.User;

@Service
public class ImsUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * This method uses e-mail as username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		//Sisteme giriş için e-posta bilgisini kullanıyoruz.
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		HashSet<GrantedAuthority> setAuthorities = new HashSet<>();
		setAuthorities.add(new SimpleGrantedAuthority(user.getUserType().name()));
		UserPrincipal userPrincipal = new UserPrincipal(user, new ArrayList<>(setAuthorities));
		return userPrincipal;
	}
}