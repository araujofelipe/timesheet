package br.com.aisdigital.araujofelipe.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.aisdigital.araujofelipe.api.repository.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
