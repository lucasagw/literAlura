package br.com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "download_count", nullable = false)
    private int downloadCount;

    public Book() {
    }


    public Book(String title, int downloadCount, List<String> languages, Author author) {
        this.title = title;
        this.downloadCount = downloadCount;
        this.language = languages.isEmpty() ? "uninformed language" : languages.get(0);
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }


}
