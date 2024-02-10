package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_13397_구간_나누기_2 {
	
	static int N;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		int left = 0;
		int right = -1;
		
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			right = Math.max(right, arr[i]);
		}
		
		while(left < right) {
			int mid = (left + right) / 2;
			
			if(solve(mid) <= m) {
				right = mid;
			}
			else left = mid+1;
		}
		
		System.out.println(right);
	}

	private static int solve(int mid) {
		int ret = 1;
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		for(int i=0;i<N;i++) {
			min = Math.min(min, arr[i]);
			max = Math.max(max, arr[i]);
			
			if(max-min > mid) {
				ret++;
				min = Integer.MAX_VALUE;
				max = Integer.MIN_VALUE;
				i--;
			}
		}
		
		return ret;
	}
}
