package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2805_나무_자르기 {
	
	static int N,M,result;
	static int rest = Integer.MAX_VALUE;
	static int[] namu;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		namu = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			namu[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(namu);
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		int left = 0;
		int right = namu[N-1];
		int mid = 0;
		
		while(left < right) {
			
			mid = (left+right) / 2;
			
			long sum = check(mid);
			
			if(sum < M) {
				// 높이 낮추는 작
				right = mid;
			}
			else {
				left = mid+1;
			}
			
		}
		
		result = left-1;
	}

	private static long check(int mid) {
		
		long cnt = 0;
		
		for(int i : namu) {
			if(i <= mid) continue;
			
			cnt += (i-mid);
		}
			
		return cnt;
	}
}
