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
  - [ ] 캐슬링
  - 왕과 타겟 룩이 한번도 이동하지 않았으며, 
  - king부터 rook까지 piece가 존재하지 않고 공격받고 있지 않은 경우, 
  - king을 rook 방향으로 두 칸 움직이고 rook을 king 반대 방향 바로 옆으로 이동할 수 있다.

---

### DB table

```mysql
CREATE TABLE chess_game
(
  `game_id` bigint auto_increment,
  `turn`    varchar(50) not null,
  PRIMARY KEY (game_id)
);

      CREATE TABLE piece
      (
        `piece_id` char(3),
        `kind`     varchar(50) not null,
        `team`     varchar(50) not null,
        `is_moved` boolean not null,
        PRIMARY KEY (piece_id),
        UNIQUE (kind, team, is_moved)
      );

CREATE TABLE position
(
    `position` char(2),
    `game_id`  bigint not null,
    `piece_id` char(3),
    PRIMARY KEY (position),
    FOREIGN KEY (game_id) REFERENCES chess_game(game_id) ON UPDATE CASCADE,
    FOREIGN KEY (piece_id) REFERENCES piece(piece_id) ON UPDATE CASCADE
);
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
