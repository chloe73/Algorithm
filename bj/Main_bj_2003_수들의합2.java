package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 투포인터 알고리즘
public class Main_bj_2003_수들의합2 {
	
	static int N,M;
	static int[] arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		// 연속되는 수들의 합
		
		System.out.println(twoPointer());
		
//		int result = 0;
//		
//		int start = 0;
//		int end = 0;
//		
//		int sum = 0;
//		
//		for(int i=0;i<N;i++) {
//			while(end < N && sum < M) {
//				sum += arr[end];
//				end++;
//			}
//			
//			if(sum == M) result++;
//			
//			sum -= arr[i];
//		}
//		System.out.println(result);
	}

	
	private static int twoPointer() {
		
		int left = 0;
		int right = 0;
		int sum = 0;
		int count = 0; // 경우의 수
		
		while(true) {
			if(sum >= M) sum -= arr[left++];
			
			else if(right == N) break;
			
			else if(sum < M) sum += arr[right++];
			
			if(sum == M) count++;
			
		}
		
		return count;
	}
}
