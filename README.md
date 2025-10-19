# java-calculator-precourse

## 📋 기능 목록

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

- [x] 결과를 출력한다.

---
> 이 아래는 MVP 구현 후, 리팩토링 과정에서 식별한 개선 점을 정리했습니다.

## 🐛 이슈

- [x] 계산 결과가 나오지 않는 문제(`결과 :  ` 만 나옴)
- [x] 빈 값(`""`) 입력 시 `0`을 반환하지 못하는 문제
- [x] 커스텀 구분자 지정 부분이 `//\n`입력 시 식별된 문제들(토큰화 과정에서 발생 추정)
    - [x] `//\n12` 입력 시 `결과 : 12`로 출력됨.
        - 커스텀 구분자 형식(문자열 길이)에 맞지 않으므로 에러를 발생시켜야 함.
    - [x] `//\n12 3` 입력 시 잘못된 에러 메시지(검증 필터링 실패)

---

## ✅ 테스트

- [x] Tokens 테스트 코드 작성하기
- [x] 계산 도메인 테스트 코드 작성하기
    - [x] Calculation
    - [x] CalculationResult
- [x] OutputView 테스트 코드 작성하기

---

## 🛠️ 리팩토링

### 🏗️ 도메인 설계

- [x] Operator를 VO로 리팩토링하기
- [x] 내부 enum 클래스 리팩토링하기
    - [x] Formula
    - [x] CustomDelimiterSection
    - [x] CalculationElement(tokenizing)
- [x] 입력된 식(Raw Input) 도메인에서 커스텀 구분자 관련 책임 리팩토링
- [x] 공통으로 쓰이는 계산 요소 클래스들 vo 패키지로 이동 후 접근 제어자 변경 (package-private → public)

### ✨ 코드 품질

- [x] 사용하지 않는 주석, 메서드 삭제하기
    - [x] Calculation 불필요한 검증 메서드 제거
- [x] 도메인 내부용 매서드 접근 제어자 스코프 수정하기 (package-private → public)
- [x] 코드 가독성 다듬기
- [x] 코드 구조 및 스타일 전반 정리
    - [x] 코드 구조 확인
    - [x] 코드 네이밍 확인(테스트 코드)
    - [x] 생성자 → 정적 팩토리 메서드로 전환
    - [x] 사용하지 않는 주석 / 메서드 / import 제거

### ⚡ 성능 최적화

- [x] (Tokens) `toCharArray()` → `charAt(index)`로 교체 (배열 생성 비용 제거)
- [x] (Tokens, Calculation) `Queue` → `ArrayDeque`로 자료구조 구체화
- [x] Stream 왕복 오버헤드 제거 (Queue(Tokens) → Stream → Queue(Calculation))
    - **초기 설계**: Stream의 메모리 효율성을 기대하여 Stream 기반으로 설계
    - **문제 인식**: 이미 파싱 완료된 데이터를 Queue에 적재한 후 Stream으로 변환하는 방식이라
      lazy evaluation의 이점이 없고 오히려 변환 오버헤드만 발생함을 확인
    - **개선 시도**: Spliterator를 직접 구현하여 진정한 lazy evaluation 시도
    - **최종 결정**: 구현 복잡도와 수정 범위를 고려하여 Collection/Deque를 직접 전달하는 방식으로 단순화
