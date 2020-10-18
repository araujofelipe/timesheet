package br.com.aisdigital.araujofelipe.api.repository.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractBean implements Serializable {
	
	

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Instant createdAt;
	
	private Instant updatedAt;
	
	protected abstract void onCreate();
	
	protected abstract void onUpdate();
}
