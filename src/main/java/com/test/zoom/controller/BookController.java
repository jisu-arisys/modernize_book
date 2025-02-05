package com.test.zoom.controller;

import com.test.zoom.entity.*;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.zoom.controller.BookController.Datas.*;

@RestController
@RequestMapping() //rewite 로 "/api" path 제거
@CrossOrigin(origins = "http://localhost:5173") // Vue 서버 주소를 허용
public class BookController {

	public static class Datas{
		static LocalDate date0 = LocalDate.of(2025, 3, 5);
		static LocalDate date = LocalDate.of(2025, 3, 3);
		static LocalDate date2 = LocalDate.of(2025, 3, 1);
		static Book book = new Book(1l, "/assets/images/products/4.jpg", "독서의 이해", "임묘한", "묘한출판사", 5l, true);
		static Book book2 = new Book(2l, "/assets/images/products/3.jpg", "독서의 탐구", "임묘한", "묘한출판사", 0l, true);
		static User me = new User();
		static User user = new User();
		static List<Book> books = Arrays.asList( book, book2);
		static List<Comment> comments = Arrays.asList(
			 new Comment( 1L, book, me, "대여신청합니다", Status.예약,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 2L, book, me, "대여신청합니다", Status.연체,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 3L, book, me, "대여신청합니다", Status.대출,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 4L, book, me, "대여신청합니다", Status.반납,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 5L, book, me, "대여신청합니다", Status.반납,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 6L, book, user, "대여신청합니다", Status.반납,Date.valueOf(date2), Date.valueOf(date), true)
			 );

	}


	@GetMapping(value = "/bookList")
	public ResponseEntity<List<Book>> getBookList() {
		System.out.printf("getBookList %s \n", books);
		return ResponseEntity.ok(books);
	}

	@GetMapping(value = "/bookDetail/{bookId}")
	public ResponseEntity<Map<String, Object>> getBookDetail(@PathVariable("bookId") long bookId ) {
		Map<String, Object> map = new HashMap<>();
		switch ((int) bookId){
			case 1: map.put("book", book); break;
			case 2: map.put("book", book2); break;
		}
		map.put("comments", Datas.comments);

		return ResponseEntity.ok(map);
	}

	@GetMapping("/commentList")
	public ResponseEntity<List<Comment>> getCommentList() {
		return ResponseEntity.ok(comments);
	}

	/** @RequestBody로 JSON 객체를 전달하여 처리. **/
	@PostMapping("/auth/login")
	public User login(@RequestBody Map<String, String> loginData) {
		System.out.println(loginData);
		me.setUserName(loginData.get("username"));
		me.setPassword(loginData.get("password"));
		me.setAuthName(Auth.USER);

		return me;
	}
}