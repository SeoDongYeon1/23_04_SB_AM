#DB 생성
DROP DATABASE IF EXISTS SB_AM_04;
CREATE DATABASE SB_AM_04;
USE SB_AM_04;

# 게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 회원 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(100) NOT NULL,
    loginPw CHAR(100) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한 레벨(3=일반, 7=관리자)',
    `name` CHAR(100) NOT NULL,
    nickname CHAR(100) NOT NULL,
    cellphoneNum CHAR(100) NOT NULL,
    email CHAR(100) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴 여부 (0=탈퇴 전, 1= 탈퇴 후)',
    delDate DATETIME COMMENT '탈퇴 날짜'
);

# 게시물 작성시 작성자 정보 남기려고 article 테이블에 memberId 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

# 게시물 테스트데이터 생성
INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 1',
`body` = '내용 1',
memberId = 1;

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 2',
`body` = '내용 2',
memberId = 2;

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 3',
`body` = '내용 3',
memberId = 3;


# 회원 테스트데이터 생성
INSERT INTO `member` 
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNum = '01012341234',
email = 'abcdef@gmail.com';

INSERT INTO `member` 
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '회원1',
nickname = '회원1',
cellphoneNum = '01043214321',
email = 'asd@gmail.com';

INSERT INTO `member` 
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '회원2',
nickname = '회원2',
cellphoneNum = '01067896789',
email = 'zxc@gmail.com';

# 게시판 테이블 생성
CREATE TABLE board(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(100) NOT NULL UNIQUE COMMENT 'Notice(공지사항), Free(자유게시판), QnA(질의응답), ...',
    `name` CHAR(100) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제 여부 (0=삭제 전, 1= 삭제 후)',
    delDate DATETIME COMMENT '삭제 날짜'
);

INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'Notice',
`name` = '공지사항';

INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'Free',
`name` = '자유게시판';

INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'QnA',
`name` = '질의응답';

ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

UPDATE article
SET boardId = 1
WHERE id IN(1,2);

UPDATE article
SET boardId = 2
WHERE id = 3;

####################################################################

# 게시물 갯수 늘리기
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 2, FLOOR(RAND() * 2), CONCAT('제목_',RAND()), CONCAT('내용_',RAND())
FROM article;

SELECT COUNT(*) FROM article; 

# 검색용
DESC article;

SELECT * FROM article;
SELECT * FROM `member`;
SELECT * FROM board;

SELECT LAST_INSERT_ID();

SELECT a.*, m.name AS 'extra__wrtier'
FROM article a
INNER JOIN `member` m
ON a.memberId = m.id
ORDER BY a.id DESC;

SELECT a.*, m.name AS 'extra__wrtier', b.name AS 'board_name'
FROM article a
INNER JOIN board b
ON a.boardId = b.id
INNER JOIN `member` m
ON a.memberId = m.id
ORDER BY a.id DESC;

