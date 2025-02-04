--spring 서버 실행시 생성될 DB의 데이터 초기화
-- 테이블 자동생성 옵션 : spring.jpa.hibernate.ddl-auto=create-drop, spring.jpa.defer-datasource-initialization=true

insert into users (auth_name, user_name, password, dept, start_lending, end_lending, count_overdue, total_overdue, activated)
values ('admin','one', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'account',  null ,null ,1,3, false),
       ('user', 'two','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'rnd', '2025-01-10', '2025-01-23', 0, 15, true),
       ('lock', 'three','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'sails',  null , null , 0, 1, false);

insert into books (img_link, book_name, author, publisher, total_lending, activated)
values ( '/assets/images/products/4.jpg', '독서의 이해', '임묘한', '묘한출판사', 5, true);

insert into comments (user_name, book_id, detail, status, create_date, update_date, activated)
values ( 'two', 1, '대여신청합니다', '예약', '2025-02-03', '2025-02-03', true);