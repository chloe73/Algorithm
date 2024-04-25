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

# ✅ MYSQL - Date

<img width="546" alt="스크린샷 2024-04-25 오후 3 07 52" src="https://github.com/chloe73/Algorithm/assets/50287759/820e1ecd-b4cd-4ca9-bf2b-76f344b5db80">
<img width="546" alt="스크린샷 2024-04-25 오후 3 08 09" src="https://github.com/chloe73/Algorithm/assets/50287759/ee43f6bd-8f59-4e0c-9c25-6e2e975a76f8">
<img width="546" alt="스크린샷 2024-04-25 오후 3 08 29" src="https://github.com/chloe73/Algorithm/assets/50287759/08f857be-98c3-40ef-9d21-66b5e7fc3a85">

---

### ✔ CEIL(), FLOOR(), ROUND(): 올림/버림/반올림

```mysql
SELECT CEIL(10, 1) ... #소수점 첫째자리에서 올림
SELECT FLOOR(10, 1) ... #소수점 첫째자리에서 버림
SELECT ROUND(10, 1) ... #소수점 첫째자리에서 반올림
```

첫 번째 숫자를 두 번째 숫자가 가리키는 자릿수를 기준으로 올림/버림/반올림

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

### ✅ MySQL CONV() 함수

- 숫자 기반 시스템 10에서 숫자 기반 시스템 2로 숫자 변환:

```sql
SELECT CONV(15, 10, 2);
```

- 숫자 기반 시스템 10에서 숫자 기반 시스템 16으로 숫자 변환:

```sql
SELECT CONV(88, 10, 16);
```

---

### ✅ 문자열 합치기 : CONCAT

- 일반적인 프로그래밍 언어에서는 +를 이용해서 문자열의 합칠 수 있지만, MySQL에서는 CONCAT()함수를 이용하여 문자열을 합친다.

```SQL
SELECT CONCAT('HELLO', ' ', 'THIS IS ', 2021);
```

- CONCAT을 이용하여 데이터에 문자열을 추가로 더 붙여서 표현할 수도 있다.

```SQL
SELECT CONCAT('O-ID: ', OrderID) FROM Orders;
```

- CONCAT_WS(S, {문자1},{문자2})은 여러문자들을 S를 이용해서 연결해준다.

```SQL
SELECT
  CONCAT_WS(' ', FirstName, LastName) AS FullName
FROM Employees;
```

---

### ✅ IFNULL(expression, alt_value)

- 데이터가 NULL인 데이터를 다른 값으로 출력한다.

---

### ✅ IS NULL / IS NOT NULL

- =, <>와 같은 비교 연산자로는 NULL을 조회할 수 없다. 따라서 NULL을 조회할 때에는 IS NULL, IS NOT NULL연산자를 사용해야 한다.

- IS NULL은 NULL인 데이터를, IS NOT NULL은 NULL이 아닌 테이터를 조회한다.

```SQL
IS NULL
SELECT column_names
FROM table_name
WHERE column_name IS NULL;
```

```SQL
# 예시

SELECT ANIMAL_ID, NAME
FROM ANIMAL_INS
WHERE 1=1
AND NAME IS NULL;
```

---

### ✅ COALESCE()

- 목록에서 NULL이 아닌 첫 번째 값을 반환한다.

COALESCE(val1, val2, ...., val_n)

```SQL
# 예시
SELECT COALESCE(NULL, NULL, NULL, 'tistory', NULL, 'passwd');
```

---

### ✅ Mysql의 SUBSTRING() 함수

- Mysql의 SUBSTR() 함수와 동의어로 조회하는 문자열의 일부를 잘라내기로 추출하는 Mysql의 함수입니다. 이는 Mysql 뿐만아니라 업무용 프로그램및 SQL문을 사용하면서 가장 많이 사용하는 함수입니다.

SUBSTRING(문자열 , 시작위치, 길이)
SUBSTRING(문자열 , 시작위치, 길이)

```SQL
SELECT SUBSTRING("WWW.Google.COM", 3);
SELECT SUBSTRING("WWW.Google.COM", 3, 5);
SELECT SUBSTRING("WWW.Google.COM" FROM 5);
SELECT SUBSTRING("WWW.Google.COM" FROM 2 FOR 2);

#W.Google.COM
#W.Goo
#Google.COM
#WW
```

### ✅ Mysql의 LEFT() 함수

- 조회하는 문자열의 일부를 추출하는 Mysql의 함수로써 왼쪽부터 잘라내어 반환하는 함수입니다.

LEFT(문자열, 길이)

```SQL
SELECT LEFT("WWW.Google.COM", 3);
SELECT LEFT("WWW.Google.COM", 5);

#WWW
#WWW.G
```

### ✅ Mysql의 RIGHT() 함수

- 조회하는 문자열의 일부를 추출하는 Mysql의 함수로써 오른쪽부터 잘라내어 반환하는 함수입니다.

RIGHT(문자열, 길이)

```SQL
SELECT RIGHT("WWW.Google.COM", 3);
SELECT RIGHT("WWW.Google.COM", 5);

#COM
#e.COM

```

### ✅ Mysql의 SUBSTRING_INDEX() 함수

- 조회하는 문자열의 일부를 추출하는 Mysql의 함수로써 구분자와 구분자 숫자만큼 잘라내어 반환하는 함수입니다.

SUBSTRING_INDEX(문자열, 구분자, 구분자 Index)

```SQL
SELECT SUBSTRING_INDEX("010-5678-9999", "-" , 2);
SELECT SUBSTRING_INDEX("www.Google.com", "." , 2);

#010-5678
#www.Google

```
