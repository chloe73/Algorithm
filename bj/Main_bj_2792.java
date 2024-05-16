package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_2792 {
	
	static int N,M;
	static int[] color;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		color = new int[M];
		int right = Integer.MIN_VALUE;
		for(int i=0;i<M;i++) {
			int cnt = Integer.parseInt(br.readLine());
			right = Math.max(right,cnt);
			color[i] = cnt;
		} // input end
		
		int left = 1;
		
		int result = 0;
		while(left <= right) {
			int mid = (left + right) / 2;
			int sum = 0;
			
			for(int i=0;i<M;i++) {
				if(color[i] % mid == 0) {
					sum += color[i] / mid;
				}
				else {
					sum += color[i]/mid + 1;
				}
			}
			
			// 현재 학생 수보다 나눠준 학생수가 큰 경우
			if(sum > N) {
				left = mid + 1;
			}
			else {
				right = mid-1;
				result = mid;
			}
		}
		
		System.out.println(result);
	}

}
