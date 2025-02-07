# 도서예약페이지

Vuetify (Vue.js를 위한 컴포넌트 라이브러리이며, Material Design Framework다. ) 의 하나인
modernize template 에 익숙해지기 위해
Toy Project 로 사내 도서예약을 위한 웹페이지를 구현한다.

### AUTH
1. USER : 댓글 생성, 수정 가능
2. ADMIN : 댓글 상태변경, 생성, 수정 가능
3. LOCK : 댓글 생성 불가
* DB에 테이블생성 없이, Enum 객체를 이용해 Users 에 String 으로 저장

### STATUS
1. 예약 : 댓글 신규작성으로 대출신청 -> 대출
2. 대출 : 관리자가 승인하고 도서 제공 -> 반납
3. 연체 : 대여종료일이 지남 -> 반납
4. 반납 : 관리자가 도서 수령 후 반납처리
* DB에 테이블생성 없이, Enum 객체를 이용해 Comments 에 Status 으로 저장
* 대출 당일이 대출시작일, 7일 뒤가 대출종료일.
* 반납 당일이 대출종료일.
* 연체 카운트는 스케쥴러를 통해 유저데이터에 저장.

### DB
1. Books : 코드, 이미지, 제목, 저자, 발행연도, 출판사, 누적 대여횟수, 보유여부
2. Users : 코드, 권한, 이름, 비밀번호, 부서, 대출여부, 대여시작일, 대여종료일, 연체일, 누적연체일
3. Comments : 코드, 코드 user, 댓글내용, 상태, 작성일, 수정일, 삭제여부
4. locks : 코드, 코드 user, 차단 시작일, 차단 종료일, 완료여부 - 미구현

### 인증 구조
* 싱글톤 객체 Db에 데이터 저장. (DB 연결 대신)
* 'InMemoryUserDetailsManager' 에 사용자정보 등록함.
* 'cors' 에 Vue 서버 등록, 'csrf' 기능 제거.
* A. spring security 6 의 'formLogin(/login)' 을 사용해 인증 구현.
  * 인증 성공 후 리다이렉트 없이 JSON 데이터를 반환하도록 핸들러 구현. ( 'onAuthenticationSuccess' )
* B. 'formLogin' 제거시 '/auth/login' API 요청을 사용해 인증 구현. ( 'SecurityController' , 'AuthenticationProvider')
