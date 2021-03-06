package br.com.aisdigital.araujofelipe.api.repository.entity;

import java.time.Instant;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractBean {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<TimeCard> timeCards = Stream.of(Month.values()).map(month -> new TimeCard(this, month, null)).collect(Collectors.toList());
	
	@Column
	private String name;
	
	@Column(unique = true)
	private String login;
	
	@Transient
	@JsonIgnore
	private String password = "secret";
	
	
	public User(Long id, String name, String login) {
		setId(id);
		this.name = name;
		this.login = login;
	}
	
	public User(Long id) {
		setId(id);
	}
	
	@PrePersist
	@Override
	protected void onCreate() {
		setCreatedAt(Instant.now());
		setUpdatedAt(Instant.now());
	}
	
	@PreUpdate
	@Override
	protected void onUpdate() {
		setUpdatedAt(Instant.now());
	}
	
	
}
