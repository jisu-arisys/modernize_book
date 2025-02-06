package com.test.zoom.entity;

import com.fasterxml.jackson.annotation.*;
import com.test.zoom.data.Db;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @JsonSetter("bookId")
    public void setBook(long bookId) {
        if (book == null && bookId > 0) {
            book = new Book();
            book.setBookId(bookId);
        }
    }

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private User user;

    @JsonProperty("userName")  // userName을 JSON으로 반환하도록 설정
    public String getUserName() {
        return user != null ? user.getUserName() : null;  // user가 null이 아닐 경우 userName 반환
    }

    @JsonSetter("userName")
    public void setUser(String userName) {
        if (user == null && userName.length() > 0) {
            user = new Db().userNameToUser(userName);
            user.setUserName(userName);
        }
    }

    @Column(name = "detail", length = 1000)
    private String detail;

    @Column(name = "start_lending")
    private Date startLending; // 대출시작일
    @Column(name = "end_lending")
    private Date endLending;   // 대출종료일

    @Enumerated(EnumType.STRING) // ENUM 값을 문자열로 저장
    private Status status; // 댓글 상태

    @CreatedDate
    @Column(name = "create_date", updatable = false, nullable = false)
    private Date createDate;  // 작성일

    @LastModifiedDate
    @Column(name = "update_date", nullable = false)
    private Date updateDate;  // 수정일

    private boolean activated; // 삭제여부
}
