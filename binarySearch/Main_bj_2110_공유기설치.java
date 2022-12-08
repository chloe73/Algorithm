package algo.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2110_공유기설치 {
	
	static int N,C,result;
	static int[] home;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		home = new int[N];
		for(int i=0;i<N;i++) {
			home[i] = Integer.parseInt(br.readLine());
		}
		
		Arrays.sort(home);
		
		int left = 1;
		int right = home[N-1] - home[0];
		result = 0;
		
		while(left <= right) {
			int cnt = 1;
			int mid = (left + right) / 2;
			int now = home[0] + mid;
			
			for(int i=0;i<N;i++) {
				if(now <= home[i]) {
					now = home[i] + mid;
					cnt++;
				}
			}
			
			if(cnt >= C) {
				result = Math.max(result, mid);
				left = mid + 1;
			} else right = mid - 1;
		}
		
		System.out.println(result);
	}

}
