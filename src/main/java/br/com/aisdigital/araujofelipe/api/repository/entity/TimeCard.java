package br.com.aisdigital.araujofelipe.api.repository.entity;

import java.time.Instant;
import java.time.Month;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class TimeCard extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TimeCard(User user, Month month) {
		this.user = user;
		this.month = month;
	}
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@Enumerated(EnumType.STRING)
	private Month month;
	
	@OneToMany
	private Set<Record> records = Collections.emptySet();

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
