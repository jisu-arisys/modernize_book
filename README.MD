# 도서예약페이지

Vuetify (Vue.js를 위한 컴포넌트 라이브러리이며, Material Design Framework다. ) 의 하나인
modernize template 에 익숙해지기 위해
Toy Project 로 사내 도서예약을 위한 웹페이지를 구현한다.


### AUTH
1. USER
2. ADMIN
3. LOCK
* DB에 테이블생성 없이, Enum 객체를 이용해 Users 에 String 으로 저장
* 
### STATUS
1. 예약 : 댓글 신규작성으로 대출신청
2. 대출 : 관리자가 승인하고 도서 제공
3. 연체 : 대여종료일이 지남
4. 반납 : 관리자가 도서 수령 후 반납처리
* DB에 테이블생성 없이, Enum 객체를 이용해 Comments 에 Status 으로 저장

### DB
1. Books : 코드, 이미지, 제목, 저자, 발행연도, 출판사, 누적 대여횟수, 보유여부
2. Users : 코드, 권한, 이름, 비밀번호, 부서, 대출여부, 대여시작일, 대여종료일, 연체일, 누적연체일
3. Comments : 코드, 코드 user, 댓글내용, 상태, 작성일, 수정일, 삭제여부
4. locks : 코드, 코드 user, 차단 시작일, 차단 종료일, 완료여부