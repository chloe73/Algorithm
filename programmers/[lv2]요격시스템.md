# 프로그래머스 - lv2.요격시스템

[문제 링크](https://school.programmers.co.kr/learn/courses/30/lessons/181188)

---

```java
import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;

        // 끝나는 점을 기준으로 오름차순 정렬
        Arrays.sort(targets, (a,b) -> (a[1]-b[1]));

        int end = targets[0][1];

        for(int[] temp : targets) {
            // System.out.print(temp[0] + ", " + temp[1] + "\n");

            int a = temp[0];
            int b = temp[1];

            if(end <= a) {
                end = b;
                answer++;
            }
        }

        return answer+1;
    }
}
```
