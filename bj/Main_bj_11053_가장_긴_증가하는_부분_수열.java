package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_11053_가장_긴_증가하는_부분_수열 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int[] dp = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		for(int i=0;i<N;i++) {
			dp[i] = 1;
			
			for(int j=0;j<i;j++) {
				// j번째 원소가 현재 i번째 원소보다 작고 i번째 dp가 j번째 dp+1보다 작은 경우
				if(arr[j] < arr[i] && dp[i] < dp[j]+1) {
					dp[i] = dp[j]+1;
				}
			}
		}

		int max = -1;
		for(int i=0;i<N;i++) {
			max = Math.max(max, dp[i]);
		}
		System.out.println(max);
	}

}
