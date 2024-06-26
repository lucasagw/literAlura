package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleIgnoreCase(String title);


    long countByLanguage(String language);
}
