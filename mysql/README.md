# **📝 코딩테스트에서 자주 나오는 SQL 문법 정리**

---

### ✅ 날짜 차이 함수

| 함수                              | 내용                                    |
| --------------------------------- | --------------------------------------- |
| DATEDIFF(날짜1, 날짜2)            | 날짜1 - 날짜2 차이를 일수로 반환        |
| TIMESTAMPDIFF(단위, 날짜1, 날짜2) | 날짜1 - 날짜2 차이를 선택한 단위로 반환 |

- MySQL에는 날짜 간의 차이를 가져오는 함수가 크게 두 가지로 나뉜다.
- 두 날짜의 차이를 단순 일수로 반환하는 DATEDIFF()
- 각 단위에 따라 반환하는 TIMESTAMPDIFF가 있습니다.

▣ TIMESTAMPDIFF 단위

| SECOND  | 초   |
| ------- | ---- |
| MINUTE  | 분   |
| HOUR    | 시   |
| DAY     | 일   |
| WEEK    | 주   |
| MONTH   | 월   |
| QUARTER | 분기 |
| YEAR    | 년   |

---

### ✅ ROUND() , TRUNCATE() - 역할

- ROUND(숫자,반올림할 자릿수) - 숫자를 반올림할 자릿수 +1 자릿수에서 반올림

```sql
SELECT ROUND(10.349) 	-- 10
SELECT ROUND(10.349, 1) -- 10.3
SELECT ROUND(10.349, 2) -- 10.35
SELECT ROUND(10.349, -1) -- 10

SELECT ROUND(11.546) 	-- 12
SELECT ROUND(11.546, 1) -- 11.5
SELECT ROUND(11.546, 2) -- 11.54
SELECT ROUND(11.546, -1) -- 10
```

- TRUNCATE(숫자,버릴 자릿수) - 숫자를 버릴 자릿수 아래로 버림
  ※ 반드시 버릴 자릿수를 명시해 주어야 함

```sql
SELECT TRUNCATE(1234.56789 ,1) FROM DUAL;
-- 1234.5

SELECT TRUNCATE(1234.56789 ,4) FROM DUAL;
-- 1234.5678

SELECT TRUNCATE(1234.56789 ,-1) FROM DUAL;
-- 1230

SELECT TRUNCATE(1234.56789 ,-2) FROM DUAL;
-- 1200
```

---

### ✅ CEILING(), FLOOR()

- CEILING 은 소수점 이하를 무조건 올리는 역할을 하는 함수.
  - ROUND와 달리 CEILING은 무조건 정수값으로 출력된다.

```sql
SELECT CEILING(21.35) -- 22
SELECT CEILING(21.9) -- 22
```

- FLOOR 는 소수점 이하를 무조건 버리는 역할을 하는 함수입니다.
  - CEILING과 마찬가지로 정수값으로 출력된다.

```sql
SELECT CEILING(21.35) -- 21
SELECT CEILING(21.9) -- 21
```

---

### ✅ IF문

```sql
if ( 조건문, 참일때 값, 거짓일때 값)
SELECT IF(required, '필수' '선택') AS '필수여부' FROM TABLE
```

---

### ✅ CASE문

```sql
CASE value
	WHEN compare_value THEN '반환 값'
	WHEN compare_value THEN '반환 값'
	ELSE 'WHEN 조건에 해당 안되는 경우 반환 값'
END
```

---
