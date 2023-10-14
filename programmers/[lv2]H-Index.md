# 프로그래머스 - lv2.H-Index

[문제 링크](https://school.programmers.co.kr/learn/courses/30/lessons/42747)

---

```java
import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[] citations) {
        int answer = 0;

        int n = citations.length;

        Arrays.sort(citations);

        int min = citations[0];
        int max = citations[n-1];

        // System.out.println(min+", "+max);

        int h = 0;

        while(h++ <= max) {

            int cnt = 0;

            for(int temp : citations) {
                if(temp >= h) cnt++;
            }

            if(cnt >= h) {
                answer = h;
            }
        }

        return answer;
    }
}
```
