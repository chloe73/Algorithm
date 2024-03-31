package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_bj_2631_줄세우기 {
	
	static int N;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		} // input end
		
		int[] dp = new int[N];
		Arrays.fill(dp, 1);
		
		int cnt = 0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<i;j++) {
				// 이전 수 중에서 현재 수보다 작은 숫자들이 있으면 1 증가시킴.
				if(arr[j] < arr[i]) {
					dp[i] = Math.max(dp[i], dp[j]+1);
				}
				cnt = Math.max(dp[i], cnt);
			}
		}
		// 전체 숫자 중에서 최장 증가 부분 수열의 길이를 빼주면 그것이 최소의 인원을 옮기는 것이다.
		System.out.println(N-cnt);
	}

}
