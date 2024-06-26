package br.com.alura.literAlura.service;

import br.com.alura.literAlura.console.ConsoleLog;
import br.com.alura.literAlura.dto.AuthorDTO;
import br.com.alura.literAlura.exception.BookNotFoundException;
import br.com.alura.literAlura.model.Author;
import br.com.alura.literAlura.model.Book;
import br.com.alura.literAlura.record.AuthorData;
import br.com.alura.literAlura.record.BookData;
import br.com.alura.literAlura.record.ResultData;
import br.com.alura.literAlura.repository.AuthorRepository;
import br.com.alura.literAlura.repository.BookRepository;
import br.com.alura.literAlura.util.ConvertsData;

import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ApiConsume apiConsume = new ApiConsume();

    private ConvertsData convertsData = new ConvertsData();

    private AuthorRepository authorRepository;

    private BookRepository bookRepository;

    private final String REQUEST_PARAM = "search=";


    public Main(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void displayMenu() {
        var option = -1;
        while (option != 0) {


            var menu = """
                    Choose an option:
                    1 - Search book on the web by title
                    2 - List books registered
                    3 - List authors registered 
                    4 - List living authors in a given year
                    5 - Count books in a specific language
                                                    
                    0 - Exit
                    """;

            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchBookWeb();
                    break;
                case 2:
                    listBookRegistered();
                    break;
                case 3:
                    listAuthorRegistered();
                    break;
                case 4:
                    listAuthorsAliveInYear();
                    break;
                case 5:
                    countBookByLanguage();
                    break;
                case 0:
                    System.out.println("Exit...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public void searchBookWeb() {
        var bookDataResult = getBookData();

        if (bookDataResult != null && bookDataResult.books() != null && !bookDataResult.books().isEmpty()) {
            var book = bookDataResult.books().get(0);
            var author = saveAuthors(book.authors());
            var bookRegistered = saveNewBook(book, author);
            ConsoleLog.book(bookRegistered);
        }
    }

    private ResultData getBookData() {

        System.out.println("Enter the book title");
        var bookName = scanner.nextLine().trim();

        var bookOptional = bookRepository.findByTitleIgnoreCase(bookName);

        if (bookOptional.isPresent()) {
            System.out.println("Book already registered: " + bookOptional.get().getTitle());
            return null;
        } else {
            System.out.println("Book not found in the database. Searching on the web...");
            try {
                var json = apiConsume.getData(REQUEST_PARAM + bookName.replace(" ", "+"));
                return convertsData.getData(json, ResultData.class);
            } catch (BookNotFoundException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    private Book saveNewBook(BookData book, Author author) {

        var bookRegistered = new Book(book.title(), book.downloadCount(), book.languages(), author);

        bookRepository.save(bookRegistered);

        return bookRegistered;
    }

    private Author saveAuthors(List<AuthorData> authorDataList) {

        Author authorRegistered;

        var author = authorDataList.get(0);

        var authorRepo = authorRepository.findByNameIgnoreCase(author.name());

        if (authorRepo.isEmpty()) {
            var newAuthor = new Author(author.name(), author.birthYear(), author.deathYear());
            authorRegistered = authorRepository.save(newAuthor);
        } else {
            authorRegistered = authorRepo.get();
        }
        return authorRegistered;
    }

    private void listBookRegistered() {

        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            System.out.println("No books registered.");
        } else {
            for (Book book : books) {
                ConsoleLog.book(book);
            }
        }
    }

    private void listAuthorRegistered() {

        List<Author> authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            System.out.println("No authors registered.");
        } else {
            List<AuthorDTO> list = authors.stream()
                    .map(a -> new AuthorDTO(a.getName(),
                            a.getBirthYear(), a.getDeathYear(),
                            a.getBooks().stream().map(Book::getTitle).toList())).toList();

            list.forEach(ConsoleLog::author);
        }
    }

    private void listAuthorsAliveInYear() {

        System.out.println("Enter the year:");
        var year = scanner.nextInt();
        scanner.nextLine();

        if (year <= 0) {
            System.out.println("Invalid year.");
            return;
        }

        List<Author> authors = authorRepository.findAuthorsAliveInYear(year);

        if (authors.isEmpty()) {
            System.out.println("No authors found.");
        } else {
            List<AuthorDTO> list = authors.stream()
                    .map(a -> new AuthorDTO(a.getName(),
                            a.getBirthYear(), a.getDeathYear(),
                            a.getBooks().stream().map(Book::getTitle).toList())).toList();

            list.forEach(ConsoleLog::author);
        }
    }

    private void countBookByLanguage() {

        System.out.println("""
                Choose the language:
                es - espanhol
                en - english 
                fr - frances
                pt - portugues
                """);

        var language = scanner.nextLine().trim();

        var count = bookRepository.countByLanguage(language);

        System.out.printf("Number of books in %s: %d%n", language, count);
    }

}
