--spring 서버 실행시 생성될 DB의 데이터 초기화
-- 테이블 자동생성 옵션 : spring.jpa.hibernate.ddl-auto=create-drop, spring.jpa.defer-datasource-initialization=true

insert into users (auth_name, user_name, password, dept, count_overdue, total_overdue, activated)
values ('admin','one', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'account',  1,3, false),
       ('user', 'two','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'rnd',  0, 15, true),
       ('lock', 'three','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'sails', 0, 1, false);
--
insert into books (img_link, book_name, author, publisher, total_lending, activated)
values ( '/assets/images/products/4.jpg', '독서의 이해', '임묘한', '묘한출판사', 5, true),
        ( '/assets/images/products/3.jpg', '독서의 탐구', '임묘한', '묘한출판사', 0, true);
--
insert into comments (user_name, book_id, detail, start_lending, end_lending, status, create_date, update_date, activated)
values ( 'two', 1, '언제 대여가능한가요', null ,null,'예약', '2025-01-10', '2025-01-11', true),
        ( 'one', 1, '대여신청합니다', '2025-01-05' ,'2025-01-10' , '연체','2025-01-05', '2025-01-05',  true),
        ( 'one', 1, '대여신청합니다', '2025-01-01' ,'2025-01-05' ,'반납', '2025-01-01', '2025-01-05', true);