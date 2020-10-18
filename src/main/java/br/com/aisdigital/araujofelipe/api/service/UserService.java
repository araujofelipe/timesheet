package br.com.aisdigital.araujofelipe.api.service;

import org.springframework.stereotype.Service;

import br.com.aisdigital.araujofelipe.api.repository.UserRepository;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;

@Service
public class UserService {
	
	private final UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	public User findByLogin(String login) {
		return repository.findByLogin(login);
	}
}
