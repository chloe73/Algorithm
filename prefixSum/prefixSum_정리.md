## 📓누적 합(Prefix Sum, Cumulative Sum)

- 누적 합이란, 말 그대로 나열된 수의 누적된 합을 말한다.

- 조금 더 엄밀히 말하면, 수열 An에 대해서, 구간 [1, 1]의 합, 구간 [1, 2]의 합, 구간 [1, 3]의 합, ..., [1, n]의 합을 누적 합이라고 한다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FpikBu%2Fbtq3cyNHvGc%2F0ZjVU7HgkgBGtkvNaa0YyK%2Fimg.png)

## ✅ 누적 합의 사용

- 대표적으로 누적 합을 사용하는 문제는 카운팅 정렬, 구간 합 구하기가 존재한다.

## ✔️ 단순 반복을 이용한 구간 합 구하기

- 일반적으로 구간의 합을 구하는 경우에는 다음과 같이 모든 구간의 값을 더해주는 방법을 생각해볼 수 있다.

```java
import java.util.Scanner;

public class PrefixSum {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] arr2 = {1, 5, 8, 10, 24, 3, 5, 100, 99, 7};
		int a = sc.nextInt();
		int b = sc.nextInt();
		int sum1 = 0;
		int sum2 = 0;
		for(int i = a; i <= b; i++) {
			sum1 += arr[i];
			sum2 += arr2[i];
		}
		System.out.println(sum1);
		System.out.println(sum2);
		sc.close();
	}
}
```

- 이렇게 모든 입력마다 구간합을 일일히 구해주는 경우에는 구간의 길이가 M이라고 하면 매 구간합을 구할 때 마다 O(M)이라는 시간이 걸리게 된다. 즉, N개의 구간 에 대해 구간의 길이가 M인 구간합을 구하는 경우 O(NM)의 시간이 걸리는 것을 알 수 있다.

---

## 💡 위와 같은 코드를 누적합을 사용해 아래 코드처럼 바꿀 수 있다.

- 시간 복잡도를 O(N + M)까지 줄일 수 있다.

```java
import java.util.Scanner;

public class PrefixSum {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int[] arr2 = { 1, 5, 8, 10, 24, 3, 5, 100, 99, 7 };
		int a = sc.nextInt();
		int b = sc.nextInt();

		// 누적 합 구하기
		// 배열의 크기를 + 1하는 이유는, 0번 인덱스 ~ n번 인덱스의 구간합도 구할 수 있게 만들기 위함이다.
		int[] prefix_sum = new int[11];
		int[] prefix_sum2 = new int[11];
		for (int i = 1; i < arr.length; i++) {
			prefix_sum[i] += prefix_sum[i - 1] + arr[i];
			prefix_sum2[i] += prefix_sum2[i - 1] + arr2[i];
		}

		// 구간 합 구하기
		System.out.println(prefix_sum[b] - prefix_sum[a - 1]);
		System.out.println(prefix_sum2[b] - prefix_sum2[a - 1]);
		sc.close();
	}
}
```
