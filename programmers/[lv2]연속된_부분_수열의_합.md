# 프로그래머스 - lv2.연속된 부분 수열의 합

[문제 링크](https://school.programmers.co.kr/learn/courses/30/lessons/178870)

- 투포인터 문제

---

```java
import java.util.*;
import java.io.*;

class A implements Comparable<A> {
    int left, right, size;

    public A(int left, int right) {
        this.left = left;
        this.right = right;
        this.size = right - left;
    }

    public int compareTo(A o) {
        // 부분 수열의 크기가 같다면
        // 길이가 짧은 수열이 여러 개인 경우 앞쪽(시작 인덱스가 작은)에 나오는 수열을 찾습니다.
        if(this.size == o.size) return this.left - o.left;
        // 합이 k인 부분 수열이 여러 개인 경우 길이가 짧은 수열을 찾습니다.
        else return this.size - o.size;
    }
}

class Solution {
    public int[] solution(int[] sequence, int k) {
        int[] answer = new int[2];

        int left = 0;
        int right = 0;
        int sumNum = sequence[0]; // 부분 수열의 총합

        int n = sequence.length;

        PriorityQueue<A> pq = new PriorityQueue<>();

        while(true) {

            if(sumNum == k) { // 부분 수열의 합이 k라면 pq에 추가
                pq.add(new A(left, right));
            }

            if(left == n && right == n) break; // 수열 끝까지 탐색 완료하면 종료

            if(sumNum <= k && right < n) { // 현재 부분수열의 총합이 k보다 작다면 right +1 증가시킨다.
                right++;

                if(right < n) sumNum += sequence[right];
            } else { // 현재 부분수열의 총합이 k보다 크면 left -1 감소시킨다.
                if(left < n) sumNum -= sequence[left];
                left++;
            }
        }

        A result = pq.poll();
        answer[0] = result.left;
        answer[1] = result.right;

        return answer;
    }
}
```
