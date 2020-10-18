package br.com.aisdigital.araujofelipe.api.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.aisdigital.araujofelipe.api.repository.UserRepository;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository repository;
	
	public CustomUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = repository.findByLogin(username);
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), Collections.emptyList());
	}

}
