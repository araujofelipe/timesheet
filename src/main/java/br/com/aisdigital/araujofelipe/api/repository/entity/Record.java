package br.com.aisdigital.araujofelipe.api.repository.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Record extends AbstractBean {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"createdAt", "updatedAt",  "timeCards", "version"})
	private User user;
	
	@Column
	private LocalDate date;
	
	@Enumerated(EnumType.STRING)
	@Column
	private Period period;
	
	@Column
	private LocalTime start;
	
	@Column
	private LocalTime end;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"records"})
	private Project project;
	
	@ManyToOne
	@JsonIgnoreProperties(value = {"records"})
	private TimeCard timeCard;

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