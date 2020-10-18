package br.com.aisdigital.araujofelipe.api.repository;

import java.time.Month;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.aisdigital.araujofelipe.api.repository.entity.TimeCard;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;

@Repository
public interface TimeCardRepository extends JpaRepository<TimeCard, Long> {
	TimeCard findByMonthAndUser(Month month, User user);
}
