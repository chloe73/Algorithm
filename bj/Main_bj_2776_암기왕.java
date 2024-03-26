package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2776_암기왕 {
	
	static int N,M;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(br.readLine());
			
			arr = new int[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			Arrays.sort(arr);
			
			M = Integer.parseInt(br.readLine());

			st = new StringTokenizer(br.readLine());
			for(int i=0;i<M;i++) {
				int input = Integer.parseInt(st.nextToken());
				
				if(solve(input)) {
					sb.append(1+"\n");
				}
				else
					sb.append(0+"\n");
			}
		} // input end
		
		System.out.println(sb.toString());
	}

	private static boolean solve(int input) {
		
		int left = 0;
		int right = N-1;
		
		while(left <= right) {
			int mid = (left + right) / 2;
			
			if(arr[mid] == input) return true;
			
			if(arr[mid] > input) {
				right = mid-1;
			}
			else {
				left = mid+1;
			}
		}
		return false;
	}

}
