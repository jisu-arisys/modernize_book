package com.test.zoom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @JsonIgnore
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가되는 PK
    private Long userId;

    @Column(name = "auth_name")
    @Enumerated(EnumType.STRING) // ENUM 값을 문자열로 저장
    private Auth authName; // USER, ADMIN, LOCK 중 하나

    @Column(name = "user_name", length = 50, unique = true, nullable = false) // comments 조회를 위해 반드시 unique, notnull 이어야함
    private String userName;

    @JsonIgnore
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "dept", length = 50)
    private String dept;

    @Column(name = "count_overdue")
    private Long countOverdue;  // 연체일
    @Column(name = "total_overdue")
    private Long totalOverdue; // 누적연체일

    @JsonIgnore
    private boolean activated; //대출여부
}
