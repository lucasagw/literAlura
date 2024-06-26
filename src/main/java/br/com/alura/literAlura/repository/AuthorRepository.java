package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameIgnoreCase(String name);


    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear > :year)")
    List<Author> findAuthorsAliveInYear(int year);

}
