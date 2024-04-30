package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_16401 {
	
	static int M,N;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		Arrays.sort(arr);

		int left = 1;
		int right = arr[N-1];
		int result = 0;
		
		while(left <= right) {
			int mid = (left + right) / 2;
			
			int cnt = 0;
			for(int i=0;i<N;i++) {
				if(arr[i] >= mid) {
					cnt += arr[i]/mid;
				}
			}
			
			if(cnt >= M) {
				if(result < mid)
					result = mid;
				left = mid+1;
			}
			else {
				right = mid-1;
			}
		}
		
		System.out.println(result);
	}

}
