package com.test.zoom.controller;

import com.test.zoom.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static com.test.zoom.controller.BookController.Datas.*;

@RestController
@RequestMapping() //rewite 로 "/api" path 제거
@CrossOrigin(origins = "http://localhost:5173") // Vue 서버 주소를 허용
public class BookController {

	public static class Datas{
		static LocalDate date0 = LocalDate.of(2025, 3, 5);
		static LocalDate date = LocalDate.of(2025, 3, 3);
		static LocalDate date2 = LocalDate.of(2025, 3, 1);
		static Book book = new Book(1L, "/assets/images/products/4.jpg", "독서의 이해", "임묘한", "묘한출판사", 6L, true);
		static Book book2 = new Book(2L, "/assets/images/products/3.jpg", "독서의 탐구", "임묘한", "묘한출판사", 0L, true);
		static User me = new User();
		static User user = new User();
		static List<Book> books = Arrays.asList( book, book2);
		static List<Comment> comments = Arrays.asList(
			 new Comment( 1L, book, me, "대여신청합니다", Status.예약,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 2L, book2, me, "대여신청합니다", Status.예약,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 3L, book, me, "대여신청합니다", Status.대출,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 4L, book, me, "대여신청합니다", Status.연체,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 5L, book2, me, "대여신청합니다", Status.연체,Date.valueOf(date), Date.valueOf(date0), true),
			 new Comment( 6L, book, user, "대여신청합니다", Status.반납,Date.valueOf(date2), Date.valueOf(date), true)
		);
	}


	@GetMapping(value = "/bookList")
	public ResponseEntity<List<Book>> getBookList() {
		System.out.printf("getBookList %s \n", books);
		if(books.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(books);
	}

	@GetMapping(value = "/bookDetail/{bookId}")
	public ResponseEntity<Map<String, Object>> getBookDetail(@PathVariable("bookId") long bookId ) {
		Map<String, Object> map = new HashMap<>();
		switch ((int) bookId){
			case 1: map.put("book", book); break;
			case 2: map.put("book", book2); break;
		}

		if(map.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/commentList/{bookId}")
	public ResponseEntity<List<Comment>> getBookCommentList(@PathVariable("bookId") long bookId) {
		List<Comment> bookComments = new ArrayList<>();
		for (Comment comment : comments) {
			if (comment.getBookId() == bookId) {
				bookComments.add(comment);
			}
		}

		if(bookComments.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(bookComments);
	}


	@GetMapping("/commentList")
	public ResponseEntity<List<Comment>> getCommentList() {
		return ResponseEntity.ok(comments);
	}

	@GetMapping("/requestList")
	public ResponseEntity<List<Comment>> getRequestList() {
		List<Comment> requestList = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getStatus() == Status.예약) {
				requestList.add(comment);
            }
        }
		if(requestList.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(requestList);
	}

	@GetMapping("/arrearsList")
	public ResponseEntity<List<Comment>> getArrearsList() {
		List<Comment> arrearsList = new ArrayList<>();
		for (Comment comment : comments) {
			if (comment.getStatus() == Status.연체) {
				arrearsList.add(comment);
			}
		}
		if(arrearsList.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(arrearsList);
	}

	/** RequestBody 로 JSON 객체를 전달하여 처리. **/
	@PostMapping("/auth/login")
	public User login(@RequestBody Map<String, String> loginData) {
		System.out.println(loginData);
		me.setUserName(loginData.get("username"));
		me.setPassword(loginData.get("password"));
		me.setAuthName(Auth.USER);
		Datas.user.setUserName("user");
		return me;
	}

	@PostMapping("/update/comment")
	public ResponseEntity<List<Comment>> updateCommentDetail(@RequestBody Map<String, String> commentData) {
		System.out.println(commentData);

		//수정
        for (Comment item : comments) {
            if (Objects.equals(item.getCommentId(), Long.valueOf(commentData.get("commentId")))) {
                item.setDetail(commentData.get("detail"));
                item.setStatus(Status.valueOf(commentData.get("status")));
            }
        }
        return ResponseEntity.ok(comments);
	}
}