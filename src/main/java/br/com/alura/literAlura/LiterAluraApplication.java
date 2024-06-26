package br.com.alura.literAlura;

import br.com.alura.literAlura.repository.AuthorRepository;
import br.com.alura.literAlura.repository.BookRepository;
import br.com.alura.literAlura.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;


    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) {

        var main = new Main(authorRepository, bookRepository);
        main.displayMenu();
    }

}
