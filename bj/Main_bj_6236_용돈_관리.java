package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_6236_용돈_관리 {
	
	static int N, M, left;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		left = 0;
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
			left = Math.max(left, arr[i]);
		} // input end
		
		solve();

		// 통장에서 인출해야 할 최소 금액 K를 출력한다.
		System.out.println(left);
	}

	private static void solve() {
		
		int right = 1000000000; // 1 ≤ 금액 ≤ 10000
		
		while(left < right) {
			int mid = (left + right) / 2;
			
			int cnt = 1; // 인출 횟수
			int tempMoney = mid;
			
			for(int i=0;i<N;i++) {
				if(tempMoney - arr[i] < 0) {
					// 돈이 부족한 경우, 돈 인출
					tempMoney = mid;
					cnt++;
				}
				
				tempMoney -= arr[i];
			}
			
			if(cnt > M) {
				left = mid+1;
			}
			else right = mid;
		}
	}

}
