package com.test.zoom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name="comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가되는 PK
    private Long commentId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    @JsonProperty("bookId")
    public long getBookId() {
        return book != null ? book.getBookId() : 1;
    }

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private User user;

    @JsonProperty("userName")  // userName을 JSON으로 반환하도록 설정
    public String getUserName() {
        return user != null ? user.getUserName() : null;  // user가 null이 아닐 경우 userName 반환
    }

    @Column(name = "detail", length = 1000, unique = true)
    private String detail;

    @Enumerated(EnumType.STRING) // ENUM 값을 문자열로 저장
    private Status status; // 댓글 상태

    @Column(name = "create_date")
    private Date createDate;  // 작성일
    @Column(name = "update_date")
    private Date updateDate;  // 수정일
    private boolean activated; // 삭제여부

}
