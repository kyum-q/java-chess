# java-chess

체스 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## 기능 구현 목록

### 입력

> 게임 시작 : start
> 게임 종료 : end
> 게임 이동 : move source위치 target위치 - 예. move b2 b3
- [x] start 입력 시 게임을 시작한다.
  - [x] start 입력 후 다시 start를 입력하는 경우 예외를 발생시킨다.
- [x] end 입력 시 게임을 종료한다.
- [x] move 입력 시 source위치에 있는 piece가 target위치로 움직인다.
- [x] status 입력 시 각 진영의 점수 및 이긴 팀을 출력한다.

### 출력
- [x] 판을 출력한다.
    - [x] 가로는 왼쪽부터 a-h, 세로는 아래부터 1-8 이다.
    - [x] 검은 말은 대문자로, 흰 말은 소문자로 출력한다.

### chess Game
- [x] 같은 팀 기물을 연속 두번 움직일 수 없다.
  - [x] 시작 팀은 흰색이다.
- [x] 체크 상태를 알아내, 왕이 체크 상태에서 벗어나지 않았으면 게임을 종료시킨다.
  - [x] pin
  - [x] check 상황에서, check를 벗어나지 않는 행동

### board
- [x] piece를 움직일 수 있다.
  - [x] source 위치에 piece가 없는 경우 예외를 발생시킨다.
  - [x] 경로에 아군 피스가 있는 경우, 그 이상으로 이동할 수 없다.
  - [x] 경로에 적군 피스가 있는 경우, 그 피스를 공격할 수 있다.
- [x] 게임의 체크 상태를 알 수 있다.
  - [x] 체크
    - [x] 왕이 공격받고 있다.
  - [x] 체크메이트
    - [x] 왕이 체크 상태여야 한다.
    - [x] 왕이 어디로 움직여도 체크 상태여야 한다.
    - [x] 아군 기물을 이동하여 체크 경로를 가로막을 수 없어야 한다.
    - [x] 아군 기물을 이용하여 체크를 건 기물을 잡을 수 없어야 한다.
- [x] 팀 별로 점수를 계산할 수 있다.
  - [x] 점수는 현재 남아 있는 말에 대한 점수의 합이다.

### piece
- [x] 팀을 가진다.
- [x] 이동 여부를 가진다.
- [x] 자신의 타입을 반환할 수 있다.
- [x] 움직일 수 있는 위치인 지 검증할 수 있다.
- [x] 작은 점수를 반환한다.
- [x] 큰 점수를 반환한다.

### position
- [x] 이차원 위치 값을 가지고 있는다.
  - [x] 1-8 사이의 값이어야한다.

### team(enum)
- [x] BLACK, WHITE

### kind(enum)
- [x] 체스 기물 별로 점수(최고 점수, 최저 점수)가 있다.
  - [x] 최고 점수
    - queen은 9점, rook은 5점, bishop은 3점, knight는 2.5점, Pawn은 1점, King은 0점
  - [x] 최저 점수
    - queen은 9점, rook은 5점, bishop은 3점, knight는 2.5점, Pawn은 0.5점, King은 0점

### pawn
- [x] piece를 상속한다.
- [x] 움직일 수 있는 위치인 지 검증할 수 있다.
  - [x] 앞으로 한 칸 이동할 수 있다.
  - [x] white는 2열일 시, black은 7열일 시 2칸 이동 가능하다.
  - [x] 상대 piece를 공격할 시, 대각선으로만 공격 가능하다. 

### knight
- [x] piece를 상속한다.
- [x] 움직일 수 있는 위치인 지 검증할 수 있다.
  - [x] 직선 방향으로 한칸, 대각선 방향으로 한칸 움직일 수 있다(날 일)
  - [x] piece를 넘어갈 수 있다.

### bishop
- [x] piece를 상속한다.
- [x] 움직일 수 있는 위치인 지 검증할 수 있다.
  - [x] 대각선 전 범위로 이동할 수 있다.

### rook
- [x] piece를 상속한다.
- [x] 움직일 수 있는 위치인 지 검증할 수 있다.
  - [x] 직선 전 범위로 이동할 수 있다.

### queen
- [x] piece를 상속한다.
- [x] 움직일 수 있는 위치인 지 검증할 수 있다.
  - [x] 직선 및 대각선 전 범위로 이동할 수 있다.

### king
- [x] piece를 상속한다.
- [x] 움직일 수 있는 위치인 지 검증할 수 있다.
  - [x] 전 방향 한 칸씩 이동할 수 있다.
---

### DB table

```mysql
CREATE TABLE IF NOT EXISTS chess_game
(
  `game_id` varchar(255),
  `turn`    enum('WHITE', 'BLACK') DEFAULT 'WHITE',
  PRIMARY KEY (game_id)
);

CREATE TABLE IF NOT EXISTS piece
(
  `piece_id` char(3),
  `kind`     varchar(50) not null,
  `team`     enum('WHITE', 'BLACK') not null,
  `is_moved` boolean     not null,
  PRIMARY KEY (piece_id),
  UNIQUE (kind, team, is_moved)
);

CREATE TABLE IF NOT EXISTS board
(
  `board_id` bigint auto_increment, 
  `position` char(2),
  `game_id`  varchar(255) not null,
  `piece_id` char(3) not null,
  PRIMARY KEY (board_id),
  UNIQUE (position, game_id),
  FOREIGN KEY (game_id) REFERENCES chess_game (game_id) ON UPDATE CASCADE,
  FOREIGN KEY (piece_id) REFERENCES piece (piece_id) ON UPDATE CASCADE
);

INSERT INTO `piece` VALUES ('000', 'PAWN', 'BLACK', '0');
INSERT INTO `piece` VALUES ('001', 'PAWN', 'BLACK', '1');
INSERT INTO `piece` VALUES ('010', 'PAWN', 'WHITE', '0');
INSERT INTO `piece` VALUES ('011', 'PAWN', 'WHITE', '1');

INSERT INTO `piece` VALUES ('100', 'KNIGHT', 'BLACK', '0');
INSERT INTO `piece` VALUES ('101', 'KNIGHT', 'BLACK', '1');
INSERT INTO `piece` VALUES ('110', 'KNIGHT', 'WHITE', '0');
INSERT INTO `piece` VALUES ('111', 'KNIGHT', 'WHITE', '1');

INSERT INTO `piece` VALUES ('200', 'BISHOP', 'BLACK', '0');
INSERT INTO `piece` VALUES ('201', 'BISHOP', 'BLACK', '1');
INSERT INTO `piece` VALUES ('210', 'BISHOP', 'WHITE', '0');
INSERT INTO `piece` VALUES ('211', 'BISHOP', 'WHITE', '1');

INSERT INTO `piece` VALUES ('300', 'ROOK', 'BLACK', '0');
INSERT INTO `piece` VALUES ('301', 'ROOK', 'BLACK', '1');
INSERT INTO `piece` VALUES ('310', 'ROOK', 'WHITE', '0');
INSERT INTO `piece` VALUES ('311', 'ROOK', 'WHITE', '1');

INSERT INTO `piece` VALUES ('400', 'QUEEN', 'BLACK', '0');
INSERT INTO `piece` VALUES ('401', 'QUEEN', 'BLACK', '1');
INSERT INTO `piece` VALUES ('410', 'QUEEN', 'WHITE', '0');
INSERT INTO `piece` VALUES ('411', 'QUEEN', 'WHITE', '1');

INSERT INTO `piece` VALUES ('500', 'KING', 'BLACK', '0');
INSERT INTO `piece` VALUES ('501', 'KING', 'BLACK', '1');
INSERT INTO `piece` VALUES ('510', 'KING', 'WHITE', '0');
INSERT INTO `piece` VALUES ('511', 'KING', 'WHITE', '1');
```

---

### 출력 예시

```
> 체스 게임을 시작합니다.
> 게임 시작 : start
> 게임 종료 : end
> 게임 이동 : move source위치 target위치 - 예. move b2 b3
start
RNBQKBNR
PPPPPPPP
........
........
........
........
pppppppp
rnbqkbnr

move b2 b3
RNBQKBNR
PPPPPPPP
........
........
........
.p......
p.pppppp
rnbqkbnr

```
