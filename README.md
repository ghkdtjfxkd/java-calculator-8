# java-calculator-precourse

## 기능 목록

### 입력 (Input)

- [x] 사용자에게 입력을 받는다.

### 입력된 식 (Raw Input Domain)

- [x] 입력으로부터 커스텀 구분자 후보를 추출할 수 있다.
- [x] 입력으로부터 실제 계산식을 추출할 수 있다.
- [x] 입력이 커스텀 구분자 형식을 만족하지 않을 경우 예외를 던진다.

### 구분자 (Delimiter Domain)

- [x] 기본 구분자를 정의할 수 있다.
- [x] 커스텀 구분자를 추가할 수 있다.
- [x] 특정 문자열이 구분자인지 확인할 수 있다.
- [x] 잘못된 구분자를 처리할 수 있다.

### 토큰화 (Tokenizing Domain)

- [x] 계산식 문자열을 숫자와 구분자로 분리할 수 있다.

### 계산 (Calculation Domain)

- [x] 토큰 목록을 기반으로 연산을 수행할 수 있다.

### 출력 (Output)

- [x] 결과를 반환한다.

---
> 아래는 MVP 구현 후, 식별한 추가적인 할 일을 정리했습니다.

## 이슈

- [x] 계산 결과가 나오지 않는 문제(`결과 :  ` 만 나옴)
- [x] 빈 값(`""`) 입력 시 `0`을 반환하지 못하는 문제
- [x] 커스텀 구분자 지정 부분이 `//\n`입력 시 식별된 문제들(토큰화 과정에서 발생 추정)
    - [x] `//\n12` 입력 시 `결과 : 12`로 출력됨.
        - 커스텀 구분자 형식(문자열 길이)에 맞지 않음으로 에러를 발생시켜야 함.
    - [x] `//\n12 3` 입력 시 잘못된 에러 메시지(검증 필터링 실패)

## 테스트

- [x] Tokens 테스트 코드 작성하기
- [x] 계산 도메인 테스트 코드 작성하기
    - [x] Calculation
    - [x] CalculationResult
- [x] OutputView 테스트 코드 작성하기

## 리팩토링

- [x] Operator를 VO로 리팩토링하기
- [x] 내부 enum 클래스 리팩토링하기
    - [x] Formula
    - [x] CustomDelimiterSection
    - [x] CalculationElement(tokenizing)
- [x] 입력된 식(Raw Input) 도메인에서 커스텀 구분자 관련 책임 리팩토링
- [x] 사용하지 않는 주석, 메서드 삭제하기
    - [x] Calculation
- [x] 코드 가독성 다듬기
- [x] 도메인 내부용 매서드 접근 제어자 스코프 수정하기(public -> (default))
    - [x] 공통으로 쓰이는 계산 요소 클래스들 vo 패키지로 이동 후 변경 ((default) -> public)
- [x] 코드 구조 및 스타일 전반 정리
    - [x] 코드 구조 확인
    - [x] 코드 네이밍 확인(테스트 코드)
    - [x] 생성자 사용 매서드 팩터리 사용 메서드로 전환
    - [x] 사용하지 않는 주석 / 메서드 / import 제거
- [ ] tokenizing 과 calculation 최적화
    - [ ] Stream 왕복 오버헤드 제거 (Queue(Tokens) -> Stream -> Queue(Calculation))
    - [x] charArray() -> charAt(index)로 교체(생성 비용 제거)