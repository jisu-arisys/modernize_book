package com.test.zoom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="books")
@Getter
@Setter
@ToString
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가되는 PK
    private long bookId;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "book_name", length = 50, unique = true)
    private String bookName;

    @Column(name = "author", length = 100)
    private String author;

    @Column(name = "publisher", length = 50)
    private String publisher;

    @Column(name = "total_lending")
    private long totalLending; // 누적대출횟수

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated; // 보유여부
}
