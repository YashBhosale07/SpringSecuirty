package in.yash.security.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import in.yash.security.repository.UserRepository;

@Component
public class CustomUserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		in.yash.security.model.User u=repository.findByEmail(username).orElseThrow(()->
		 new UsernameNotFoundException("user not present with email: "+username));
		String role=u.getRole().name();
		return
				new User(u.getEmail(),
						u.getPassword(), 
						Collections.singletonList(new SimpleGrantedAuthority(role)));
	}
}


