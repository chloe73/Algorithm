package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1806_부분합 {
	
	static int N,S,result;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		arr = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		result = Integer.MAX_VALUE;
		int start = 0;
		int end = 0;
		int total = 0;
		
		while(start <= N && end <= N) {
			if(total >= S && result > end - start) result = end - start;
			
			if(total < S) total += arr[end++];
			
			else total -= arr[start++];
		}
		
		if(result == Integer.MAX_VALUE) result = 0;
		
		System.out.println(result);
	}

}
