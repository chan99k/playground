package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.sakila.adapter.persistence.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Short> {
}