package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 투포인터 알고리즘
public class Main_bj_2003_수들의합2 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		int result = 0;
		
		int start = 0;
		int end = 0;
		
		int sum = 0;
		
		for(int i=0;i<N;i++) {
			while(end < N && sum < M) {
				sum += arr[end];
				end++;
			}
			
			if(sum == M) result++;
			
			sum -= arr[i];
		}
		System.out.println(result);
	}

}
