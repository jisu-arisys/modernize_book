package com.test.zoom.data;

import com.test.zoom.entity.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Db {

    public static final Db DB = new Db();

    public static Db getInstance() {
        return DB;
    }


    static LocalDate date0 = LocalDate.of(2025, 2, 5);
    static LocalDate date = LocalDate.of(2025, 2, 3);
    static LocalDate date2 = LocalDate.of(2025, 2, 1);
    public static User me = new User(1l, Auth.ADMIN, "jisu.um", "pw", "dept", 0l, 3l, true);
    public static User user = new User(2l,Auth.USER, "user", "pw", "dept", 0l, 3l, true);
    public static List<Book> books = new ArrayList<>(Arrays.asList(
            new Book(1L, "/assets/images/products/4.jpg", "독서의 이해", "임묘한", "묘한출판사", 6L, true),
            new Book(2L, "/assets/images/products/3.jpg", "독서의 탐구", "임묘한", "묘한출판사", 0L, true),
            new Book(3L, "/assets/images/products/2.jpg", "같이가자", "강감찬", "묘한출판사", 2L, true),
            new Book(4L, "/assets/images/products/1.jpg", "호떡맛집", "시장이모님", "연서시장", 3L, true),
            new Book(5L, "/assets/images/products/5.jpg", "오늘은성공", "녹차", "연서시장", 8L, true)
    ));
    public static List<Comment> comments = new ArrayList<>(Arrays.asList(
            new Comment( 1L, books.get(0), me, "대여신청합니다", null, null, Status.예약, Date.valueOf(date), Date.valueOf(date0), true),
            new Comment( 2L, books.get(1), me, "대여신청합니다", null, null, Status.예약,Date.valueOf(date), Date.valueOf(date0), true),
            new Comment( 3L, books.get(2), me, "대여신청합니다", Date.valueOf(date), Date.valueOf(date0), Status.대출,Date.valueOf(date), Date.valueOf(date0), true),
            new Comment( 4L, books.get(3), me, "대여신청합니다", Date.valueOf(date2), Date.valueOf(date), Status.연체,Date.valueOf(date2), Date.valueOf(date0), true),
            new Comment( 5L, books.get(4), me, "대여신청합니다", Date.valueOf(date2), Date.valueOf(date), Status.연체,Date.valueOf(date2), Date.valueOf(date0), true),
            new Comment( 6L, books.get(0), user, "대여신청합니다", Date.valueOf(date2), Date.valueOf(date), Status.반납,Date.valueOf(date2), Date.valueOf(date), true)
    ));

    public User userNameToUser(String userName) {
        switch (userName) {
            case "jisu.um": return me;
            case "user": return user;
            default: return null;
        }
    }
}
