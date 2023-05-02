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
ALTER TABLE article CONVERT TO CHARSET UTF8;
ALTER TABLE `member` CONVERT TO CHARSET UTF8;

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
ALTER TABLE board CONVERT TO CHARSET UTF8;

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

ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;

# reactionPoint 테이블 생성
CREATE TABLE reactionPoint(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) NOT NULL COMMENT '관련 데이터 번호',
    `point` INT(10) NOT NULL
);
ALTER TABLE reactionPoint CONVERT TO CHARSET UTF8;

# reactionPoint 테스트 데이터
# 1번 회원이 1번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 1번 회원이 2번 글에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

# 2번 회원이 1번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 2번 회원이 2번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = -1;

# 3번 회원이 1번 글에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;


# 게시물 테이블에 추천 관련 컬럼 추가
ALTER TABLE article ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE article ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 기존 게시물의 good,bad ReactionPoint 필드의 값을 채운다
UPDATE article AS A
INNER JOIN (
    SELECT RP.relTypeCode, RP.relId,
    SUM(IF(RP.point > 0, RP.point,0)) AS goodReactionPoint,
    SUM(IF(RP.point < 0, RP.point * -1,0)) AS badReactionPoint
    FROM reactionPoint AS RP
    GROUP BY RP.relTypeCode, RP.relId
) AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
A.badReactionPoint = RP_SUM.badReactionPoint;


# reply 테이블 생성
CREATE TABLE reply(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) NOT NULL COMMENT '관련 데이터 번호',
    `body` TEXT NOT NULL
);

# 2번 회원이 1번 글에 댓글
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`body` = '댓글 1';

# 2번 회원이 1번 글에 댓글
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`body` = "댓글 2";

# 3번 회원이 1번 글에 댓글
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`body` = "댓글 3";

# 3번 회원이 2번 글에 댓글
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 2,
`body` = "댓글 4";

###########################################################################

SELECT * FROM article;
SELECT * FROM `member`;
SELECT * FROM board;
SELECT * FROM reactionPoint;
SELECT * FROM reply;

SELECT *
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

SELECT IF(RP.point > 0, '큼','작음')
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

# 각 게시물 별 좋아요, 싫어요의 갯수
SELECT RP.relTypeCode, RP.relId,
SUM(IF(RP.point > 0, RP.point,0)) AS goodReactionPoint,
SUM(IF(RP.point < 0, RP.point * -1,0)) AS badReactionPoint
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

SELECT IFNULL(SUM(RP.point),0)
FROM reactionPoint AS RP
WHERE RP.relTypeCode = 'article'
AND RP.relId = 3
AND RP.memberId = 2


# 게시물 갯수 늘리기
INSERT INTO article 
( 
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 2, FLOOR(RAND() * 2) + 1, CONCAT('제목_',RAND()), CONCAT('내용_',RAND())
FROM article;

SELECT COUNT(*) FROM article;

DESC article;

SELECT LAST_INSERT_ID();

SELECT  CONCAT('%' 'abc' '%');

# left join
SELECT A.*, M.nickname, RP.point
FROM article AS A
INNER JOIN `member` AS M 
ON A.memberId = M.id
LEFT JOIN reactionPoint AS RP 
ON A.id = RP.relId AND RP.relTypeCode = 'article'
GROUP BY A.id
ORDER BY A.id DESC;

# 게시판별 제목으로 검색하는 쿼리
SELECT a.*, m.name AS 'extra__wrtier', b.name AS 'board_name'
FROM article a
INNER JOIN board b
ON a.boardId = b.id
INNER JOIN `member` m
ON a.memberId = m.id
WHERE b.id = 1 AND a.title LIKE "%녕%"
ORDER BY a.id DESC
LIMIT 1, 10;

# 서브쿼리 버전
SELECT A.*, 
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
IFNULL(SUM(IF(RP.point > 0, RP.point, 0)),0) AS extra__goodReactionPoint,
IFNULL(SUM(IF(RP.point < 0, RP.point, 0)),0) AS extra__badReactionPoint
FROM (
	SELECT A.*, M.nickname AS extra__writerName
	FROM article AS A
	LEFT JOIN `member` AS M
	ON A.memberId= M.id 
			) AS A
LEFT JOIN reactionPoint AS RP
ON RP.relTypeCode = 'article'
AND A.id = RP.relId
GROUP BY A.id
ORDER BY id DESC;


# join 버전
SELECT A.*, M.name AS 'extra__wrtier',
IFNULL(SUM(R.`point`), 0) AS 'extra__sumReactionPoint',
IFNULL(SUM(IF(R.`point` > 0, R.`point`, 0)),0) AS 'extra__goodReactionPoint',
IFNULL(SUM(IF(R.`point` < 0, R.`point`, 0)),0) AS 'extra__badReactionPoint'
FROM article A
INNER JOIN `member` M
ON A.memberId = M.id
LEFT JOIN reactionPoint AS R
ON A.id = R.relId AND R.relTypeCode = 'article'
GROUP BY A.id
ORDER BY A.id DESC


######################################################################
# 연습용 코드

SELECT a.id,
IFNULL(SUM(R.`point`), 0) AS 'extra__sumReactionPoint',
IFNULL(SUM(IF(R.`point` > 0, R.`point`, 0)),0) AS 'extra__goodReactionPoint',
IFNULL(SUM(IF(R.`point` < 0, R.`point`, 0)),0) AS 'extra__badReactionPoint'
FROM article a
INNER JOIN reactionPoint R
ON a.id = R.relId AND R.relTypeCode = 'article'
WHERE R.relId = 2 AND a.memberId = 2
GROUP BY a.id;


SELECT 
IFNULL(SUM(R.`point`), 0) AS 'extra__sumReactionPoint',
IFNULL(SUM(IF(R.`point` > 0, R.`point`, 0)),0) AS 'extra__goodReactionPoint',
IFNULL(SUM(IF(R.`point` < 0, R.`point`, 0)),0) AS 'extra__badReactionPoint'
FROM reactionPoint r
WHERE memberId = 2 AND relTypeCode = 'article' AND relId = 2
AND memberId IS NOT NULL AND relId IS NOT NULL 
GROUP BY relId;

SELECT IFNULL(SUM(R.`point`), 0) AS 'extra__sumReactionPoint',
IFNULL(SUM(IF(R.`point` > 0, R.`point`, 0)),0) AS 'extra__goodReactionPoint',
IFNULL(SUM(IF(R.`point` < 0, R.`point`, 0)),0) AS 'extra__badReactionPoint'
FROM reactionPoint AS R
WHERE memberId = 1 AND relTypeCode = 'article' AND relId = 1


SELECT IFNULL(SUM(R.`point`), 0) AS 'sumReactionPoint',
IFNULL(SUM(IF(R.`point` > 0, R.`point`, 0)),0) AS 'goodReactionPoint',
IFNULL(SUM(IF(R.`point` < 0, R.`point`, 0)),0) AS 'badReactionPoint'
FROM reactionPoint AS R
WHERE memberId = 3 AND relTypeCode = 'article' AND relId = 2

SELECT IFNULL(SUM(RP.point),0)
FROM reactionPoint AS RP
WHERE RP.relTypeCode = 'article'
AND RP.relId = 2
AND RP.memberId = 3

INSERT INTO reactionPoint
		SET regDate = NOW(),
		updateDate = NOW(),
		memberId = #{actorId},
		relTypeCode = #{relTypeCode},
		relId = #{relId},
		`point` = 1;

SELECT * FROM reactionPoint;
SELECT * FROM article;
DELETE FROM reactionPoint
WHERE memberId = 2 AND relTypeCode = 'article' AND relId = 3;

SELECT r.*, m.name
FROM reply r
INNER JOIN `member` m
ON r.memberId = m.id
WHERE r.relId = 1;






SELECT a.id,
IFNULL(SUM(R.`point`), 0) AS 'extra__sumReactionPoint',
IFNULL(SUM(IF(R.`point` > 0, R.`point`, 0)),0) AS 'extra__goodReactionPoint',
IFNULL(SUM(IF(R.`point` < 0, R.`point`, 0)),0) AS 'extra__badReactionPoint'
FROM article a
INNER JOIN reactionPoint R
ON a.id = R.relId AND R.relTypeCode = 'article'
WHERE R.relId = 2 AND a.memberId = 2
GROUP BY a.id;


SELECT a.*, COUNT(re.id)
FROM article a
INNER JOIN reply re
ON a.id = re.relId
GROUP BY a.id;
