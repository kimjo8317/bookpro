-- ================ bookinfo 책정보
DROP TABLE IF EXISTS 책정보;
CREATE TABLE 책정보(
    idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(200) NOT NULL ,
    writer varchar(100) NOT NULL,
    genre varchar(100) NOT NULL,
    publisher varchar(100) NOT NULL,
    publication_date DATE NOT NULL,
    poster LONGTEXT NOT NULL,
    summary LONGTEXT NOT NULL
);


-- ================ user 회원정보
DROP TABLE IF EXISTS 회원정보;
CREATE TABLE 회원정보 (
  idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  id INT UNIQUE,
  pw VARCHAR(200) NOT NULL,
  name VARCHAR(50) NOT NULL,
  subscribe TINYINT NOT NULL CHECK (sub IN (0, 1)),
  like_idx INT NOT NULL
);

-- ================ sub 구독
DROP TABLE IF EXISTS 구독;
create table 구독(
    idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    confirm TINYINT NOT NULL CHECK (sub IN (0, 1))
    );
-- ================ bookmark 즐겨찾기
DROP TABLE IF EXISTS 즐겨찾기;
create table 즐겨찾기(
    idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_idx INT NOT NULL,
    book_idx INT NOT NULL
);

-- ================ like 피드 랜덤으로 view에 띄워주기 "좋아요"
DROP TABLE IF EXISTS 좋아요;
create table 좋아요(
    idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_idx INT NOT NULL,
    feed_idx int NOT NULL
);

-- ================ feed
DROP TABLE IF EXISTS 피드;
create table 피드(
    idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    writer varchar(100) NOT NULL,
    title varchar(200) NOT NULL ,
    content LONGTEXT NOT NULL,
    create_date DATE NOT NULL,
    views INT NOT NULL,
    likes INT NOT NULL,
    bookinfo_idx INT NOT NULL
);
-- ================ 리뷰
DROP TABLE IF EXISTS 리뷰;
create table 리뷰(
    idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    feed_idx INT NOT NULL,
    writer_idx INT NOT NULL,
    content LONGTEXT NOT NULL,
    create_date DATE NOT NULL,
    bookinfo_idx INT NOT NULL
    );


