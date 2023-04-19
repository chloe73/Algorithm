-- CAR_RENTAL_COMPANY_CAR 테이블에서 '네비게이션' 옵션이 포함된 자동차 리스트를 출력하는 SQL문을 작성해주세요. 결과는 자동차 ID를 기준으로 내림차순 정렬해주세요.
SELECT *
from CAR_RENTAL_COMPANY_CAR
where options like '%네비게이션%'
order by car_id desc;

-- locate를 활용한 방법
SELECT
    car_id, car_type, daily_fee, options
FROM
    car_rental_company_car
WHERE
    locate('네비게이션', options)
ORDER BY
    car_id desc;