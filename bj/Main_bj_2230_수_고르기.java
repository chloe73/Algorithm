package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2230_수_고르기 {
	
	static int N,M,result;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		} // input end
		
		Arrays.sort(arr);
		
		int left = 0;
		int right = 1;
		
		result = Integer.MAX_VALUE;
		while(right < N) {
			
			int diff = arr[right] - arr[left];
			
			if(diff == M) {
				result = Math.min(result, diff);
				break;
			}
			
			if(diff > M) {
				result = Math.min(result, diff);
				left++;
			}
			else {
				right++;
			}
		}
		System.out.println(result);
	}

}
