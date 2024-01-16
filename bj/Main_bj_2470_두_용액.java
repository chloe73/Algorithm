package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 정렬, 이분탐색, 투포인터
public class Main_bj_2470_두_용액 {
	
	static int N;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		arr = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		Arrays.sort(arr);

		int[] result = new int[N];
		
		int start = 0;
		int end = N-1;
		int min = Integer.MAX_VALUE;
		
		while(start < end) {
			int sum = arr[start] + arr[end];
			
			if(min > Math.abs(sum)) {
				min = Math.abs(sum);
				
				result[0] = arr[start];
				result[1] = arr[end];
				
				if(sum == 0) break;
			}
			
			if(sum < 0) start++;
			else end--;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(result[0]+" "+result[1]);
		System.out.println(sb.toString());
	}

}
