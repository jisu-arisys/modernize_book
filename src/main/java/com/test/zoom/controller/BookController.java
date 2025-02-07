package com.test.zoom.controller;

import com.test.zoom.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static com.test.zoom.data.Db.DB;

@RestController
@RequestMapping() //rewite 로 "/api" path 제거
@CrossOrigin(origins = "http://localhost:5173") // Vue 서버 주소를 허용
public class BookController {

    @GetMapping(value = "/check/login")
	public ResponseEntity<String> checkLogin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
			// 인증된 사용자가 있을 경우
			System.out.println(authentication.getPrincipal());
			return ResponseEntity.ok("login");
		}else {
			System.out.println("checkLogin");
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@GetMapping(value = "/bookList")
	public ResponseEntity<List<Book>> getBookList() {
		System.out.printf("getBookList %s \n", DB.books);
		if(DB.books.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(DB.books);
	}

	@GetMapping(value = "/bookDetail/{bookId}")
	public ResponseEntity<Book> getBookDetail(@PathVariable("bookId") long bookId ) {
		Optional<Book> foundBook = DB.books.stream().filter(b -> b.getBookId() == bookId).findFirst();
        return foundBook.isPresent() ? ResponseEntity.ok(foundBook.get()) : ResponseEntity.notFound().build();
    }

	@GetMapping("/commentList/{bookId}")
	public ResponseEntity<List<Comment>> getBookCommentList(@PathVariable("bookId") long bookId) {
		List<Comment> bookComments = new ArrayList<>();
		for (Comment comment : DB.comments) {
			if (comment.getBookId() == bookId) {
				bookComments.add(comment);
			}
		}

		if(bookComments.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(bookComments);
	}

	@GetMapping("/commentList/mine/{userId}")
	public ResponseEntity<List<Comment>> getMyCommentList(@PathVariable("userId") long userId) {
		List<Comment> myComments = new ArrayList<>();
		for (Comment comment : DB.comments) {
			if (comment.getUser() !=null && comment.getUser().getUserId() == userId) {
				myComments.add(comment);
			}
		}

		if(myComments.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(myComments);
	}

	@GetMapping("/commentList")
	public ResponseEntity<List<Comment>> getCommentList() {
		return ResponseEntity.ok(DB.comments);
	}

	@GetMapping("/requestList")
	public ResponseEntity<List<Comment>> getRequestList() {
		List<Comment> requestList = new ArrayList<>();
        for (Comment comment : DB.comments) {
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
		for (Comment comment : DB.comments) {
			if (comment.getStatus() == Status.연체) {
				arrearsList.add(comment);
			}
		}
		if(arrearsList.isEmpty()){return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(arrearsList);
	}

	@PostMapping("/update/comment")
	public ResponseEntity<List<Comment>> updateCommentDetail(@RequestBody Comment commentData) {
		System.out.println(commentData);
		if(commentData == null){return ResponseEntity.notFound().build();}

		if( commentData.getCommentId() == -1){
			//등록
			commentData.setCommentId( DB.comments.size() + 1L );
			DB.comments.add(0,commentData);

			System.out.println(commentData);
		} else {
			//수정
			for (Comment item : DB.comments) {
				if (commentData.getCommentId().equals(item.getCommentId())) {
					item.setDetail(commentData.getDetail());
					item.setStatus(commentData.getStatus());
					item.setStartLending(commentData.getStartLending());
					item.setEndLending(commentData.getEndLending());
				}
			}
		}
        return ResponseEntity.ok(DB.comments);
	}

	public Date parsingStringToSqlDate(String stringDate){
		if(stringDate.isEmpty()){return null;}
		LocalDate localDate = LocalDate.parse(stringDate);
		return Date.valueOf(localDate);
	}
}