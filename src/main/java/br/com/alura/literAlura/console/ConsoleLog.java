package br.com.alura.literAlura.console;

import br.com.alura.literAlura.dto.AuthorDTO;
import br.com.alura.literAlura.model.Book;

public class ConsoleLog {

    public static void book(Book bookRegistered) {
        System.out.printf("""
                        ------- Book -------
                        Title: %s
                        Author: %s
                        Language: %s
                        Number of downloads: %d
                        --------------------%n
                        %n""", bookRegistered.getTitle(),
                bookRegistered.getAuthor().getName(),
                bookRegistered.getLanguage(),
                bookRegistered.getDownloadCount());
    }

    public static void author(AuthorDTO author) {
        System.out.printf("""
                        ------- Author -------
                        Author: %s
                        Birth year: %d
                        Death year: %d
                        Books: %s
                        ---------------------%n
                        %n""", author.author(),
                author.birthYear(),
                author.deathYear(),
                author.books());
    }
}
